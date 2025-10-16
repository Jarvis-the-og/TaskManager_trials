// TaskManager.java
// A Personal Task Manager using OOP and Swing GUI
// Author: Rishabh Dev
// Date: October 2025

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// ----- MODEL -----
class Task {
    private String title;
    private boolean isDone;

    public Task(String title) {
        this.title = title;
        this.isDone = false;
    }

    public String getTitle() {
        return title;
    }

    public boolean isDone() {
        return isDone;
    }

    public void markDone() {
        isDone = true;
    }

    public void markUndone() {
        isDone = false;
    }

    @Override
    public String toString() {
        return (isDone ? "✔ " : "✗ ") + title;
    }
}

// ----- VIEW + CONTROLLER -----
public class TaskManager extends JFrame implements ActionListener {
    private DefaultListModel<Task> taskListModel;
    private JList<Task> taskList;
    private JTextField taskInput;
    private JButton addButton, deleteButton, doneButton, resetButton;

    public TaskManager() {
        // Frame setup
        setTitle("🧠 Personal Task Manager");
        setSize(420, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Heading
        JLabel title = new JLabel("Personal Task Manager", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        // Center List
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setFont(new Font("Consolas", Font.PLAIN, 16));
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(taskList);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        taskInput = new JTextField();
        bottomPanel.add(taskInput, BorderLayout.CENTER);

        addButton = new JButton("Add");
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.addActionListener(this);
        bottomPanel.add(addButton, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        // Side buttons panel
        JPanel sidePanel = new JPanel(new GridLayout(3, 1, 10, 10));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        doneButton = new JButton("Mark Done");
        deleteButton = new JButton("Delete");
        resetButton = new JButton("Reset All");

        for (JButton btn : new JButton[]{doneButton, deleteButton, resetButton}) {
            btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
            btn.addActionListener(this);
            sidePanel.add(btn);
        }

        add(sidePanel, BorderLayout.EAST);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == addButton) {
            String text = taskInput.getText().trim();
            if (!text.isEmpty()) {
                taskListModel.addElement(new Task(text));
                taskInput.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Enter a task first!");
            }
        } 
        else if (src == deleteButton) {
            int index = taskList.getSelectedIndex();
            if (index != -1) {
                taskListModel.remove(index);
            } else {
                JOptionPane.showMessageDialog(this, "Select a task to delete!");
            }
        } 
        else if (src == doneButton) {
            int index = taskList.getSelectedIndex();
            if (index != -1) {
                Task t = taskListModel.get(index);
                t.markDone();
                taskList.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Select a task to mark as done!");
            }
        } 
        else if (src == resetButton) {
            int confirm = JOptionPane.showConfirmDialog(this, "Clear all tasks?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                taskListModel.clear();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TaskManager::new);
    }
}

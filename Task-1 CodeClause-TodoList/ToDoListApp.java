import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ToDoListApp {
    private static ArrayList<String> tasks = new ArrayList<>();
    private static JFrame frame;
    private static JTextField taskField;
    private static JList<String> taskList;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        frame = new JFrame("ToDo List App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(800, 600)); 

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JLabel taskLabel = new JLabel("Enter a new task: ");
        topPanel.add(taskLabel);

        taskField = new JTextField(20);
        topPanel.add(taskField);

        JButton addButton = createStyledButton("Add Task", new Color(186,18,145));
        addButton.addActionListener(e -> addTask());
        topPanel.add(addButton);

        JButton editButton = createStyledButton("Edit Task", new Color(147, 112, 216));
        editButton.addActionListener(e -> editTask());
        topPanel.add(editButton);

        JButton removeButton = createStyledButton("Remove Task", new Color(5, 150, 105));
        removeButton.addActionListener(e -> removeTask());
        topPanel.add(removeButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        taskList = new JList<>(new DefaultListModel<>());
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(taskList);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton clearButton = createStyledButton("Clear All Tasks", new Color(178, 34, 34));
        clearButton.addActionListener(e -> clearTasks());
        mainPanel.add(clearButton, BorderLayout.SOUTH);

        frame.setContentPane(mainPanel);
        frame.pack(); 
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        updateTaskArea();
    }

    private static JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private static void addTask() {
        String task = taskField.getText().trim();
        if (!task.isEmpty()) {
            tasks.add(task);
            renumberTasks();
        }
    }

    private static void editTask() {
        int selectedTaskIndex = taskList.getSelectedIndex();
        if (selectedTaskIndex != -1) {
            String editedTask = JOptionPane.showInputDialog(frame, "Edit Task:", tasks.get(selectedTaskIndex));
            if (editedTask != null) {
                tasks.set(selectedTaskIndex, editedTask);
                renumberTasks();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Select a task to edit.");
        }
    }

    private static void removeTask() {
        int selectedTaskIndex = taskList.getSelectedIndex();
        if (selectedTaskIndex != -1) {
            int choice = JOptionPane.showConfirmDialog(frame, "Are you sure you want to remove this task?", "Confirm Removal", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                tasks.remove(selectedTaskIndex);
                renumberTasks();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Select a task to remove.");
        }
    }

    private static void clearTasks() {
        tasks.clear();
        updateTaskArea();
    }

    private static void updateTaskArea() {
        DefaultListModel<String> listModel = (DefaultListModel<String>) taskList.getModel();
        listModel.clear();

        for (int i = 0; i < tasks.size(); i++) {
            listModel.addElement((i + 1) + ". " + tasks.get(i));
        }
    }

    private static void renumberTasks() {
        DefaultListModel<String> listModel = (DefaultListModel<String>) taskList.getModel();
        listModel.clear();

        for (int i = 0; i < tasks.size(); i++) {
            listModel.addElement((i + 1) + ". " + tasks.get(i));
        }
    }
}

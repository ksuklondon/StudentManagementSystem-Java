package com.student.management;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class StudentManagementGUI {
    private static final StudentManagerImpl studentManager = new StudentManagerImpl();

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Student Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();
        JLabel gradeLabel = new JLabel("Grade:");
        JTextField gradeField = new JTextField();
        JLabel idLabel = new JLabel("Student ID:");
        JTextField idField = new JTextField();

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(ageLabel);
        inputPanel.add(ageField);
        inputPanel.add(gradeLabel);
        inputPanel.add(gradeField);
        inputPanel.add(idLabel);
        inputPanel.add(idField);

        // Button panel with uniform button size
        JPanel buttonPanel = new JPanel(new GridLayout(4, 3, 10, 10));
        JButton addButton = new JButton("Add Student");
        JButton removeButton = new JButton("Remove Student");
        JButton updateButton = new JButton("Update Student");
        JButton displayButton = new JButton("Display All Students");
        JButton averageButton = new JButton("Calculate Average");
        JButton clearButton = new JButton("Clear Data");
        JButton findButton = new JButton("Find Student");
        JButton filterButton = new JButton("Filter Students");
        JButton clearAllButton = new JButton("Clear All Students");
        JButton exportButton = new JButton("Export to CSV");

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(averageButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(findButton);
        buttonPanel.add(filterButton);
        buttonPanel.add(clearAllButton);
        buttonPanel.add(exportButton);

        // Output panel
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setPreferredSize(new Dimension(700, 200));

        // Add components to main panel
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        frame.add(mainPanel);

        // Helper method to clear fields
        Runnable clearFields = () -> {
            nameField.setText("");
            ageField.setText("");
            gradeField.setText("");
            idField.setText("");
        };

        // Button functionalities
        addButton.addActionListener(_ -> {
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String gradeText = gradeField.getText().trim();
            String id = idField.getText().trim();

            try {
                int age = Integer.parseInt(ageText);
                double grade = Double.parseDouble(gradeText);

                if (name.isEmpty() || id.isEmpty()) {
                    outputArea.append("Error: Name and ID cannot be empty.\n");
                } else {
                    try {
                        Student student = new Student(name, age, grade, id);
                        studentManager.addStudent(student);
                        outputArea.append("Student added successfully: " + student.displayInfo() + "\n");
                    } catch (IllegalArgumentException ex) {
                        outputArea.append("Error: " + ex.getMessage() + "\n");
                    }
                }
            } catch (NumberFormatException ex) {
                outputArea.append("Error: Age and grade must be valid numbers.\n");
            }

            clearFields.run();
        });

        removeButton.addActionListener(_ -> {
            String id = idField.getText().trim();

            if (id.isEmpty()) {
                outputArea.append("Error: Student ID cannot be empty.\n");
            } else {
                try {
                    studentManager.removeStudent(id);
                    outputArea.append("Student with ID " + id + " removed successfully.\n");
                } catch (IllegalArgumentException ex) {
                    outputArea.append("Error: " + ex.getMessage() + "\n");
                }
            }
            idField.setText(""); // Clear the ID field after removing
        });

        updateButton.addActionListener(_ -> {
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String gradeText = gradeField.getText().trim();
            String id = idField.getText().trim();

            try {
                int age = Integer.parseInt(ageText);
                double grade = Double.parseDouble(gradeText);

                if (name.isEmpty() || id.isEmpty()) {
                    outputArea.append("Error: Name and ID cannot be empty.\n");
                } else {
                    try {
                        Student updatedStudent = new Student(name, age, grade, id);
                        studentManager.updateStudent(id, updatedStudent);
                        outputArea.append("Student with ID " + id + " updated successfully.\n");
                    } catch (IllegalArgumentException ex) {
                        outputArea.append("Error: " + ex.getMessage() + "\n");
                    }
                }
            } catch (NumberFormatException ex) {
                outputArea.append("Error: Age and grade must be valid numbers.\n");
            }

            clearFields.run();
        });

        findButton.addActionListener(_ -> {
            String id = idField.getText().trim();

            if (id.isEmpty()) {
                outputArea.append("Error: Student ID cannot be empty.\n");
            } else {
                try {
                    Student student = studentManager.findStudent(id);
                    if (student != null) {
                        outputArea.append("Student found: " + student.displayInfo() + "\n");
                    } else {
                        outputArea.append("Student with ID " + id + " not found.\n");
                    }
                } catch (IllegalArgumentException ex) {
                    outputArea.append("Error: " + ex.getMessage() + "\n");
                }
            }
            idField.setText(""); // Clear the ID field after search
        });

        displayButton.addActionListener(_ -> {
            List<Student> students = studentManager.displayAllStudents();
            outputArea.append("--- List of Students ---\n");
            for (Student student : students) {
                outputArea.append(student.displayInfo() + "\n");
            }
        });

        exportButton.addActionListener(_ -> {
            List<Student> students = studentManager.displayAllStudents();
            try (FileWriter writer = new FileWriter("students.csv")) {
                writer.write("Name,Age,Grade,ID\n");
                for (Student student : students) {
                    writer.write(student.getName() + "," + student.getAge() + "," + student.getGrade() + "," + student.getStudentID() + "\n");
                }
                outputArea.append("Students exported to students.csv\n");
            } catch (IOException ex) {
                outputArea.append("Error exporting to CSV: " + ex.getMessage() + "\n");
            }
        });

        filterButton.addActionListener(_ -> {
            String gradeText = gradeField.getText().trim();

            try {
                double minGrade = Double.parseDouble(gradeText);
                List<Student> filteredStudents = studentManager.filterStudentsByGrade(minGrade);
                outputArea.append("--- Students with Grade >= " + minGrade + " ---\n");
                for (Student student : filteredStudents) {
                    outputArea.append(student.displayInfo() + "\n");
                }
            } catch (NumberFormatException ex) {
                outputArea.append("Error: Grade must be a valid number.\n");
            }

            gradeField.setText(""); // Clear the grade field after filtering
        });

        averageButton.addActionListener(_ -> {
            double average = studentManager.calculateAverageGrade();
            outputArea.append("Average grade of all students: " + average + "\n");
        });

        clearAllButton.addActionListener(_ -> {
            studentManager.clearStudentList();
            outputArea.append("All students have been cleared from the database.\n");
        });

        clearButton.addActionListener(_ -> {
            clearFields.run();
            outputArea.setText("");
        });

        frame.setVisible(true);
    }
}






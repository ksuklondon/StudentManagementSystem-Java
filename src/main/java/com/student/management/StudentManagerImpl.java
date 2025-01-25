package com.student.management;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentManagerImpl implements StudentManager {

    @Override
    public void addStudent(Student student) {
        validateStudent(student);
        try {
            executeUpdate(
                    "INSERT INTO students (studentID, name, age, grade) VALUES (?, ?, ?, ?)",
                    ps -> {
                        ps.setString(1, student.getStudentID());
                        ps.setString(2, student.getName());
                        ps.setInt(3, student.getAge());
                        ps.setDouble(4, student.getGrade());
                    }
            );
            System.out.println("Student added to database: " + student.getName());
        } catch (RuntimeException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                throw new IllegalArgumentException("Student with ID " + student.getStudentID() + " already exists.");
            }
            throw e;
        }
    }


    @Override
    public void removeStudent(String studentID) {
        if (studentID == null || studentID.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty.");
        }
        int rowsAffected = executeUpdate(
                "DELETE FROM students WHERE studentID = ?",
                ps -> ps.setString(1, studentID)
        );
        if (rowsAffected == 0) {
            throw new IllegalArgumentException("Student with ID " + studentID + " not found.");
        }
        System.out.println("Student with ID " + studentID + " has been removed.");
    }

    @Override
    public void updateStudent(String studentID, Student updatedStudent) {
        validateStudent(updatedStudent);
        int rowsAffected = executeUpdate(
                "UPDATE students SET name = ?, age = ?, grade = ? WHERE studentID = ?",
                ps -> {
                    ps.setString(1, updatedStudent.getName());
                    ps.setInt(2, updatedStudent.getAge());
                    ps.setDouble(3, updatedStudent.getGrade());
                    ps.setString(4, studentID);
                }
        );
        if (rowsAffected == 0) {
            throw new IllegalArgumentException("Student with ID " + studentID + " not found.");
        }
        System.out.println("Student with ID " + studentID + " has been updated.");
    }

    @Override
    public ArrayList<Student> displayAllStudents() {
        return executeQuery(
                "SELECT * FROM students",
                rs -> {
                    ArrayList<Student> students = new ArrayList<>();
                    while (rs.next()) {
                        students.add(createStudentFromResultSet(rs));
                    }
                    return students;
                }
        );
    }

    @Override
    public double calculateAverageGrade() {
        return executeQuery(
                "SELECT AVG(grade) AS averageGrade FROM students",
                rs -> rs.next() ? rs.getDouble("averageGrade") : 0.0
        );
    }

    @Override
    public Student findStudent(String studentID) {
        if (studentID == null || studentID.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty.");
        }
        return executeQuery(
                "SELECT * FROM students WHERE studentID = ?",
                ps -> ps.setString(1, studentID),
                rs -> rs.next() ? createStudentFromResultSet(rs) : null
        );
    }

    @Override
    public List<Student> filterStudentsByGrade(double minGrade) {
        if (minGrade < 0.0 || minGrade > 100.0) {
            throw new IllegalArgumentException("Grade must be between 0.0 and 100.0.");
        }
        return executeQuery(
                "SELECT * FROM students WHERE grade >= ?",
                ps -> ps.setDouble(1, minGrade),
                rs -> {
                    List<Student> students = new ArrayList<>();
                    while (rs.next()) {
                        students.add(createStudentFromResultSet(rs));
                    }
                    return students;
                }
        );
    }

    @Override
    public void clearStudentList() {
        executeUpdate(
                "DELETE FROM students",
                _ -> {} // No parameters needed
        );
        System.out.println("All students have been cleared from the database.");
    }

    private void validateStudent(Student student) {
        if (student.getName() == null || student.getName().isBlank()) {
            throw new IllegalArgumentException("Student name cannot be null or empty.");
        }
        if (student.getAge() <= 0) {
            throw new IllegalArgumentException("Age must be greater than 0.");
        }
        if (student.getGrade() < 0.0 || student.getGrade() > 100.0) {
            throw new IllegalArgumentException("Grade must be between 0.0 and 100.0.");
        }
        if (student.getStudentID() == null || student.getStudentID().isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty.");
        }
    }

    private Student createStudentFromResultSet(ResultSet rs) throws SQLException {
        String studentID = rs.getString("studentID");
        String name = rs.getString("name");
        int age = rs.getInt("age");
        double grade = rs.getDouble("grade");
        return new Student(name, age, grade, studentID);
    }

    private int executeUpdate(String sql, UpdateExecutor executor) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            if (executor != null) executor.execute(ps);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("SQL Error: " + e.getMessage());
        }
    }

    private <T> T executeQuery(String sql, ResultSetExtractor<T> extractor) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return extractor.extract(rs);
        } catch (SQLException e) {
            throw new RuntimeException("SQL Error: " + e.getMessage());
        }
    }

    private <T> T executeQuery(String sql, UpdateExecutor psExecutor, ResultSetExtractor<T> rsExtractor) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            psExecutor.execute(ps);
            try (ResultSet rs = ps.executeQuery()) {
                return rsExtractor.extract(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("SQL Error: " + e.getMessage());
        }
    }

    @FunctionalInterface
    private interface UpdateExecutor {
        void execute(PreparedStatement ps) throws SQLException;
    }

    @FunctionalInterface
    private interface ResultSetExtractor<T> {
        T extract(ResultSet rs) throws SQLException;
    }
}




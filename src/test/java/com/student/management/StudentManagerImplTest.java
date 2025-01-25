package com.student.management;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentManagerImplTest {
    private StudentManagerImpl manager;

    @BeforeEach
    public void setUp() {
        manager = new StudentManagerImpl();
        DatabaseManager.initializeDatabase();
        manager.clearStudentList();
    }

    @SuppressWarnings("SequencedCollectionMethodCanBeUsed")
    @Test
    public void testAddStudent() {
        Student student = new Student("Jan Kowalski", 21, 85.5, "S123");
        manager.addStudent(student);

        List<Student> students = manager.displayAllStudents();
        assertEquals(1, students.size());
        assertEquals("Jan Kowalski", students.get(0).getName());
    }

    @Test
    public void testRemoveStudent() {
        Student student = new Student("Jan Kowalski", 21, 85.5, "S123");
        manager.addStudent(student);

        manager.removeStudent("S123");

        List<Student> students = manager.displayAllStudents();
        assertEquals(0, students.size());
    }

    @SuppressWarnings("SequencedCollectionMethodCanBeUsed")
    @Test
    public void testUpdateStudent() {
        Student student = new Student("Jan Kowalski", 21, 85.5, "S123");
        manager.addStudent(student);

        Student updatedStudent = new Student("Jurek Kowalski", 22, 88.0, "S123");
        manager.updateStudent("S123", updatedStudent);

        List<Student> students = manager.displayAllStudents();
        assertEquals(1, students.size());
        assertEquals("Jurek Kowalski", students.get(0).getName());
    }

    @Test
    public void testValidationExceptionForAge() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Student("Jan", 0, 85.5, "S123"));
        assertEquals("Age must be greater than 0.", exception.getMessage());
    }

    @Test
    public void testValidationExceptionForGrade() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Student("Jan", 21, 150.0, "S123"));
        assertEquals("Grade must be between 0.0 and 100.0.", exception.getMessage());
    }

    @Test
    public void testCalculateAverageGrade() {
        Student student1 = new Student("Jan Kowalski", 21, 85.5, "S123");
        Student student2 = new Student("Anna Nowak", 23, 90.0, "S456");

        manager.addStudent(student1);
        manager.addStudent(student2);

        double average = manager.calculateAverageGrade();
        assertEquals(87.75, average, 0.01);
    }

    @Test
    public void testAddStudentWithExistingID() {
        Student student1 = new Student("Jan Kowalski", 21, 85.5, "S123");
        Student student2 = new Student("Anna Nowak", 23, 90.0, "S123");

        manager.addStudent(student1);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> manager.addStudent(student2));
        assertTrue(exception.getMessage().contains("already exists"));
    }

}






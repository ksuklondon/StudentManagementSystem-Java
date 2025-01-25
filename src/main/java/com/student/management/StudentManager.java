package com.student.management;

import java.util.ArrayList;
import java.util.List;

public interface StudentManager {
    void addStudent(Student student);
    void removeStudent(String studentID);
    void updateStudent(String studentID, Student updatedStudent);
    ArrayList<Student> displayAllStudents();
    double calculateAverageGrade();
    Student findStudent(String studentID);
    List<Student> filterStudentsByGrade(double minimumGrade);
    void clearStudentList();
}

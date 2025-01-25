package com.student.management;

public class Student {
    private String name; // Imię studenta
    private int age; // Wiek studenta (musi być liczbą dodatnią)
    private double grade; // Ocena studenta (w zakresie od 0.0 do 100.0)
    private String studentID; // Unikalny identyfikator studenta

    // Konstruktor
    public Student(String name, int age, double grade, String studentID) {
        setName(name);
        setAge(age);
        setGrade(grade);
        setStudentID(studentID);
    }

    // Getter i setter dla pola 'name'
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        this.name = name;
    }

    // Getter i setter dla pola 'age'
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age <= 0) { // Wiek musi być liczbą dodatnią
            throw new IllegalArgumentException("Age must be greater than 0.");
        }
        this.age = age;
    }

    // Getter i setter dla pola 'grade'
    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        if (grade < 0.0 || grade > 100.0) { // Ocena musi być w zakresie 0.0 - 100.0
            throw new IllegalArgumentException("Grade must be between 0.0 and 100.0.");
        }
        this.grade = grade;
    }

    // Getter i setter dla pola 'studentID'
    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        if (studentID == null || studentID.isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty.");
        }
        this.studentID = studentID;
    }

    public String displayInfo() {
        return "Student Info -> Name: " + name + ", Age: " + age + ", Grade: " + grade + ", ID: " + studentID;
    }

    @Override
    public String toString() {
        return displayInfo();
    }
}
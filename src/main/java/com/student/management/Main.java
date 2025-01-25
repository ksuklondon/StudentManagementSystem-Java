package com.student.management;

public class Main {
    public static void main(String[] args) {
        // Inicjalizacja bazy danych
        DatabaseManager.initializeDatabase();
        System.out.println("Baza danych została zainicjalizowana.");

        // Utworzenie instancji menedżera studentów
        StudentManager manager = new StudentManagerImpl();

        // Dodawanie studentów
        manager.addStudent(new Student("Jan Kowalski", 21, 85.5, "S123"));
        manager.addStudent(new Student("Anna Nowak", 23, 90.0, "S456"));

        // Wyświetlanie studentów
        System.out.println("\n--- Lista studentów ---");
        for (Student student : manager.displayAllStudents()) {
            System.out.println(student); // Użycie toString()
        }

        // Aktualizacja danych studenta
        manager.updateStudent("S123", new Student("Jurek Kowalski", 22, 88.0, "S123"));

        // Wyświetlanie po aktualizacji
        System.out.println("\n--- Lista studentów po aktualizacji ---");
        for (Student student : manager.displayAllStudents()) {
            System.out.println(student); // Użycie toString()
        }

        // Obliczanie średniej ocen
        System.out.println("Średnia ocen: " + manager.calculateAverageGrade());

        // Usunięcie studenta
        manager.removeStudent("S456");

        // Wyświetlanie po usunięciu
        System.out.println("\n--- Lista studentów po usunięciu ---");
        for (Student student : manager.displayAllStudents()) {
            System.out.println(student); // Użycie toString()
        }
    }
}


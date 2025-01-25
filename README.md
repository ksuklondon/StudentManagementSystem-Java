# Student Management System (SMS)

## Opis projektu

Student Management System (SMS) to aplikacja napisana w języku Java, która umożliwia zarządzanie danymi studentów za pomocą graficznego interfejsu użytkownika (GUI) oraz integracji z bazą danych SQLite.

Funkcje aplikacji obejmują:
- Dodawanie nowych studentów.
- Usuwanie studentów.
- Aktualizowanie danych studentów.
- Wyświetlanie wszystkich studentów.
- Obliczanie średniej ocen.
- Eksportowanie danych do pliku CSV.

---

## Wymagania systemowe

- Java Development Kit (JDK) 8 lub nowszy.
- IntelliJ IDEA lub inne środowisko programistyczne z obsługą Javy.
- SQLite.

---

## Jak uruchomić projekt?

### 1. Klonowanie repozytorium (jeśli dotyczy)
Jeśli projekt znajduje się w systemie kontroli wersji (np. Git):
```bash
git clone <url-repozytorium>
cd StudentManagementSystem
```

### 2. Konfiguracja środowiska w IntelliJ IDEA
1. Otwórz IntelliJ IDEA.
2. Wybierz opcję **Open** i wskaż folder projektu `StudentManagementSystem`.
3. Upewnij się, że SDK jest poprawnie skonfigurowane:
    - **File > Project Structure > SDK**.
    - Wybierz JDK 8 lub nowszy.

### 3. Uruchomienie aplikacji
1. W IntelliJ IDEA przejdź do klasy `StudentManagementGUI`.
2. Kliknij ikonę uruchamiania (zielona strzałka obok metody `main`).
3. Aplikacja uruchomi się z graficznym interfejsem użytkownika.

---

## Struktura projektu

```
StudentManagementSystem/
│
├── src/
│   ├── main/
│   │   ├── java/com/student/management/
│   │   │   ├── DatabaseManager.java
│   │   │   ├── Main.java
│   │   │   ├── Student.java
│   │   │   ├── StudentManagementGUI.java
│   │   │   ├── StudentManager.java
│   │   │   └── StudentManagerImpl.java
│   └── resources/
│       └── student_management.db
│
├── test/
│   ├── java/com/student/management/
│   │   └── StudentManagerImplTest.java
│   └── resources/
│
├── .gitignore
├── pom.xml
└── README.md
```

---

## Instrukcje konfiguracji bazy danych

1. Baza danych SQLite (`student_management.db`) jest automatycznie tworzona w folderze `resources` podczas pierwszego uruchomienia aplikacji.
2. Tabela `students` zostanie automatycznie wygenerowana przez klasę `DatabaseManager`. Struktura tabeli:

```sql
CREATE TABLE IF NOT EXISTS students (
    studentID TEXT PRIMARY KEY,
    name TEXT NOT NULL,
    age INTEGER NOT NULL,
    grade REAL NOT NULL
);
```

---

## Funkcjonalności aplikacji

### Przyciski w GUI:
- **Add Student**: Dodaje nowego studenta.
- **Remove Student**: Usuwa studenta na podstawie ID.
- **Update Student**: Aktualizuje dane studenta.
- **Display All Students**: Wyświetla listę wszystkich studentów.
- **Calculate Average**: Oblicza i wyświetla średnią ocen.
- **Find Student**: Wyszukuje studenta na podstawie ID.
- **Filter Students**: Filtruje studentów według minimalnej oceny.
- **Clear All Students**: Usuwa wszystkich studentów z bazy danych.
- **Export to CSV**: Eksportuje dane do pliku CSV.

---

## Testowanie

Testy jednostkowe dla klasy `StudentManagerImpl` znajdują się w pliku `StudentManagerImplTest.java`.
Aby je uruchomić:
1. Otwórz IntelliJ IDEA.
2. Kliknij prawym przyciskiem na pliku `StudentManagerImplTest.java`.
3. Wybierz **Run 'StudentManagerImplTest'**.

---

## Autor

Imię i nazwisko: Kalina Staniszewska
Data: 08.01.2025

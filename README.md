# University Course Registration System

## Requirements
- Java JDK 17 or later
- VS Code
- Java Extension Pack for VS Code (recommended)

## Run in VS Code

Open this folder in VS Code.

Open the VS Code terminal and run:

```bash
javac *.java
java Main
```

Or open `Main.java` and click the Run button in VS Code.

## Sample Student Login

Aarav:
- Email: aarav@university.com
- Password: 1234

Rahul:
- Email: rahul@university.com
- Password: 1234

Arushi:
- Email: arushi@university.com
- Password: 1234

## Sample Professor Login

Dr. Sharma:
- Email: sharma@university.com
- Password: 1234

Dr. Patel:
- Email: patel@university.com
- Password: 1234

## Administrator Login

- Email: admin@university.com
- Password: admin123

## OOP Concepts Used

1. Class - Student, Professor, Administrator, Course, Complaint
2. Encapsulation - private fields with getters/setters
3. Inheritance - Student, Professor and Administrator extend User
4. Abstraction - User is an abstract class
5. Interface - Authenticatable
6. Polymorphism - overridden displayMenu() method
7. Method overriding - each role implements its own menu

## Important
This project intentionally uses in-memory ArrayLists only.
It does not use a database or external library.
Data is reset when the program is restarted.

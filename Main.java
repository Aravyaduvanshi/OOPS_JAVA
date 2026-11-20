import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        createSampleData();

        while (true) {
            System.out.println("\n==========================================");
            System.out.println(" UNIVERSITY COURSE REGISTRATION SYSTEM");
            System.out.println("==========================================");
            System.out.println("1. Enter Application");
            System.out.println("2. Exit");

            int choice = readInt("Enter choice: ");

            if (choice == 1) {
                loginMenu();
            } else if (choice == 2) {
                System.out.println("Thank you for using the system.");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }

        sc.close();
    }

    static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    static void loginMenu() {
        while (true) {
            System.out.println("\n========== LOGIN ==========");
            System.out.println("1. Student");
            System.out.println("2. Professor");
            System.out.println("3. Administrator");
            System.out.println("4. Back");

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1: studentLogin(); break;
                case 2: professorLogin(); break;
                case 3: adminLogin(); break;
                case 4: return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    static void studentLogin() {
        System.out.println("\n===== STUDENT =====");
        System.out.println("1. Login");
        System.out.println("2. Sign Up");

        int choice = readInt("Enter choice: ");

        if (choice == 1) {
            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Password: ");
            String password = sc.nextLine();

            for (Student student : Data.students) {
                if (student.login(email, password)) {
                    System.out.println("Login successful.");
                    studentMenu(student);
                    return;
                }
            }

            System.out.println("Invalid email or password.");

        } else if (choice == 2) {
            studentSignUp();
        } else {
            System.out.println("Invalid choice.");
        }
    }

    static void studentSignUp() {
        System.out.println("\n===== STUDENT SIGN UP =====");

        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter email: ");
        String email = sc.nextLine();

        for (Student student : Data.students) {
            if (student.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Email already exists.");
                return;
            }
        }

        System.out.print("Create password: ");
        String password = sc.nextLine();

        int id = 101;
        for (Student student : Data.students) {
            if (student.getId() >= id) {
                id = student.getId() + 1;
            }
        }

        Data.students.add(new Student(id, name, email, password));

        System.out.println("Student registered successfully.");
        System.out.println("Your Student ID is: " + id);
    }

    static void studentMenu(Student student) {
        while (true) {
            student.displayMenu();

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    student.viewAvailableCourses();
                    break;

                case 2:
                    int semester = readInt("Enter semester: ");

                    if (semester != student.getCurrentSemester()) {
                        System.out.println("You can register only for your current semester.");
                        break;
                    }

                    student.viewAvailableCourses();
                    System.out.print("Enter course code: ");
                    student.registerCourse(sc.nextLine());
                    break;

                case 3:
                    student.viewSchedule();
                    break;

                case 4:
                    student.displayAcademicProgress();
                    break;

                case 5:
                    System.out.print("Enter course code to drop: ");
                    student.dropCourse(sc.nextLine());
                    break;

                case 6:
                    System.out.print("Enter complaint: ");
                    student.submitComplaint(sc.nextLine());
                    break;

                case 7:
                    student.viewComplaints();
                    break;

                case 8:
                    System.out.println("Logged out.");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    static void professorLogin() {
        System.out.println("\n===== PROFESSOR =====");
        System.out.println("1. Login");
        System.out.println("2. Sign Up");

        int choice = readInt("Enter choice: ");

        if (choice == 1) {
            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Password: ");
            String password = sc.nextLine();

            for (Professor professor : Data.professors) {
                if (professor.login(email, password)) {
                    System.out.println("Login successful.");
                    professorMenu(professor);
                    return;
                }
            }

            System.out.println("Invalid email or password.");

        } else if (choice == 2) {
            professorSignUp();
        } else {
            System.out.println("Invalid choice.");
        }
    }

    static void professorSignUp() {
        System.out.println("\n===== PROFESSOR SIGN UP =====");

        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter email: ");
        String email = sc.nextLine();

        for (Professor professor : Data.professors) {
            if (professor.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Email already exists.");
                return;
            }
        }

        System.out.print("Create password: ");
        String password = sc.nextLine();

        System.out.print("Enter specialization: ");
        String specialization = sc.nextLine();

        int id = 201;
        for (Professor professor : Data.professors) {
            if (professor.getId() >= id) {
                id = professor.getId() + 1;
            }
        }

        Data.professors.add(
                new Professor(id, name, email, password, specialization)
        );

        System.out.println("Professor registered successfully.");
        System.out.println("Professor ID: " + id);
    }

    static void professorMenu(Professor professor) {
        while (true) {
            professor.displayMenu();

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    professor.viewMyCourses();
                    break;

                case 2:
                    professor.manageCourse(sc);
                    break;

                case 3:
                    professor.viewEnrolledStudents();
                    break;

                case 4:
                    System.out.println("Logged out.");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    static void adminLogin() {
        System.out.println("\n===== ADMINISTRATOR LOGIN =====");

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        if (email.equals("admin@university.com")
                && password.equals("admin123")) {

            Administrator admin = new Administrator(
                    1,
                    "Administrator",
                    email,
                    password
            );

            System.out.println("Login successful.");
            adminMenu(admin);

        } else {
            System.out.println("Invalid administrator credentials.");
        }
    }

    static void adminMenu(Administrator admin) {
        while (true) {
            admin.displayMenu();

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    admin.manageCourseCatalog(sc);
                    break;

                case 2:
                    admin.manageStudentRecords(sc);
                    break;

                case 3:
                    admin.assignProfessor(sc);
                    break;

                case 4:
                    admin.handleComplaints(sc);
                    break;

                case 5:
                    System.out.println("Logged out.");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // Required demonstration data:
    // 3 students, 2 professors and 5 courses.
    static void createSampleData() {
        Data.students.add(new Student(
                101, "Aarav", "aarav@university.com", "1234"));
        Data.students.add(new Student(
                102, "Rahul", "rahul@university.com", "1234"));
        Data.students.add(new Student(
                103, "Arushi", "arushi@university.com", "1234"));

        Professor p1 = new Professor(
                201, "Dr. Sharma", "sharma@university.com",
                "1234", "Computer Science");

        Professor p2 = new Professor(
                202, "Dr. Patel", "patel@university.com",
                "1234", "Mathematics");

        Data.professors.add(p1);
        Data.professors.add(p2);

        Course c1 = new Course(
                "CSE101", "Programming", 4, 1,
                "10:00-11:00", "A101", 40);

        Course c2 = new Course(
                "MAT101", "Mathematics", 4, 1,
                "11:00-12:00", "A102", 40);

        Course c3 = new Course(
                "PHY101", "Physics", 4, 1,
                "12:00-13:00", "A103", 40);

        Course c4 = new Course(
                "CSE201", "Data Structures", 4, 2,
                "10:00-11:00", "B101", 40);

        Course c5 = new Course(
                "OOP201", "Object Oriented Programming", 4, 2,
                "11:00-12:00", "B102", 40);

        c4.addPrerequisite("CSE101");
        c5.addPrerequisite("CSE101");

        c1.setProfessor(p1);
        c2.setProfessor(p2);
        c3.setProfessor(p2);
        c4.setProfessor(p1);
        c5.setProfessor(p1);

        Data.courses.add(c1);
        Data.courses.add(c2);
        Data.courses.add(c3);
        Data.courses.add(c4);
        Data.courses.add(c5);
    }
}

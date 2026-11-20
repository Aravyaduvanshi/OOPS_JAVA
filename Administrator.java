import java.util.Scanner;

public class Administrator extends User {

    public Administrator(int id, String name, String email, String password) {
        super(id, name, email, password);
    }

    @Override
    public void displayMenu() {
        System.out.println("\n========== ADMINISTRATOR MENU ==========");
        System.out.println("1. Manage Course Catalog");
        System.out.println("2. Manage Student Records");
        System.out.println("3. Assign Professor to Course");
        System.out.println("4. Handle Complaints");
        System.out.println("5. Logout");
        System.out.println("========================================");
    }

    public void manageCourseCatalog(Scanner sc) {
        while (true) {
            System.out.println("\n===== COURSE CATALOG =====");
            System.out.println("1. View Courses");
            System.out.println("2. Add Course");
            System.out.println("3. Delete Course");
            System.out.println("4. Back");

            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    if (Data.courses.isEmpty()) {
                        System.out.println("No courses.");
                    } else {
                        for (Course course : Data.courses) {
                            course.displayCourse();
                        }
                    }
                    break;

                case 2:
                    addCourse(sc);
                    break;

                case 3:
                    deleteCourse(sc);
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void addCourse(Scanner sc) {
        System.out.print("Course code: ");
        String code = sc.nextLine();

        if (findCourse(code) != null) {
            System.out.println("Course code already exists.");
            return;
        }

        System.out.print("Title: ");
        String title = sc.nextLine();

        System.out.print("Credits (2 or 4): ");
        int credits = Integer.parseInt(sc.nextLine());

        if (credits != 2 && credits != 4) {
            System.out.println("Credits must be 2 or 4.");
            return;
        }

        System.out.print("Semester: ");
        int semester = Integer.parseInt(sc.nextLine());

        System.out.print("Timing: ");
        String timing = sc.nextLine();

        System.out.print("Location: ");
        String location = sc.nextLine();

        System.out.print("Enrollment limit: ");
        int limit = Integer.parseInt(sc.nextLine());

        Data.courses.add(new Course(
                code, title, credits, semester,
                timing, location, limit
        ));

        System.out.println("Course added successfully.");
    }

    private void deleteCourse(Scanner sc) {
        System.out.print("Enter course code: ");
        String code = sc.nextLine();

        Course course = findCourse(code);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        Data.courses.remove(course);
        System.out.println("Course deleted successfully.");
    }

    public void manageStudentRecords(Scanner sc) {
        while (true) {
            System.out.println("\n===== STUDENT RECORDS =====");
            System.out.println("1. View Students");
            System.out.println("2. Update Student Information");
            System.out.println("3. Assign Grade");
            System.out.println("4. Complete Semester");
            System.out.println("5. Back");

            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    viewStudents();
                    break;

                case 2:
                    updateStudent(sc);
                    break;

                case 3:
                    assignGrade(sc);
                    break;

                case 4:
                    completeSemester(sc);
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void viewStudents() {
        if (Data.students.isEmpty()) {
            System.out.println("No students.");
            return;
        }

        for (Student student : Data.students) {
            System.out.println("--------------------------------");
            System.out.println("ID       : " + student.getId());
            System.out.println("Name     : " + student.getName());
            System.out.println("Email    : " + student.getEmail());
            System.out.println("Semester : " + student.getCurrentSemester());
        }
    }

    private void updateStudent(Scanner sc) {
        System.out.print("Enter student ID: ");
        int id = Integer.parseInt(sc.nextLine());

        Student student = findStudent(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter new name: ");
        student.setName(sc.nextLine());

        System.out.print("Enter new email: ");
        student.setEmail(sc.nextLine());

        System.out.println("Student information updated.");
    }

    private void assignGrade(Scanner sc) {
        System.out.print("Enter student ID: ");
        int id = Integer.parseInt(sc.nextLine());

        Student student = findStudent(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter course code: ");
        String courseCode = sc.nextLine();

        Course course = findCourse(courseCode);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        System.out.print("Enter grade (A, B+, B, C+, C, D, F): ");
        String grade = sc.nextLine().toUpperCase();

        if (!grade.equals("A") && !grade.equals("B+") &&
            !grade.equals("B") && !grade.equals("C+") &&
            !grade.equals("C") && !grade.equals("D") &&
            !grade.equals("F")) {
            System.out.println("Invalid grade.");
            return;
        }

        student.addGrade(courseCode, grade);
        System.out.println("Grade assigned successfully.");
    }

    private void completeSemester(Scanner sc) {
        System.out.print("Enter student ID: ");
        int id = Integer.parseInt(sc.nextLine());

        Student student = findStudent(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        student.completeSemester();
    }

    public void assignProfessor(Scanner sc) {
        System.out.print("Enter course code: ");
        String courseCode = sc.nextLine();

        Course course = findCourse(courseCode);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        System.out.println("\nAvailable Professors:");

        for (Professor professor : Data.professors) {
            System.out.println(
                    professor.getId() + " - " +
                    professor.getName() + " - " +
                    professor.getSpecialization()
            );
        }

        System.out.print("Enter professor ID: ");
        int id = Integer.parseInt(sc.nextLine());

        Professor professor = findProfessor(id);

        if (professor == null) {
            System.out.println("Professor not found.");
            return;
        }

        course.setProfessor(professor);
        System.out.println("Professor assigned successfully.");
    }

    public void handleComplaints(Scanner sc) {
        while (true) {
            System.out.println("\n===== COMPLAINTS =====");
            System.out.println("1. View All");
            System.out.println("2. View Pending");
            System.out.println("3. View Resolved");
            System.out.println("4. Resolve Complaint");
            System.out.println("5. Back");

            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    viewComplaints("All");
                    break;

                case 2:
                    viewComplaints("Pending");
                    break;

                case 3:
                    viewComplaints("Resolved");
                    break;

                case 4:
                    resolveComplaint(sc);
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void viewComplaints(String filter) {
        boolean found = false;

        for (Complaint complaint : Data.complaints) {
            if (filter.equals("All") ||
                complaint.getStatus().equals(filter)) {

                complaint.displayComplaint();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No complaints found.");
        }
    }

    private void resolveComplaint(Scanner sc) {
        System.out.print("Enter complaint ID: ");
        int id = Integer.parseInt(sc.nextLine());

        Complaint selected = null;

        for (Complaint complaint : Data.complaints) {
            if (complaint.getComplaintId() == id) {
                selected = complaint;
                break;
            }
        }

        if (selected == null) {
            System.out.println("Complaint not found.");
            return;
        }

        if (selected.getStatus().equals("Resolved")) {
            System.out.println("Complaint is already resolved.");
            return;
        }

        System.out.print("Enter resolution: ");
        String resolution = sc.nextLine();

        selected.resolve(resolution);
        System.out.println("Complaint resolved.");
    }

    private Student findStudent(int id) {
        for (Student student : Data.students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    private Course findCourse(String code) {
        for (Course course : Data.courses) {
            if (course.getCourseCode().equalsIgnoreCase(code)) {
                return course;
            }
        }
        return null;
    }

    private Professor findProfessor(int id) {
        for (Professor professor : Data.professors) {
            if (professor.getId() == id) {
                return professor;
            }
        }
        return null;
    }
}

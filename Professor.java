import java.util.Scanner;

public class Professor extends User {
    private String specialization;

    public Professor(int id, String name, String email, String password, String specialization) {
        super(id, name, email, password);
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    @Override
    public void displayMenu() {
        System.out.println("\n========== PROFESSOR MENU ==========");
        System.out.println("1. View My Courses");
        System.out.println("2. Manage Course");
        System.out.println("3. View Enrolled Students");
        System.out.println("4. Logout");
        System.out.println("====================================");
    }

    public void viewMyCourses() {
        boolean found = false;

        for (Course course : Data.courses) {
            if (course.getProfessor() == this) {
                course.displayCourse();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No courses assigned to you.");
        }
    }

    public void manageCourse(Scanner sc) {
        System.out.print("Enter course code: ");
        String code = sc.nextLine();

        Course selected = null;

        for (Course course : Data.courses) {
            if (course.getCourseCode().equalsIgnoreCase(code)
                    && course.getProfessor() == this) {
                selected = course;
                break;
            }
        }

        if (selected == null) {
            System.out.println("Course not found or not assigned to you.");
            return;
        }

        while (true) {
            System.out.println("\n===== UPDATE COURSE =====");
            System.out.println("1. Update Syllabus");
            System.out.println("2. Update Timing");
            System.out.println("3. Update Credits");
            System.out.println("4. Update Prerequisites");
            System.out.println("5. Update Enrollment Limit");
            System.out.println("6. Update Office Hours");
            System.out.println("7. View Course");
            System.out.println("8. Back");

            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Enter syllabus: ");
                    selected.setSyllabus(sc.nextLine());
                    System.out.println("Syllabus updated.");
                    break;

                case 2:
                    System.out.print("Enter timing: ");
                    selected.setTiming(sc.nextLine());
                    System.out.println("Timing updated.");
                    break;

                case 3:
                    System.out.print("Enter credits (2 or 4): ");
                    int credits = Integer.parseInt(sc.nextLine());

                    if (credits == 2 || credits == 4) {
                        selected.setCredits(credits);
                        System.out.println("Credits updated.");
                    } else {
                        System.out.println("Credits must be 2 or 4.");
                    }
                    break;

                case 4:
                    System.out.print("Enter prerequisite course code: ");
                    selected.addPrerequisite(sc.nextLine());
                    System.out.println("Prerequisite added.");
                    break;

                case 5:
                    System.out.print("Enter enrollment limit: ");
                    selected.setEnrollmentLimit(Integer.parseInt(sc.nextLine()));
                    System.out.println("Enrollment limit updated.");
                    break;

                case 6:
                    System.out.print("Enter office hours: ");
                    selected.setOfficeHours(sc.nextLine());
                    System.out.println("Office hours updated.");
                    break;

                case 7:
                    selected.displayCourse();
                    break;

                case 8:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public void viewEnrolledStudents() {
        System.out.println("\n========== ENROLLED STUDENTS ==========");
        boolean found = false;

        for (Course course : Data.courses) {
            if (course.getProfessor() == this) {
                System.out.println("\nCourse: " + course.getCourseCode());

                for (Student student : Data.students) {
                    if (student.getRegisteredCourses().contains(course)) {
                        System.out.println("ID       : " + student.getId());
                        System.out.println("Name     : " + student.getName());
                        System.out.println("Email    : " + student.getEmail());
                        System.out.println("Semester : " + student.getCurrentSemester());
                        System.out.println();
                        found = true;
                    }
                }
            }
        }

        if (!found) {
            System.out.println("No enrolled students.");
        }
    }
}

import java.util.ArrayList;
import java.util.HashMap;

public class Student extends User {
    private int currentSemester;
    private ArrayList<Course> registeredCourses;
    private ArrayList<String> completedCourses;
    private HashMap<String, String> grades;
    private ArrayList<Complaint> complaints;

    public Student(int id, String name, String email, String password) {
        super(id, name, email, password);
        currentSemester = 1;
        registeredCourses = new ArrayList<>();
        completedCourses = new ArrayList<>();
        grades = new HashMap<>();
        complaints = new ArrayList<>();
    }

    @Override
    public void displayMenu() {
        System.out.println("\n========== STUDENT MENU ==========");
        System.out.println("1. View Available Courses");
        System.out.println("2. Register for Course");
        System.out.println("3. View Schedule");
        System.out.println("4. Track Academic Progress");
        System.out.println("5. Drop Course");
        System.out.println("6. Submit Complaint");
        System.out.println("7. View Complaints");
        System.out.println("8. Logout");
        System.out.println("==================================");
    }

    public int getCurrentSemester() { return currentSemester; }
    public ArrayList<Course> getRegisteredCourses() { return registeredCourses; }
    public ArrayList<String> getCompletedCourses() { return completedCourses; }
    public HashMap<String, String> getGrades() { return grades; }
    public ArrayList<Complaint> getComplaints() { return complaints; }

    public void viewAvailableCourses() {
        System.out.println("\nCourses for Semester " + currentSemester);
        boolean found = false;

        for (Course course : Data.courses) {
            if (course.getSemester() == currentSemester) {
                course.displayCourse();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No courses available.");
        }
    }

    public void registerCourse(String courseCode) {
        Course selected = null;

        for (Course course : Data.courses) {
            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                selected = course;
                break;
            }
        }

        if (selected == null) {
            System.out.println("Course not found.");
            return;
        }

        if (selected.getSemester() != currentSemester) {
            System.out.println("You can register only for courses in your current semester.");
            return;
        }

        for (Course course : registeredCourses) {
            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                System.out.println("You are already registered.");
                return;
            }
        }

        for (String prerequisite : selected.getPrerequisites()) {
            if (!completedCourses.contains(prerequisite)) {
                System.out.println("Prerequisite not completed: " + prerequisite);
                return;
            }
        }

        int totalCredits = 0;
        for (Course course : registeredCourses) {
            totalCredits += course.getCredits();
        }

        if (totalCredits + selected.getCredits() > 20) {
            System.out.println("Registration failed. Maximum credit limit is 20.");
            return;
        }

        int enrolledStudents = 0;
        for (Student student : Data.students) {
            if (student.registeredCourses.contains(selected)) {
                enrolledStudents++;
            }
        }

        if (enrolledStudents >= selected.getEnrollmentLimit()) {
            System.out.println("Course enrollment limit reached.");
            return;
        }

        registeredCourses.add(selected);
        System.out.println("Course registered successfully.");
    }

    public void viewSchedule() {
        if (registeredCourses.isEmpty()) {
            System.out.println("No courses registered.");
            return;
        }

        System.out.println("\n========== WEEKLY SCHEDULE ==========");
        for (Course course : registeredCourses) {
            System.out.println("\nCourse    : " + course.getCourseCode());
            System.out.println("Title     : " + course.getTitle());
            System.out.println("Timing    : " + course.getTiming());
            System.out.println("Location  : " + course.getLocation());
            System.out.println("Professor : " +
                    (course.getProfessor() == null ? "Not Assigned" : course.getProfessor().getName()));
        }
    }

    public void dropCourse(String courseCode) {
        Course found = null;

        for (Course course : registeredCourses) {
            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                found = course;
                break;
            }
        }

        if (found == null) {
            System.out.println("You are not registered in this course.");
            return;
        }

        registeredCourses.remove(found);
        System.out.println("Course dropped successfully.");
    }

    // Called by the administrator after assigning a grade.
    public void addGrade(String courseCode, String grade) {
        grades.put(courseCode, grade.toUpperCase());

        if (!completedCourses.contains(courseCode)) {
            completedCourses.add(courseCode);
        }

        Course completedCourse = null;
        for (Course course : registeredCourses) {
            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                completedCourse = course;
                break;
            }
        }

        if (completedCourse != null) {
            registeredCourses.remove(completedCourse);
        }
    }

    public boolean isSemesterComplete() {
        boolean hasSemesterCourse = false;

        for (Course course : Data.courses) {
            if (course.getSemester() == currentSemester) {
                hasSemesterCourse = true;

                if (!grades.containsKey(course.getCourseCode())) {
                    return false;
                }
            }
        }

        return hasSemesterCourse;
    }

    public void completeSemester() {
        if (isSemesterComplete()) {
            currentSemester++;
            System.out.println("Semester completed successfully.");
            System.out.println("You are now in Semester " + currentSemester);
        } else {
            System.out.println("Semester cannot be completed.");
            System.out.println("All courses in the semester must have grades.");
        }
    }

    private int getGradePoint(String grade) {
        switch (grade.toUpperCase()) {
            case "A":  return 10;
            case "B+": return 9;
            case "B":  return 8;
            case "C+": return 7;
            case "C":  return 6;
            case "D":  return 5;
            case "F":  return 0;
            default:   return 0;
        }
    }

    public double calculateSGPA(int semester) {
        double totalPoints = 0;
        int totalCredits = 0;

        for (Course course : Data.courses) {
            if (course.getSemester() == semester) {
                String grade = grades.get(course.getCourseCode());

                if (grade != null) {
                    totalPoints += getGradePoint(grade) * course.getCredits();
                    totalCredits += course.getCredits();
                }
            }
        }

        return totalCredits == 0 ? 0 : totalPoints / totalCredits;
    }

    public double calculateCGPA() {
        double totalPoints = 0;
        int totalCredits = 0;

        for (Course course : Data.courses) {
            String grade = grades.get(course.getCourseCode());

            if (grade != null) {
                totalPoints += getGradePoint(grade) * course.getCredits();
                totalCredits += course.getCredits();
            }
        }

        return totalCredits == 0 ? 0 : totalPoints / totalCredits;
    }

    public void displayAcademicProgress() {
        System.out.println("\n========== ACADEMIC PROGRESS ==========");

        if (grades.isEmpty()) {
            System.out.println("No grades available.");
            return;
        }

        for (String courseCode : grades.keySet()) {
            System.out.println(courseCode + " : " + grades.get(courseCode));
        }

        System.out.println("Current Semester: " + currentSemester);
        System.out.printf("CGPA: %.2f%n", calculateCGPA());

        for (int i = 1; i < currentSemester; i++) {
            System.out.printf("SGPA Semester %d: %.2f%n", i, calculateSGPA(i));
        }
    }

    public void submitComplaint(String description) {
        Complaint complaint = new Complaint(this, description);
        complaints.add(complaint);
        Data.complaints.add(complaint);

        System.out.println("Complaint submitted successfully.");
        System.out.println("Status: Pending");
    }

    public void viewComplaints() {
        if (complaints.isEmpty()) {
            System.out.println("No complaints.");
            return;
        }

        for (Complaint complaint : complaints) {
            complaint.displayComplaint();
        }
    }
}

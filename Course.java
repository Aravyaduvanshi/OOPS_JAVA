import java.util.ArrayList;

// Represents one course in the university course catalog.
public class Course {
    private String courseCode;
    private String title;
    private Professor professor;
    private int credits;
    private ArrayList<String> prerequisites;
    private String timing;
    private String location;
    private String syllabus;
    private int enrollmentLimit;
    private String officeHours;
    private int semester;

    public Course(String courseCode, String title, int credits, int semester,
                  String timing, String location, int enrollmentLimit) {
        this.courseCode = courseCode;
        this.title = title;
        this.credits = credits;
        this.semester = semester;
        this.timing = timing;
        this.location = location;
        this.enrollmentLimit = enrollmentLimit;
        this.prerequisites = new ArrayList<>();
        this.syllabus = "Not specified";
        this.officeHours = "Not specified";
    }

    public String getCourseCode() { return courseCode; }
    public String getTitle() { return title; }
    public Professor getProfessor() { return professor; }
    public int getCredits() { return credits; }
    public ArrayList<String> getPrerequisites() { return prerequisites; }
    public String getTiming() { return timing; }
    public String getLocation() { return location; }
    public String getSyllabus() { return syllabus; }
    public int getEnrollmentLimit() { return enrollmentLimit; }
    public String getOfficeHours() { return officeHours; }
    public int getSemester() { return semester; }

    public void setProfessor(Professor professor) { this.professor = professor; }
    public void setCredits(int credits) { this.credits = credits; }
    public void setTiming(String timing) { this.timing = timing; }
    public void setLocation(String location) { this.location = location; }
    public void setSyllabus(String syllabus) { this.syllabus = syllabus; }
    public void setEnrollmentLimit(int enrollmentLimit) { this.enrollmentLimit = enrollmentLimit; }
    public void setOfficeHours(String officeHours) { this.officeHours = officeHours; }

    public void addPrerequisite(String courseCode) {
        if (!prerequisites.contains(courseCode)) {
            prerequisites.add(courseCode);
        }
    }

    public void displayCourse() {
        System.out.println("------------------------------------------");
        System.out.println("Course Code      : " + courseCode);
        System.out.println("Title            : " + title);
        System.out.println("Professor        : " +
                (professor == null ? "Not Assigned" : professor.getName()));
        System.out.println("Credits          : " + credits);
        System.out.println("Semester         : " + semester);
        System.out.println("Timing           : " + timing);
        System.out.println("Location         : " + location);
        System.out.println("Prerequisites    : " +
                (prerequisites.isEmpty() ? "None" : prerequisites));
        System.out.println("Enrollment Limit : " + enrollmentLimit);
        System.out.println("Syllabus         : " + syllabus);
        System.out.println("Office Hours     : " + officeHours);
        System.out.println("------------------------------------------");
    }
}

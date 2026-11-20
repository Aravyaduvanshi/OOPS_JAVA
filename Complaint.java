import java.time.LocalDate;

// Represents a complaint submitted by a student.
public class Complaint {
    private static int nextId = 1;

    private int complaintId;
    private Student student;
    private String description;
    private String status;
    private String resolution;
    private LocalDate date;

    public Complaint(Student student, String description) {
        this.complaintId = nextId++;
        this.student = student;
        this.description = description;
        this.status = "Pending";
        this.resolution = "Not resolved";
        this.date = LocalDate.now();
    }

    public int getComplaintId() { return complaintId; }
    public Student getStudent() { return student; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public String getResolution() { return resolution; }
    public LocalDate getDate() { return date; }

    // Only the administrator calls this operation.
    public void resolve(String resolution) {
        this.status = "Resolved";
        this.resolution = resolution;
    }

    public void displayComplaint() {
        System.out.println("------------------------------------------");
        System.out.println("Complaint ID : " + complaintId);
        System.out.println("Student      : " + student.getName());
        System.out.println("Description  : " + description);
        System.out.println("Date         : " + date);
        System.out.println("Status       : " + status);
        System.out.println("Resolution   : " + resolution);
        System.out.println("------------------------------------------");
    }
}

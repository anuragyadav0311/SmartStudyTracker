public class StudySession {
    private String subject;
    private int duration; // in minutes
    private String date;

    public StudySession(String subject, int duration, String date) {
        this.subject = subject;
        this.duration = duration;
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public int getDuration() {
        return duration;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Subject: " + subject + " | Duration: " + duration + " mins | Date: " + date;
    }

    // Convert to CSV format for file storage
    public String toFileFormat() {
        return subject + "," + duration + "," + date;
    }

    // Parse a CSV line back into a StudySession
    public static StudySession fromFileFormat(String line) {
        String[] parts = line.split(",");
        if (parts.length == 3) {
            return new StudySession(parts[0].trim(), Integer.parseInt(parts[1].trim()), parts[2].trim());
        }
        return null;
    }
}

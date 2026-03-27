import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TrackerService {
    private static final String DATA_FILE = "../data/sessions.txt";

    // Add a study session to the file
    public void addSession(StudySession session) {
        try {
            // Ensure the data directory exists
            File file = new File(DATA_FILE);
            file.getParentFile().mkdirs();

            FileWriter fw = new FileWriter(DATA_FILE, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(session.toFileFormat());
            bw.newLine();
            bw.close();

            System.out.println("\n✅ Session added successfully!\n");
        } catch (IOException e) {
            System.out.println("\n❌ Error saving session: " + e.getMessage() + "\n");
        }
    }

    // View all study sessions from the file
    public List<StudySession> viewSessions() {
        List<StudySession> sessions = new ArrayList<>();
        File file = new File(DATA_FILE);

        if (!file.exists()) {
            return sessions;
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(DATA_FILE));
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    StudySession session = StudySession.fromFileFormat(line);
                    if (session != null) {
                        sessions.add(session);
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("\n❌ Error reading sessions: " + e.getMessage() + "\n");
        }

        return sessions;
    }
}

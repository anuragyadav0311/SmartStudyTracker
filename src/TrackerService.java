import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Core service for managing study sessions.
 * Handles CRUD operations for study session data with file-based persistence.
 * Supports adding, viewing, deleting, and searching/filtering sessions.
 */
public class TrackerService {
    private static final String DATA_FILE = "../data/sessions.txt";

    /**
     * Adds a study session and persists it to the data file.
     */
    public void addSession(StudySession session) {
        try {
            File file = new File(DATA_FILE);
            file.getParentFile().mkdirs();

            FileWriter fw = new FileWriter(DATA_FILE, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(session.toFileFormat());
            bw.newLine();
            bw.close();

            System.out.println("\n" + ConsoleColors.success("✅ Session added successfully!") + "\n");
        } catch (IOException e) {
            System.out.println("\n" + ConsoleColors.error("❌ Error saving session: " + e.getMessage()) + "\n");
        }
    }

    /**
     * Returns all study sessions from the data file.
     */
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
            System.out.println("\n" + ConsoleColors.error("❌ Error reading sessions: " + e.getMessage()) + "\n");
        }

        return sessions;
    }

    /**
     * Deletes a session by its 1-based index.
     * Returns true if the deletion was successful.
     */
    public boolean deleteSession(int index) {
        List<StudySession> sessions = viewSessions();

        if (index < 1 || index > sessions.size()) {
            System.out.println("\n" + ConsoleColors.error("❌ Invalid session number.") + "\n");
            return false;
        }

        StudySession removed = sessions.remove(index - 1);
        rewriteFile(sessions);

        System.out.println("\n" + ConsoleColors.success("🗑️  Deleted: " + removed) + "\n");
        return true;
    }

    /**
     * Searches sessions by subject name (case-insensitive partial match).
     */
    public List<StudySession> searchBySubject(String keyword) {
        return viewSessions().stream()
                .filter(s -> s.getSubject().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Filters sessions by a specific date.
     */
    public List<StudySession> filterByDate(String date) {
        return viewSessions().stream()
                .filter(s -> s.getDate().equals(date))
                .collect(Collectors.toList());
    }

    /**
     * Rewrites the entire data file with the given list of sessions.
     * Used internally after deletions.
     */
    private void rewriteFile(List<StudySession> sessions) {
        try {
            File file = new File(DATA_FILE);
            file.getParentFile().mkdirs();

            BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_FILE, false));
            for (StudySession session : sessions) {
                bw.write(session.toFileFormat());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("\n" + ConsoleColors.error("❌ Error writing sessions: " + e.getMessage()) + "\n");
        }
    }
}

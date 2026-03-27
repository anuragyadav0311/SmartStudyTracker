import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TrackerService tracker = new TrackerService();

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║       📚 Smart Study Tracker 📚      ║");
        System.out.println("╚══════════════════════════════════════╝");

        while (true) {
            System.out.println("┌──────────────────────────────────────┐");
            System.out.println("│  1. Add Study Session                │");
            System.out.println("│  2. View All Sessions                │");
            System.out.println("│  3. Exit                             │");
            System.out.println("└──────────────────────────────────────┘");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addSession(scanner, tracker);
                    break;
                case "2":
                    viewSessions(tracker);
                    break;
                case "3":
                    System.out.println("\n👋 Goodbye! Keep studying hard!\n");
                    scanner.close();
                    return;
                default:
                    System.out.println("\n⚠️  Invalid option. Please try again.\n");
            }
        }
    }

    private static void addSession(Scanner scanner, TrackerService tracker) {
        System.out.println("\n--- Add New Study Session ---\n");

        System.out.print("Enter subject: ");
        String subject = scanner.nextLine().trim();

        if (subject.isEmpty()) {
            System.out.println("\n⚠️  Subject cannot be empty.\n");
            return;
        }

        System.out.print("Enter duration (in minutes): ");
        int duration;
        try {
            duration = Integer.parseInt(scanner.nextLine().trim());
            if (duration <= 0) {
                System.out.println("\n⚠️  Duration must be a positive number.\n");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("\n⚠️  Invalid duration. Please enter a number.\n");
            return;
        }

        System.out.print("Enter date (e.g., 2026-03-27): ");
        String date = scanner.nextLine().trim();

        if (date.isEmpty()) {
            System.out.println("\n⚠️  Date cannot be empty.\n");
            return;
        }

        StudySession session = new StudySession(subject, duration, date);
        tracker.addSession(session);
    }

    private static void viewSessions(TrackerService tracker) {
        List<StudySession> sessions = tracker.viewSessions();

        if (sessions.isEmpty()) {
            System.out.println("\n📭 No study sessions recorded yet.\n");
        } else {
            System.out.println("\n--- All Study Sessions ---\n");
            System.out.println("┌────┬────────────────────┬──────────┬────────────┐");
            System.out.println("│ #  │ Subject            │ Duration │ Date       │");
            System.out.println("├────┼────────────────────┼──────────┼────────────┤");
            for (int i = 0; i < sessions.size(); i++) {
                StudySession s = sessions.get(i);
                System.out.printf("│ %-2d │ %-18s │ %-8s │ %-10s │%n",
                        i + 1,
                        truncate(s.getSubject(), 18),
                        s.getDuration() + " mins",
                        s.getDate());
            }
            System.out.println("└────┴────────────────────┴──────────┴────────────┘");
            System.out.println("\nTotal sessions: " + sessions.size() + "\n");
        }
    }

    private static String truncate(String str, int maxLen) {
        if (str.length() > maxLen) {
            return str.substring(0, maxLen - 2) + "..";
        }
        return str;
    }
}

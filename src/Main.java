import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Main entry point for Smart Study Tracker.
 * Provides a rich, color-coded CLI menu integrating all features:
 * session management, statistics, goals, search, and motivation.
 */
public class Main {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TrackerService tracker = new TrackerService();
        StudyGoals goals = new StudyGoals();

        clearScreen();
        printBanner();
        MotivationEngine.displayQuote();

        while (true) {
            printMenu();
            System.out.print(ConsoleColors.BOLD_WHITE + "  ➤ Choose an option: " + ConsoleColors.RESET);

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addSession(scanner, tracker);
                    break;
                case "2":
                    viewSessions(tracker);
                    break;
                case "3":
                    deleteSession(scanner, tracker);
                    break;
                case "4":
                    searchSessions(scanner, tracker);
                    break;
                case "5":
                    StudyStatistics.displayDashboard(tracker.viewSessions());
                    break;
                case "6":
                    manageGoals(scanner, goals, tracker);
                    break;
                case "7":
                    MotivationEngine.displayQuote();
                    break;
                case "8":
                    printExitMessage();
                    scanner.close();
                    return;
                default:
                    System.out.println("\n" + ConsoleColors.warning("  ⚠️  Invalid option. Please try again.") + "\n");
            }
        }
    }

    // ─────────────────────────────────────────────
    //  UI Components
    // ─────────────────────────────────────────────

    private static void printBanner() {
        System.out.println(ConsoleColors.BOLD_CYAN +
            "\n" +
            "   ╔═══════════════════════════════════════════════════════╗\n" +
            "   ║                                                       ║\n" +
            "   ║    ███████╗███████╗████████╗                          ║\n" +
            "   ║    ██╔════╝██╔════╝╚══██╔══╝                          ║\n" +
            "   ║    ███████╗███████╗   ██║                             ║\n" +
            "   ║    ╚════██║╚════██║   ██║                             ║\n" +
            "   ║    ███████║███████║   ██║                             ║\n" +
            "   ║    ╚══════╝╚══════╝   ╚═╝                             ║\n" +
            "   ║                                                       ║\n" +
            "   ║         📚 Smart Study Tracker v2.0 📚                ║\n" +
            "   ║      ─── Track · Analyze · Achieve ───                ║\n" +
            "   ║                                                       ║\n" +
            "   ╚═══════════════════════════════════════════════════════╝\n" +
            ConsoleColors.RESET);
    }

    private static void printMenu() {
        System.out.println(ConsoleColors.BOLD_BLUE +
            "   ┌───────────────────────────────────────┐" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BOLD_BLUE + "   │" + ConsoleColors.BOLD_WHITE +
            "       📋 MAIN MENU                     " + ConsoleColors.BOLD_BLUE + "│" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BOLD_BLUE +
            "   ├───────────────────────────────────────┤" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BOLD_BLUE + "   │" + ConsoleColors.GREEN +
            "  1. ➕ Add Study Session                " + ConsoleColors.BOLD_BLUE + "│" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BOLD_BLUE + "   │" + ConsoleColors.CYAN +
            "  2. 📖 View All Sessions                " + ConsoleColors.BOLD_BLUE + "│" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BOLD_BLUE + "   │" + ConsoleColors.RED +
            "  3. 🗑️  Delete a Session                " + ConsoleColors.BOLD_BLUE + "│" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BOLD_BLUE + "   │" + ConsoleColors.YELLOW +
            "  4. 🔍 Search & Filter Sessions         " + ConsoleColors.BOLD_BLUE + "│" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BOLD_BLUE + "   │" + ConsoleColors.PURPLE +
            "  5. 📊 Study Statistics                 " + ConsoleColors.BOLD_BLUE + "│" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BOLD_BLUE + "   │" + ConsoleColors.BOLD_GREEN +
            "  6. 🎯 Study Goals                      " + ConsoleColors.BOLD_BLUE + "│" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BOLD_BLUE + "   │" + ConsoleColors.BOLD_CYAN +
            "  7. 💡 Get Motivated                    " + ConsoleColors.BOLD_BLUE + "│" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BOLD_BLUE + "   │" + ConsoleColors.DIM +
            "  8. 🚪 Exit                             " + ConsoleColors.BOLD_BLUE + "│" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BOLD_BLUE +
            "   └───────────────────────────────────────┘" + ConsoleColors.RESET);
    }

    private static void printExitMessage() {
        System.out.println();
        System.out.println(ConsoleColors.BOLD_CYAN +
            "   ╔═══════════════════════════════════════════════════════╗" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BOLD_CYAN +
            "   ║   👋 Goodbye! Keep studying hard and stay consistent! ║" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BOLD_CYAN +
            "   ╚═══════════════════════════════════════════════════════╝" + ConsoleColors.RESET);
        System.out.println();
    }

    // ─────────────────────────────────────────────
    //  Feature: Add Session
    // ─────────────────────────────────────────────

    private static void addSession(Scanner scanner, TrackerService tracker) {
        System.out.println("\n" + ConsoleColors.BOLD_GREEN + "   ── ➕ Add New Study Session ──────────────────" + ConsoleColors.RESET + "\n");

        // Subject
        System.out.print(ConsoleColors.BOLD_WHITE + "   Enter subject: " + ConsoleColors.RESET);
        String subject = scanner.nextLine().trim();
        if (subject.isEmpty()) {
            System.out.println("\n" + ConsoleColors.warning("   ⚠️  Subject cannot be empty.") + "\n");
            return;
        }

        // Duration
        System.out.print(ConsoleColors.BOLD_WHITE + "   Enter duration (in minutes): " + ConsoleColors.RESET);
        int duration;
        try {
            duration = Integer.parseInt(scanner.nextLine().trim());
            if (duration <= 0) {
                System.out.println("\n" + ConsoleColors.warning("   ⚠️  Duration must be a positive number.") + "\n");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("\n" + ConsoleColors.warning("   ⚠️  Invalid duration. Please enter a number.") + "\n");
            return;
        }

        // Date with validation
        System.out.print(ConsoleColors.BOLD_WHITE + "   Enter date (yyyy-MM-dd) or press Enter for today: " + ConsoleColors.RESET);
        String dateInput = scanner.nextLine().trim();
        String date;
        if (dateInput.isEmpty()) {
            date = LocalDate.now().format(DATE_FORMAT);
            System.out.println(ConsoleColors.DIM + "   Using today's date: " + date + ConsoleColors.RESET);
        } else {
            if (!isValidDate(dateInput)) {
                System.out.println("\n" + ConsoleColors.warning("   ⚠️  Invalid date format. Use yyyy-MM-dd (e.g., 2026-03-27)") + "\n");
                return;
            }
            date = dateInput;
        }

        StudySession session = new StudySession(subject, duration, date);
        tracker.addSession(session);
    }

    // ─────────────────────────────────────────────
    //  Feature: View Sessions
    // ─────────────────────────────────────────────

    private static void viewSessions(TrackerService tracker) {
        List<StudySession> sessions = tracker.viewSessions();

        if (sessions.isEmpty()) {
            System.out.println("\n" + ConsoleColors.warning("   📭 No study sessions recorded yet.") + "\n");
        } else {
            printSessionTable(sessions, "All Study Sessions");
        }
    }

    // ─────────────────────────────────────────────
    //  Feature: Delete Session
    // ─────────────────────────────────────────────

    private static void deleteSession(Scanner scanner, TrackerService tracker) {
        List<StudySession> sessions = tracker.viewSessions();

        if (sessions.isEmpty()) {
            System.out.println("\n" + ConsoleColors.warning("   📭 No sessions to delete.") + "\n");
            return;
        }

        printSessionTable(sessions, "Select Session to Delete");

        System.out.print(ConsoleColors.BOLD_WHITE + "   Enter session number to delete (0 to cancel): " + ConsoleColors.RESET);
        try {
            int num = Integer.parseInt(scanner.nextLine().trim());
            if (num == 0) {
                System.out.println("\n" + ConsoleColors.info("   Cancelled.") + "\n");
                return;
            }
            tracker.deleteSession(num);
        } catch (NumberFormatException e) {
            System.out.println("\n" + ConsoleColors.warning("   ⚠️  Please enter a valid number.") + "\n");
        }
    }

    // ─────────────────────────────────────────────
    //  Feature: Search & Filter
    // ─────────────────────────────────────────────

    private static void searchSessions(Scanner scanner, TrackerService tracker) {
        System.out.println("\n" + ConsoleColors.BOLD_YELLOW + "   ── 🔍 Search & Filter ──────────────────────" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW + "   1. Search by subject" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW + "   2. Filter by date" + ConsoleColors.RESET);
        System.out.print(ConsoleColors.BOLD_WHITE + "\n   ➤ Choose: " + ConsoleColors.RESET);

        String searchChoice = scanner.nextLine().trim();

        switch (searchChoice) {
            case "1":
                System.out.print(ConsoleColors.BOLD_WHITE + "   Enter subject keyword: " + ConsoleColors.RESET);
                String keyword = scanner.nextLine().trim();
                if (keyword.isEmpty()) {
                    System.out.println("\n" + ConsoleColors.warning("   ⚠️  Keyword cannot be empty.") + "\n");
                    return;
                }
                List<StudySession> bySubject = tracker.searchBySubject(keyword);
                if (bySubject.isEmpty()) {
                    System.out.println("\n" + ConsoleColors.warning("   📭 No sessions found for '" + keyword + "'.") + "\n");
                } else {
                    printSessionTable(bySubject, "Results for '" + keyword + "'");
                }
                break;
            case "2":
                System.out.print(ConsoleColors.BOLD_WHITE + "   Enter date (yyyy-MM-dd): " + ConsoleColors.RESET);
                String filterDate = scanner.nextLine().trim();
                if (!isValidDate(filterDate)) {
                    System.out.println("\n" + ConsoleColors.warning("   ⚠️  Invalid date format.") + "\n");
                    return;
                }
                List<StudySession> byDate = tracker.filterByDate(filterDate);
                if (byDate.isEmpty()) {
                    System.out.println("\n" + ConsoleColors.warning("   📭 No sessions found for " + filterDate + ".") + "\n");
                } else {
                    printSessionTable(byDate, "Sessions on " + filterDate);
                }
                break;
            default:
                System.out.println("\n" + ConsoleColors.warning("   ⚠️  Invalid option.") + "\n");
        }
    }

    // ─────────────────────────────────────────────
    //  Feature: Study Goals
    // ─────────────────────────────────────────────

    private static void manageGoals(Scanner scanner, StudyGoals goals, TrackerService tracker) {
        System.out.println("\n" + ConsoleColors.BOLD_GREEN + "   ── 🎯 Study Goals ──────────────────────────" + ConsoleColors.RESET);

        if (goals.getDailyGoal() > 0) {
            System.out.println(ConsoleColors.info("   Current goal: " + goals.getDailyGoal() + " minutes/day"));
        } else {
            System.out.println(ConsoleColors.DIM + "   No goal set yet." + ConsoleColors.RESET);
        }

        System.out.println(ConsoleColors.GREEN + "\n   1. View today's progress" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.GREEN + "   2. Set/update daily goal" + ConsoleColors.RESET);
        System.out.print(ConsoleColors.BOLD_WHITE + "\n   ➤ Choose: " + ConsoleColors.RESET);

        String goalChoice = scanner.nextLine().trim();
        String today = LocalDate.now().format(DATE_FORMAT);

        switch (goalChoice) {
            case "1":
                goals.displayGoalProgress(tracker.viewSessions(), today);
                break;
            case "2":
                System.out.print(ConsoleColors.BOLD_WHITE + "   Enter daily goal (minutes): " + ConsoleColors.RESET);
                try {
                    int mins = Integer.parseInt(scanner.nextLine().trim());
                    if (mins <= 0) {
                        System.out.println("\n" + ConsoleColors.warning("   ⚠️  Goal must be a positive number.") + "\n");
                        return;
                    }
                    goals.setDailyGoal(mins);
                    System.out.println("\n" + ConsoleColors.success("   ✅ Daily goal set to " + mins + " minutes!") + "\n");
                } catch (NumberFormatException e) {
                    System.out.println("\n" + ConsoleColors.warning("   ⚠️  Please enter a valid number.") + "\n");
                }
                break;
            default:
                System.out.println("\n" + ConsoleColors.warning("   ⚠️  Invalid option.") + "\n");
        }
    }

    // ─────────────────────────────────────────────
    //  Utility Methods
    // ─────────────────────────────────────────────

    /**
     * Prints a formatted, color-coded table of study sessions.
     */
    private static void printSessionTable(List<StudySession> sessions, String title) {
        System.out.println("\n" + ConsoleColors.BOLD_CYAN + "   ── " + title + " ──" + ConsoleColors.RESET + "\n");
        System.out.println(ConsoleColors.BOLD_BLUE +
            "   ┌────┬────────────────────┬──────────────┬────────────┐" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BOLD_BLUE +
            "   │" + ConsoleColors.BOLD_WHITE + " #  " + ConsoleColors.BOLD_BLUE +
            "│" + ConsoleColors.BOLD_WHITE + " Subject            " + ConsoleColors.BOLD_BLUE +
            "│" + ConsoleColors.BOLD_WHITE + " Duration     " + ConsoleColors.BOLD_BLUE +
            "│" + ConsoleColors.BOLD_WHITE + " Date       " + ConsoleColors.BOLD_BLUE + "│" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BOLD_BLUE +
            "   ├────┼────────────────────┼──────────────┼────────────┤" + ConsoleColors.RESET);

        for (int i = 0; i < sessions.size(); i++) {
            StudySession s = sessions.get(i);
            String durationStr = formatDuration(s.getDuration());
            System.out.printf(ConsoleColors.BOLD_BLUE + "   │" + ConsoleColors.RESET +
                    " %-2d " + ConsoleColors.BOLD_BLUE + "│" + ConsoleColors.RESET +
                    " %-18s " + ConsoleColors.BOLD_BLUE + "│" + ConsoleColors.GREEN +
                    " %-12s " + ConsoleColors.BOLD_BLUE + "│" + ConsoleColors.CYAN +
                    " %-10s " + ConsoleColors.BOLD_BLUE + "│" + ConsoleColors.RESET + "%n",
                    i + 1,
                    truncate(s.getSubject(), 18),
                    durationStr,
                    s.getDate());
        }

        System.out.println(ConsoleColors.BOLD_BLUE +
            "   └────┴────────────────────┴──────────────┴────────────┘" + ConsoleColors.RESET);

        int totalMins = sessions.stream().mapToInt(StudySession::getDuration).sum();
        System.out.println(ConsoleColors.DIM + "   Total: " + sessions.size() + " session(s), " +
                formatDuration(totalMins) + ConsoleColors.RESET + "\n");
    }

    /**
     * Formats minutes into a human-readable string like "1h 30m".
     */
    private static String formatDuration(int minutes) {
        if (minutes >= 60) {
            return (minutes / 60) + "h " + (minutes % 60) + "m";
        }
        return minutes + " mins";
    }

    private static String truncate(String str, int maxLen) {
        if (str.length() > maxLen) {
            return str.substring(0, maxLen - 2) + "..";
        }
        return str;
    }

    /**
     * Validates a date string in yyyy-MM-dd format.
     */
    private static boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, DATE_FORMAT);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Clears the terminal screen for a clean start.
     */
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

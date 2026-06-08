import java.util.*;
import java.util.stream.Collectors;

/**
 * Provides comprehensive study analytics and statistics.
 * Analyzes study session data to generate insights including:
 * - Total study time and session counts
 * - Per-subject breakdown with visual progress bars
 * - Study streak tracking
 * - Daily/weekly averages
 */
public class StudyStatistics {

    /**
     * Displays a full statistics dashboard for the given sessions.
     */
    public static void displayDashboard(List<StudySession> sessions) {
        if (sessions.isEmpty()) {
            System.out.println("\n" + ConsoleColors.warning("📭 No study data available yet. Start logging sessions!") + "\n");
            return;
        }

        System.out.println("\n" + ConsoleColors.BOLD_PURPLE + "╔══════════════════════════════════════════════╗" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BOLD_PURPLE + "║         📊 Study Analytics Dashboard          ║" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BOLD_PURPLE + "╚══════════════════════════════════════════════╝" + ConsoleColors.RESET);

        displayOverallStats(sessions);
        displaySubjectBreakdown(sessions);
        displayStudyStreak(sessions);
        displayTopStudyDays(sessions);

        System.out.println();
    }

    /**
     * Displays overall study statistics (total sessions, total time, averages).
     */
    private static void displayOverallStats(List<StudySession> sessions) {
        int totalMinutes = sessions.stream().mapToInt(StudySession::getDuration).sum();
        int totalHours = totalMinutes / 60;
        int remainingMins = totalMinutes % 60;
        double avgPerSession = (double) totalMinutes / sessions.size();

        long uniqueDays = sessions.stream()
                .map(StudySession::getDate)
                .distinct()
                .count();

        double avgPerDay = uniqueDays > 0 ? (double) totalMinutes / uniqueDays : 0;

        System.out.println("\n" + ConsoleColors.BOLD_CYAN + "  ── Overview ──────────────────────────────────" + ConsoleColors.RESET);
        System.out.printf("  📚 Total Sessions:    %s%n", ConsoleColors.success(String.valueOf(sessions.size())));
        System.out.printf("  ⏱️  Total Study Time:  %s%n", ConsoleColors.success(totalHours + "h " + remainingMins + "m"));
        System.out.printf("  📅 Days Studied:      %s%n", ConsoleColors.info(String.valueOf(uniqueDays)));
        System.out.printf("  📈 Avg per Session:   %s%n", ConsoleColors.info(String.format("%.1f mins", avgPerSession)));
        System.out.printf("  📊 Avg per Day:       %s%n", ConsoleColors.info(String.format("%.1f mins", avgPerDay)));
    }

    /**
     * Displays a per-subject breakdown with visual progress bars.
     */
    private static void displaySubjectBreakdown(List<StudySession> sessions) {
        Map<String, Integer> subjectMinutes = new LinkedHashMap<>();
        Map<String, Integer> subjectCount = new LinkedHashMap<>();

        for (StudySession s : sessions) {
            String subject = s.getSubject();
            subjectMinutes.merge(subject, s.getDuration(), Integer::sum);
            subjectCount.merge(subject, 1, Integer::sum);
        }

        // Sort by total minutes descending
        List<Map.Entry<String, Integer>> sorted = subjectMinutes.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());

        int maxMinutes = sorted.get(0).getValue();

        System.out.println("\n" + ConsoleColors.BOLD_CYAN + "  ── Subject Breakdown ─────────────────────────" + ConsoleColors.RESET);

        String[] barColors = {
            ConsoleColors.BOLD_GREEN, ConsoleColors.BOLD_BLUE,
            ConsoleColors.BOLD_PURPLE, ConsoleColors.BOLD_YELLOW,
            ConsoleColors.BOLD_CYAN, ConsoleColors.BOLD_RED
        };

        int colorIdx = 0;
        for (Map.Entry<String, Integer> entry : sorted) {
            String subject = entry.getKey();
            int minutes = entry.getValue();
            int count = subjectCount.get(subject);
            int barLen = (int) ((double) minutes / maxMinutes * 20);
            barLen = Math.max(barLen, 1);

            String color = barColors[colorIdx % barColors.length];
            String bar = color + "█".repeat(barLen) + ConsoleColors.DIM + "░".repeat(20 - barLen) + ConsoleColors.RESET;

            System.out.printf("  %-16s %s %s (%d sessions)%n",
                    subject, bar,
                    ConsoleColors.BOLD_WHITE + minutes + "m" + ConsoleColors.RESET,
                    count);
            colorIdx++;
        }
    }

    /**
     * Calculates and displays the current study streak.
     */
    private static void displayStudyStreak(List<StudySession> sessions) {
        Set<String> studyDates = sessions.stream()
                .map(StudySession::getDate)
                .collect(Collectors.toCollection(TreeSet::new));

        List<String> sortedDates = new ArrayList<>(studyDates);
        Collections.sort(sortedDates);

        // Calculate current streak (consecutive days from most recent)
        int currentStreak = 1;
        for (int i = sortedDates.size() - 1; i > 0; i--) {
            if (isConsecutiveDay(sortedDates.get(i - 1), sortedDates.get(i))) {
                currentStreak++;
            } else {
                break;
            }
        }

        // Calculate longest streak
        int longestStreak = 1;
        int tempStreak = 1;
        for (int i = 1; i < sortedDates.size(); i++) {
            if (isConsecutiveDay(sortedDates.get(i - 1), sortedDates.get(i))) {
                tempStreak++;
                longestStreak = Math.max(longestStreak, tempStreak);
            } else {
                tempStreak = 1;
            }
        }

        System.out.println("\n" + ConsoleColors.BOLD_CYAN + "  ── Study Streaks ─────────────────────────────" + ConsoleColors.RESET);
        System.out.printf("  🔥 Current Streak:    %s%n",
                ConsoleColors.success(currentStreak + " day" + (currentStreak != 1 ? "s" : "")));
        System.out.printf("  🏆 Longest Streak:    %s%n",
                ConsoleColors.highlight(longestStreak + " day" + (longestStreak != 1 ? "s" : "")));

        // Streak visualization
        System.out.print("  ");
        for (int i = 0; i < Math.min(currentStreak, 30); i++) {
            System.out.print("🔥");
        }
        if (currentStreak > 30) {
            System.out.print(" +" + (currentStreak - 30) + " more");
        }
        System.out.println();
    }

    /**
     * Displays the top study days by total minutes.
     */
    private static void displayTopStudyDays(List<StudySession> sessions) {
        Map<String, Integer> dayMinutes = new LinkedHashMap<>();
        for (StudySession s : sessions) {
            dayMinutes.merge(s.getDate(), s.getDuration(), Integer::sum);
        }

        List<Map.Entry<String, Integer>> topDays = dayMinutes.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toList());

        System.out.println("\n" + ConsoleColors.BOLD_CYAN + "  ── Top Study Days ────────────────────────────" + ConsoleColors.RESET);
        int rank = 1;
        String[] medals = {"🥇", "🥈", "🥉", "  4.", "  5."};
        for (Map.Entry<String, Integer> entry : topDays) {
            System.out.printf("  %s %s — %s%n",
                    medals[rank - 1],
                    ConsoleColors.BOLD_WHITE + entry.getKey() + ConsoleColors.RESET,
                    ConsoleColors.info(entry.getValue() + " minutes"));
            rank++;
        }
    }

    /**
     * Checks if two date strings (yyyy-MM-dd) are consecutive days.
     */
    private static boolean isConsecutiveDay(String date1, String date2) {
        try {
            String[] parts1 = date1.split("-");
            String[] parts2 = date2.split("-");

            int y1 = Integer.parseInt(parts1[0]), m1 = Integer.parseInt(parts1[1]), d1 = Integer.parseInt(parts1[2]);
            int y2 = Integer.parseInt(parts2[0]), m2 = Integer.parseInt(parts2[1]), d2 = Integer.parseInt(parts2[2]);

            // Simple check: same month/year and day differs by 1
            if (y1 == y2 && m1 == m2 && d2 - d1 == 1) return true;

            // Month boundary check (simplified)
            if (y1 == y2 && m2 - m1 == 1 && d2 == 1) {
                int[] daysInMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
                if (y1 % 4 == 0 && (y1 % 100 != 0 || y1 % 400 == 0)) daysInMonth[2] = 29;
                return d1 == daysInMonth[m1];
            }

            // Year boundary
            if (y2 - y1 == 1 && m1 == 12 && m2 == 1 && d1 == 31 && d2 == 1) return true;

            return false;
        } catch (Exception e) {
            return false;
        }
    }
}

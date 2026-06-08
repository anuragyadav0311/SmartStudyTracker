import java.io.*;
import java.util.List;

/**
 * Manages daily study goals and tracks progress toward targets.
 * Goals are persisted to disk so they survive across app restarts.
 * Provides visual progress indicators and achievement celebrations.
 */
public class StudyGoals {

    private static final String GOALS_FILE = "../data/goals.txt";

    private int dailyGoalMinutes;

    public StudyGoals() {
        this.dailyGoalMinutes = loadGoal();
    }

    /**
     * Sets a new daily study goal in minutes.
     */
    public void setDailyGoal(int minutes) {
        this.dailyGoalMinutes = minutes;
        saveGoal(minutes);
    }

    /**
     * Returns the current daily goal in minutes, or 0 if not set.
     */
    public int getDailyGoal() {
        return dailyGoalMinutes;
    }

    /**
     * Displays goal progress for today given a list of all sessions.
     */
    public void displayGoalProgress(List<StudySession> sessions, String today) {
        if (dailyGoalMinutes <= 0) {
            System.out.println("\n" + ConsoleColors.warning("⚠️  No daily goal set. Use 'Set Study Goal' to create one!") + "\n");
            return;
        }

        int todayMinutes = sessions.stream()
                .filter(s -> s.getDate().equals(today))
                .mapToInt(StudySession::getDuration)
                .sum();

        double progress = Math.min((double) todayMinutes / dailyGoalMinutes * 100, 100);
        int progressBarLen = 30;
        int filled = (int) (progress / 100 * progressBarLen);

        System.out.println("\n" + ConsoleColors.BOLD_BLUE + "╔══════════════════════════════════════════════╗" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BOLD_BLUE + "║           🎯 Daily Study Goal                 ║" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BOLD_BLUE + "╚══════════════════════════════════════════════╝" + ConsoleColors.RESET);

        System.out.printf("\n  📅 Date:   %s%n", ConsoleColors.info(today));
        System.out.printf("  🎯 Goal:   %s%n", ConsoleColors.info(dailyGoalMinutes + " minutes"));
        System.out.printf("  ✅ Done:   %s%n", ConsoleColors.success(todayMinutes + " minutes"));

        // Progress bar
        String progressColor;
        if (progress >= 100) {
            progressColor = ConsoleColors.BOLD_GREEN;
        } else if (progress >= 60) {
            progressColor = ConsoleColors.BOLD_YELLOW;
        } else {
            progressColor = ConsoleColors.BOLD_RED;
        }

        System.out.print("\n  Progress: " + progressColor + "[");
        for (int i = 0; i < progressBarLen; i++) {
            System.out.print(i < filled ? "█" : "░");
        }
        System.out.printf("] %.1f%%%s%n", progress, ConsoleColors.RESET);

        // Achievement message
        if (progress >= 100) {
            System.out.println("\n  " + ConsoleColors.BOLD_GREEN + "🎉🎉🎉 GOAL ACHIEVED! Amazing work today! 🎉🎉🎉" + ConsoleColors.RESET);
        } else if (progress >= 75) {
            System.out.println("\n  " + ConsoleColors.BOLD_YELLOW + "💪 Almost there! Keep pushing!" + ConsoleColors.RESET);
        } else if (progress >= 50) {
            System.out.println("\n  " + ConsoleColors.info("👍 Halfway through! You're doing great!"));
        } else if (progress > 0) {
            System.out.println("\n  " + ConsoleColors.warning("🚀 Good start! Keep the momentum going!"));
        } else {
            System.out.println("\n  " + ConsoleColors.DIM + "📝 No sessions logged today yet. Time to start!" + ConsoleColors.RESET);
        }

        System.out.println();
    }

    /**
     * Saves the daily goal to file.
     */
    private void saveGoal(int minutes) {
        try {
            File file = new File(GOALS_FILE);
            file.getParentFile().mkdirs();
            BufferedWriter bw = new BufferedWriter(new FileWriter(GOALS_FILE));
            bw.write(String.valueOf(minutes));
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println(ConsoleColors.error("❌ Error saving goal: " + e.getMessage()));
        }
    }

    /**
     * Loads the daily goal from file.
     */
    private int loadGoal() {
        File file = new File(GOALS_FILE);
        if (!file.exists()) return 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(GOALS_FILE));
            String line = br.readLine();
            br.close();
            if (line != null && !line.trim().isEmpty()) {
                return Integer.parseInt(line.trim());
            }
        } catch (Exception e) {
            // Goal file corrupted or unreadable, return default
        }
        return 0;
    }
}

import java.util.Random;

/**
 * Provides motivational study quotes to inspire students.
 * Displays a random quote on application startup and can be
 * invoked anytime for a fresh dose of motivation.
 */
public class MotivationEngine {

    private static final String[] QUOTES = {
        "The expert in anything was once a beginner. — Helen Hayes",
        "Success is the sum of small efforts repeated day in and day out. — Robert Collier",
        "Don't watch the clock; do what it does. Keep going. — Sam Levenson",
        "The secret of getting ahead is getting started. — Mark Twain",
        "It always seems impossible until it's done. — Nelson Mandela",
        "Education is the passport to the future. — Malcolm X",
        "The only way to do great work is to love what you do. — Steve Jobs",
        "Study hard, for the well is deep, and our brains are shallow. — Richard Baxter",
        "Push yourself, because no one else is going to do it for you.",
        "There are no shortcuts to any place worth going. — Beverly Sills",
        "The more that you read, the more things you will know. — Dr. Seuss",
        "An investment in knowledge pays the best interest. — Benjamin Franklin",
        "Learning is not attained by chance; it must be sought for with ardor. — Abigail Adams",
        "The beautiful thing about learning is that no one can take it away from you. — B.B. King",
        "Live as if you were to die tomorrow. Learn as if you were to live forever. — Mahatma Gandhi",
        "Discipline is the bridge between goals and accomplishment. — Jim Rohn",
        "You don't have to be great to start, but you have to start to be great. — Zig Ziglar",
        "The mind is not a vessel to be filled, but a fire to be kindled. — Plutarch",
        "Small daily improvements over time lead to stunning results. — Robin Sharma",
        "Focus on being productive instead of busy. — Tim Ferriss"
    };

    private static final Random random = new Random();

    /**
     * Returns a random motivational quote.
     */
    public static String getRandomQuote() {
        return QUOTES[random.nextInt(QUOTES.length)];
    }

    /**
     * Prints a formatted motivational quote to the console.
     */
    public static void displayQuote() {
        String quote = getRandomQuote();
        System.out.println();
        System.out.println(ConsoleColors.DIM + "  ┌─── 💡 Today's Motivation ───────────────────────────┐" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BOLD_CYAN + "    \"" + quote + "\"" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.DIM + "  └────────────────────────────────────────────────────┘" + ConsoleColors.RESET);
        System.out.println();
    }
}

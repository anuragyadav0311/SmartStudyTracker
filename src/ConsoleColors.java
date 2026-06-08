/**
 * Utility class for ANSI terminal color codes.
 * Provides constants for styling console output with colors,
 * bold text, and background colors for a polished CLI experience.
 */
public class ConsoleColors {

    // Reset
    public static final String RESET = "\033[0m";

    // Text styles
    public static final String BOLD = "\033[1m";
    public static final String DIM = "\033[2m";
    public static final String UNDERLINE = "\033[4m";

    // Regular colors
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";

    // Bold colors
    public static final String BOLD_RED = "\033[1;31m";
    public static final String BOLD_GREEN = "\033[1;32m";
    public static final String BOLD_YELLOW = "\033[1;33m";
    public static final String BOLD_BLUE = "\033[1;34m";
    public static final String BOLD_PURPLE = "\033[1;35m";
    public static final String BOLD_CYAN = "\033[1;36m";
    public static final String BOLD_WHITE = "\033[1;37m";

    // Background colors
    public static final String BG_GREEN = "\033[42m";
    public static final String BG_YELLOW = "\033[43m";
    public static final String BG_BLUE = "\033[44m";
    public static final String BG_PURPLE = "\033[45m";
    public static final String BG_CYAN = "\033[46m";

    // Convenience methods for wrapping text in colors
    public static String success(String text) {
        return BOLD_GREEN + text + RESET;
    }

    public static String error(String text) {
        return BOLD_RED + text + RESET;
    }

    public static String warning(String text) {
        return BOLD_YELLOW + text + RESET;
    }

    public static String info(String text) {
        return BOLD_CYAN + text + RESET;
    }

    public static String highlight(String text) {
        return BOLD_PURPLE + text + RESET;
    }

    public static String accent(String text) {
        return BOLD_BLUE + text + RESET;
    }

    // Private constructor — utility class should not be instantiated
    private ConsoleColors() {}
}

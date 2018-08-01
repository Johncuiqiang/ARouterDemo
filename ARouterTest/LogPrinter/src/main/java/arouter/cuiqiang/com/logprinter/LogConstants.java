package arouter.cuiqiang.com.logprinter;

/**
 * Created by David小硕 on 2017/4/19.
 */

public class LogConstants {

    /**
     * Default TAG
     */
    public static final String DEFAULT_TAG = "Ling-LogPrinter";

    /**
     * Drawing toolbox
     */
    public static final char TOP_LEFT_CORNER = '╔';
    public static final char BOTTOM_LEFT_CORNER = '╚';
    public static final char MIDDLE_CORNER = '╟';
    public static final char HORIZONTAL_DOUBLE_LINE = '║';
    public static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
    public static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
    public static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    public static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    public static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;

    /**
     * Android's max limit for a log entry is ~4076 bytes,
     * so 4000 bytes is used as chunk size since default charset
     * is UTF-8
     */
    public static final int CHUNK_SIZE = 4000;

    /**
     * It is used for json pretty print
     */
    public static final int JSON_INDENT = 2;

    /**
     * The minimum stack trace index, starts at this class after two native calls.
     */
    public static final int MIN_STACK_OFFSET = 5;

    public static final int NUM_METHOD_PRINTER = 1;

}

package arouter.cuiqiang.com.logprinter;

/**
 * Created by David小硕 on 2017/4/19.
 */

public final class LogPrinter {


    private static Printer sPrinter = new PrinterImpl();


    /**
     * It is used to change the tag
     *
     * @param tag is the given string which will be used in Logger as TAG
     */
    public static Settings init(String tag) {
        sPrinter = new PrinterImpl();
        return sPrinter.init(tag);
    }

    public static void debug(String msg, Object... args){
        sPrinter.debug(msg, args);
    }

    public static void debug(Object object){
        sPrinter.debug(object);
    }

    public static void err(String message, Object... args){
        sPrinter.err(message, args);
    }

    public static void err(Throwable throwable, String message, Object... args){
        sPrinter.err(throwable, message, args);
    }

    public static void warn(String message, Object... args){
        sPrinter.warn(message, args);
    }

    public static void info(String message, Object... args){
        sPrinter.info(message, args);
    }

    public static void verbose(String message, Object... args){
        sPrinter.verbose(message, args);
    }
    public static void assertWTF(String message, Object... args){
        sPrinter.assertWTF(message, args);
    }

    public static void json(String json){
        sPrinter.json(json);
    }

    public static void json(Object obj){
        sPrinter.json(obj);
    }

    public static void xml(String xml){
        sPrinter.xml(xml);
    }

}

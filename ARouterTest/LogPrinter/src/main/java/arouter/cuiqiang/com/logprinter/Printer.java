package arouter.cuiqiang.com.logprinter;

/**
 * Created by David小硕 on 2017/4/19.
 */

public interface Printer {

    Settings init(String tag);

    void debug(String msg, Object... args);

    void debug(Object object);

    void err(String message, Object... args);

    void err(Throwable throwable, String message, Object... args);

    void warn(String message, Object... args);

    void info(String message, Object... args);

    void verbose(String message, Object... args);

    void assertWTF(String message, Object... args);

    void json(String json);

    void json(Object obj);

    void xml(String xml);

    void log(int priority, String tag, String message, Throwable throwable);

}

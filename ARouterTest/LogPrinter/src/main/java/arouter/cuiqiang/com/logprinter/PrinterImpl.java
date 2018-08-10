package arouter.cuiqiang.com.logprinter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.LogPrinter;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import static arouter.cuiqiang.com.logprinter.LogConstants.BOTTOM_BORDER;
import static arouter.cuiqiang.com.logprinter.LogConstants.CHUNK_SIZE;
import static arouter.cuiqiang.com.logprinter.LogConstants.DEFAULT_TAG;
import static arouter.cuiqiang.com.logprinter.LogConstants.HORIZONTAL_DOUBLE_LINE;
import static arouter.cuiqiang.com.logprinter.LogConstants.JSON_INDENT;
import static arouter.cuiqiang.com.logprinter.LogConstants.MIDDLE_BORDER;
import static arouter.cuiqiang.com.logprinter.LogConstants.MIN_STACK_OFFSET;
import static arouter.cuiqiang.com.logprinter.LogConstants.NUM_METHOD_PRINTER;
import static arouter.cuiqiang.com.logprinter.LogConstants.TOP_BORDER;

/**
 * Created by David小硕 on 2017/4/19.
 */

public class PrinterImpl implements Printer {

    private static final int DEBUG = 3;
    private static final int ERROR = 6;
    private static final int ASSERT = 7;
    private static final int INFO = 4;
    private static final int VERBOSE = 2;
    private static final int WARN = 5;

    private String mTag = DEFAULT_TAG;
    private Settings mSettings = new Settings();


    @Override
    public Settings init(String tag) {
        this.mTag = tag;
        return mSettings;
    }

    @Override
    public void debug(String msg, Object... args) {
        log(DEBUG, null, msg, args);
    }

    @Override
    public void debug(Object object) {
        String message;
        if (object.getClass().isArray()) {
            message = Arrays.deepToString((Object[]) object);
        } else {
            message = object.toString();
        }
        log(DEBUG, null, message);
    }

    @Override
    public void err(String message, Object... args) {
        err(null, message, args);
    }

    @Override
    public void err(Throwable throwable, String message, Object... args) {
        log(ERROR, throwable, message, args);
    }

    @Override
    public void warn(String message, Object... args) {
        log(WARN, null, message, args);
    }

    @Override
    public void info(String message, Object... args) {
        log(INFO, null, message, args);
    }

    @Override
    public void verbose(String message, Object... args) {
        log(VERBOSE, null, message, args);
    }

    @Override
    public void assertWTF(String message, Object... args) {
        log(ASSERT, null, message, args);
    }

    @Override
    public void json(String json) {
        if (LogHelper.isEmpty(json)) {
            debug("Empty/Null json content");
            return;
        }
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String message = jsonObject.toString(JSON_INDENT);
                debug(message);
                return;
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(JSON_INDENT);
                debug(message);
                return;
            }
            err("Invalid Json");
        } catch (JSONException e) {
            err("Invalid Json");
        }
    }

    @Override
    public void json(Object obj) {
        String jsonStr = new Gson().toJson(obj);
        json(jsonStr);
    }

    @Override
    public void xml(String xml) {
        if (LogHelper.isEmpty(xml)) {
            debug("Empty/Null xml content");
            return;
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            debug(xmlOutput.getWriter().toString().replaceFirst(">", ">\n"));
        } catch (TransformerException e) {
            err("Invalid xml");
        }
    }

    @Override
    public synchronized void log(int priority, String tag, String msg, Throwable throwable) {
        if (!mSettings.isShowLog()) {
            return;
        }

        if (mSettings.isSimpleLogStyle()) {
            logChunk(priority, tag, msg);
            return;
        }

        if (throwable != null && msg != null) {
            msg += " : " + LogHelper.getStackTraceString(throwable);
        }

        if (throwable != null && msg == null) {
            msg = LogHelper.getStackTraceString(throwable);
        }
        if (msg == null) {
            msg = "No message/exception is set";
        }
        int methodCount = NUM_METHOD_PRINTER;
        if (LogHelper.isEmpty(msg)) {
            msg = "Empty/NULL log message";
        }

        setTopBorder(priority, mTag);
        logHeaderContent(priority, mTag, methodCount);
        byte[] bytes = msg.getBytes();
        int length = bytes.length;
        if (length <= CHUNK_SIZE) {
            if (methodCount > 0) {
                logDivider(priority, mTag);
            }
            logContent(priority, mTag, msg);
            setBottomBorder(priority, mTag);
            return;
        }
        if (methodCount > 0) {
            logDivider(priority, mTag);
        }
        for (int i = 0; i < length; i += CHUNK_SIZE) {
            int count = Math.min(length - i, CHUNK_SIZE);
            //create a new String with system's default charset (which is UTF-8 for Android)
            logContent(priority, mTag, new String(bytes, i, count));
        }
        setBottomBorder(priority, mTag);
    }

    private synchronized void log(int priority, Throwable throwable, String msg, Object... args) {
        String message = createMessage(msg, args);
        log(priority, mTag, message, throwable);
    }

    private String createMessage(String message, Object... args) {
        return args == null || args.length == 0 ? message : String.format(message, args);
    }

    private void setTopBorder(int logType, String tag) {
        logChunk(logType, tag, TOP_BORDER);
    }

    private void setBottomBorder(int logType, String tag) {
        logChunk(logType, tag, BOTTOM_BORDER);
    }

    private void logDivider(int logType, String tag) {
        logChunk(logType, tag, MIDDLE_BORDER);
    }

    private void logContent(int logType, String tag, String chunk) {
        String[] lines = chunk.split(System.getProperty("line.separator"));
        for (String line : lines) {
            logChunk(logType, tag, HORIZONTAL_DOUBLE_LINE + " " + line);
        }
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    private void logHeaderContent(int logType, String tag, int methodCount) {

        StackTraceElement[] trace = new Throwable().getStackTrace();//Thread.currentThread().getStackTrace();
        if (mSettings.isShowThread()) {
            logChunk(logType, tag, HORIZONTAL_DOUBLE_LINE + " Thread: " + Thread.currentThread().getName());
            logDivider(logType, tag);
        }
        String level = "";
        int stackOffset = getStackOffset(trace);

        //corresponding method count with the current stack may exceeds the stack trace. Trims the count
        if (methodCount + stackOffset > trace.length) {
            methodCount = trace.length - stackOffset - 1;
        }

        for (int i = methodCount; i > 0; i--) {
            int stackIndex = i + stackOffset;
            if (stackIndex >= trace.length) {
                continue;
            }
            StringBuilder builder = new StringBuilder();
            builder.append("║ ")
                    .append(level)
                    .append(getSimpleClassName(trace[stackIndex].getClassName()))
                    .append(".")
                    .append(trace[stackIndex].getMethodName())
                    .append(" ")
                    .append(" (")
                    .append(trace[stackIndex].getFileName())
                    .append(":")
                    .append(trace[stackIndex].getLineNumber())
                    .append(")");
            level += "   ";
            logChunk(logType, tag, builder.toString());
        }
    }

    private void logChunk(int logType, String tag, String chunk) {
        String finalTag = formatTag(tag);
        switch (logType) {
            case ERROR:
                Log.e(finalTag, chunk);
                break;
            case INFO:
                Log.i(finalTag, chunk);
                break;
            case VERBOSE:
                Log.v(finalTag, chunk);
                break;
            case WARN:
                Log.w(finalTag, chunk);
                break;
            case ASSERT:
                Log.wtf(finalTag, chunk);
                break;
            case DEBUG:
                // Fall through, log debug by default
                Log.d(finalTag, chunk);
            default:
                Log.d(finalTag, chunk);
                break;
        }
    }

    private String formatTag(String tag) {
        if (!LogHelper.isEmpty(tag) && !LogHelper.equals(this.mTag, tag)) {
            return this.mTag + "-" + tag;
        }
        return this.mTag;
    }

    /**
     * Determines the starting index of the stack trace, after method calls made by this class.
     *
     * @param trace the stack trace
     * @return the stack offset
     */
    private int getStackOffset(StackTraceElement[] trace) {
        for (int i = MIN_STACK_OFFSET; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (!name.contains(LogPrinter.class.getName()) &&
                    !name.contains(Logger.class.getName()) &&
                    !name.contains(PrinterImpl.class.getName())) {
                return i;
            }
        }
        return -1;
    }

    @NonNull
    private String getSimpleClassName(String name) {
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }

}

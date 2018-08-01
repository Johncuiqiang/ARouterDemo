package arouter.cuiqiang.com.baselib.utils.file;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by cuiqiang on 2017/4/28.
 */

public class IOUtil {

    /**
     * 用于通知读写进度
     */
    public static interface ProgressListener {
        /**
         * 有新进度时会叫到此方法。
         *
         * @param bytesDone  已经读写完成的总字节数
         * @param totalBytes 需要读写的总字节数
         */
        public void onNewProgress(long bytesDone, long totalBytes);
    }

    /**
     * Writes string to file. Basically same as "echo -n $string > $filename"
     *
     * @param file   file to be written to
     * @param string string to be written
     * @throws java.io.IOException
     */
    public static void writeStringToFile(File file, String string) throws IOException {
        FileWriter out = new FileWriter(file);
        try {
            out.write(string);
        } finally {
            out.close();
        }
    }

    /**
     * Writes string to file. Basically same as "echo -n $string > $filename"
     *
     * @param fileName file to be written to
     * @param string   string to be written
     * @throws java.io.IOException
     */
    public static void writeStringToFile(String fileName, String string) throws IOException {
        FileWriter out = new FileWriter(fileName);
        try {
            out.write(string);
        } finally {
            out.close();
        }
    }


    /**
     * Write specified InputStream to file.
     * This will NOT close InputStream after write.
     *
     * @param file     file to be written to
     * @param stream   stream to be written
     * @param maxBytes 最多读取多少byte。如果不限制，传入{@link Long#MAX_VALUE}
     * @throws IOException
     * @throws InterruptedException
     */
    public static void writePartlyStreamToFile(File file, InputStream stream, long maxBytes)
            throws IOException, InterruptedException {
        writeStreamToFile(file, stream, maxBytes, false, 0, 0, 0, null);
    }

    /**
     * Write specified InputStream to file.
     * This will close InputStream after write.
     *
     * @param file          file to be written to
     * @param stream        stream to be written
     * @param append        true: 往文件末尾追加, false: 覆盖现有文件
     * @param bytesInterval 每隔多少字节通知一次进度更新
     * @param bytesDone     已经完成的字节数
     * @param bytesTotal    总共需要写入的字节数
     * @param listener      listener to be called
     * @throws IOException
     * @throws InterruptedException
     */
    public static void writeStreamToFileAndClose(File file, InputStream stream, boolean append,
                                                 long bytesInterval, long bytesDone, long bytesTotal, ProgressListener listener)
            throws IOException, InterruptedException {
        try {
            writeStreamToFile(file, stream, Long.MAX_VALUE, append, bytesInterval, bytesDone, bytesTotal, listener);
        } finally {
            IOUtil.closeSilently(stream);
        }
    }

    /**
     * Write specified InputStream to file.
     *
     * @param file          file to be written to
     * @param stream        stream to be written
     * @param maxBytes      最多读取多少byte。如果不限制，传入{@link Long#MAX_VALUE}
     * @param append        true: 往文件末尾追加, false: 覆盖现有文件
     * @param bytesInterval 每隔多少字节通知一次进度更新
     * @param bytesDone     已经完成的字节数
     * @param bytesTotal    总共需要写入的字节数
     * @param listener      listener to be called
     * @throws IOException
     * @throws InterruptedException
     */
    private static void writeStreamToFile(File file, InputStream stream, long maxBytes, boolean append,
                                          long bytesInterval, long bytesDone, long bytesTotal, ProgressListener listener)
            throws IOException, InterruptedException {
        BufferedOutputStream outputStream = null;
        try {
            File folder = file.getParentFile();
            if (folder.isDirectory() || folder.mkdirs()) {
                byte[] buffer = new byte[8192];
                int len;
                long totalBytesWritten = bytesDone;
                long lastTotalBytesWritten = bytesDone;
                long remainedBytes = maxBytes;
                outputStream = new BufferedOutputStream(new FileOutputStream(file, append));
                while (remainedBytes > 0 && (len = stream.read(buffer, 0, (int) Math.min(buffer.length, remainedBytes))) != -1) {
                    remainedBytes -= len;
                    outputStream.write(buffer, 0, len);
                    totalBytesWritten += len;
                    if (listener != null) {
                        if (totalBytesWritten - lastTotalBytesWritten >= bytesInterval
                                || totalBytesWritten >= bytesTotal) {
                            listener.onNewProgress(totalBytesWritten, bytesTotal);
                            lastTotalBytesWritten = totalBytesWritten;
                        }
                    }
                    if (Thread.interrupted()) {
                        throw new InterruptedException();
                    }
                }
            } else {
                throw new IOException("file creation failed.");
            }
        } finally {
            closeSilently(outputStream);
        }
    }

    /**
     * 将一个byte数组写入到文件
     *
     * @param file  要保存到的文件
     * @param bytes 要保存的数据
     * @throws IOException
     */
    public static void writeBytesToFile(File file, byte[] bytes) throws IOException {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            os.write(bytes);
        } finally {
            closeSilently(os);
        }
    }

    /**
     * 将一个文件里的数据读入到byte数组
     *
     * @param file 要读入的文件
     * @return 文件对应的byte数组
     */
    public static byte[] getBytesFromFile(File file) throws IOException {
        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
            throw new IOException("File is too large!");
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;

        InputStream is = new FileInputStream(file);
        try {
            while (offset < bytes.length
                    && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }
        } finally {
            is.close();
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
        return bytes;
    }

    /**
     * 将一个InputStream转换成String
     *
     * @param stream stream to be read
     * @return String
     * @throws IOException
     */
    public static String streamToString(InputStream stream) throws IOException {
        if (stream == null) {
            return null;
        }

        int len;
        byte[] buffer = new byte[4096];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            while ((len = stream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            return outputStream.toString("UTF-8");
        } finally {
            IOUtil.closeSilently(outputStream);
        }
    }

    /**
     * 将一个InputStream转换成JSONObject
     *
     * @param stream stream to be read
     * @return JSONObject
     * @throws IOException
     * @throws JSONException
     */
    public static JSONObject streamToJSONObject(InputStream stream) throws IOException, JSONException {
        return new JSONObject(streamToString(stream));
    }

    /**
     * 从一个文件中读取数据并转换成JSONObject
     *
     * @param file file to be read
     * @return JSONObject
     * @throws IOException
     * @throws JSONException
     */
    public static JSONObject fileToJSONObject(File file) throws IOException, JSONException {
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
            return streamToJSONObject(in);
        } finally {
            IOUtil.closeSilently(in);
        }
    }

    /**
     * 关闭一个Closable，不会抛异常。
     *
     * @param closeable the object which to be closed
     */
    public static void closeSilently(Closeable closeable) {
        if (closeable == null) {
            return;
        }

        try {
            closeable.close();
        } catch (Throwable ignored) {
        }
    }

    /**
     * 将InputStream的内容写入到OutputStream中。
     *
     * @param in  InputStream
     * @param out OutputStream
     * @throws IOException
     */
    public static void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[8192];
        int len;
        while ((len = in.read(buffer, 0, buffer.length)) != -1) {
            out.write(buffer, 0, len);
        }
    }

}

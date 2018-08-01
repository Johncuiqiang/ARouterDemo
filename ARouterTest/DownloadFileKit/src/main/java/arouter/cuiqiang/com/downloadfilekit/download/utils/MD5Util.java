package arouter.cuiqiang.com.downloadfilekit.download.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;

/**
 * Created by cuiqiang on 18-2-24.
 *
 * @author cuiqiang
 */

public class MD5Util {

    /**
     * 计算md5值使用的buffer
     * 经过测试，每次创建buffer会耗费30ms左右的时间
     * 把buffer共用，可以提升计算多个文件md5值的速度
     */
    private static byte[] md5Buffer = new byte[1024 * 1024];

    public static String getFileMd5(File file) {
        FileInputStream fileInputStream = null;
        MessageDigest messageDigest = null;
        try {
            fileInputStream = new FileInputStream(file);
            messageDigest = MessageDigest.getInstance("MD5");
            int length = 0;
            while (fileInputStream.available() > 0) {
                length = fileInputStream.read(md5Buffer);
                messageDigest.update(md5Buffer, 0, length);
            }
            return stringToHexString(bytesToString(messageDigest.digest()));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return "";
        } catch (Throwable ex) {
            ex.printStackTrace();
            return "";
        } finally {
            try {
                if (null != fileInputStream) {
                    fileInputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    /*
     * 字节数组转字符串
     */
    public static String bytesToString(byte[] b) throws Exception {
        return new String(b,"UTF-8");
    }

    /*
     * 字符串转16进制字符串
     */
    public static String stringToHexString(String s) {
        return bytesToHexString(string2Bytes(s));
    }

    /*
     * 字符串转字节数组
     */
    public static byte[] string2Bytes(String s) {
        return s.getBytes();
    }

    /*
     * 字节数组转16进制字符串
     */
    public static String bytesToHexString(byte[] b) {
        String r = "";

        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            r += hex.toUpperCase();
        }

        return r;
    }
}

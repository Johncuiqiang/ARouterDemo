package arouter.cuiqiang.com.baselib.utils.file;

import android.util.Base64;

/**
 * Created by cuiqiang on 17-11-23.
 *
 * @author cuiqiang
 */

public class EncryptionUtil {

    /**
     * See: http://brockallen.com/2014/10/17/base64url-encoding/
     *
     * @param str string
     * @return a new Base64 encoded string based on the hashed string
     */
    public static String base64UrlEncode(String str) {
        // 在这里使用的是encode方式，返回的是byte类型加密数据，可使用new String转为String类型
        return new String(Base64.encode(str.getBytes(), Base64.DEFAULT));
    }
}

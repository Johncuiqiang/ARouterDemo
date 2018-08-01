package arouter.cuiqiang.com.baselib.utils.file;

import android.os.Environment;

/**
 * Created by cuiqiang on 2016/9/28.
 */

public class SDcardUtil {

    /**
     * 判断sdcard是否存在
     *
     * @return
     */
    public static boolean isSdcardExists() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取sdcard路径
     *
     * @return
     */
    public static String getSdcardPath() {
        if (isSdcardExists()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return "";
    }
}

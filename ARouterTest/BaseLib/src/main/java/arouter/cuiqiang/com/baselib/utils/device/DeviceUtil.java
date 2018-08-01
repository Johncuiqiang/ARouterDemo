package arouter.cuiqiang.com.baselib.utils.device;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import arouter.cuiqiang.com.baselib.LingContext;

/**
 * Created by cuiqiang on 2018/7/20.
 *
 */
public class DeviceUtil {

    private static Context sContext = LingContext.getApplicatonContext();
    private static String sImei;
    private static String sMac;
    private static String sSubscriberId;

    private static float sDensity = 0.0f;
    private static float sScreenWidth = 0.0f;
    private static float sScreenHeight = 0.0f;

    /**
     * 获取手机imei
     *
     * @return
     */
    public static String getImei() {
        if (TextUtils.isEmpty(sImei)) {
            TelephonyManager phoneManager = (TelephonyManager) sContext
                    .getSystemService(Context.TELEPHONY_SERVICE);
            sImei = phoneManager.getDeviceId();
            if (TextUtils.isEmpty(sImei)) {
                sImei = "NoImei";
            }
        }
        return sImei;
    }

    /**
     * 获取imsi
     *
     * @return
     */
    public static String getSubscriberId() {
        if (TextUtils.isEmpty(sSubscriberId)) {
            TelephonyManager phoneManager = (TelephonyManager) sContext
                    .getSystemService(Context.TELEPHONY_SERVICE);
            sSubscriberId = phoneManager.getSubscriberId();
        }
        return sSubscriberId;
    }

    /**
     * 获取设备wifi mac地址
     *
     * @return
     */
    public static String getMac() {
        if (TextUtils.isEmpty(sMac)) {
            WifiManager wifiManager = (WifiManager) sContext
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager == null ? null : wifiManager
                    .getConnectionInfo();
            if (wifiInfo != null) {
                sMac = wifiInfo.getMacAddress();
            }
        }
        return sMac;
    }

    /**
     * 获取运营商名称
     *
     * @return
     */
    public static String getOperatorName() {
        String returnValue = null;
        TelephonyManager phoneManager = (TelephonyManager) sContext
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = phoneManager.getSubscriberId();
        if (TextUtils.isEmpty(imsi)) {
            returnValue = "";
        } else {
            if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
                returnValue = "中国移动";
            } else if (imsi.startsWith("46001")) {
                returnValue = "中国联通";
            } else if (imsi.startsWith("46003")) {
                returnValue = "中国电信";
            }
        }
        return returnValue;
    }

    /**
     * 获取设备的屏幕宽度
     *
     * @return 像素
     */
    public static float getScreenWidth() {
        if (sScreenWidth == 0.0f) {
            getInfoFromDisplayMetrics();
        }
        return sScreenWidth;
    }

    /**
     * 获取设备的屏幕高度
     *
     * @return 像素
     */
    public static float getScreenHeight() {
        if (sScreenHeight == 0.0f) {
            getInfoFromDisplayMetrics();
        }
        return sScreenHeight;
    }

    /**
     * 获取设备屏幕密度
     *
     * @return
     */
    public static float getDensity() {
        if (Float.compare(sDensity, 0.0f) == 0) {
            getInfoFromDisplayMetrics();
        }
        return sDensity;
    }

    private static void getInfoFromDisplayMetrics() {
        DisplayMetrics metrics = sContext.getResources().getDisplayMetrics();
        sDensity = metrics.density;
        sScreenWidth = metrics.widthPixels;
        sScreenHeight = metrics.heightPixels;
    }


    /**
     * 获取sdcard是否存在
     *
     * @return
     */
    public static boolean isSdcardExists() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 获得sdcard路径
     *
     * @return
     */
    public static String getSdcardPath() {
        if (DeviceUtil.isSdcardExists()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }

        return null;
    }

    /**
     * 得到cpu温度
     * @return
     */
    private static String getCpuTemp(){
        String temp = "0";
        try {
            FileReader fileReader = new FileReader("/sys/class/thermal/thermal_zone9/subsystem/thermal_zone9/temp");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            temp = bufferedReader.readLine();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }
}

package arouter.cuiqiang.com.baselib.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.LinkedList;

import arouter.cuiqiang.com.baselib.LingContext;


/**
 * Created by cuiqiang on 2018/7/20.
 */

public class NetStatusUtil {

    private static final String TAG = "NetStatusUtil";

    private static int NET_STATUS = 0;
    private static LinkedList<INetworkCallBack> mObservers = new LinkedList<>();
    private static boolean isConnectingWifi = false;

    /**
     * 网络改变
     */
    public static void changed() {
        int netStatus = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) LingContext.getApplicatonContext().
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if ((networkInfo != null) && (networkInfo.isConnectedOrConnecting())) {
            int type = networkInfo.getType();
            String typeName = networkInfo.getTypeName();
            if (typeName.equalsIgnoreCase("WIFI")) {
                netStatus = 1;
            } else if (typeName.equalsIgnoreCase("MOBILE")) {
                netStatus = 2;
            }
        }
        setStatus(netStatus);
        notifyAllObserver();
    }

    /**
     *  根据返回的intent设置网络状态
     */
    public static void setNetworkStatus(Intent intent) {
        NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if (networkInfo == null) {
            return;
        }
        NetworkInfo.State state = networkInfo.getState();
        switch (state) {
            case CONNECTING:
                Log.d(TAG, "wifi正在连接");
                isConnectingWifi = true;
                break;
            case CONNECTED:
                Log.d(TAG, "wifi已连接");
                isConnectingWifi = false;
                break;
            case SUSPENDED:
                Log.d(TAG, "wifi正在获取ip地址");
                isConnectingWifi = true;
                break;
            case DISCONNECTING:
                Log.d(TAG, "wifi正在断开");
                isConnectingWifi = false;
                break;
            case DISCONNECTED:
                Log.d(TAG, "wifi已断开");
                isConnectingWifi = false;
                break;
            default:
                break;
        }

    }

    /**
     * 是否正在连接网络
     *
     * @return true正在连接 false未连接
     */
    public static boolean isConnectingNetwork() {
        return isConnectingWifi;
    }

    /**
     * 是否有网络
     *
     * @return true有 false没有
     */
    public static boolean hasNetwork() {
        return NET_STATUS != 0;
    }

    /**
     * 是否是wifi
     *
     * @return true是 false不是
     */
    public static boolean isWifi() {
        return NET_STATUS == 1;
    }

    /**
     * 设置网络状态
     * @param status 0无网络 1wifi  2移动
     */
    private static void setStatus(int status) {
        NET_STATUS = status;
    }

    /**
     * 改变所有回调的网络状态
     */
    private static void notifyAllObserver() {
        for (INetworkCallBack networkCallBack : mObservers) {
            networkCallBack.onChange();
        }
    }

    /**
     * 清楚所有的回调
     */
    public static void clear() {
        mObservers.clear();
    }

    /**
     * 添加回调函数
     */
    public static void addNetworkCallBack(INetworkCallBack networkCallBack) {
        if (!mObservers.contains(networkCallBack)) {
            mObservers.add(networkCallBack);
        }
    }

    /**
     * 移除回调函数
     */
    public static void removeNetWorkCallback(INetworkCallBack networkCallBack) {
        mObservers.remove(networkCallBack);

    }

    public interface INetworkCallBack {

        /**
         * 网络状态改变
         */
        void onChange();
    }

}

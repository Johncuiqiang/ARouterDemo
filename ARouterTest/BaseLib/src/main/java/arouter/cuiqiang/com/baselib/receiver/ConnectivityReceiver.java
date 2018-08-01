package arouter.cuiqiang.com.baselib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import arouter.cuiqiang.com.baselib.utils.NetStatusUtil;


/**
 * Created by cuiqiang on 2018/7/20.
 */
public class ConnectivityReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (!"android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
            return;
        }
        NetStatusUtil.changed();
        NetStatusUtil.setNetworkStatus(intent);
    }
}

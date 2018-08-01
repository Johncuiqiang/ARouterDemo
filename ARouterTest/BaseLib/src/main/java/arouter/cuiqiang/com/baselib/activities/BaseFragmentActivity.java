package arouter.cuiqiang.com.baselib.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.WindowManager;

import arouter.cuiqiang.com.baselib.ActivityManager;
import arouter.cuiqiang.com.baselib.IObjectDescription;
import arouter.cuiqiang.com.baselib.handler.SafeHandler;
import arouter.cuiqiang.com.baselib.view.LingAlertDialog;


/**
 * Created by cuiqiang on 2018/7/20.
 */

public abstract class BaseFragmentActivity extends FragmentActivity implements IObjectDescription,SafeHandler.IHandlerMessage{

    protected String mUniqueIdentifier = "";
    protected Handler mDefaultHandler;
    /**
     * 设置状态栏是否可见
     *
     * true 可见 false不可见
     */
    protected boolean isVisibleStatusBar = true;
    private LingAlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUniqueIdentifier = String.valueOf(hashCode());
        mDefaultHandler = new SafeHandler(this);
        ActivityManager.addActivity(this);
        mDialog = new LingAlertDialog(this);
        setActivityParam();
        init(savedInstanceState);
    }

    /**
     * 设置界面参数,需要改变以下设置只要写在重写onCreate并写在super.onCreate(savedInstanceState);之前
     */
    private void setActivityParam(){
        if (!isVisibleStatusBar){
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    protected void showLoading(String msg) {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.setLoadingView(msg);
            mDialog.show();
        }
    }

    protected void hideLoading() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }

    @Override
    public void handlerMessage(Message msg) {

    }

    @Override
    public String getUniqueIdentifier() {
        return mUniqueIdentifier;
    }

    protected boolean visibleStatusBar(){
        return false;
    }

    protected abstract void init(Bundle savedInstanceState);
}

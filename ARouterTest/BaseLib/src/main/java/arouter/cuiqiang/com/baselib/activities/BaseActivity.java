package arouter.cuiqiang.com.baselib.activities;


import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import arouter.cuiqiang.com.baselib.ActivityManager;
import arouter.cuiqiang.com.baselib.IObjectDescription;
import arouter.cuiqiang.com.baselib.handler.SafeHandler;
import arouter.cuiqiang.com.baselib.view.LingAlertDialog;


/**
 * Created by cuiqiang on 2018/7/20.
 */

public abstract class BaseActivity extends AppCompatActivity implements IObjectDescription,SafeHandler.IHandlerMessage{

    protected String mUniqueIdentifier = "";
    protected Handler mDefaultHandler;
    /**
     * 设置状态栏是否可见
     *
     * true 可见 false不可见
     */
    protected boolean isVisibleStatusBar = true;
    /**
     * 设置标题栏是否可见
     *
     * true 可见 false不可见
     */
    protected boolean isVisibleTitleBar  = true;
    /**
     * 加载进度框
     */
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUniqueIdentifier = String.valueOf(hashCode());
        mDefaultHandler = new SafeHandler(this);
        ActivityManager.addActivity(this);
        setActivityParam();
        init(savedInstanceState);
    }

    /**
     * 展示进度框
     */
    protected void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        if (!mProgressDialog.isShowing()){
            mProgressDialog.show();
        }
    }

    /**
     * 展示进度框
     * @param message 进度框文字
     */
    protected void showLoading(@NonNull String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.setMessage(message);
        if (!mProgressDialog.isShowing()){
            mProgressDialog.show();
        }
    }

    /**
     * 隐藏进度框
     */
    protected void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    /**
     * 设置界面参数,需要改变以下设置只要写在重写onCreate并写在super.onCreate(savedInstanceState);之前
     */
    private void setActivityParam(){
        if (!isVisibleStatusBar){
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        if (!isVisibleTitleBar){
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null){
                actionBar.hide();
            }
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
    public String getUniqueIdentifier(){
        return mUniqueIdentifier;
    }

    public void handleMessage(Message msg) {}

    protected abstract void init(Bundle savedInstanceState);

}

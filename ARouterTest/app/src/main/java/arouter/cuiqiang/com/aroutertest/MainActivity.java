package arouter.cuiqiang.com.aroutertest;

import android.Manifest;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import arouter.cuiqiang.com.aroutertest.presenter.MainPresenter;
import arouter.cuiqiang.com.baselib.activities.BaseActivity;
import arouter.cuiqiang.com.logprinter.LogPrinter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

import static arouter.cuiqiang.com.applicationconfiglib.constant.ApplicationCodeConstant.DOWN_DONE;
import static arouter.cuiqiang.com.applicationconfiglib.constant.ApplicationCodeConstant.DOWN_LOADING;

@RuntimePermissions
public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.btn_navigation)
    Button mBtnNavigation;
    @BindView(R.id.btn_navigation_params)
    Button mBtnNavigationParams;
    @BindView(R.id.btn_user_params)
    Button mBtnNavigationUser;
    @BindView(R.id.btn_download)
    Button mBtnDownload;
    @BindView(R.id.btn_pause)
    Button mBtnPause;
    @BindView(R.id.btn_test)
    Button mBtnTest;

    private Unbinder mBinder;
    private MainPresenter mMainPresenter;

    @Override
    protected void init(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        initData();
    }

    private void initData() {
        mBinder = ButterKnife.bind(this);
        mMainPresenter = new MainPresenter(this);
        requestDownloadPermission();
        requestPhonePermission();
        LogPrinter.info("onCreate");
    }

    /**
     * 请求下载相关的权限
     */
    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    void requestDownloadPermission() {
        //检查是否获取该权限
        mMainPresenter.initDownload(mDefaultHandler);
    }

    /**
     * 请求读取手机状态相关的权限
     */
    @NeedsPermission(Manifest.permission.READ_PHONE_STATE)
    void requestPhonePermission() {
        Log.d("MainActivity", "READ_PHONE_STATE");
    }

    /**
     * 跳转到测试界面
     */
    @OnClick(R.id.btn_navigation)
    void turnTestActivity(View view) {
        mMainPresenter.turnTestActivity();
    }

    /**
     * 携带参数跳转到测试界面
     */
    @OnClick(R.id.btn_navigation_params)
    void turnTestParamActivity(View view) {
        mMainPresenter.turnTestParamActivity();
    }

    /**
     * 携带参数跳转到个人中心界面
     */
    @OnClick(R.id.btn_user_params)
    void turnUserActivity(View view) {
        mMainPresenter.turnUserActivity();
    }

    /**
     * 下载
     */
    @OnClick(R.id.btn_download)
    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    void download(View view) {
        mMainPresenter.download();
        LogPrinter.info("download");
    }

    /**
     * 暂停
     */
    //@RequiresPermission(Manifest.permission.BLUETOOTH)
    @OnClick(R.id.btn_pause)
    void pause(View view) {
        hideLoading();
        mMainPresenter.pause();
    }

    @OnClick(R.id.btn_test)
    void test(View view) {
     
    }

    @Override
    public void handlerMessage(Message msg) {
        switch (msg.what) {
            case DOWN_LOADING:
                showLoading(msg.obj + "");
                break;
            case DOWN_DONE:
                hideLoading();
                break;
            default:
                break;
        }
    }

    /**
     * 用户拒绝授权回调（可选）
     */
    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    void setDownloadUnAvailable() {
        mMainPresenter.setDownloadAvailable(false);
    }

    /**
     * 用户勾选了“不再提醒”时调用（可选）
     */
    @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    void setDownloadNeverAvailable() {
        mMainPresenter.setDownloadAvailable(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideLoading();
        mMainPresenter.onDestroy();
        mBinder.unbind();
    }

}

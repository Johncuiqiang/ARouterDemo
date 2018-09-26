package arouter.cuiqiang.com.aroutertest;

import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import arouter.cuiqiang.com.aroutertest.presenter.MainPresenter;
import arouter.cuiqiang.com.baselib.activities.BaseActivity;
import arouter.cuiqiang.com.logprinter.LogPrinter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import static arouter.cuiqiang.com.applicationconfiglib.constant.ARouterConstant.ANIM_ACTIVITY;
import static arouter.cuiqiang.com.applicationconfiglib.constant.ARouterConstant.GLITCH_ACTIVITY;
import static arouter.cuiqiang.com.applicationconfiglib.constant.ApplicationCodeConstant.DOWN_DONE;
import static arouter.cuiqiang.com.applicationconfiglib.constant.ApplicationCodeConstant.DOWN_LOADING;

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
    @BindView(R.id.btn_partcle)
    Button mBtnFace;
    @BindView(R.id.btn_flower)
    Button mBtnFlower;

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
    void requestDownloadPermission() {
        //检查是否获取该权限
        mMainPresenter.initDownload(mDefaultHandler);
    }

    /**
     * 请求读取手机状态相关的权限
     */
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

    @OnClick(R.id.btn_partcle)
    void turnPartcleActivity(View view) {
        mMainPresenter.turnActivity(ANIM_ACTIVITY);
    }

    @OnClick(R.id.btn_flower)
    void turnFlowerActivty(View view){
        mMainPresenter.turnActivity(GLITCH_ACTIVITY);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideLoading();
        mMainPresenter.onDestroy();
        mBinder.unbind();
    }

}

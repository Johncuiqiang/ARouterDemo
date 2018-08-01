package arouter.cuiqiang.com.aroutertest.presenter;

import android.content.Context;
import android.os.Handler;
import com.alibaba.android.arouter.launcher.ARouter;
import arouter.cuiqiang.com.aroutertest.manager.DownloadManager;
import static arouter.cuiqiang.com.applicationconfiglib.constant.ARouterConstant.TEST_ACTIVITY;


/**
 * @author cuiqiang
 * @since 2018/7/25
 */
public class MainPresenter implements IMainPresenter{

    private Context mContext;
    private boolean isDownloadAvailable = true;

    public MainPresenter(Context context){
        mContext = context;
    }

    @Override
    public void initDownload(Handler handler){
        if (isDownloadAvailable) {
            DownloadManager.getInstance().init(mContext);
            DownloadManager.getInstance().setHandler(handler);
        }
    }

    @Override
    public void turnTestActivity(){
        ARouter.getInstance().build(TEST_ACTIVITY).navigation();
    }

    @Override
    public void turnTestParamActivity(){
        ARouter.getInstance().build(TEST_ACTIVITY)
                .withString("name", "测试界面的参数")
                .withInt("age", 23)
                .navigation();
    }

    @Override
    public void turnUserActivity(){
        ARouter.getInstance().build("/user/activity")
                .withString("name", "测试界面的参数")
                .withInt("age", 23)
                .navigation();
    }

    @Override
    public void download(){
        if (isDownloadAvailable) {
            DownloadManager.getInstance().download();
        }
    }

    @Override
    public void pause() {
        if (isDownloadAvailable){
            DownloadManager.getInstance().pause();
        }
    }

    @Override
    public void onDestroy() {
        DownloadManager.getInstance().unregisterReceiver();
    }

    @Override
    public void setDownloadAvailable(boolean isAvailable) {
        isDownloadAvailable = isAvailable;
    }


}

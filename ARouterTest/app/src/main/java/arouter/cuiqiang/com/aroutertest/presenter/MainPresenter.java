package arouter.cuiqiang.com.aroutertest.presenter;

import android.content.Context;
import android.os.Handler;
import com.alibaba.android.arouter.launcher.ARouter;
import arouter.cuiqiang.com.aroutertest.manager.DownloadManager;
import arouter.cuiqiang.com.aroutertest.presenter.IMainPresenter;

import static arouter.cuiqiang.com.applicationconfiglib.constant.ARouterConstant.ANIM_ACTIVITY;
import static arouter.cuiqiang.com.applicationconfiglib.constant.ARouterConstant.OPENGL_ACTIVTY;
import static arouter.cuiqiang.com.applicationconfiglib.constant.ARouterConstant.TEST_ACTIVITY;
import static arouter.cuiqiang.com.applicationconfiglib.constant.ARouterConstant.USER_ACTIVITY;


/**
 * @author cuiqiang
 * @since 2018/7/25
 */
public class MainPresenter implements IMainPresenter {

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
        ARouter.getInstance().build(OPENGL_ACTIVTY).navigation();
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
        ARouter.getInstance().build(USER_ACTIVITY)
                .withString("name", "测试界面的参数")
                .withInt("age", 23)
                .navigation();
    }

    @Override
    public void turnActivity(String path){
        ARouter.getInstance().build(path)
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

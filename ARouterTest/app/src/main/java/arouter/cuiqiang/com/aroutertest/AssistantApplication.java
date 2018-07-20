package arouter.cuiqiang.com.aroutertest;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author cuiqiang
 * @since 2018/7/19
 */
public class AssistantApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(this);
    }

}

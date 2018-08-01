package arouter.cuiqiang.com.aroutertest;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

import arouter.cuiqiang.com.baselib.LingContext;
import arouter.cuiqiang.com.logprinter.LogPrinter;

/**
 * @author cuiqiang
 * @since 2018/7/19
 */
public class AssistantApplication extends Application {

    private static final String TAG = "AssistantDemo";

    @Override
    public void onCreate() {
        super.onCreate();
        LingContext.init(this);
        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(this);
        LogPrinter.init(TAG).setIsShowLog(true)
                            .setIsShowMethodName(true)
                            .setIsShowThread(true);
    }

}

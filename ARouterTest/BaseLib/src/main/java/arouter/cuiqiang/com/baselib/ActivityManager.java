package arouter.cuiqiang.com.baselib;

import android.app.Activity;
import android.os.Process;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuiqiang on 2018/7/20.
 */

public class ActivityManager {

    private static List<Activity> mCacheActivities = new ArrayList<Activity>();

    /**
     * 添加activity到缓存中
     *
     * @param activity 需要添加的activity
     * @return true成功添加 false添加失败
     */
    public static final boolean addActivity(Activity activity) {
        return mCacheActivities.add(activity);
    }

    /**
     * 将activity从缓存中移除
     *
     * @param activity 需要移除的activity
     * @return true成功移除 false移除失败
     */
    public static final boolean removeActivity(Activity activity) {
        return mCacheActivities.remove(activity);
    }

    /**
     * 获取当前的activity
     *
     * @return activty对象
     */
    public static Activity getForegroundActivity() {
        int size = mCacheActivities.size();
        if (size > 0) {
            return mCacheActivities.get(size - 1);
        }
        return null;
    }

    /**
     * 结束所有的activity
     */
    public static void finishAllActivity() {
        if (!mCacheActivities.isEmpty()) {
            ((Activity) mCacheActivities.get(mCacheActivities.size() - 1))
                    .moveTaskToBack(true);
            for (Activity activity : mCacheActivities) {
                activity.finish();
            }
        }
    }

    /**
     * 退出应用
     */
    public static void exit() {
        finishAllActivity();
        Process.killProcess(Process.myPid());
    }
}

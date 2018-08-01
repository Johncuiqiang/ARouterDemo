package arouter.cuiqiang.com.baselib.threadpool;

import android.text.TextUtils;


/**
 * Created by David小硕 on 2016/9/28.
 */

public abstract class BaseTask {

    /* task 级别 */
    public static final int TASK_LEVEL_NORMAL = 0x00;
    public static final int TASK_LEVEL_HIGH = 0x01;
    public static final int TASK_LEVEL_BACKGROUND = 0x02;
    public static final int TASK_OK = 200;
    public static final int TASK_NETWORK_ERR = 0x01;
    public static final int TASK_ERR = 0x02;
    public static final int TASK_NOMORE = 0x03;

    private String mUrl;
    private int mPiority;
    private int mTypte;
    private boolean mIsNeedNetWork;
    private ITaskListener mListener;

    public BaseTask setUrl(String url) {
        this.mUrl = url;
        if (TextUtils.isEmpty(mUrl)) {
            this.mIsNeedNetWork = true;
        }
        return this;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public BaseTask setPiority(int piority) {
        this.mPiority = piority;
        return this;
    }

    public int getPiority() {
        return this.mPiority;
    }

    public BaseTask setType(int type) {
        this.mTypte = type;
        return this;
    }

    public int getType() {
        return this.mTypte;
    }

    public BaseTask setNeedNetWork(boolean isNeedNetWork) {
        this.mIsNeedNetWork = isNeedNetWork;
        return this;
    }

    public boolean isNeedNetWork() {
        return this.mIsNeedNetWork;
    }

    public BaseTask setTaskListener(ITaskListener listener) {
        this.mListener = listener;
        return this;
    }

    public ITaskListener getTaskListener() {
        return this.mListener;
    }


    public abstract void run();

}

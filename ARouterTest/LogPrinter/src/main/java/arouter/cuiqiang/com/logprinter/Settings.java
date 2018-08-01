package arouter.cuiqiang.com.logprinter;

/**
 * Created by David小硕 on 2017/4/19.
 */

public final class Settings {

    private boolean mIsShowLog = true;
    private boolean mIsShowThread = true;
    private boolean mIsShowMethodName = true;

    private boolean mIsSimpleLogStyle = false;

    public void setIsShowThread(boolean isShowThread) {
        this.mIsShowThread = isShowThread;
    }

    public Settings setIsShowMethodName(boolean isShowMethodName) {
        this.mIsShowMethodName = isShowMethodName;
        return this;
    }

    public boolean isShowThread() {
        return mIsShowThread;
    }

    public boolean isShowMethodName() {
        return mIsShowMethodName;
    }

    public boolean isShowLog() {
        return mIsShowLog;
    }

    public Settings setIsShowLog(boolean isShowLog) {
        this.mIsShowLog = isShowLog;
        return this;
    }

    public boolean isSimpleLogStyle() {
        return mIsSimpleLogStyle;
    }

    public Settings setIsSimpleLogStyle(boolean isSimpleLogStyle) {
        this.mIsSimpleLogStyle = isSimpleLogStyle;
        return this;
    }
}

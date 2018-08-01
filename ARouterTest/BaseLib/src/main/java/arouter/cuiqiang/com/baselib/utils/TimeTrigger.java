package arouter.cuiqiang.com.baselib.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 时间触发器，可在一定延时之后触发
 *
 * Created by cuiqiang on 2018/7/20.
 */
public class TimeTrigger {

    private static final String TAG = "TimeTrigger";

    public interface TriggerListener {
        public void onTrigger();
    }

    class TriggerTask extends TimerTask {

        @Override
        public void run() {
            mTimer.cancel();
            mTimer = null;
            mTimerTask = null;

            if (null != mListener) {
                mListener.onTrigger();
            }
        }
    }

    private long mPeriod;

    private long mDelay;

    private Timer mTimer;

    private TimerTask mTimerTask;

    private TriggerListener mListener;

    private TriggerRepeatTask mTriggerRepeatTask;

    /**
     * 构建函数
     *
     * @param delay 延时，单位：ms。将在timeLen之后触发
     */
    public TimeTrigger(long delay) {
        mDelay = delay;
    }

    public TimeTrigger(long delay, long period) {
        mDelay = delay;
        mPeriod = period;
    }

    public void setListener(TriggerListener listener) {
        mListener = listener;
    }

    class TriggerRepeatTask extends TimerTask {

        @Override
        public void run() {
            if (null != mListener) {
                mListener.onTrigger();
            }
        }
    }

    /**
     * 开启触发器
     */
    public void start() {
        if (null == mTimer) {
            mTimer = new Timer();
            mTimerTask = new TriggerTask();
            mTimer.schedule(mTimerTask, mDelay);

        }
    }

    /**
     * 开启触发器
     */
    public void startRepeat() {
        if (null == mTimer) {
            mTimer = new Timer();
            mTriggerRepeatTask = new TriggerRepeatTask();
            mTimer.schedule(mTriggerRepeatTask, mDelay, mPeriod);
            //DebugLog.LogD(TAG, "TimeTrigger start");
        }
    }

    /**
     * 重置触发器到初始状态，重新计时
     *
     * @return 触发器开启时返回true，关闭状态下返回false
     */
    public boolean reset() {
        if (null != mTimer) {
            if (null != mTimerTask) {
                mTimerTask.cancel();
                mTimerTask = new TriggerTask();
                mTimer.schedule(mTimerTask, mDelay);

                return true;
            }
        }
        return false;
    }

    /**
     * 取消触发器
     */
    public void cancel() {
        if (null != mTimer) {
            if (null != mTimerTask) {
                mTimerTask.cancel();
            }
            mTimer.cancel();
            mTimer = null;
            mTimerTask = null;

        }
    }

    /**
     * 取消触发器
     */
    public void cancelRepeat() {
        if (null != mTimer) {
            if (null != mTriggerRepeatTask) {
                mTriggerRepeatTask.cancel();
            }
            mTimer.cancel();
            mTimer = null;
            mTriggerRepeatTask = null;
            //DebugLog.LogD(TAG, "TimeTrigger cancel");
        }
    }

}

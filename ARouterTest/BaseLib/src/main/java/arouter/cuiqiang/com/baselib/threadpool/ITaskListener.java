package arouter.cuiqiang.com.baselib.threadpool;

/**
 * Created by David小硕 on 2016/9/28.
 */

public interface ITaskListener {

    public void onFinishedListener(BaseTask task, int resultCode, Object result);

}

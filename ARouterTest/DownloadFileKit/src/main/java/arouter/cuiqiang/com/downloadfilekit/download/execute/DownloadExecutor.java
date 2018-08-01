package arouter.cuiqiang.com.downloadfilekit.download.execute;


import android.content.Intent;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import arouter.cuiqiang.com.downloadfilekit.download.DownloadConstant;
import arouter.cuiqiang.com.downloadfilekit.download.DownloadStatus;
import arouter.cuiqiang.com.downloadfilekit.download.utils.LogUtils;

/**
 * @author   www.yaoxiaowen.com
 * time:  2017/12/20 18:36
 * @since 1.0.0
 */
public class DownloadExecutor extends ThreadPoolExecutor{
    
    public static final String TAG = "DownloadExecutor";
    
    public DownloadExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                            TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public void executeTask(DownloadTask task){
        int status = task.getStatus();
        if (status== DownloadStatus.PAUSE || status== DownloadStatus.FAIL){
            task.setFileStatus(DownloadStatus.WAIT);

            Intent intent = new Intent();
            intent.setAction(task.getDownLoadInfo().getAction());
            intent.putExtra(DownloadConstant.EXTRA_INTENT_DOWNLOAD, task.getFileInfo());
            task.sendBroadcast(intent);

            execute(task);
        }else {
            LogUtils.w(TAG, "文件状态不正确, 不进行下载 FileInfo=" + task.getFileInfo());
        }
    }
}

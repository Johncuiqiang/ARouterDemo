package arouter.cuiqiang.com.downloadfilekit.download.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import arouter.cuiqiang.com.downloadfilekit.download.DownloadConstant;
import arouter.cuiqiang.com.downloadfilekit.download.DownloadStatus;
import arouter.cuiqiang.com.downloadfilekit.download.FileInfo;
import arouter.cuiqiang.com.downloadfilekit.download.bean.DownloadInfo;
import arouter.cuiqiang.com.downloadfilekit.download.bean.RequestInfo;
import arouter.cuiqiang.com.downloadfilekit.download.config.InnerConstant;
import arouter.cuiqiang.com.downloadfilekit.download.db.DbHolder;
import arouter.cuiqiang.com.downloadfilekit.download.execute.DownloadExecutor;
import arouter.cuiqiang.com.downloadfilekit.download.execute.DownloadTask;
import arouter.cuiqiang.com.downloadfilekit.download.utils.LogUtils;

/**
 * @author   www.yaoxiaowen.com
 * time:  2017/12/18 14:25
 * @since 1.0.0
 */
public class DownloadService extends Service{

    public static final String TAG = "Download";

    public static boolean canRequest = true;

    //关于线程池的一些配置
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(3, CPU_COUNT/2);
    private static final int MAX_POOL_SIZE =  CORE_POOL_SIZE * 2;
    private static final long KEEP_ALIVE_TIME  = 0L;

    private DownloadExecutor mExecutor = new DownloadExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
            KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());

    //存储任务
    private HashMap<String, DownloadTask> mTasks = new HashMap<>();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (canRequest){
            LogUtils.i(TAG, "onStartCommand() -> 启动了service服务 intent=" + intent + "\t this=" + this);
            canRequest = false;

            if (null!=intent && intent.hasExtra(InnerConstant.Inner.SERVICE_INTENT_EXTRA)){
                try {
                    ArrayList<RequestInfo> requesetes =
                            (ArrayList<RequestInfo>)intent.getSerializableExtra(InnerConstant.Inner.SERVICE_INTENT_EXTRA);
                    if (null != requesetes && requesetes.size()>0){
                        for (RequestInfo request : requesetes){
                            executeDownload(request);
                        }
                    }
                }catch (Exception e){
                    LogUtils.i(TAG, "onStartCommand()-> 接受数据,启动线程中发生异常");
                    e.printStackTrace();
                }
            }
            canRequest = true;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    //Todo  除了简单的synchronized, 是否有更好的方式来进行加锁呢
    private synchronized void executeDownload(RequestInfo requestInfo){
        DownloadInfo downloadInfo = requestInfo.getDownloadInfo();

        //先看看在任务列表里，是否有这个任务
        DownloadTask task = mTasks.get(downloadInfo.getUniqueId());
        DbHolder dbHolder = new DbHolder(getBaseContext());
        FileInfo mFileInfo = dbHolder.getFileInfo(downloadInfo.getUniqueId());

        Log.i(TAG, "executeDownload() -> task=" + task + "\t mFileInfo=" + mFileInfo);


        if (null == task){ //之前没有类似任务
            if (null != mFileInfo){
                if (mFileInfo.getDownloadStatus()== DownloadStatus.LOADING
                        || mFileInfo.getDownloadStatus()== DownloadStatus.PREPARE){
                    //修正文件状态
                    dbHolder.updateState(mFileInfo.getId(), DownloadStatus.PAUSE);
                }else if (mFileInfo.getDownloadStatus() == DownloadStatus.COMPLETE){
                    if (downloadInfo.getFile().exists()){
                        if (!TextUtils.isEmpty(downloadInfo.getAction())){
                            Intent intent = new Intent();
                            intent.setAction(downloadInfo.getAction());
                            intent.putExtra(DownloadConstant.EXTRA_INTENT_DOWNLOAD, mFileInfo);
                            sendBroadcast(intent);
                        }
                        return;
                    } else {
                        dbHolder.deleteFileInfo(downloadInfo.getUniqueId());
                    }
                }
            }//end of "  null != mFileInfo "

            //创建一个下载任务
            if (requestInfo.getDictate() == InnerConstant.Request.loading){
                task = new DownloadTask(this, downloadInfo, dbHolder);
                mTasks.put(downloadInfo.getUniqueId(), task);
            }
        }else {
            // 什么情况下, 可能存在这种这种状态
            if (task.getStatus()== DownloadStatus.COMPLETE || task.getStatus()== DownloadStatus.LOADING){
                if (!downloadInfo.getFile().exists()){
                    task.pause();
                    mTasks.remove(downloadInfo.getUniqueId());
                    LogUtils.i(TAG, " 状态标示完成，但是文件不存在，重新执行下载文件  ");
                    executeDownload(requestInfo);
                    return;
                }
            }
        }

        if (null != task){
            if (requestInfo.getDictate() == InnerConstant.Request.loading){
                mExecutor.executeTask(task);
            }else {
                task.pause();
            }
        }
    }
}

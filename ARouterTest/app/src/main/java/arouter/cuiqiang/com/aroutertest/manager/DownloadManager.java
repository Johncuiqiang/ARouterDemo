package arouter.cuiqiang.com.aroutertest.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.lang.ref.WeakReference;

import arouter.cuiqiang.com.aroutertest.R;
import arouter.cuiqiang.com.downloadfilekit.download.DownloadConstant;
import arouter.cuiqiang.com.downloadfilekit.download.DownloadHelper;
import arouter.cuiqiang.com.downloadfilekit.download.DownloadStatus;
import arouter.cuiqiang.com.downloadfilekit.download.FileInfo;
import arouter.cuiqiang.com.baselib.utils.file.FileUtil;

import static arouter.cuiqiang.com.applicationconfiglib.constant.ApplicationCodeConstant.DOWN_DONE;
import static arouter.cuiqiang.com.applicationconfiglib.constant.ApplicationCodeConstant.DOWN_LOADING;

/**
 * @author cuiqiang
 * @since 2018/7/24
 */
public class DownloadManager {

    private static final String TAG = "DownloadManager";
    //淘宝 app 下载地址
    private static final String firstUrl = "http://ucan.25pp.com/Wandoujia_web_seo_baidu_homepage.apk";
    private static final String FIRST_ACTION = "download_helper_first_action";
    private volatile static DownloadManager INSTANCE;

    private File firstFile;
    private DownloadHelper mDownloadHelper;
    private WeakReference<Context> mWeakContext;
    private Handler mHandler;
    private boolean isRegister = false;//是否注册过广播

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != intent) {
                String action = intent.getAction();
                if (null != action) {
                    switch (action) {
                        case FIRST_ACTION: {
                            FileInfo firstFileInfo = (FileInfo) intent.getSerializableExtra(DownloadConstant.EXTRA_INTENT_DOWNLOAD);
                            updateUI(firstFileInfo);
                        }
                        break;
                        default:
                            break;
                    }
                }
            }
        }
    };

    public static DownloadManager getInstance() {
        if (null == INSTANCE) {
            synchronized (DownloadManager.class) {
                if (null == INSTANCE) {
                    INSTANCE = new DownloadManager();
                }
            }
        }
        return INSTANCE;
    }


    /**
     * 初始化
     *
     * @param context 引用context防止内存泄漏
     */
    public void init(Context context) {
        mWeakContext = new WeakReference<>(context);
        mDownloadHelper = new DownloadHelper();
        createFile();
        registerReceiver();
    }

    /**
     * 注册广播
     */
    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(FIRST_ACTION);
        if (mWeakContext.get() != null) {
            mWeakContext.get().registerReceiver(receiver, filter);
            isRegister = true;
        }
    }

    /**
     * 设置handler
     *
     * @param handler base activity中已经是弱引用
     */
    public void setHandler(Handler handler) {
        this.mHandler = handler;
    }

    /**
     * 开始或者继续下载
     */
    public void download() {
        mDownloadHelper.addTask(firstUrl, firstFile, FIRST_ACTION).submit(mWeakContext.get());
    }

    /**
     * 暂停下载
     */
    public void pause() {
        if (mWeakContext.get() != null) {
            mDownloadHelper.pauseTask(firstUrl, firstFile, FIRST_ACTION).submit(mWeakContext.get());
        }
    }

    /**
     * 创建文件
     */
    private void createFile() {
        String firstName =  mWeakContext.get().getResources().getString(R.string.download_file_name);
        if (mWeakContext.get() != null) {
            String path = Environment.getExternalStorageDirectory() + "/download";
            firstFile = FileUtil.getFile(path, firstName);
        }
    }

    /**
     * 注销广播
     */
    public void unregisterReceiver() {
        if (isRegister && mWeakContext.get() != null) {
            mWeakContext.get().unregisterReceiver(receiver);
        }
    }

    /**
     * 更新ui
     *
     * @param fileInfo 文件信息
     */
    private void updateUI(FileInfo fileInfo) {
        float pro = (float) (fileInfo.getDownloadLocation() * 1.0 / fileInfo.getSize());
        String progress = (int) (pro * 100) + " %";
        //需要数据传递，用下面方法；
        Message msg = new Message();
        if (fileInfo.getDownloadStatus() == DownloadStatus.COMPLETE) {
            progress = "下载完成";
            msg.what = DOWN_DONE;
        } else {
            msg.what = DOWN_LOADING;
        }
        Log.d(TAG, "progress " + progress);
        msg.obj = progress;
        if (mHandler != null) {
            mHandler.sendMessage(msg);
        }
    }


}

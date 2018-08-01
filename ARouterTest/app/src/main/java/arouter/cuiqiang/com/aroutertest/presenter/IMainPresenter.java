package arouter.cuiqiang.com.aroutertest.presenter;

import android.os.Handler;

/**
 * @author cuiqiang
 * @since 2018/7/25
 */
public interface IMainPresenter {

    /**
     * 初始化下载相关
     *
     * @param handler 参数最好传baseActivity已经实现的handler
     */
    void initDownload(Handler handler);

    /**
     * 跳转测试界面
     */
    void turnTestActivity();

    /**
     * 携带参数跳转测试界面
     */
    void turnTestParamActivity();

    /**
     * 携带参数跳转个人界面
     */
    void turnUserActivity();

    /**
     * 下载
     */
    void download();

    /**
     * 暂停
     */
    void pause();

    /**
     * 销毁
     */
    void onDestroy();

    /**
     * 设置下载模块可用
     * @param isAvailable true可用 false不可用
     */
    void setDownloadAvailable(boolean isAvailable);

}

package arouter.cuiqiang.com.baselib.okhttp;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;

/**
 * @author cuiqiang
 * @since 2018/8/6
 */
public class OkHttpHelper {

    private static volatile OkHttpHelper INSTANCE;
    private OkHttpClient mOkHttpClient= new OkHttpClient();
    private Gson mGson = new Gson();

    public static OkHttpHelper getInstance(){
        if (null == INSTANCE) {
            synchronized (OkHttpHelper.class) {
                if (null == INSTANCE) {
                    INSTANCE = new OkHttpHelper();
                }
            }
        }
        return INSTANCE;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public Gson getGson() {
        return mGson;
    }
}

package arouter.cuiqiang.com.baselib.okhttp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import arouter.cuiqiang.com.baselib.constant.ErrorCode;
import arouter.cuiqiang.com.baselib.entities.BaseEntity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author cuiqiang
 * @since 2018/8/6
 */
public class BaseModel<T extends BaseEntity> implements IModel {

    protected String mApiPath;
    protected Request.Builder mRequestBuilder;
    protected Request mRequest = null;
    protected IResponseCallback mIResponseCallback;
    protected Map<String, String> mParams = new HashMap();
    protected Map<String, String> mHeaders = new HashMap();
    protected Class<T> mResponseEntity = null;
    protected Gson mGson = OkHttpHelper.getInstance().getGson();
    protected OkHttpClient mOkHttpClient = OkHttpHelper.getInstance().getOkHttpClient();

    public BaseModel(@NonNull IResponseCallback iResponseCallback) {
        this.mIResponseCallback = iResponseCallback;
        this.mRequestBuilder = new Request.Builder();
    }


    /**
     * 是否用json加参数
     *
     * @see {@link IModel}
     */
    @Override
    public boolean isJsonRPCServer() {
        return false;
    }


    /**
     * 开始网络请求
     *
     * @param method
     */
    public void startHttpRequestSync(HttpMethod method) {
        addHeader();
        excuteRequest(method);
        excuteHttp();
    }

    /**
     * 开始网络请求
     *
     * @param method
     */
    public BaseEntity startHttpRequest(HttpMethod method) {
        addHeader();
        excuteRequest(method);
        BaseEntity baseEntity = new BaseEntity();
        try {
            Response response = mOkHttpClient.newCall(mRequest).execute();
            if (response.isSuccessful()){
                return mGson.fromJson(response.body().toString(), mResponseEntity);
            } else {
                baseEntity.setBaseErrorCode(ErrorCode.SEVER_ERROR);
            }
            return baseEntity;
        } catch (IOException e) {
            baseEntity.setBaseErrorCode(ErrorCode.IO_ERROR);
            e.printStackTrace();
            return baseEntity;
        }
    }

    /**
     * 得到request请求参数
     */
    private void excuteRequest(HttpMethod method) {
        if (TextUtils.isEmpty(mApiPath)) {
            mIResponseCallback.onError(ErrorCode.PARAMS_ERROR);
            return;
        }
        if (method == HttpMethod.Get) {
            getRequestParams();
            mRequestBuilder = mRequestBuilder.get();
        } else if (method == HttpMethod.Post) {
            if (isJsonRPCServer()) {
                postRequestParamsJson();
            } else {
                postRequestParams();
            }
        } else {
            mIResponseCallback.onError(ErrorCode.PARAMS_ERROR);
            return;
        }
        mRequest = mRequestBuilder.url(mApiPath).build();
    }

    /**
     * 执行网络请求
     */
    private void excuteHttp() {
        mOkHttpClient.newCall(mRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mIResponseCallback.onError(ErrorCode.SEVER_ERROR);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                onHttpResponse(response);
            }
        });
    }

    /**
     * http回调
     *
     * @param response 返回参数
     */
    private void onHttpResponse(Response response) {
        if (response.isSuccessful()) {
            T dataEntity = mGson.fromJson(response.body().toString(), mResponseEntity);
            mIResponseCallback.onCallback(dataEntity);
        } else {
            mIResponseCallback.onError(response.code());
        }
    }


    /**
     * 添加请求头
     */
    private void addHeader() {
        for (String key : mHeaders.keySet()) {
            mRequestBuilder.addHeader(key, mHeaders.get(key));
        }
    }

    /**
     * 得到get请求参数
     */
    private void getRequestParams() {
        mApiPath = mApiPath + "?" + getParams();
    }

    /**
     * 得到post请求参数
     */
    private void postRequestParams() {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text"), getParams());
        mRequestBuilder = mRequestBuilder.post(requestBody);
    }

    /**
     * 得到请求参数
     */
    private String getParams() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : mParams.keySet()) {
            stringBuilder.append(key + "=" + mParams.get(key) + "&");
        }
        return stringBuilder.toString();
    }

    /**
     * 得到post-json请求参数
     */
    private void postRequestParamsJson() {
        String json = mGson.toJson(mParams);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        mRequestBuilder = mRequestBuilder.post(requestBody);

    }
}

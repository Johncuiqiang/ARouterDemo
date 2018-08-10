package arouter.cuiqiang.com.aroutertest.model;

import android.support.annotation.NonNull;

import arouter.cuiqiang.com.baselib.okhttp.BaseModel;
import arouter.cuiqiang.com.baselib.okhttp.HttpMethod;
import arouter.cuiqiang.com.baselib.okhttp.IResponseCallback;

/**
 * @author cuiqiang
 * @since 2018/8/6
 */
public class GetModel extends BaseModel{

    public GetModel(@NonNull IResponseCallback iResponseCallback) {
        super(iResponseCallback);
    }

    public boolean isJsonRPCServer(){
        return true;
    }

    public void excute(){
        this.startHttpRequest(HttpMethod.Post);
    }
}

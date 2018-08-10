package arouter.cuiqiang.com.baselib.okhttp;

import arouter.cuiqiang.com.baselib.constant.ErrorCode;
import arouter.cuiqiang.com.baselib.entities.BaseEntity;

/**
 * @author cuiqiang
 * @since 2018/8/6
 */
public interface IResponseCallback<T extends BaseEntity>  {

    /**
     * 请求回调
     */
    void onCallback(T dataEntity);

    /**
     * 请求错误
     * @param errorCode see{@link ErrorCode}
     */
    void onError(int errorCode);
}

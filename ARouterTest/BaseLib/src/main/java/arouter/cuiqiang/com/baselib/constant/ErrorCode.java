package arouter.cuiqiang.com.baselib.constant;

/**
 * @author cuiqiang
 * @since 2018/8/6
 */
public class ErrorCode {

    /**
     * 无错误，正常返回
     */
    public static final int NO_ERROR = 0;

    /**
     * 网络错误
     */
    public static final int NET_WORK_ERROR = -1;

    /**
     * 服务器错误，这个一般要依据服务器的返回参数确定
     */
    public static final int SEVER_ERROR = -2;

    /**
     * 参数异常
     */
    public static final int PARAMS_ERROR = -3;

    /**
     * IO异常
     */
    public static final int IO_ERROR = -4;
}

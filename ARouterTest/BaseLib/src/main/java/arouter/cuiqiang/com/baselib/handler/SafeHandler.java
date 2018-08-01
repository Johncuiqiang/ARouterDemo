package arouter.cuiqiang.com.baselib.handler;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by cuiqiang on 2018/7/20.
 */

public class SafeHandler extends Handler {

    public interface IHandlerMessage{
        public void handlerMessage(Message msg);
    }

    private WeakReference<IHandlerMessage> mWeakReference;

    public SafeHandler(IHandlerMessage handlerMessage){
        mWeakReference = new WeakReference<IHandlerMessage>(handlerMessage);
    }


    @Override
    public void handleMessage(Message msg) {
        IHandlerMessage handlerMessage = mWeakReference.get();
        if (handlerMessage!=null){
            handlerMessage.handlerMessage(msg);
        }
    }
}

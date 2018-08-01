package arouter.cuiqiang.com.baselib.adapters;

import android.view.View;

/**
 * Created by cuiqiang on 2018/7/20.
 */
public interface OnItemClickListener<T> {
    /**
     * 返回点击item事件
     */
    <T> void onItemClick(View view,T data);

    /**
     * 返回长按item事件
     */
    <T> boolean onLongClick(View view,T data);
}

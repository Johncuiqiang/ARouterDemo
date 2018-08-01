package arouter.cuiqiang.com.baselib.utils.device;

import android.content.Context;
import android.util.DisplayMetrics;


/**
 * Created by cuiqiang on 2018/7/20.
 */

public class DisplayUitl {


    public static int dip2px(Context context, float dpValue) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int)(dpValue * displayMetrics.density + 0.5F);
    }

    public static int px2dip(Context context, float pxValue) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int)(pxValue / displayMetrics.density + 0.5F);
    }

    public static int sp2px(Context context, float spValue){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int)(spValue * displayMetrics.scaledDensity + 0.5F);
    }

    public static int px2sp(Context context, float pxValue) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int)(pxValue / displayMetrics.scaledDensity + 0.5F);
    }
}

package arouter.cuiqiang.com.aroutertest.presenter.partcie;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import arouter.cuiqiang.com.baselib.utils.ImageUtils;

/**
 * @author cuiqiang
 * @since 2018/8/6
 */
public class ImageHelper {

    private static final Canvas sCanvas = new Canvas();

    public static Bitmap createBitmapFromView(View view) {
        view.clearFocus();
        Bitmap bitmap = ImageUtils.createBitmapSafely(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        if (bitmap != null) {
            synchronized (sCanvas) {
                Canvas canvas = sCanvas;
                canvas.setBitmap(bitmap);
                view.draw(canvas);
                canvas.setBitmap(null);
            }
        }
        return bitmap;
    }
}

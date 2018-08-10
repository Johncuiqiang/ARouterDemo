package arouter.cuiqiang.com.baselib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import arouter.cuiqiang.com.baselib.utils.device.DeviceUtil;
import arouter.cuiqiang.com.baselib.utils.device.DisplayUitl;


/**
 * Created by cuiqiang on 2018/7/20.
 */

public class ImageUtils {

    private final static int MAX_NUM_PIXELS = 320 * 490;
    private final static int MIN_SIDE_LENGTH = 350;
    public static int IMAGE_SIZE = 83;// 圆形图片的大小

    /**
     * 文件是否存在
     *
     * @param filePath
     * @return
     */
    public static boolean isFileExist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 创建文件
     *
     * @param fullpath
     * @return
     * @throws IOException
     */
    public static File creatFile(String fullpath) throws IOException {
        File file = new File(fullpath);
        file.createNewFile();
        return file;
    }

    /**
     * 放大缩放bitmap
     *
     * @param bitmap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        Bitmap newbmp = null;
        if (bitmap != null) {
            int tempWidth = bitmap.getWidth();
            int tempHeight = bitmap.getHeight();
            Matrix matrix = new Matrix();
            float scaleWidht = ((float) width / tempWidth);
            float scaleHeight = ((float) height / tempHeight);
            matrix.postScale(scaleWidht, scaleHeight);
            newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
                    true);
        }
        return newbmp;
    }

    /**
     * 根据路径获取bitmap
     *
     * @param filePath
     * @param opts
     * @return
     */
    public static Bitmap getBitmapByPath(String filePath,
            BitmapFactory.Options opts) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fis, null, opts);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return bitmap;
    }

    /**
     * 根据路径获取bitmap
     *
     * @param filePath
     * @return
     */
    public static Bitmap getBitmapByPath(String filePath) {
        return getBitmapByPath(filePath, null);
    }

    /**
     * 将bitmap存放到指定路径
     *
     * @param bitmap
     * @param path
     * @return
     */
    public static boolean putBitmapToSdcardByPath(final Bitmap bitmap, final String path) {
        if (null == bitmap || TextUtils.isEmpty(path)) {
            return false;
        }
        File file = new File(path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 根据路径加载缩放后的bitmap
     *
     * @param filePath
     * @param w
     * @param h
     * @return
     */
    public static Bitmap loadImgThumbnail(String filePath, int w, int h) {
        Bitmap bitmap = getBitmapByPath(filePath);
        return zoomBitmap(bitmap, w, h);
    }

    /**
     * drawable 转 bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
                .getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }


    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }


    /**
     * 将bitmap转成byte[]
     *
     * @param resBitmap
     */
    static byte[] BitmapRecoverByte(Bitmap resBitmap)
            throws IOException {
        ByteArrayOutputStream outStream = null;
        byte[] needBytes = null;
        if (resBitmap != null) {
            try {
                outStream = new ByteArrayOutputStream();
                resBitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                needBytes = outStream.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                outStream.close();
                // resBitmap.recycle();
            }
        }
        return needBytes;
    }

    /**
     * 将bitmap转成流
     *
     * @param bmp
     * @param needRecycle 需要回收
     * @return
     */
    public static OutputStream bitmapToStream(final Bitmap bmp,
            final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return output;
    }

    /**
     * 获取压缩后的图片
     *
     * @param resBitMap
     * @param reqWidth
     * @param reqHeight
     * @param inSampleSize
     * @return
     * @throws IOException
     */
    private static Bitmap decodeSampledBitmapFromResource(Bitmap resBitMap,
            int reqWidth, int reqHeight, int inSampleSize)
            throws IOException {
        byte[] bytes = BitmapRecoverByte(resBitMap);
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = inSampleSize;
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 裁剪图片成圆形
     *
     * @param bitmap
     * @return
     */
    private static Bitmap getRoundedCornerBitmap(Bitmap bitmap, Context context) {
        // 创建一个和资源图片一样大小的
        Bitmap outBitMap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        int space = DisplayUitl.dip2px(context, 1.5f);// 外面白色圈的宽度
        // 裁剪
        Canvas canvas = new Canvas(outBitMap);
        Rect rect = new Rect(space, space, bitmap.getWidth(),
                bitmap.getHeight());
        // Rect rect = new Rect(0, 0, bitmap.getWidth(), //返回不带框的需接触注释
        // bitmap.getHeight());
        RectF rectF = new RectF(rect);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 255, 255, 255);// 设置外围透明
        paint.setColor(0xffffffff);
        canvas.drawRoundRect(rectF, bitmap.getWidth(), bitmap.getWidth(), paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // 交错生成新图片
        canvas.drawBitmap(bitmap, rect, rect, paint);
        // return outBitMap; //返回不带框的需接触注释
        // 外面加3px的白色圆圈
        return addWhiteRound(outBitMap, space, rect);
    }

    /**
     * 图片外层添加圆形框
     *
     * @param outBitMap 需要添加框的图片
     * @param space     框的space
     * @param rect      裁剪的矩形
     * @return
     */
    private static Bitmap addWhiteRound(Bitmap outBitMap, int space, Rect rect) {
        Bitmap whiteRoundBitmap = Bitmap.createBitmap(outBitMap.getWidth()
                + space, outBitMap.getHeight() + space, Bitmap.Config.ARGB_8888);
        Canvas whiteCanvas = new Canvas(whiteRoundBitmap);
        Rect rect1 = new Rect(space, space, outBitMap.getWidth(),
                outBitMap.getHeight());
        RectF rectF1 = new RectF(rect);
        Paint paint1 = new Paint();
        paint1.setAntiAlias(true);
        whiteCanvas.drawARGB(0, 255, 255, 255);
        paint1.setColor(0xffffffff);
        whiteCanvas.drawRoundRect(rectF1, outBitMap.getWidth(),
                outBitMap.getHeight(), paint1);
        whiteCanvas.drawBitmap(outBitMap, rect1, rect1, paint1);
        return whiteRoundBitmap;
    }

    /**
     * 将图片裁剪成圆形
     *
     * @param bitmap 需要被裁剪的图片
     * @return 圆形图片
     */
    public static Bitmap makeRoundCorner(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int left = 0, top = 0, right = width, bottom = height;
        float roundPx = height / 2;
        Bitmap output = null;
        if (width >= height) {
            left = (width - height) / 2;
            top = 0;
            right = left + height;
            bottom = height;
            output = Bitmap.createBitmap(height, height,
                    Bitmap.Config.ARGB_8888);
        } else if (height > width) {
            left = 0;
            top = (height - width) / 2;
            right = width;
            bottom = top + width;
            roundPx = width / 2;
            output = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect(left, top, right, bottom);
        RectF rectF = new RectF(0, 0, output.getHeight(), output.getHeight());

        // 设置抗锯齿
        paint.setAntiAlias(true);
        // 设置画笔颜色
        paint.setColor(0xff424242);
        // 根据垂直半径和水平半径 在指定区域中，用指定画笔画圆
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        // 设置渲染方式：取两层绘制交集。显示上层。默认是Mode.SRC 正常显示新添加的图层
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // 这个方法的意思可以理解为根据一定条件复印一个bitmap到画布上面
        canvas.drawBitmap(bitmap, rect, rectF, paint);
        return output;
    }

    /**
     * 获取圆形图片
     *
     * @param resBitMap
     * @param reqWidth
     * @param reqHeight
     * @return
     * @throws IOException
     */
    public static Bitmap getRoundBitMap(Bitmap resBitMap, int reqWidth,
            int reqHeight, Context context) throws IOException {
        Canvas canvas = null;
        Bitmap zoomBitmap = null;
        int inSampleSize = 1;
        if (resBitMap != null) {
            int resWidth = resBitMap.getWidth();
            int resHeight = resBitMap.getHeight();

            // 如果是超大图，先压缩图片大小
            if (resHeight > reqHeight || resWidth > reqWidth) {
                // 计算出实际宽高和目标宽高的比率
                final int heightRatio = Math.round((float) resHeight
                        / (float) reqHeight);
                final int widthRatio = Math.round((float) resWidth
                        / (float) reqWidth);
                // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
                // 一定都会大于等于目标的宽和高。
                inSampleSize = heightRatio < widthRatio ? heightRatio
                        : widthRatio;
            }
            zoomBitmap = decodeSampledBitmapFromResource(resBitMap, reqWidth,
                    reqHeight, inSampleSize);
            // 截图图片
            if (zoomBitmap != null) {
                return getRoundedCornerBitmap(zoomBitmap, context);
            }
        }
        return null;
    }

    /**
     * 将bitmap保存到sdcard
     *
     * @param bitmap
     * @param context
     * @param dir      存放的目录
     * @param fileName 图片名
     * @return
     */
    public static boolean addBitmapToSdCard(Bitmap bitmap, Context context,
            String dir, String fileName) {
        if (bitmap != null && DeviceUtil.isSdcardExists()) {
            try {
                File file = new File(dir, fileName);
                OutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * 创建bitmap文件
     *
     * @param width
     * @param height
     * @param config
     * @return
     */
    public static Bitmap createBitmapSafely(int width, int height, Bitmap.Config config) {
        try {
            return Bitmap.createBitmap(width, height, config);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }
}

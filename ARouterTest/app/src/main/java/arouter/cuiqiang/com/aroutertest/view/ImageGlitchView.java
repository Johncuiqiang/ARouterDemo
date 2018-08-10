package arouter.cuiqiang.com.aroutertest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Random;

import arouter.cuiqiang.com.aroutertest.R;
import arouter.cuiqiang.com.baselib.utils.device.DeviceUtil;

/**
 * @author cuiqiang
 * @since 2018/8/8
 */
public class ImageGlitchView extends View {

    private Random random = new Random();
    private Bitmap mBitmap;
    //定义两个常量,这两个常量指定该图片横向,纵向上都被划分为20格
    private final int WIDTH = 20;
    private final int HEIGHT = 20;
    //记录该图片上包含441个顶点
    private final int COUNT = (WIDTH + 1) * (HEIGHT + 1);
    //定义一个数组,记录Bitmap上的21*21个点的坐标
    private final float[] verts = new float[COUNT * 2];
    //定义一个数组,记录Bitmap上的21*21个点经过扭曲后的坐标
    //对图片扭曲的关键就是修改该数组里元素的值
    private final float[] vertsAfter = new float[COUNT * 2];
    private float height = DeviceUtil.getScreenHeight();
    private float width = DeviceUtil.getScreenWidth();

    public ImageGlitchView(Context context) {
        super(context);
        init();
    }

    public ImageGlitchView(Context context, AttributeSet attrs) {
        super(context,attrs);
        init();
    }

    public ImageGlitchView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        init();
    }

    private void init(){
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_logo_partcle);
        float bitmapWidth = mBitmap.getWidth();
        float bitmapHeight = mBitmap.getHeight();
        int index = 0;
        for (int y = 0; y <= HEIGHT; y++) {
            //从0开始 * 原图片顶点高度 除以我们定义的格子数量
            //因为是网格所以 y=0时，对应算出x的坐标
            float fy = bitmapHeight * y / HEIGHT;
            for (int x = 0; x <= WIDTH; x++) {
                float fx = bitmapWidth * x / WIDTH;
                //初始化orig,verts数组
                //初始化,orig,verts两个数组均匀地保存了21 * 21个点的x,y坐标　
                //一维数组保存x，y所以每次*2，x+0，y+1
                vertsAfter[index * 2 + 0] = verts[index * 2 + 0] = fx;
                vertsAfter[index * 2 + 1] = verts[index * 2 + 1] = fy;
                index += 1;
            }
        }
        //verts对应就是index的0，1，2，3，4，5对应x0，y0,x1,y1,x2,y2
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureSpec(widthMeasureSpec),measureSpec(heightMeasureSpec));
    }

    public int measureSpec(int measureSpec){
        int specMode=MeasureSpec.getMode(measureSpec);//获取测量模式
        int specSize=MeasureSpec.getSize(measureSpec);//获取测量尺寸
        int result= 700;
        if (specMode==MeasureSpec.EXACTLY){//精确模式：包括准确设置dp值和match_parent
            Log.d("1111","EXACTLY");
            result = specSize;
        } else if (specMode == MeasureSpec.UNSPECIFIED){
            Log.d("1111","UNSPECIFIED");
            result = Math.min(result,specSize);
        } else if (specMode==MeasureSpec.AT_MOST){
            result = Math.min(result,specSize);
            Log.d("1111","AT_MOST");
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmapMesh(mBitmap, WIDTH, HEIGHT, verts,
                0, null, 0, null);
    }


    public void excuteAnim() {
        //测试
        float dx = 10;
        float pull = 50;
        int randomIndex = random.nextInt(verts.length);
        for (int i = 0; i < randomIndex; i++) {
            verts[i + 0] = vertsAfter[i + 0] + dx * pull;
            verts[i + 1] = vertsAfter[i + 1] + dx * pull;
        }
        invalidate();
    }
}

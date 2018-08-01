package arouter.cuiqiang.com.baselib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import arouter.cuiqiang.com.baselib.R;


public class LoadingView extends View {

    private Paint mPaintBar;
    private int mVlaue = 0;
    private final static int FRONT_LEN = 400;
    private Handler mHandler = new Handler();
    private int[] mShaderRes = new int[]{getResources().getColor(R.color.loading_bg), getResources().getColor(R.color.loading_front),
            getResources().getColor(R.color.loading_bg)};
    private boolean mIsStarted = true; 
    private RectF mRectf;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        startLoading();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mRectf == null) {
            mRectf = new RectF(0, 0, getMeasuredWidth(), 10);
        }
        canvas.drawRoundRect(mRectf, 5f, 5f, mPaintBar);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private void initPaint() {
        mPaintBar = new Paint();
        mPaintBar.setAntiAlias(true);
        mPaintBar.setStyle(Paint.Style.FILL);
        mPaintBar.setColor(Color.WHITE);
        mPaintBar.setStrokeWidth(dip2px(4));
    }

    public void setViewColor(int color) {
        mPaintBar.setColor(color);
        postInvalidate();
    }


    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void startLoading() {
        if (mIsStarted) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mVlaue += 3;
                    mVlaue = mVlaue % 100;
                    int startpos = mVlaue * (getMeasuredWidth() + 600) / 100;
                    Shader shader = new LinearGradient(startpos - FRONT_LEN, 0, startpos, 60, mShaderRes, null, Shader.TileMode.CLAMP);
                    mPaintBar.setShader(shader);
                    invalidate();
                    startLoading();
                }
            }, 20);
        }
    }

    public void stopLoading() {
        mIsStarted = false;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopLoading();
    }
}

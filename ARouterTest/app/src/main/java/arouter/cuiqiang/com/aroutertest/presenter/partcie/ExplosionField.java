package arouter.cuiqiang.com.aroutertest.presenter.partcie;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * Created by Administrator on 2015/11/28 0028.
 */
public class ExplosionField extends View {
    private static final String TAG = "ExplosionField";
    private ExplosionAnimator mExplosionAnimator;
    private OnClickListener onClickListener;
    private ParticleFactory mParticleFactory;

    public ExplosionField(Context context, ParticleFactory particleFactory) {
        super(context);
        init(particleFactory);
    }

    public ExplosionField(Context context, AttributeSet attrs, ParticleFactory particleFactory) {
        super(context, attrs);
        init(particleFactory);
    }

    private void init(ParticleFactory particleFactory) {
        mParticleFactory = particleFactory;
        attach2Activity((Activity) getContext());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mExplosionAnimator != null) {
            mExplosionAnimator.draw(canvas);
        }
    }

    /**
     * 爆破
     *
     * @param view 使得该view爆破
     */
    public void explode(final View view) {
        //防止重复点击
        if (mExplosionAnimator != null && mExplosionAnimator.isStarted()) {
            return;
        }
        if (view.getVisibility() != View.VISIBLE || view.getAlpha() == 0) {
            return;
        }

        final Rect rect = new Rect();
        view.getGlobalVisibleRect(rect); //得到view相对于整个屏幕的坐标
        int contentTop = ((ViewGroup) getParent()).getTop();
        Rect frame = new Rect();
        ((Activity) getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        rect.offset(0, -contentTop - statusBarHeight);//去掉状态栏高度和标题栏高度
        if (rect.width() == 0 || rect.height() == 0) {
            return;
        }
        explode(view, rect);

    }

    private void explode(final View view, Rect rect) {
        mExplosionAnimator = new ExplosionAnimator(this, ImageHelper.createBitmapFromView(view), rect, mParticleFactory);
        mExplosionAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                //缩小,透明动画
                view.animate().setDuration(150).scaleX(0f).scaleY(0f).alpha(0f).start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(150).start();
                mExplosionAnimator = null;
            }
        });
        mExplosionAnimator.start();
    }

    /**
     * 给Activity加上全屏覆盖的ExplosionField
     */
    private void attach2Activity(Activity activity) {
        ViewGroup rootView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootView.addView(this, lp);
    }


    /**
     * 希望谁有破碎效果，就给谁加Listener
     *
     * @param view 可以是ViewGroup
     */
    public void addListener(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                addListener(viewGroup.getChildAt(i));
            }
        } else {
            view.setClickable(true);
            view.setOnClickListener(getOnClickListener());
        }
    }


    private OnClickListener getOnClickListener() {
        if (null == onClickListener) {
            onClickListener = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExplosionField.this.explode(v);
                }
            };
        }
        return onClickListener;
    }
}

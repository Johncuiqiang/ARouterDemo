package arouter.cuiqiang.com.aroutertest.presenter.partcie;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;


import java.util.Random;

/**
 * Created by Administrator on 2015/11/29 0029.
 */
public class Particle {
    Random random = new Random();
    float radius = ParticleFactory.PART_WH;
    float alpha = 1.0f;
    Rect mBound;
    private float x;
    private float y;
    private int color;
    /**
     * @param color 颜色
     * @param x
     * @param y
     */
    public Particle(int color, float x, float y,Rect bound) {
        mBound = bound;
        this.color = color;
        this.x = x;
        this.y = y;
    }


    protected void draw(Canvas canvas,Paint paint){
        paint.setColor(color);
        paint.setAlpha((int) (Color.alpha(color) * alpha)); //这样透明颜色就不是黑色了
        canvas.drawCircle(x, y, radius, paint);
    }

    protected void caculate(float factor){
        x = x + factor * random.nextInt(mBound.width()) * (random.nextFloat() - 0.5f);
        y = y + factor * random.nextInt(mBound.height() / 2);

        radius = radius - factor * random.nextInt(2);

        alpha = (1f - factor) * (1 + random.nextFloat());
    }

    public void advance(Canvas canvas,Paint paint,float factor) {
        caculate(factor);
        draw(canvas,paint);
    }
}

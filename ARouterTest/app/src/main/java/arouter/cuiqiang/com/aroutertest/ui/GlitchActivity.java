package arouter.cuiqiang.com.aroutertest.ui;

import android.os.Bundle;
import android.os.Message;

import com.alibaba.android.arouter.facade.annotation.Route;

import arouter.cuiqiang.com.aroutertest.R;
import arouter.cuiqiang.com.baselib.activities.BaseActivity;
import butterknife.ButterKnife;

import static arouter.cuiqiang.com.applicationconfiglib.constant.ARouterConstant.GLITCH_ACTIVITY;

/**
 * @author cuiqiang
 * @since 2018/8/6
 */
@Route(path = GLITCH_ACTIVITY)
public class GlitchActivity extends BaseActivity {

//    @BindView(R.id.iv_logo)
//    ImageView mIvLogo;
//    @BindView(R.id.iv_glitch)
//    ImageGlitchView mImageGlitchView;

    @Override
    protected void init(Bundle savedInstanceState) {
        setContentView(R.layout.activity_glitch);
        initData();
    }

    /**
     * bitmap: 指定需要扭曲的源位图
     * mesh网格
     * meshWidth: 该参数控制在横向上把该源位图划分成多少格.
     * meshHeight: 该参数控制在纵向上把该源位图划分为多少格.
     * verts: 顶点数，该参数是一个长度为(meshWidth + 1)(meshHeight+1) 2 的数组,它记录了扭曲后的位图各"顶点"位置.
     * 虽然它是个一维数组,实际上它记录的数据是形如(x0, y0),(x1, y1),(x2, y2)......(xN, yN)格式的数据,
     * 这些数组元素控制对bitmap位图的扭曲效果.
     * vertOffset: 坐标错位，如果讲动画中所有坐标都计算完成储存在一个verts里，动画是只需要移动offset就可以了。
     */
    private void initData() {
        ButterKnife.bind(this);
    }

//  @OnClick(R.id.iv_logo)
    void onGlitch() {
        // GlitchEffect.showGlitch(this);

    }

//  @OnClick(R.id.iv_glitch)
    void onTest(){
//        mImageGlitchView.excuteAnim();
    }

    @Override
    public void handlerMessage(Message msg) {

    }

}

package arouter.cuiqiang.com.aroutertest.ui;

import android.os.Bundle;
import android.os.Message;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;

import arouter.cuiqiang.com.aroutertest.R;
import arouter.cuiqiang.com.aroutertest.presenter.partcie.ExplosionField;
import arouter.cuiqiang.com.aroutertest.presenter.partcie.ParticleFactory;
import arouter.cuiqiang.com.baselib.activities.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import static arouter.cuiqiang.com.applicationconfiglib.constant.ARouterConstant.ANIM_ACTIVITY;

/**
 * @author cuiqiang
 * @since 2018/8/6
 */
@Route(path = ANIM_ACTIVITY)
public class PartcleActivity extends BaseActivity {

    @BindView(R.id.iv_logo)
    ImageView mIvLogo;

    @Override
    protected void init(Bundle savedInstanceState) {
        setContentView(R.layout.activity_partcle);
        initData();
    }

    private void initData() {
        ButterKnife.bind(this);
        ExplosionField explosionField = new ExplosionField(this,new ParticleFactory());
        explosionField.addListener(mIvLogo);
    }

    @Override
    public void handlerMessage(Message msg) {

    }
}

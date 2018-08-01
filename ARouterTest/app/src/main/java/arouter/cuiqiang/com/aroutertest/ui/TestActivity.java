package arouter.cuiqiang.com.aroutertest.ui;

import android.Manifest;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import arouter.cuiqiang.com.aroutertest.R;
import arouter.cuiqiang.com.baselib.activities.BaseActivity;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import static arouter.cuiqiang.com.applicationconfiglib.constant.ARouterConstant.TEST_ACTIVITY;

/**
 * @author cuiqiang
 * @since 2018/7/19
 */
@Route(path = TEST_ACTIVITY)
public class TestActivity extends BaseActivity {

    private TextView mTvText;
    @Autowired
    public String name;
    @Autowired
    public int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
        initData();
    }

    private void initView() {
        mTvText = (TextView) findViewById(R.id.tv_test);
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    void initData() {
        //传递参数注入
        ARouter.getInstance().inject(this);
        mTvText.setText(name + " : " + age);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    public void handlerMessage(Message msg) {

    }

}

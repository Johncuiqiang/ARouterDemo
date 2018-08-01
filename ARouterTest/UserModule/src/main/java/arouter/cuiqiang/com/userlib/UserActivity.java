package arouter.cuiqiang.com.userlib;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import arouter.cuiqiang.com.baselib.activities.BaseActivity;

import static arouter.cuiqiang.com.applicationconfiglib.constant.ARouterConstant.USER_ACTIVITY;

/**
 * @author cuiqiang
 * @since 2018/7/19
 */
@Route(path = USER_ACTIVITY)
public class UserActivity extends BaseActivity {

    private TextView mTvText;
    @Autowired
    public String name;
    @Autowired
    public int age;

    @Override
    protected void init(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user);
        initView();
        initData();
    }

    private void initView() {
        mTvText = (TextView) findViewById(R.id.tv_test);
    }

    private void initData() {
        //传递参数注入
        ARouter.getInstance().inject(this);
        mTvText.setText("用户界面"+name + " : " + age);
    }

    @Override
    public void handlerMessage(Message msg) {

    }
}

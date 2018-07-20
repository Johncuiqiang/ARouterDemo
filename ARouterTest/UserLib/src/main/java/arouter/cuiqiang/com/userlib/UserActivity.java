package arouter.cuiqiang.com.userlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author cuiqiang
 * @since 2018/7/19
 */
@Route(path = "/user/activity")
public class UserActivity extends AppCompatActivity {

    private TextView mTvText;
    @Autowired
    public String name;
    @Autowired
    public int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}

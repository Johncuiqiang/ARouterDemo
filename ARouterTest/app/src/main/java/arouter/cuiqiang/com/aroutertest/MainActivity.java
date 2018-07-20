package arouter.cuiqiang.com.aroutertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.alibaba.android.arouter.launcher.ARouter;

public class MainActivity extends AppCompatActivity {

    private Button mBtnNavigation;
    private Button mBtnNavigationParams;
    private Button mBtnNavigationUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mBtnNavigation = (Button)findViewById(R.id.btn_navigation);
        mBtnNavigationParams = (Button)findViewById(R.id.btn_navigation_params);
        mBtnNavigationUser = (Button)findViewById(R.id.btn_user_params);
    }

    private void initData() {
        mBtnNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance()
                        .build("/cuiqiang/activity")
                        .navigation();
            }
        });
        mBtnNavigationParams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance()
                        .build("/cuiqiang/activity")
                        .withString("name", "测试界面的参数")
                        .withInt("age", 23)
                        .navigation();
            }
        });
        mBtnNavigationUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance()
                        .build("/user/activity")
                        .withString("name", "测试界面的参数")
                        .withInt("age", 23)
                        .navigation();
            }
        });
    }


}

package tyzl.company.activity.center;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tyzl.company.R;
import tyzl.company.activity.MainActivity;
import tyzl.company.global.Config;
import tyzl.company.main.BaseActivity;
import tyzl.company.main.MyApplication;
import tyzl.company.weight.TitleView;

/**
 * 修改登录密码成功
 * Created by hjy on 2017/3/13.
 */

public class ChangeLoginPasswordSuccessActivity extends BaseActivity {

    private TitleView titleView;
    private Button know_btn;
    private ChangeLoginPasswordSuccessActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_change_login_password_success);
        mActivity = this;
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.hiddenSearchButton();
        titleView.setTitle("修改登录密码");
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 跳转到ChangeLoginPasswardSuccessActivity
     *
     * @param context
     */
    public static void startActivity(Context context) {

        Intent intent = new Intent(context, ChangeLoginPasswordSuccessActivity.class);
        context.startActivity(intent);


    }

    @Override
    protected void initView() {
        know_btn = (Button) findViewById(R.id.know_btn);

    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setEvent() {
        know_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.equals(know_btn)) {
            UserLoginActivity.startActivity(context);
        }

    }
}

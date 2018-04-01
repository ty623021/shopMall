package tyzl.company.activity.center;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import tyzl.company.R;
import tyzl.company.activity.MainActivity;
import tyzl.company.global.Config;
import tyzl.company.main.BaseActivity;
import tyzl.company.weight.TitleView;

/**
 * 注册成功
 * Created by hjy on 2017/3/14.
 */

public class UserRegisterSuccessActivity extends BaseActivity {
    private TitleView titleView;
    private Button know_btn;
    private UserRegisterSuccessActivity mActivity;
    private TextView tv_register_success;
    private String user_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register_success);
        mActivity = this;
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.hiddenSearchButton();
        titleView.setTitle("注册成功");
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    /**
     * 跳转到UserRegisterSuccessActivity
     *
     * @param context
     */

    public static void startActivity(Context context,String msg) {
        Intent intent = new Intent(context, UserRegisterSuccessActivity.class);
        intent.putExtra("msg",msg);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        know_btn = (Button) findViewById(R.id.know_btn);
        tv_register_success= (TextView) findViewById(R.id.tv_register_success);
        user_amount=this.getIntent().getStringExtra("msg");



    }

    @Override
    protected void setData() {
        tv_register_success.setText(user_amount);
    }

    @Override
    protected void setEvent() {
        know_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.equals(know_btn)) {
            MainActivity.startMainActivity(mActivity, 3);
        }

    }
}

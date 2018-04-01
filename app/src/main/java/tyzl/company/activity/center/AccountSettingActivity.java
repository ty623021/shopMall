package tyzl.company.activity.center;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import tyzl.company.R;
import tyzl.company.activity.MainActivity;
import tyzl.company.entity.UserInfomationInfo;
import tyzl.company.global.Config;
import tyzl.company.main.BaseActivity;
import tyzl.company.main.MyApplication;
import tyzl.company.utils.AbJsonUtil;
import tyzl.company.utils.AbLogUtil;
import tyzl.company.utils.AbStringUtil;
import tyzl.company.utils.AbToastUtil;
import tyzl.company.volley.RequestListener;
import tyzl.company.weight.TitleView;

/**
 * 账户设置
 * Created by hjy on 2017/3/10.
 */

public class AccountSettingActivity extends BaseActivity {
    private TitleView titleView;
    private RelativeLayout change_phone;
    private RelativeLayout change_passward;
    private Button safe_exit;
    private UserInfomationInfo info;
    private TextView phone;
    private String phoneStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_account_setting);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setTitle("账户管理");
        titleView.hiddenSearchButton();
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /**
     * 跳转到AccountSettingActivity
     */
    public static void startActivity(Context context, String phone) {
        Intent intent = new Intent(context, AccountSettingActivity.class);
        intent.putExtra("phone", phone);
        context.startActivity(intent);
    }
    /**
     * 跳转到AccountSettingActivity
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AccountSettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        change_phone = (RelativeLayout) findViewById(R.id.change_phone);
        change_passward = (RelativeLayout) findViewById(R.id.change_passward);
        safe_exit = (Button) findViewById(R.id.safe_exit);
        phone = (TextView) findViewById(R.id.phone);

        phoneStr = getIntent().getStringExtra("phone");
        if (!AbStringUtil.isEmpty(phoneStr)){
            phone.setText(phoneStr);
        }

    }


    @Override
    protected void setData() {

        if (MyApplication.mApp.hasLogin()) {
            safe_exit.setClickable(true);
        } else {
            safe_exit.setClickable(false);
            safe_exit.setBackgroundResource(R.color.red_enabled_false);
        }

    }

    @Override
    protected void setEvent() {
        change_phone.setOnClickListener(this);
        change_passward.setOnClickListener(this);
        safe_exit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.equals(change_phone)) {
            ChangePhoneNumberActivity.startActivity(context, phoneStr);
        } else if (v.equals(change_passward)) {
            ChangeLoginPasswordActivity.startActivity(context, phoneStr);
        } else if (v.equals(safe_exit)) {

            MyApplication.mApp.logout();
            MainActivity.startMainActivity(this, 3);


        }

    }
}

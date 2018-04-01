package tyzl.company.activity.center;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import tyzl.company.R;
import tyzl.company.global.Config;
import tyzl.company.main.BaseActivity;
import tyzl.company.utils.AbJsonUtil;
import tyzl.company.utils.AbStringUtil;
import tyzl.company.utils.AbToastUtil;
import tyzl.company.volley.RequestListener;
import tyzl.company.weight.EditTextWithDel;
import tyzl.company.weight.TitleView;

/**
 * 用输入新的密码
 * Created by hjy on 2017/3/14.
 */

public class UserLoginInputNewPasswordActivity extends BaseActivity {


    private TitleView titleView;
    private Button sure_btn;
    private EditTextWithDel new_password;
    private String new_passwordStr;
    private TextView password_rules;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_login_input_new_password);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setTitle("输入新的密码");
        titleView.setLeftImageButton(R.drawable.back);
        titleView.hiddenSearchButton();
        titleView.showLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public static void startActivity(Context context, String phone) {
        Intent intent = new Intent(context, UserLoginInputNewPasswordActivity.class);
        intent.putExtra("mobile", phone);
        context.startActivity(intent);

    }

    @Override
    protected void initView() {
        sure_btn = (Button) findViewById(R.id.sure_btn);
        new_password = (EditTextWithDel) findViewById(R.id.new_password);
        password_rules = (TextView) findViewById(R.id.password_rules);
        phone = getIntent().getStringExtra("mobile");


    }

    @Override
    protected void setData() {

    }

    /**
     * 发送设置密码请求
     */
    private void sendNewPassword() {
        if (!AbStringUtil.isEmpty(phone)) {
            params.put("mobile", phone);
        }
        if (!AbStringUtil.isEmpty(new_passwordStr)) {
            params.put("password", new_passwordStr);
        }
        http.post(Config.URL_FORGETPWD, params, "", new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                if (AbJsonUtil.isSuccess(json)) {

                    ChangeLoginPasswordSuccessActivity.startActivity(context);

                } else {
                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                }
            }

            @Override
            public void requestError(String message) {
                AbToastUtil.showToast(context, message);
            }
        });

    }


    @Override
    protected void setEvent() {
        sure_btn.setOnClickListener(this);
        new_password.addTextChangedListener(new MyTextWatcher());
    }

    @Override
    public void onClick(View v) {
        if (v.equals(sure_btn)) {
            new_passwordStr = new_password.getText().toString().trim();
            if (!AbStringUtil.isEmpty(new_passwordStr)) {
                if (new_passwordStr.length() >= 8 && new_passwordStr.length() <= 20) {
                    sendNewPassword();
                } else {
                    password_rules.setVisibility(View.VISIBLE);
                    password_rules.setText(R.string.input_istrue_password_amount);
                }

            } else {
                AbToastUtil.showToast(context, "请输入新的密码");
            }

        }


    }

    private class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 0) {
                password_rules.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
            new_passwordStr = new_password.getText().toString().trim();
            if (new_passwordStr.length() >= 8) {
                password_rules.setVisibility(View.INVISIBLE);
            }
        }
    }
}

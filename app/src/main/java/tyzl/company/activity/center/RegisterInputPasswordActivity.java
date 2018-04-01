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
import tyzl.company.entity.UserRegisterInfo;
import tyzl.company.global.Config;
import tyzl.company.main.BaseActivity;
import tyzl.company.main.MyApplication;
import tyzl.company.utils.AbJsonUtil;
import tyzl.company.utils.AbLogUtil;
import tyzl.company.utils.AbStringUtil;
import tyzl.company.utils.AbToastUtil;
import tyzl.company.volley.RequestListener;
import tyzl.company.weight.EditTextWithDel;
import tyzl.company.weight.TitleView;

/**
 * 注册时的输入密码
 * Created by hjy on 2017/3/14.
 */

public class RegisterInputPasswordActivity extends BaseActivity {

    private TitleView titleView;
    private Button sure_btn;
    private EditTextWithDel set_password;
    private String passwordStr;
    private TextView password_rules;
    private String phoneStr;
    private UserRegisterInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_change_login_password_next);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setLeftImageButton(R.drawable.back);

        titleView.showLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleView.setTitle("设置密码");
        titleView.hiddenSearchButton();


    }

    /**
     * 跳转到RegisterInputPasswardActivity
     */

    public static void startActivity(Context context, String phone) {
        Intent intent = new Intent(context, RegisterInputPasswordActivity.class);
        intent.putExtra("phone", phone);
        context.startActivity(intent);

    }

    /**
     * 注册
     */
    private void setRegisterHttp() {


        if (!AbStringUtil.isEmpty(passwordStr)) {
            params.put("password", passwordStr);
        }
        if (!AbStringUtil.isEmpty(phoneStr)) {
            params.put("mobile", phoneStr);
        }
        http.post(Config.URL_REGISTER, params, "注册中...", new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                if (AbJsonUtil.isSuccess(json)) {
                    AbLogUtil.e("REGISTER", json.toString());
                    info = (UserRegisterInfo) AbJsonUtil.fromJson(json, UserRegisterInfo.class);
                    if (info != null) {
                        //保存用户的注册信息
                        MyApplication.mApp.saveUserRegister(info);
                        UserRegisterSuccessActivity.startActivity(context, info.getMsg());
                    } else {
                        AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                    }
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
    protected void initView() {
        sure_btn = (Button) findViewById(R.id.sure_btn);
        set_password = (EditTextWithDel) findViewById(R.id.new_password);
        password_rules = (TextView) findViewById(R.id.password_rules);
        phoneStr = this.getIntent().getStringExtra("phone");

    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setEvent() {
        sure_btn.setOnClickListener(this);
        set_password.addTextChangedListener(new MyTextWatcher());

    }

    @Override
    public void onClick(View v) {
        if (v.equals(sure_btn)) {
            if (!AbStringUtil.isEmpty(passwordStr)) {
                if (passwordStr.length() >= 8 && passwordStr.length() <= 20) {
                    setRegisterHttp();
                } else {
                    password_rules.setVisibility(View.VISIBLE);
                    password_rules.setText(R.string.input_istrue_password_amount);
                }
            } else {
                AbToastUtil.showToast(context, "请输入密码");
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
            passwordStr = set_password.getText().toString().trim();
            if (passwordStr.length() >= 8) {
                password_rules.setVisibility(View.INVISIBLE);
            }
        }
    }
}

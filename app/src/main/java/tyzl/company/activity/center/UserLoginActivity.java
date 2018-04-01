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
import tyzl.company.activity.MainActivity;
import tyzl.company.entity.UserRegisterInfo;
import tyzl.company.global.Config;
import tyzl.company.main.BaseActivity;
import tyzl.company.main.MyApplication;
import tyzl.company.utils.AbJsonUtil;
import tyzl.company.utils.AbLogUtil;
import tyzl.company.utils.AbStringUtil;
import tyzl.company.utils.AbToastUtil;
import tyzl.company.utils.AddSpaceTextWatcher;
import tyzl.company.volley.RequestListener;
import tyzl.company.weight.EditTextWithDel;
import tyzl.company.weight.TitleView;

/**
 * 登录
 * Created by hjy on 2017/3/14.
 */

public class UserLoginActivity extends BaseActivity {
    private TitleView titleView;
    private AddSpaceTextWatcher addSpaceTextWatcher;
    private EditTextWithDel phone_edit;
    private EditTextWithDel password_edit;
    private Button login_btn;
    private String phoneStr, passwordStr;
    private UserLoginActivity mActivity;
    private TextView forget_password;
    private TextView rules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        mActivity = this;
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setLeftImageButton(R.drawable.back);
        titleView.hiddenSearchButton();
        titleView.showLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleView.setTitle("登录");
        titleView.setRightTextButton("注册", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRegisterActivity.startActivity(context);
            }
        });
        titleView.showRightTextButton();

    }

    /**
     * 跳转到UserLoginActivity
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, UserLoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        phone_edit = (EditTextWithDel) findViewById(R.id.phone_edit);
        password_edit = (EditTextWithDel) findViewById(R.id.password_edit);
        login_btn = (Button) findViewById(R.id.login_btn);
        forget_password = (TextView) findViewById(R.id.forget_password);
        rules = (TextView) findViewById(R.id.rules);
        addSpaceTextWatcher = new AddSpaceTextWatcher(phone_edit, 13);
        addSpaceTextWatcher.setSpaceType(AddSpaceTextWatcher.SpaceType.mobilePhoneNumberType);


    }


    private void setLoginHttp() {

        if (!AbStringUtil.isEmpty(phoneStr)) {
            params.put("mobile", phoneStr);
        }
        if (!AbStringUtil.isEmpty(passwordStr)) {
            params.put("password", passwordStr);
        }
        http.post(Config.URL_LOGIN, params, "登录中...", new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                if (AbJsonUtil.isSuccess(json)) {
                    AbLogUtil.e("URL_LOGIN", json.toString());
                    UserRegisterInfo info = (UserRegisterInfo) AbJsonUtil.fromJson(json, UserRegisterInfo.class);
                    if (info != null) {
                        //保存用户登录信息
                        MyApplication.mApp.saveUserRegister(info);
                        MainActivity.startMainActivity(mActivity,3);
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
    protected void setData() {

    }

    @Override
    protected void setEvent() {
        login_btn.setOnClickListener(this);
        forget_password.setOnClickListener(this);
        password_edit.addTextChangedListener(new MyTextWatcher());
        phone_edit.addTextChangedListener(new MyTextWatcher());
        addSpaceTextWatcher = new AddSpaceTextWatcher(phone_edit, 13);
        addSpaceTextWatcher.setSpaceType(AddSpaceTextWatcher.SpaceType.mobilePhoneNumberType);

    }

    @Override
    public void onClick(View v) {
        if (v.equals(login_btn)) {
            if (AbStringUtil.isEmpty(phoneStr)) {
                AbToastUtil.showToast(context, "请输入手机号码");
            } else {
                if (AbStringUtil.isMobileNo(phoneStr)) {
                    if (!AbStringUtil.isEmpty(passwordStr)) {
//                        AbToastUtil.showToast(context, "登录成功");
//                        MainActivity.startMainActivity(mActivity, 3);
                        setLoginHttp();

                    } else {
                        AbToastUtil.showToast(context, "请输入密码");
                    }

                } else {
                    rules.setVisibility(View.VISIBLE);
                    rules.setText(R.string.input_istrue_phone);
                }
            }

        } else if (v.equals(forget_password)) {
            UserLoginForgetPasswordActivity.startActivity(context);
        }
    }

    private class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 0) {
                rules.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            phoneStr = addSpaceTextWatcher.getTextNotSpace();
            passwordStr = password_edit.getText().toString().trim();
            if (AbStringUtil.isMobileNo(phoneStr)) {
                rules.setVisibility(View.INVISIBLE);
            }
        }
    }
}

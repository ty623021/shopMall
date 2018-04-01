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
import tyzl.company.main.MyApplication;
import tyzl.company.utils.AbJsonUtil;
import tyzl.company.utils.AbStringUtil;
import tyzl.company.utils.AbToastUtil;
import tyzl.company.volley.RequestListener;
import tyzl.company.weight.EditTextWithDel;
import tyzl.company.weight.TitleView;

/**
 * 修改登录密码下一步
 * Created by hjy on 2017/3/10.
 */

public class ChangeLoginPasswordNextActivity extends BaseActivity {
    private TitleView titleView;
    private Button sure_btn;
    private TextView password_rules;
    private EditTextWithDel new_password;
    private String new_passwordStr_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_change_login_password_next);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setTitle("修改登录密码");
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
     * 跳转到ChangeLoginPasswardNextActivity
     *
     * @param context
     */

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ChangeLoginPasswordNextActivity.class);
        context.startActivity(intent);

    }

    @Override
    protected void initView() {
        sure_btn = (Button) findViewById(R.id.sure_btn);
        password_rules = (TextView) findViewById(R.id.password_rules);
        new_password = (EditTextWithDel) findViewById(R.id.new_password);

    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setEvent() {
        sure_btn.setOnClickListener(this);
        new_password.addTextChangedListener(new MyTextWatcher());
        password_rules.addTextChangedListener(new MyTextWatcher());

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
            new_passwordStr_edit = new_password.getText().toString().trim();
            if (new_passwordStr_edit.length() >= 8) {
                password_rules.setVisibility(View.INVISIBLE);
            }
        }
    }

    /*
    修改登录密码
     */
    private void changeLoginPasswordHttp() {

        new_passwordStr_edit = new_password.getText().toString().trim();

        if (!AbStringUtil.isEmpty(new_passwordStr_edit)) {
            params.put("password", new_passwordStr_edit);
        }

        http.post(Config.URL_UPUSERINFO, params, "正在修改...", new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                if (AbJsonUtil.isSuccess(json)) {
                    MyApplication.mApp.logout();
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
    public void onClick(View v) {
        if (v.equals(sure_btn)) {
            new_passwordStr_edit = new_password.getText().toString().trim();
            if (!AbStringUtil.isEmpty(new_passwordStr_edit)) {
                if (new_passwordStr_edit.length() >= 8 && new_passwordStr_edit.length() <= 20) {
                    changeLoginPasswordHttp();
                } else {
                    password_rules.setVisibility(View.VISIBLE);
                    password_rules.setText(R.string.input_istrue_password_amount);
                }
            } else {
                AbToastUtil.showToast(context, "请输入新的登录密码");
            }

        }

    }


}

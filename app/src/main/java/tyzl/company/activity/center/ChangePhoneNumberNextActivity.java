package tyzl.company.activity.center;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import tyzl.company.R;
import tyzl.company.entity.UserInfomationInfo;
import tyzl.company.global.Config;
import tyzl.company.main.BaseActivity;
import tyzl.company.utils.AbJsonUtil;
import tyzl.company.utils.AbStringUtil;
import tyzl.company.utils.AbToastUtil;
import tyzl.company.utils.AddSpaceTextWatcher;
import tyzl.company.utils.VerificationCodeUtils;
import tyzl.company.volley.RequestListener;
import tyzl.company.weight.CountdownProgressBar;
import tyzl.company.weight.EditTextWithDel;
import tyzl.company.weight.TitleView;

/**
 * 修改手机号码下一步
 * Created by hjy on 2017/3/10.
 */

public class ChangePhoneNumberNextActivity extends BaseActivity implements VerificationCodeUtils.OnCheckVerify {
    private TitleView titleView;
    private Button sure_btn;
    private EditTextWithDel phone_edit1;
    private String phoneStr_edit, codeStr_edit;
    private AddSpaceTextWatcher addSpaceTextWatcher;
    private EditTextWithDel et_verification_code;
    private TextView rules;
    private Button get_verification_code;
    private CountdownProgressBar roundProgressBar;
    private VerificationCodeUtils verificationCodeUtils;
    private UserInfomationInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_change_phone_number_next);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setTitle("修改手机号码");
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
     * 跳转到ChangePhoneNumberNextActivity
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ChangePhoneNumberNextActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        sure_btn = (Button) findViewById(R.id.sure_btn);
        phone_edit1 = (EditTextWithDel) findViewById(R.id.phone_edit1);
        et_verification_code = (EditTextWithDel) findViewById(R.id.et_verification_code);
        rules = (TextView) findViewById(R.id.rules);
        get_verification_code = (Button) findViewById(R.id.get_verification_code);
        roundProgressBar = (CountdownProgressBar) findViewById(R.id.roundProgressBar);

        addSpaceTextWatcher = new AddSpaceTextWatcher(phone_edit1, 13);
        addSpaceTextWatcher.setSpaceType(AddSpaceTextWatcher.SpaceType.mobilePhoneNumberType);
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setEvent() {
        sure_btn.setOnClickListener(this);
        get_verification_code.setOnClickListener(this);
        phone_edit1.addTextChangedListener(new MyTextWatcher());


    }

    @Override
    public void onClick(View v) {
        if (v.equals(sure_btn)) {
            phoneStr_edit = addSpaceTextWatcher.getTextNotSpace();
            codeStr_edit = et_verification_code.getText().toString().trim();
            if (AbStringUtil.isEmpty(phoneStr_edit)) {
                AbToastUtil.showToast(context, "请输入新的手机号码");
            } else {
                if (AbStringUtil.isMobileNo(phoneStr_edit)) {
                    if (!AbStringUtil.isEmpty(codeStr_edit)) {
                        if (verificationCodeUtils == null) {
                            verificationCodeUtils = new VerificationCodeUtils(this, get_verification_code, roundProgressBar, et_verification_code);
                        }
                        verificationCodeUtils.checkVerifyOn(http, phoneStr_edit, 5, codeStr_edit, sure_btn);

                    } else {
                        AbToastUtil.showToast(context, "请输入短信验证码");
                    }

                } else {
                    rules.setVisibility(View.VISIBLE);
                    rules.setText(R.string.input_istrue_phone);
                }
            }

        } else if (v.equals(get_verification_code)) {
            if (verificationCodeUtils == null) {
                verificationCodeUtils = new VerificationCodeUtils(this, get_verification_code, roundProgressBar, et_verification_code);
            }
            phoneStr_edit = addSpaceTextWatcher.getTextNotSpace();
            verificationCodeUtils.getVerificationCode(http, phoneStr_edit, 5);
            verificationCodeUtils.setOnCheckVerify(this);
        }

    }

    @Override
    public void onCheckVerify(boolean is) {
        if (is) {
            changePhoneHttp();
        }

    }

    private void changePhoneHttp() {
        phoneStr_edit = addSpaceTextWatcher.getTextNotSpace();
        if(!AbStringUtil.isEmpty(phoneStr_edit)){
            params.put("mobile", phoneStr_edit);
        }
        http.post(Config.URL_UPUSERINFO, params, "正在修改...", new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                if (AbJsonUtil.isSuccess(json)) {
                    ChangePhoneNumberSuccessActivity.startActivity(context);
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


    private class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {
                rules.setVisibility(View.INVISIBLE);
            }
        }
    }
}

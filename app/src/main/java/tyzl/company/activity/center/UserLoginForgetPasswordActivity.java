package tyzl.company.activity.center;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import tyzl.company.R;
import tyzl.company.global.Config;
import tyzl.company.main.BaseActivity;
import tyzl.company.utils.AbStringUtil;
import tyzl.company.utils.AbToastUtil;
import tyzl.company.utils.AddSpaceTextWatcher;
import tyzl.company.utils.CodeUtils;
import tyzl.company.utils.VerificationCodeUtils;
import tyzl.company.weight.CountdownProgressBar;
import tyzl.company.weight.EditTextWithDel;
import tyzl.company.weight.TitleView;

/**
 * 用户忘记密码
 * Created by hjy on 2017/3/14.
 */

public class UserLoginForgetPasswordActivity extends BaseActivity implements VerificationCodeUtils.OnCheckVerify{

    private TitleView titleView;
    private Button forget_password_next_btn;
    private CodeUtils codeUtils;
    private ImageView code;
    private String graph_codeStr;
    private EditText code_edit1;
    private TextView forget_password_rules;
    private EditTextWithDel phone_edit;
    private String phoneStr;
    private String codeStr;
    private AddSpaceTextWatcher addSpaceTextWatcher;

    private VerificationCodeUtils verificationCodeUtils;
    private Button get_verification_code;
    private CountdownProgressBar roundProgressBar;
    private EditTextWithDel et_verification_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_login_forget_password);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.hiddenSearchButton();
        titleView.setTitle("忘记密码");
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, UserLoginForgetPasswordActivity.class);
        context.startActivity(intent);

    }

    @Override
    protected void initView() {
        forget_password_next_btn = (Button) findViewById(R.id.forget_password_next_btn);
        code = (ImageView) findViewById(R.id.code);
        code_edit1 = (EditText) findViewById(R.id.code_edit1);
        forget_password_rules = (TextView) findViewById(R.id.forget_password_rules);
        phone_edit = (EditTextWithDel) findViewById(R.id.phone_edit);
        get_verification_code= (Button) findViewById(R.id.get_verification_code);
        roundProgressBar= (CountdownProgressBar) findViewById(R.id.roundProgressBar);
        et_verification_code= (EditTextWithDel) findViewById(R.id.et_verification_code);
        addSpaceTextWatcher=new AddSpaceTextWatcher(phone_edit,13);
        addSpaceTextWatcher.setSpaceType(AddSpaceTextWatcher.SpaceType.mobilePhoneNumberType);
    }

    /**
     * 生成图形验证码
     */
    private void initCode() {
        codeUtils = CodeUtils.getInstance();
        Bitmap bitmap = codeUtils.createBitmap();
        code.setImageBitmap(bitmap);
    }

    @Override
    protected void setData() {
        initCode();
    }

    @Override
    protected void setEvent() {
        forget_password_next_btn.setOnClickListener(this);
        code.setOnClickListener(this);
        get_verification_code.setOnClickListener(this);
        code_edit1.addTextChangedListener(new MyTextWatcher());
        phone_edit.addTextChangedListener(new MyTextWatcher());

    }

    @Override
    public void onClick(View v) {
        if (v.equals(forget_password_next_btn)) {
            phoneStr=addSpaceTextWatcher.getTextNotSpace();
            graph_codeStr = code_edit1.getText().toString().trim();
            codeStr=et_verification_code.getText().toString().trim();
            String code = codeUtils.getCode();
            if (AbStringUtil.isEmpty(phoneStr)) {
                AbToastUtil.showToast(context, "请输入手机号码");
            } else {
                if (AbStringUtil.isMobileNo(phoneStr)) {
                    if (!AbStringUtil.isEmpty(graph_codeStr)) {
                        if (graph_codeStr.equals(code)) {
                          if (AbStringUtil.isEmpty(codeStr)){
                              AbToastUtil.showToast(context,"请输入短信验证码");
                          }else {

                              if (verificationCodeUtils == null) {
                                  verificationCodeUtils = new VerificationCodeUtils(this, get_verification_code, roundProgressBar, et_verification_code);
                              }
                              verificationCodeUtils.checkVerifyOn(http, phoneStr, 2, codeStr, forget_password_next_btn);


                          }
                        } else {
                            forget_password_rules.setVisibility(View.VISIBLE);
                            forget_password_rules.setText(R.string.input_istrue_graph_code);
                        }

                    } else {
                        AbToastUtil.showToast(context, "请输入图形验证码");
                    }
                } else {
                    forget_password_rules.setVisibility(View.VISIBLE);
                    forget_password_rules.setText(R.string.input_istrue_phone);
                }

            }


        } else if (v.equals(code)) {
            codeUtils = CodeUtils.getInstance();
            Bitmap bitmap = codeUtils.createBitmap();
            code.setImageBitmap(bitmap);
        }else if(v.equals(get_verification_code)){
            if (verificationCodeUtils == null) {
                verificationCodeUtils = new VerificationCodeUtils(this, get_verification_code, roundProgressBar, et_verification_code);
            }
            //type为1   是  注册时的短信验证码
            phoneStr = addSpaceTextWatcher.getTextNotSpace();
            verificationCodeUtils.getVerificationCode(http, phoneStr, 2);
            verificationCodeUtils.setOnCheckVerify(this);

        }
    }

    @Override
    public void onCheckVerify(boolean is) {
        if(is){
            UserLoginInputNewPasswordActivity.startActivity(context,phoneStr);
        }
    }

    private class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 0) {
                forget_password_rules.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            phoneStr = phone_edit.getText().toString().trim();
            if (AbStringUtil.isMobileNo(phoneStr)) {
                forget_password_rules.setVisibility(View.INVISIBLE);
            }
        }
    }
}

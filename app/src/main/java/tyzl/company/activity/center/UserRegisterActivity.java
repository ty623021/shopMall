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
import tyzl.company.volley.RequestListener;
import tyzl.company.weight.CountdownProgressBar;
import tyzl.company.weight.EditTextWithDel;
import tyzl.company.weight.TitleView;

/**
 * 注册
 * Created by hjy on 2017/3/14.
 */

public class UserRegisterActivity extends BaseActivity implements VerificationCodeUtils.OnCheckVerify {
    private TitleView titleView;
    private EditTextWithDel phone_edit;
    private Button next_btn;
    private ImageView code;
    private String phoneStr, graph_codeStr, codeStr;
    private CodeUtils codeUtils;
    private EditText graph_code_edit;
    private AddSpaceTextWatcher addSpaceTextWatcher;
    private TextView phone_rules;
    private VerificationCodeUtils verificationCodeUtils;
    private Button get_verification_code;
    private CountdownProgressBar roundProgressBar;
    private EditTextWithDel et_verification_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setLeftImageButton(R.drawable.back);
        titleView.setTitle("注册");
        titleView.showLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleView.hiddenSearchButton();

    }

    /**
     * 跳转到UserRegisterActivity
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, UserRegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        phone_edit = (EditTextWithDel) findViewById(R.id.phone_edit);
        next_btn = (Button) findViewById(R.id.next_btn);
        code = (ImageView) findViewById(R.id.code);
        graph_code_edit = (EditText) findViewById(R.id.graph_code_edit);
        et_verification_code = (EditTextWithDel) findViewById(R.id.et_verification_code);
        phone_rules = (TextView) findViewById(R.id.phone_rules);
        get_verification_code = (Button) findViewById(R.id.get_verification_code);
        roundProgressBar = (CountdownProgressBar) findViewById(R.id.roundProgressBar);
        addSpaceTextWatcher = new AddSpaceTextWatcher(phone_edit, 13);
        addSpaceTextWatcher.setSpaceType(AddSpaceTextWatcher.SpaceType.mobilePhoneNumberType);


    }

    @Override
    protected void setData() {
        initCode();
    }

    @Override
    protected void setEvent() {
        next_btn.setOnClickListener(this);
        code.setOnClickListener(this);
        phone_edit.addTextChangedListener(new MyTextWatcher());
        graph_code_edit.addTextChangedListener(new MyTextWatcher());
        et_verification_code.addTextChangedListener(new MyTextWatcher());
        get_verification_code.setOnClickListener(this);

    }

    /**
     * 生成图形验证码
     */
    private void initCode() {
        codeUtils = CodeUtils.getInstance();
        Bitmap bitmap = codeUtils.createBitmap();
        code.setImageBitmap(bitmap);
    }

    /**
     * 下一步
     */
    private void nextBtn() {
        String graph_code = codeUtils.getCode();
        if (AbStringUtil.isEmpty(phoneStr)) {
            AbToastUtil.showToast(context, "请输入手机号码");
        } else {
            if (AbStringUtil.isMobileNo(phoneStr)) {
                if (!AbStringUtil.isEmpty(graph_codeStr)) {
                    if (graph_codeStr.equals(graph_code)) {
                        if (AbStringUtil.isEmpty(codeStr)) {
                            AbToastUtil.showToast(context, "请输入短信验证码");
                        } else {
                            if (verificationCodeUtils == null) {
                                verificationCodeUtils = new VerificationCodeUtils(this, get_verification_code, roundProgressBar, et_verification_code);
                            }
                            verificationCodeUtils.checkVerifyOn(http, phoneStr, 1, codeStr, next_btn);
                        }
                    } else {
                        phone_rules.setVisibility(View.VISIBLE);
                        phone_rules.setText(R.string.input_istrue_graph_code);
                    }
                } else {
                    AbToastUtil.showToast(context, "请输入图形验证码");
                }
            } else {
                phone_rules.setVisibility(View.VISIBLE);
                phone_rules.setText(R.string.input_istrue_phone);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.equals(next_btn)) {
            nextBtn();
        } else if (v.equals(code)) {
            codeUtils = CodeUtils.getInstance();
            Bitmap bitmap = codeUtils.createBitmap();
            code.setImageBitmap(bitmap);
        } else if (v.equals(get_verification_code)) {
            if (verificationCodeUtils == null) {
                verificationCodeUtils = new VerificationCodeUtils(this, get_verification_code, roundProgressBar, et_verification_code);
            }
            //type为1   是  注册时的短信验证码
            phoneStr = addSpaceTextWatcher.getTextNotSpace();
            verificationCodeUtils.getVerificationCode(http, phoneStr, 1);
            verificationCodeUtils.setOnCheckVerify(this);
        }

    }

    @Override
    public void onCheckVerify(boolean is) {
        if (is) {
            RegisterInputPasswordActivity.startActivity(context,phoneStr);
        }
    }

    private class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 0) {
                phone_rules.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            phoneStr = addSpaceTextWatcher.getTextNotSpace();
            codeStr = et_verification_code.getText().toString().trim();
            graph_codeStr = graph_code_edit.getText().toString().trim();
            String graph_code = codeUtils.getCode();
            if (graph_codeStr.equals(graph_code)) {
                phone_rules.setVisibility(View.INVISIBLE);
            }
        }
    }
}

package tyzl.company.activity.center;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import tyzl.company.R;
import tyzl.company.entity.UserInfomationInfo;
import tyzl.company.global.Config;
import tyzl.company.main.BaseActivity;
import tyzl.company.utils.AbJsonUtil;
import tyzl.company.utils.AbLogUtil;
import tyzl.company.utils.AbStringUtil;
import tyzl.company.utils.AbToastUtil;
import tyzl.company.utils.CodeUtils;
import tyzl.company.utils.VerificationCodeUtils;
import tyzl.company.volley.RequestListener;
import tyzl.company.weight.CountdownProgressBar;
import tyzl.company.weight.EditTextWithDel;
import tyzl.company.weight.TitleView;

/**
 * 修改手机号码
 * Created by hjy on 2017/3/10.
 */

public class ChangePhoneNumberActivity extends BaseActivity implements VerificationCodeUtils.OnCheckVerify {
    private TitleView titleView;
    private Button next_btn;
    private ImageView code;
    private CodeUtils codeUtils;
    private EditText graph_code_edit1;
    private String graph_codeStr_edit;
    private String codeStr;
    private Button get_verification_code;
    private CountdownProgressBar roundProgressBar;
    private EditTextWithDel et_verification_code;
    private VerificationCodeUtils verificationCodeUtils;
    private TextView user_phone;
    private String current_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_change_phone_number);
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
     * 跳转到ChangePhoneNumberActivity
     */
    public static void startActivity(Context context, String phone) {
        Intent intent = new Intent(context, ChangePhoneNumberActivity.class);
        intent.putExtra("phone", phone);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        next_btn = (Button) findViewById(R.id.next_btn);
        code = (ImageView) findViewById(R.id.code);
        user_phone = (TextView) findViewById(R.id.user_phone);
        graph_code_edit1 = (EditText) findViewById(R.id.graph_code_edit1);
        get_verification_code = (Button) findViewById(R.id.get_verification_code);
        roundProgressBar = (CountdownProgressBar) findViewById(R.id.roundProgressBar);
        et_verification_code = (EditTextWithDel) findViewById(R.id.et_verification_code);

        current_phone = getIntent().getStringExtra("phone");
        user_phone.setText(current_phone);
        initCode();

    }

    @Override
    protected void setData() {

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
    protected void setEvent() {
        next_btn.setOnClickListener(this);
        code.setOnClickListener(this);
        get_verification_code.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.equals(next_btn)) {
            graph_codeStr_edit = graph_code_edit1.getText().toString().trim();
            codeStr = et_verification_code.getText().toString().trim();
            String code = codeUtils.getCode();
            if (!AbStringUtil.isEmpty(graph_codeStr_edit)) {
                if (graph_codeStr_edit.equals(code)) {
                    if (!AbStringUtil.isEmpty(codeStr)) {
                        if (verificationCodeUtils == null) {
                            verificationCodeUtils = new VerificationCodeUtils(this, get_verification_code, roundProgressBar, et_verification_code);
                        }
                        verificationCodeUtils.checkVerifyOn(http, current_phone, 4, codeStr, next_btn);
                    } else {
                        AbToastUtil.showToast(context, "请输入短信验证码");
                    }

                } else {
                    AbToastUtil.showToast(context, "请输入正确的图形验证码");
                }

            } else {
                AbToastUtil.showToast(context, "请输入图形验证码");
            }
        } else if (v.equals(code)) {
            codeUtils = CodeUtils.getInstance();
            Bitmap bitmap = codeUtils.createBitmap();
            code.setImageBitmap(bitmap);
        } else if (v.equals(get_verification_code)) {
            if (verificationCodeUtils == null) {
                verificationCodeUtils = new VerificationCodeUtils(this, get_verification_code, roundProgressBar, et_verification_code);
            }
            verificationCodeUtils.getVerificationCode(http, current_phone, 4);
            verificationCodeUtils.setOnCheckVerify(this);
        }

    }

    @Override
    public void onCheckVerify(boolean is) {
        if (is) {
            ChangePhoneNumberNextActivity.startActivity(context);
        }

    }
}

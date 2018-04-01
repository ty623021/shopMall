package tyzl.company.activity.center;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Interpolator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import tyzl.company.R;
import tyzl.company.entity.UserInfomationInfo;
import tyzl.company.global.Config;
import tyzl.company.main.BaseActivity;
import tyzl.company.utils.AbJsonUtil;
import tyzl.company.utils.AbLogUtil;
import tyzl.company.utils.AbStringUtil;
import tyzl.company.utils.AbToastUtil;
import tyzl.company.volley.RequestListener;
import tyzl.company.weight.TitleView;

/**
 * 选择性别
 * Created by hjy on 2017/3/17.
 */

public class ChooseGenderActivity extends BaseActivity {

    private TitleView titleView;
    private RelativeLayout men, women, secret;
    private ImageView  men_right, women_right, secret_right;
    private String sex_type;
    private UserInfomationInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_choose_gender);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.hiddenSearchButton();
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleView.setTitle("修改性别");
    }


    public static void startActivity(Context context, String sex) {
        Intent intent = new Intent(context, ChooseGenderActivity.class);
        intent.putExtra("sex", sex);
        context.startActivity(intent);
    }


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ChooseGenderActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        men = (RelativeLayout) findViewById(R.id.men);
        women = (RelativeLayout) findViewById(R.id.women);
        secret = (RelativeLayout) findViewById(R.id.secret);
        men_right = (ImageView) findViewById(R.id.men_right);
        women_right = (ImageView) findViewById(R.id.women_right);
        secret_right = (ImageView) findViewById(R.id.secret_right);
        sex_type = getIntent().getStringExtra("sex");


    }

    @Override
    protected void setData() {
        if (!AbStringUtil.isEmpty(sex_type)) {
            if (sex_type.equals("男")) {
                men_right.setVisibility(View.VISIBLE);
            } else if (sex_type.equals("女")) {
                women_right.setVisibility(View.VISIBLE);
            } else if (sex_type.equals("保密")) {
                secret_right.setVisibility(View.VISIBLE);
            }
        } else {
            AbToastUtil.showToast(context, "网络连接失败，请稍后再试");
        }

    }

    @Override
    protected void setEvent() {
        men.setOnClickListener(this);
        women.setOnClickListener(this);
        secret.setOnClickListener(this);

    }

    private void sexOnClickHttp() {

        http.post(Config.URL_UPUSERINFO, params, "正在修改...", new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                if (AbJsonUtil.isSuccess(json)) {
                    AbLogUtil.e("URL_UPUSERINFO", json.toString());
                    info = (UserInfomationInfo) AbJsonUtil.fromJson(json, UserInfomationInfo.class);
                    if (info != null) {
                        AbToastUtil.showToast(context,"修改性别成功");
                        finish();
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
    public void onClick(View v) {
       if (v.equals(men)) {
            men_right.setVisibility(View.VISIBLE);
            women_right.setVisibility(View.INVISIBLE);
            secret_right.setVisibility(View.INVISIBLE);
            params.put("sex", 1 + "");
            sexOnClickHttp();
        } else if (v.equals(women)) {
            men_right.setVisibility(View.INVISIBLE);
            women_right.setVisibility(View.VISIBLE);
            secret_right.setVisibility(View.INVISIBLE);
            params.put("sex", 2 + "");
            sexOnClickHttp();
        } else if (v.equals(secret)) {
            men_right.setVisibility(View.INVISIBLE);
            women_right.setVisibility(View.INVISIBLE);
            secret_right.setVisibility(View.VISIBLE);
            params.put("sex", 0 + "");
            sexOnClickHttp();
        }

    }


}

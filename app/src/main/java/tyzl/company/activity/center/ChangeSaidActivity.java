package tyzl.company.activity.center;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import tyzl.company.R;
import tyzl.company.activity.MainActivity;
import tyzl.company.entity.UserInfomationInfo;
import tyzl.company.global.Config;
import tyzl.company.main.BaseActivity;
import tyzl.company.utils.AbJsonUtil;
import tyzl.company.utils.AbStringUtil;
import tyzl.company.utils.AbToastUtil;
import tyzl.company.volley.RequestListener;
import tyzl.company.weight.EditTextWithDel;
import tyzl.company.weight.TitleView;

/**
 * 修改呢称
 * Created by hjy on 2017/3/13.
 */

public class ChangeSaidActivity extends BaseActivity {

    private TitleView titleView;
    private ChangeSaidActivity mActivity;
    private EditTextWithDel said_edit;
    private String saidStr;
    private UserInfomationInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_change_said);
        mActivity = this;
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setLeftImageButton(R.drawable.back);
        titleView.hiddenSearchButton();
        titleView.showRightTextButton();
        titleView.showLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleView.setTitle("修改昵称");
        titleView.setRightTextButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saidStr = said_edit.getText().toString().trim();
                if (!AbStringUtil.isEmpty(saidStr)) {
                    changeSaidHttp();
                }else {
                    AbToastUtil.showToast(context,"请输入您修改的呢称");
                }


            }
        });

    }

    /**
     * 跳转到ChangeSaidActivity
     *
     * @param context
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ChangeSaidActivity.class);
        context.startActivity(intent);
    }

    /*
    修改呢称的请求
     */
    private void changeSaidHttp() {
        params.put("nickname", saidStr);
        http.post(Config.URL_UPUSERINFO, params, "正在修改...", new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                if (AbJsonUtil.isSuccess(json)) {
                    info = (UserInfomationInfo) AbJsonUtil.fromJson(json, UserInfomationInfo.class);
                    if (info != null) {
                        AbToastUtil.showToast(context, "修改呢称成功");
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
    protected void initView() {
        said_edit = (EditTextWithDel) findViewById(R.id.said_edit);

    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setEvent() {

    }

    @Override
    public void onClick(View v) {

    }
}

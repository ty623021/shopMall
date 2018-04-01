package tyzl.company.activity.center;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tyzl.company.R;
import tyzl.company.activity.MainActivity;
import tyzl.company.global.Config;
import tyzl.company.main.BaseActivity;
import tyzl.company.weight.TitleView;

/**
 * 修改手机号码成功页面
 * Created by hjy on 2017/3/10.
 */

public class ChangePhoneNumberSuccessActivity extends BaseActivity {
    private TitleView titleView;
    private Button know_btn;
    private ChangePhoneNumberSuccessActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_change_phone_number_success);
        mActivity = this;
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setTitle("修改成功");
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
     * 跳转到ChangePhoneNumberSuccessActivity
     *
     * @param context
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ChangePhoneNumberSuccessActivity.class);
        context.startActivity(intent);

    }

    @Override
    protected void initView() {
        know_btn = (Button) findViewById(R.id.know_btn);

    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setEvent() {
        know_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.equals(know_btn)) {
            MainActivity.startMainActivity(mActivity, 3);
        }

    }
}

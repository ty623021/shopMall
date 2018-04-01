package tyzl.company.activity.shopping;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import tyzl.company.R;
import tyzl.company.global.Config;
import tyzl.company.main.BaseActivity;
import tyzl.company.weight.TitleView;


/****
 * 支付失败页面
 */
public class PayFailActivity extends BaseActivity {


    private TitleView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pay_fail);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));

    }

    /**
     * 跳转到 PayFailActivity
     *
     * @param context
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, PayFailActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void initTitle() {
        // TODO Auto-generated method stub

    }


    @Override
    protected void initView() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setTitle("支付");
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }


    @Override
    protected void setData() {
        // TODO Auto-generated method stub

    }


    @Override
    protected void setEvent() {

    }


    @Override
    public void onClick(View v) {

    }
}


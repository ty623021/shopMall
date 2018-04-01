package tyzl.company.activity.shopping;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tyzl.company.R;
import tyzl.company.entity.ShoppingCartInfo;
import tyzl.company.global.Config;
import tyzl.company.main.BaseActivity;
import tyzl.company.weight.TitleView;


/****
 * 支付页面
 */
public class PayActivity extends BaseActivity {

    private TitleView titleView;
    private Button go_pay;
    private ShoppingCartInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pay);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));

    }

    /**
     * 跳转到 PayActivity
     *
     * @param context
     */
    public static void startActivity(Context context, ShoppingCartInfo info) {
        Intent intent = new Intent(context, PayActivity.class);
        intent.putExtra("info", info);
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
        info = (ShoppingCartInfo) getIntent().getSerializableExtra("info");
        go_pay = (Button) findViewById(R.id.go_pay);
    }


    @Override
    protected void setData() {
        // TODO Auto-generated method stub

    }


    @Override
    protected void setEvent() {
        go_pay.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.equals(go_pay)) {
            if (info != null) {
                PaySuccessActivity.startActivity(this, info);
            }
        }
    }
}


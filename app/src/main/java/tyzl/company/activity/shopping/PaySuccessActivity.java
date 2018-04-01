package tyzl.company.activity.shopping;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import tyzl.company.R;
import tyzl.company.activity.MainActivity;
import tyzl.company.entity.ShoppingCartInfo;
import tyzl.company.global.Config;
import tyzl.company.main.BaseActivity;
import tyzl.company.weight.TitleView;


/****
 * 支付成功页面
 */
public class PaySuccessActivity extends BaseActivity {


    private TitleView titleView;
    private ShoppingCartInfo info;
    private TextView pay_amount;
    private TextView pay_coupon_amount;
    private Button pay_go_home;
    private Button pay_go_order;
    private TextView pay_goods_money;
    private TextView pay_goods_number;
    private TextView pay_goods_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pay_success);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));

    }

    /**
     * 跳转到 PayActivity
     *
     * @param context
     */
    public static void startActivity(Context context, ShoppingCartInfo info) {
        Intent intent = new Intent(context, PaySuccessActivity.class);
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
        titleView.setTitle("支付成功");
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        info = (ShoppingCartInfo) getIntent().getSerializableExtra("info");

        pay_amount = (TextView) findViewById(R.id.pay_amount);
        pay_coupon_amount = (TextView) findViewById(R.id.pay_coupon_amount);
        pay_go_home = (Button) findViewById(R.id.pay_go_home);
        pay_go_order = (Button) findViewById(R.id.pay_go_order);
        pay_goods_money = (TextView) findViewById(R.id.pay_goods_money);
        pay_goods_number = (TextView) findViewById(R.id.pay_goods_number);
        pay_goods_title = (TextView) findViewById(R.id.pay_goods_title);

    }


    @Override
    protected void setData() {
        // TODO Auto-generated method stub
        if (info != null) {
            pay_amount.setText("￥" + info.getReal_price());
            pay_goods_money.setText("￥" + info.getReal_price());
            pay_goods_number.setText(info.getGoods_num() + "");
            pay_goods_title.setText(info.getGoods_name() + "");
        }
    }


    @Override
    protected void setEvent() {
        pay_go_home.setOnClickListener(this);
        pay_go_order.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v.equals(pay_go_home)) {
            MainActivity.startMainActivity(this, 0);
        } else if (v.equals(pay_go_order)) {
            PayFailActivity.startActivity(this);
        }
    }
}


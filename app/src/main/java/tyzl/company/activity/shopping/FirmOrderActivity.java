package tyzl.company.activity.shopping;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import tyzl.company.R;
import tyzl.company.activity.address.SelectAddressActivity;
import tyzl.company.adapter.FirmOrderAdapter;
import tyzl.company.entity.AddressInfo;
import tyzl.company.entity.OrderSumInfo;
import tyzl.company.entity.ShoppingCartInfo;
import tyzl.company.global.Config;
import tyzl.company.global.Constant;
import tyzl.company.main.BaseActivity;
import tyzl.company.utils.AbMathUtil;
import tyzl.company.utils.AbStringUtil;
import tyzl.company.weight.TitleView;

/**
 * Created by hjy on 2016/11/5.
 * 确认订单
 */
public class FirmOrderActivity extends BaseActivity {

    private static List<ShoppingCartInfo> infos;
    private TitleView titleView;
    private FirmOrderAdapter adapter;
    private ListView listView;
    private Button go_pay;
    private LinearLayout address_linear;
    private OrderSumInfo orderSumInfo;
    private AddressInfo addressInfo;
    private TextView tv_sum_integral;
    private TextView tv_sum_money;
    private TextView goods_receipt_name;
    private TextView goods_receipt_phone;
    private TextView tv_address;
    private EditText et_remarks;
    private String remarks;
    private RelativeLayout coupon_relative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_firm_order);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }

    /**
     * 跳转到 FirmOrderActivity
     *
     * @param context
     */
    public static void startActivity(Context context, List<ShoppingCartInfo> orderInfo) {
        infos = orderInfo;
        Intent intent = new Intent(context, FirmOrderActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转到 FirmOrderActivity
     *
     * @param context
     */
    public static void startActivity(Context context, List<ShoppingCartInfo> orderInfo, OrderSumInfo orderSumInfo, AddressInfo addressInfo) {
        infos = orderInfo;
        Intent intent = new Intent(context, FirmOrderActivity.class);
        intent.putExtra("orderSumInfo", orderSumInfo);
        intent.putExtra("addressInfo", addressInfo);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setTitle("确认订单");
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        Intent intent = getIntent();
        orderSumInfo = (OrderSumInfo) intent.getSerializableExtra("orderSumInfo");
        addressInfo = (AddressInfo) intent.getSerializableExtra("addressInfo");
    }

    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.listView);
        address_linear = (LinearLayout) findViewById(R.id.address_linear);
        go_pay = (Button) findViewById(R.id.go_pay);
        if (infos != null) {
            adapter = new FirmOrderAdapter(this, R.layout.firm_order_item_layout, infos);
            listView.setAdapter(adapter);
        }
        tv_sum_integral = (TextView) findViewById(R.id.tv_sum_integral);
        tv_sum_money = (TextView) findViewById(R.id.tv_sum_money);
        goods_receipt_name = (TextView) findViewById(R.id.goods_receipt_name);
        goods_receipt_phone = (TextView) findViewById(R.id.goods_receipt_phone);
        tv_address = (TextView) findViewById(R.id.tv_address);
        et_remarks = (EditText) findViewById(R.id.et_remarks);
        coupon_relative = (RelativeLayout) findViewById(R.id.coupon_relative);
    }

    @Override
    protected void setData() {
        if (orderSumInfo != null) {
            tv_sum_integral.setText(AbMathUtil.roundStr(orderSumInfo.getTotal_real_integral(), 0) + "积分");
            tv_sum_money.setText(AbMathUtil.roundStr(orderSumInfo.getTotal_real_price(), Constant.ROUND_DIGIT) + "元");
        }
        setAddress(addressInfo);
    }

    private void setAddress(AddressInfo info) {
        if (info != null) {
            addressInfo = info;
            goods_receipt_name.setText("收货人：" + info.getConsignee());
            goods_receipt_phone.setText(info.getMobile());
            tv_address.setText(AbStringUtil.getAddress(info.getProvince(), info.getCity(), info.getDistrict(), info.getAddress()));
        }
    }

    @Override
    protected void setEvent() {
        go_pay.setOnClickListener(this);
        address_linear.setOnClickListener(this);
        coupon_relative.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(go_pay)) {
            payOrder();
        } else if (view.equals(address_linear)) {
//            SelectAddressActivity.startActivity(FirmOrderActivity.this, addressInfo.getAddress_id());
            SelectAddressActivity.startActivity(FirmOrderActivity.this);
        } else if (view.equals(coupon_relative)) {
            CouponActivity.startActivity(this);
        }
    }

    private void payOrder() {
        ShoppingCartInfo info = infos.get(0);
        PayActivity.startActivity(this, info);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            AddressInfo info = (AddressInfo) data.getSerializableExtra("info");
            setAddress(info);
        }
    }

}

package tyzl.company.activity.center;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import tyzl.company.R;
import tyzl.company.adapter.AllTabAdapter;
import tyzl.company.fragment.otherFragment.FragmentAllOrderManager;
import tyzl.company.global.Config;
import tyzl.company.main.BaseActivity;
import tyzl.company.weight.TitleView;

/**
 * 订单管理
 * Created by hjy on 2017/3/9.
 */

public class OrderMangerActivity extends BaseActivity {

    private TitleView titleView;
    private static TabLayout tabLayout;
    public AllTabAdapter adapter;
    private static String all;
    private static String waitting_pay;
    private static String waiting_delivery;
    private static String account_paid;
    private static String canceled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order_manager);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setTitle("订单管理");
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    /**
     * 订单管理OrderMangerActivity
     */

    public static void startActivity(Context context, int type) {
        Intent intent = new Intent(context, OrderMangerActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new AllTabAdapter(this, viewPager);

        adapter.addTab("全部", "0", FragmentAllOrderManager.class);
        adapter.addTab("待付款", "1", FragmentAllOrderManager.class);
        adapter.addTab("待发货", "2", FragmentAllOrderManager.class);
        adapter.addTab("已发货", "3", FragmentAllOrderManager.class);
        adapter.addTab("已取消", "4", FragmentAllOrderManager.class);
        //方法二：一步到位
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        int type = getIntent().getIntExtra("type", 0);
        viewPager.setCurrentItem(type);
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

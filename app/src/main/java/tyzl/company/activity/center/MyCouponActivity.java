package tyzl.company.activity.center;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.TabLayout;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import tyzl.company.R;
import tyzl.company.adapter.AllTabAdapter;
import tyzl.company.entity.MyCouponInfo;
import tyzl.company.fragment.otherFragment.FragmentMyCoupon;
import tyzl.company.global.Config;
import tyzl.company.main.BaseActivity;
import tyzl.company.utils.AbJsonUtil;
import tyzl.company.utils.AbLogUtil;
import tyzl.company.utils.AbToastUtil;
import tyzl.company.volley.RequestListener;
import tyzl.company.weight.TitleView;


/**
 * 我的优惠券
 * Created by hjy on 2017/3/22.
 */

public class MyCouponActivity extends BaseActivity {
    private TitleView titleView;
    private TabLayout tabLayout;
    private AllTabAdapter adapter;
    private ViewPager viewPager;
    private MyCouponInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_coupon);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));


    }


    @Override
    protected void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new AllTabAdapter(this, viewPager);
        setHttp();

    }

    /**
     * 获取优惠券的个数
     */
    private void setHttp() {

        http.post(context, Config.URL_GETCOUPONNUM, params, "正在加载...", new RequestListener() {

            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e("json", json);
                if (AbJsonUtil.isSuccess(json)) {
                    TypeToken typeToken = new TypeToken<List<MyCouponInfo>>() {
                    };
                    List<MyCouponInfo> data = (List<MyCouponInfo>) AbJsonUtil.fromJson(json, typeToken, "result");
                    AbLogUtil.e("data", data.toString());
                    if (data != null) {
                        for (int i = 0; i < data.size(); i++) {
                            info = data.get(i);
                            if (info != null) {
                                if (info.getType() == 0) {
                                    adapter.addTab("未使用" + "" + "(" + info.getNums() + ")", "0", FragmentMyCoupon.class);
                                    AbLogUtil.e("no_used", info.getNums() + "");
//                            tv_no_useType.setText("未使用" + "" + "(" + info.getNum() + ")");
//            tabLayout.getTabAt(Integer.parseInt(type)).setText("未使用"+String.valueOf(kinds));
                                } else if (info.getType() == 1) {
                                    adapter.addTab("已使用" + "" + "(" + info.getNums() + ")", "1", FragmentMyCoupon.class);
                                    AbLogUtil.e("used", info.getNums() + "");
//                            tv_usedType.setText("已使用" + "" + "(" + info.getNum() + ")");
//            tabLayout.getTabAt(Integer.parseInt(type)).setText("已使用"+String.valueOf(kinds));
                                } else if (info.getType() == 2) {
                                    adapter.addTab("已过期" + "" + "(" + info.getNums() + ")", "2", FragmentMyCoupon.class);
                                    AbLogUtil.e("over_used", info.getNums() + "");
//                            tv_overType.setText("已过期" + "" + "(" + info.getNum() + ")");
//            tabLayout.getTabAt(Integer.parseInt(type)).setText("已过期"+String.valueOf(kinds));
                                }
                                viewPager.setCurrentItem(0);
                                viewPager.setOffscreenPageLimit(3);
                                viewPager.setAdapter(adapter);
                                tabLayout.setupWithViewPager(viewPager);
                            }

                        }


                    }
                } else {
                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                }
            }

            @Override
            public void requestError(String message) {
                AbToastUtil.showToast(context,message);

            }
        });
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.hiddenSearchButton();
        titleView.setTitle("我的优惠券");
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MyCouponActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setEvent() {


    }

    @Override
    protected void setData() {

    }

    @Override
    public void onClick(View v) {

    }

}

package tyzl.company.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tyzl.company.R;
import tyzl.company.adapter.LicenseViewPagerAdapter;
import tyzl.company.global.Config;
import tyzl.company.main.BaseActivity;
import tyzl.company.viewPager.GuideTransformer;
import tyzl.company.weight.TitleView;

/**
 * 引导页
 */
public class GuideActivity extends BaseActivity {
    protected String TAG = this.getClass().getSimpleName();
    private ViewPager vp;
    private List<View> views;
    private LicenseViewPagerAdapter adapter;
    private int[] view = {
            R.drawable.guide_page1,
            R.drawable.guide_page3,
            R.drawable.guide_page4,

    };
    private TextView immediately;
    private TextView skip;
    private TitleView titleView;
    private GuideActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_guide);
        mActivity = this;
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, false, Color.parseColor(Config.colorPrimary));
    }

    @Override
    protected void initTitle() {

    }

    /**
     * 跳转到 ConfirmBidActivity
     *
     * @param context
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, GuideActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        titleView = (TitleView) findViewById(R.id.title);
        vp = (ViewPager) findViewById(R.id.vp);
        views = new ArrayList<>();
        for (int i = 0; i < view.length; i++) {
            View guide_item = View.inflate(this, R.layout.guide_item, null);
            ImageView imageView = (ImageView) guide_item.findViewById(R.id.iv_guide);
            skip = (TextView) guide_item.findViewById(R.id.tiaoguo);
            immediately = (TextView) guide_item.findViewById(R.id.liji);
            skip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.startMainActivity(mActivity, 0);
                }
            });
            immediately.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.startMainActivity(mActivity, 0);
                }
            });
            int i1 = this.view[i];
            imageView.setBackgroundResource(i1);
            if (i == view.length - 1) {
                skip.setVisibility(View.GONE);
                immediately.setVisibility(View.VISIBLE);
            }
            views.add(guide_item);
        }
        adapter = new LicenseViewPagerAdapter(views, this);
        vp.setPageTransformer(true, new GuideTransformer());
        vp.setAdapter(adapter);
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

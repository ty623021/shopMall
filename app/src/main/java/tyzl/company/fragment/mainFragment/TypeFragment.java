
package tyzl.company.fragment.mainFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import tyzl.company.R;
import tyzl.company.activity.shopping.SearchActivity;
import tyzl.company.entity.HomeTypeInfo;
import tyzl.company.fragment.otherFragment.FragmentGoodsType;
import tyzl.company.global.Config;
import tyzl.company.main.BaseFragment;
import tyzl.company.main.MyApplication;
import tyzl.company.pullview.AbRefreshUtil;
import tyzl.company.utils.AbJsonUtil;
import tyzl.company.utils.AbStringUtil;
import tyzl.company.utils.AbToastUtil;
import tyzl.company.volley.RequestListener;
import tyzl.company.weight.TitleView;

/**
 * 商品一级分类
 */
public class TypeFragment extends BaseFragment {

    private View view;
    private FragmentActivity mActivity;
    private List<HomeTypeInfo> toolsList = new ArrayList<>();
    private TextView toolsTextViews[];
    private ImageView toolsImgViews[];
    private RelativeLayout toolsRelative[];
    private View views[];
    private LayoutInflater inflater;
    private ScrollView scrollView;
    private int scrollViewWidth = 0, scrollViewMiddle = 0;
    private ViewPager shop_pager;
    private int currentItem = 0;
    private ShopAdapter shopAdapter;
    private TitleView titleView;

    private ProgressBar progressBar;
    private ImageView network_img;
    private ImageView nodata_img;
    private LinearLayout tools_linear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_type, container, false);
        mActivity = getActivity();
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) view.findViewById(R.id.title);
        titleView.showSearchTvButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.startActivity(context);
            }
        });
    }

    @Override
    protected void initView() {
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        network_img = (ImageView) view.findViewById(R.id.network_img);
        nodata_img = (ImageView) view.findViewById(R.id.nodata_img);
        tools_linear = (LinearLayout) view.findViewById(R.id.tools_linear);
        scrollView = (ScrollView) view.findViewById(R.id.tools_scrollview);
        shopAdapter = new ShopAdapter(mActivity.getSupportFragmentManager());
        inflater = LayoutInflater.from(mActivity);
        shop_pager = (ViewPager) view.findViewById(R.id.goods_pager);
        shop_pager.setAdapter(shopAdapter);
        shop_pager.setOnPageChangeListener(onPageChangeListener);
    }

    @Override
    protected void setData() {
        sendHttp();
    }

    @Override
    protected void setEvent() {
        network_img.setOnClickListener(this);
        nodata_img.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.equals(network_img)) {
            progressBar.setVisibility(View.VISIBLE);
            network_img.setVisibility(View.GONE);
            sendHttp();
        } else if (v.equals(nodata_img)) {
            nodata_img.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            sendHttp();
        }
    }


    /**
     * 动态生成显示items中的textview
     */
    private void showToolsView(List<HomeTypeInfo> list) {
        if (toolsList.size() > 0) {
            toolsList.clear();
        }
        if (list != null) {
            toolsList.addAll(list);
        } else {
            List<HomeTypeInfo> typeInfoList = MyApplication.mApp.getHomeTypeInfoList();
            if (AbStringUtil.isListEmpty(typeInfoList)) {
                toolsList.addAll(typeInfoList);
            }
        }
        shopAdapter.notifyDataSetChanged();
        LinearLayout toolsLayout = (LinearLayout) view.findViewById(R.id.tools);
        toolsTextViews = new TextView[toolsList.size()];
        toolsImgViews = new ImageView[toolsList.size()];
        toolsRelative = new RelativeLayout[toolsList.size()];
        views = new View[toolsList.size()];

        for (int i = 0; i < toolsList.size(); i++) {
            View view = inflater.inflate(R.layout.goods_type_title_item_layout, null);
            view.setId(i);
            view.setOnClickListener(toolsItemListener);
            TextView textView = (TextView) view.findViewById(R.id.text);
            ImageView line_right = (ImageView) view.findViewById(R.id.line_right);
            RelativeLayout type_item_bg = (RelativeLayout) view.findViewById(R.id.type_item_bg);
            textView.setText(toolsList.get(i).getName() + "");
            toolsLayout.addView(view);
            toolsTextViews[i] = textView;
            views[i] = view;
            toolsRelative[i] = type_item_bg;
            toolsImgViews[i] = line_right;
        }
        changeTextColor(0);
    }

    private View.OnClickListener toolsItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            shop_pager.setCurrentItem(v.getId(), false);
        }
    };

    /**
     * OnPageChangeListener<br/>
     * 监听ViewPager选项卡变化事的事件
     */

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int arg0) {
            if (shop_pager.getCurrentItem() != arg0) shop_pager.setCurrentItem(arg0);
            if (currentItem != arg0) {
                changeTextColor(arg0);
                changeTextLocation(arg0);
            }
            currentItem = arg0;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };


    /**
     * ViewPager 加载选项卡
     *
     * @author Administrator
     */
    private class ShopAdapter extends FragmentPagerAdapter {
        public ShopAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            Fragment fragment = new FragmentGoodsType();
            Bundle bundle = new Bundle();
            HomeTypeInfo homeTypeInfo = toolsList.get(arg0);
            bundle.putSerializable("homeTypeInfo", homeTypeInfo);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return toolsList.size();
        }
    }


    /**
     * 改变textView的颜色
     * 隐藏或显示被选中的title 右边的线条
     *
     * @param id
     */
    private void changeTextColor(int id) {
        for (int i = 0; i < toolsTextViews.length; i++) {
            if (i != id) {
                toolsTextViews[i].setTextColor(0xff000000);
                toolsImgViews[i].setVisibility(View.VISIBLE);
                toolsRelative[i].setSelected(false);
            }
        }
        if (toolsTextViews.length > 0) {
            toolsTextViews[id].setTextColor(0xff1a1a1a);
        }
        if (toolsImgViews.length > 0) {
            toolsImgViews[id].setVisibility(View.GONE);
        }
        if (toolsRelative.length > 0) {
            toolsRelative[id].setSelected(true);
        }

    }


    /**
     * 改变栏目位置
     *
     * @param clickPosition
     */
    private void changeTextLocation(int clickPosition) {

        int x = (views[clickPosition].getTop() - getScrollViewMiddle() + (getViewHeight(views[clickPosition]) / 2));
        scrollView.smoothScrollTo(0, x);
    }

    /**
     * 返回scrollview的中间位置
     *
     * @return
     */
    private int getScrollViewMiddle() {
        if (scrollViewMiddle == 0)
            scrollViewMiddle = getScrollViewHeight() / 2;
        return scrollViewMiddle;
    }

    /**
     * 返回ScrollView的宽度
     *
     * @return
     */
    private int getScrollViewHeight() {
        if (scrollViewWidth == 0)
            scrollViewWidth = scrollView.getBottom() - scrollView.getTop();
        return scrollViewWidth;
    }

    /**
     * 返回view的宽度
     *
     * @param view
     * @return
     */
    private int getViewHeight(View view) {
        return view.getBottom() - view.getTop();
    }


    private void sendHttp() {
        http.post(Config.URL_GOODS_CATEGORY_LIST, params, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                tools_linear.setVisibility(View.VISIBLE);
                if (AbJsonUtil.isSuccess(json)) {
                    TypeToken typeToken = new TypeToken<List<HomeTypeInfo>>() {
                    };
                    List<HomeTypeInfo> result = (List<HomeTypeInfo>) AbJsonUtil.fromJson(json, typeToken, "result");
                    if (AbStringUtil.isListEmpty(result)) {
                        MyApplication.mApp.saveHomeTypeInfoList(result);
                        showToolsView(result);
                    } else {
                        showToolsView(null);
                    }
                } else {
                    showToolsView(null);
                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                }
                AbRefreshUtil.hintView(shopAdapter, false, network_img, nodata_img, progressBar);
            }

            @Override
            public void requestError(String message) {
                AbToastUtil.showToast(context, message);
                AbRefreshUtil.hintView(shopAdapter, true, network_img, nodata_img, progressBar);
            }
        });
    }

}

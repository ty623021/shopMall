
package tyzl.company.fragment.mainFragment;


import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import tyzl.company.R;
import tyzl.company.activity.WebViewActivity;
import tyzl.company.activity.shopping.GoodsListActivity;
import tyzl.company.activity.shopping.SearchActivity;
import tyzl.company.adapter.HomeGoodsRecycleAdapter;
import tyzl.company.adapter.HomeTypeAdapter;
import tyzl.company.entity.BannerInfo;
import tyzl.company.entity.GoodsInfo;
import tyzl.company.entity.HomeTypeInfo;
import tyzl.company.global.Config;
import tyzl.company.global.Constant;
import tyzl.company.main.BaseFragment;
import tyzl.company.pullview.AbPullToRefreshView;
import tyzl.company.pullview.AbRefreshUtil;
import tyzl.company.recycleView.SpacesItemDecoration;
import tyzl.company.utils.AbImageUtil;
import tyzl.company.utils.AbJsonUtil;
import tyzl.company.utils.AbLogUtil;
import tyzl.company.utils.AbStringUtil;
import tyzl.company.utils.AbToastUtil;
import tyzl.company.utils.BannerUtil;
import tyzl.company.viewPager.CycleViewPager;
import tyzl.company.viewPager.ViewFactory;
import tyzl.company.volley.RequestListener;
import tyzl.company.weight.MyGridView;

import static tyzl.company.R.id.gridView;
import static tyzl.company.utils.AbJsonUtil.fromJsonMall;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, AbPullToRefreshView.OnFooterLoadListener, BannerUtil.OnBannerClick {

    private View view;
    private FragmentActivity mActivity;
    private CycleViewPager cycleViewPager;
    private SwipeRefreshLayout swipe;
    // 轮播圆点
    private List<ImageView> views = new ArrayList<>();
    private BannerUtil getBanner;
    private RecyclerView homeGoodsRecycle;//首页商品
    private MyGridView homeTypeGridView;//首页分类
    private HomeTypeAdapter homeTypeAdapter;
    private NestedScrollView scrollView;
    private List<GoodsInfo> goods_list = new ArrayList<>();
    private List<HomeTypeInfo> typeInfos = new ArrayList<>();
    private LinearLayout search, search_liner;
    private TextView search_text;
    private ImageView search_icon;

    private ImageView advert_img;
    private BannerInfo advert_info;

    private ProgressBar progressBar;
    private ImageView network_img;
    private ImageView nodata_img;
    private boolean isRefresh = true;

    //透明度
    private static final int START_ALPHA = 0;
    private static final int START_ALPHA1 = 200;
    private static final int END_ALPHA = 255;
    private RelativeLayout home_relative;
    private GridLayoutManager layoutManager;
    private HomeGoodsRecycleAdapter homeGoodsAdapter;
    private int height;
    private AbPullToRefreshView pull;
    private int nextPage = 1;
    private int totalPages = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        mActivity = getActivity();
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void initView() {
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        pull = (AbPullToRefreshView) view.findViewById(R.id.pull);
        homeGoodsRecycle = (RecyclerView) view.findViewById(R.id.home_goods);
        scrollView = (NestedScrollView) view.findViewById(R.id.scrollView);
        search_liner = (LinearLayout) view.findViewById(R.id.search_liner);
        search = (LinearLayout) view.findViewById(R.id.search);
        search_text = (TextView) view.findViewById(R.id.search_text);
        search_icon = (ImageView) view.findViewById(R.id.search_icon);

        home_relative = (RelativeLayout) view.findViewById(R.id.home_relative);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        network_img = (ImageView) view.findViewById(R.id.network_img);
        nodata_img = (ImageView) view.findViewById(R.id.nodata_img);
        advert_img = (ImageView) view.findViewById(R.id.advert_img);
        homeTypeGridView = (MyGridView) view.findViewById(gridView);
        layoutManager = new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);

        homeGoodsRecycle.setLayoutManager(layoutManager);

        homeGoodsRecycle.addItemDecoration(new SpacesItemDecoration(20));

        AbRefreshUtil.initRefresh(pull, swipe, this, this);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void setEvent() {
        search.setOnClickListener(this);
        advert_img.setOnClickListener(this);
        network_img.setOnClickListener(this);
        nodata_img.setOnClickListener(this);
        setOnScrollListener();
        addCycleViewPager();
        homeGoodsRecycle.setNestedScrollingEnabled(false);
        homeGoodsRecycle.setFocusable(false);
        homeTypeGridView.setFocusable(false);
        homeTypeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GoodsListActivity.startActivity(context, typeInfos.get(position));
            }
        });


    }

    @Override
    protected void setData() {
        homeGoodsAdapter = new HomeGoodsRecycleAdapter(context, goods_list);
        homeGoodsRecycle.setAdapter(homeGoodsAdapter);

        homeTypeAdapter = new HomeTypeAdapter(context, R.layout.fragment_home_type_item, typeInfos);
        homeTypeGridView.setAdapter(homeTypeAdapter);

        if (getBanner == null) {
            getBanner = new BannerUtil(context, http, Constant.ACTIVITY_BANNER);
        }

        getBanner.getBanner();
        getBanner.setOnBannerClick(this);
        sendHttp();
        height = 120;//初始高度设置为120
        setAlpha(0);//第一次进入的时候搜索栏显示为半透明
    }

    @Override
    public void onClick(View v) {
        if (v.equals(search)) {
            SearchActivity.startActivity(context);
        } else if (v.equals(advert_img)) {
            if (advert_info != null && !AbStringUtil.isEmail(advert_info.getLink())) {
                WebViewActivity.startWebActivity(getActivity(), advert_info.getLink(), advert_info.getTitle());
            }
        } else if (v.equals(network_img)) {
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
     * 添加首页导航栏
     */
    private void addCycleViewPager() {
        FragmentTransaction transaction = mActivity.getFragmentManager().beginTransaction();
        cycleViewPager = new CycleViewPager();
        transaction.add(R.id.viewPager_content, cycleViewPager);
        transaction.commitAllowingStateLoss();
        cycleViewPager.setSwipeRefreshLayout(swipe);
    }


    /**
     * 发送网络请求
     */
    private void sendHttp() {
        http.post(Config.URL_INDEX, params, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e(TAG + "INDEX", json);
                //在首页数据加载成功之后显示首页的布局,如果首页布局已经显示，就不做处理
                if (home_relative.getVisibility() == View.GONE) {
                    home_relative.setVisibility(View.VISIBLE);
                }
                if (AbJsonUtil.isSuccess(json)) {
                    setValue(json);
                } else {
                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                }
                AbRefreshUtil.onLoadComplete(pull, swipe, nextPage, totalPages);
                AbRefreshUtil.hintView(homeGoodsAdapter, false, network_img, nodata_img, progressBar);
            }

            @Override
            public void requestError(String message) {
                AbRefreshUtil.onLoadComplete(pull, swipe, nextPage, totalPages);
                AbToastUtil.showToast(context, message);
                AbRefreshUtil.hintView(homeGoodsAdapter, true, network_img, nodata_img, progressBar);
            }
        });
    }

    /**
     * 赋值
     *
     * @param json
     */
    private void setValue(String json) {
        TypeToken token = new TypeToken<List<HomeTypeInfo>>() {
        };
        TypeToken token1 = new TypeToken<List<GoodsInfo>>() {
        };
        nextPage = AbJsonUtil.getInteger(json, "nextPage");
        totalPages = AbJsonUtil.getInteger(json, "totalPages");
        //获取首页分类数据
        List<HomeTypeInfo> iconList = (List<HomeTypeInfo>) fromJsonMall(json, token, "iconList");
        //获取首页商品数据
        List<GoodsInfo> goods = (List<GoodsInfo>) fromJsonMall(json, token1, "goods");
        //获取首页广告图片
        advert_info = (BannerInfo) AbJsonUtil.fromJsonMall(json, "middle_adv", BannerInfo.class);
        if (AbStringUtil.isListEmpty(iconList)) {
            if (typeInfos.size() > 0) {
                typeInfos.clear();
            }
            typeInfos.addAll(iconList);
            homeTypeAdapter.notifyDataSetChanged();
        }
        if (AbStringUtil.isListEmpty(goods)) {
            if (isRefresh && goods_list.size() > 0) {
                goods_list.clear();
            }
            goods_list.addAll(goods);
            homeGoodsAdapter.notifyDataSetChanged();
        }
        if (advert_info != null) {
            AbImageUtil.glideImageList(advert_info.getImgUrl(), advert_img, R.drawable.default_banner);
        }
    }

    /**
     * 设置scrollView滑动监听 改变搜索栏的透明度变化
     */
    private void setOnScrollListener() {
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                height = search_liner.getHeight();
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    AbLogUtil.e(TAG, "滑动到底部");
                }
                if (scrollY > height) {
                    scrollY = height;   //当滑动到指定位置之后设置颜色为纯色，之前的话要渐变---实现下面的公式即可
                }
                setAlpha(scrollY);
            }
        });

    }

    private void setAlpha(int scrollY) {
        if (scrollY > 0) {
            search_text.setTextColor(Color.WHITE);
        } else {
            search_text.setTextColor(Color.WHITE);
        }
        if (height > 0) {
            search_liner.getBackground().setAlpha(scrollY * (END_ALPHA - START_ALPHA) / height + START_ALPHA);
            search.getBackground().setAlpha(scrollY * (END_ALPHA - START_ALPHA1) / height + START_ALPHA1);
            search_icon.getBackground().setAlpha(scrollY * (END_ALPHA - START_ALPHA1) / height + START_ALPHA1);
        }

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * 设置导航栏图片
     *
     * @param list
     */
    private void setHomeBanner(List<BannerInfo> list) {
        if (mActivity == null && mActivity.isFinishing()) {
            return;
        }
        if (cycleViewPager == null) {
            addCycleViewPager();
        }
        if (views.size() > 0) {
            views.clear();
        }
        if (list.size() > 1) {
            //当图片大于1的时候需要循环滑动
            //将最后一个ImageView添加进来
            views.add(ViewFactory.getImageView(mActivity, list.get(list.size() - 1).getImgUrl()));
            for (int i = 0; i < list.size(); i++) {
                views.add(ViewFactory.getImageView(mActivity, list.get(i).getImgUrl()));
            }
            // 将第一个ImageView添加进来
            views.add(ViewFactory.getImageView(mActivity, list.get(0).getImgUrl()));
        } else {
            //只有一张图片的时候禁止滑动
            for (int i = 0; i < list.size(); i++) {
                views.add(ViewFactory.getImageView(mActivity, list.get(i).getImgUrl()));
            }
        }

        //设置轮播
        cycleViewPager.setWheel(true);
        // 设置循环，在调用setData方法前调用
        cycleViewPager.setCycle(true);
        // 在加载数据前设置是否循环
        cycleViewPager.setData(views, list, mAdCycleViewListener);
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(BannerInfo info, int position, View imageView) {
            if (cycleViewPager != null && cycleViewPager.isCycle()) {
                if (!AbStringUtil.isEmpty(info.getLink())) {
                    WebViewActivity.startWebActivity(mActivity, info.getLink(), info.getTitle());
                }
            }
        }

    };

    @Override
    public void onBannerClick(List<BannerInfo> list) {
        setHomeBanner(list);
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        getBanner.getBanner();
        sendHttp();
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView view) {
        isRefresh = false;
        sendHttp();
    }
}

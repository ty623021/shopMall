package tyzl.company.activity.shopping;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import tyzl.company.R;
import tyzl.company.adapter.GoodsListAdapter;
import tyzl.company.adapter.ScreenTypeAdapter;
import tyzl.company.entity.GoodsInfo;
import tyzl.company.entity.HomeTypeInfo;
import tyzl.company.entity.ScreenTypeInfo;
import tyzl.company.global.Config;
import tyzl.company.global.Constant;
import tyzl.company.main.BaseActivity;
import tyzl.company.pullview.AbRefreshUtil;
import tyzl.company.utils.AbJsonUtil;
import tyzl.company.utils.AbLogUtil;
import tyzl.company.utils.AbStringUtil;
import tyzl.company.utils.AbToastUtil;
import tyzl.company.volley.RequestListener;
import tyzl.company.weight.LoadListView;
import tyzl.company.weight.TitleView;

/**
 * Created by hjy on 2016/11/5.
 * 商品列表
 */
public class GoodsListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, LoadListView.OnLoadingListener {

    private TitleView titleView;
    private GoodsListAdapter adapter;
    private List<GoodsInfo> list = new ArrayList<>();
    private LoadListView listView;
    private SwipeRefreshLayout swipe;
    private ImageView goods_list_price_img;
    private ImageView goods_list_most_img;
    private LinearLayout goods_list_all_linear;
    private LinearLayout goods_list_screen_linear;
    private LinearLayout goods_list_price_linear;
    private LinearLayout goods_list_most_linear;
    private TextView goods_list_all_tv;
    private TextView goods_list_screen_tv;
    private TextView goods_list_price_tv;
    private TextView goods_list_most_tv;
    private View view;
    private PopupWindow window;
    private ExpandableListView screen_type_lv;
    private ScreenTypeAdapter mAdapter;

    private ImageView network_img;
    private ImageView nodata_img;
    private ProgressBar progressBar;
    private HomeTypeInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_goods_list);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }

    /**
     * 跳转到 GoodsListActivity
     *
     * @param context
     */
    public static void startActivity(Context context, HomeTypeInfo info) {
        Intent intent = new Intent(context, GoodsListActivity.class);
        intent.putExtra("info", info);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setLeftImageButton(R.drawable.back);

        titleView.showLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        titleView.showSearchTvButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.startActivity(context);
            }
        });

    }

    @Override
    protected void initView() {
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        listView = (LoadListView) findViewById(R.id.listView);

        adapter = new GoodsListAdapter(this, R.layout.goods_item, list);
        listView.setAdapter(adapter);
        listView.setSwipe(swipe);
        listView.setOnLoadingListener(this);

        network_img = (ImageView) findViewById(R.id.network_img);
        nodata_img = (ImageView) findViewById(R.id.nodata_img);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        goods_list_all_linear = (LinearLayout) findViewById(R.id.goods_list_all_linear);
        goods_list_screen_linear = (LinearLayout) findViewById(R.id.goods_list_screen_linear);
        goods_list_price_linear = (LinearLayout) findViewById(R.id.goods_list_price_linear);
        goods_list_most_linear = (LinearLayout) findViewById(R.id.goods_list_most_linear);

        goods_list_price_img = (ImageView) findViewById(R.id.goods_list_price_img);
        goods_list_most_img = (ImageView) findViewById(R.id.goods_list_most_img);

        goods_list_all_tv = (TextView) findViewById(R.id.goods_list_all_tv);
        goods_list_screen_tv = (TextView) findViewById(R.id.goods_list_screen_tv);
        goods_list_price_tv = (TextView) findViewById(R.id.goods_list_price_tv);
        goods_list_most_tv = (TextView) findViewById(R.id.goods_list_most_tv);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GoodsInfo info = list.get(position);
                GoodsDetailsActivity.startActivity(context, info);
            }
        });

        info = (HomeTypeInfo) getIntent().getSerializableExtra("info");
        AbLogUtil.e("info", info.toString());
    }

    @Override
    protected void setData() {
        AbRefreshUtil.setSwipeRefresh(context, swipe, this);
        progressTitle = null;
        sendHttp();
        goods_list_all_tv.setSelected(true);
    }

    @Override
    protected void setEvent() {
        network_img.setOnClickListener(this);
        nodata_img.setOnClickListener(this);
        goods_list_all_linear.setOnClickListener(this);
        goods_list_screen_linear.setOnClickListener(this);
        goods_list_price_linear.setOnClickListener(this);
        goods_list_most_linear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        setSelectDefault();
        if (v.equals(nodata_img)) {
            clickHttp();
            nodata_img.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else if (v.equals(network_img)) {
            clickHttp();
            progressBar.setVisibility(View.VISIBLE);
            network_img.setVisibility(View.GONE);
        } else if (v.equals(goods_list_all_linear)) {
            clickHttp();
            progressTitle = Constant.LOADING;
            goods_list_all_tv.setSelected(true);
        } else if (v.equals(goods_list_most_linear)) {
            clickHttp();
            progressTitle = Constant.LOADING;
            setSelect(goods_list_most_img);
            goods_list_most_tv.setSelected(true);
        } else if (v.equals(goods_list_price_linear)) {
            clickHttp();
            progressTitle = Constant.LOADING;
            setSelect(goods_list_price_img);
            goods_list_price_tv.setSelected(true);
        } else if (v.equals(goods_list_screen_linear)) {
            showPopWindow();
        }
    }

    private void clickHttp() {
        isRefresh = true;
        nextPage = 1;
        sendHttp();
    }

    private void setSelect(ImageView imageView) {
        if (imageView.isSelected()) {
            imageView.setSelected(false);
        } else {
            imageView.setSelected(true);
        }
    }

    private void setSelectDefault() {
        goods_list_all_tv.setSelected(false);
        goods_list_screen_tv.setSelected(false);
        goods_list_price_tv.setSelected(false);
        goods_list_most_tv.setSelected(false);
    }

    private int totalPage, nextPage = 1, max = 10;
    private boolean isRefresh = true;//是否下拉刷新

    private void sendHttp() {
        params.put("cat_id", info.getId() + "");
        params.put("page", nextPage + "");
        AbLogUtil.e("nextPage", nextPage + "");
        http.post(Config.URL_GOODS_LIST, params, progressTitle, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e(TAG, json);
                listView.hideFooter();
                if (AbJsonUtil.isSuccess(json)) {
                    TypeToken typeToken = new TypeToken<List<GoodsInfo>>() {
                    };
                    nextPage = AbJsonUtil.getInteger(json, "nextPage");
                    totalPage = AbJsonUtil.getInteger(json, "totalPages");
                    List<GoodsInfo> goods = (List<GoodsInfo>) AbJsonUtil.fromJson(json, typeToken, "result");
                    if (AbStringUtil.isListEmpty(goods)) {
                        progressTitle = null;
                        if (isRefresh && list.size() > 0) {
                            list.clear();
                        }
                        list.addAll(goods);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                }
                AbRefreshUtil.hintView(adapter, false, network_img, nodata_img, progressBar);
            }

            @Override
            public void requestError(String message) {
                listView.hideFooter();
                AbRefreshUtil.hintView(adapter, true, network_img, nodata_img, progressBar);
                AbToastUtil.showToast(context, message);
            }
        });
    }

    @Override
    public void onRefresh() {
        clickHttp();
    }

    @Override
    public void loading() {
        isRefresh = false;
        if (AbRefreshUtil.isLoading(nextPage, totalPage, listView)) {
            sendHttp();
        } else {
            AbToastUtil.showToast(this, Constant.LOADED);
        }
    }


    private void showPopWindow() {
        // TODO Auto-generated method stub
        view = LayoutInflater.from(this).inflate(R.layout.screen, null);

        initPopView();
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        window = new PopupWindow(view,
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00808080);
        window.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.my_popshow_right_anim_style);
        //设置在右侧显示
        window.showAtLocation(swipe, Gravity.RIGHT, 0, 100);
        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        backgroundAlpha(0.5f);
    }

    private void initPopView() {
        screen_type_lv = (ExpandableListView) view.findViewById(R.id.screen_type_lv);
        List<ScreenTypeInfo> data = new ArrayList();

        List<String> str1 = new ArrayList();
        str1.add("蓝色");
        str1.add("黄色");
        str1.add("紫色");
        str1.add("黑色");
        List<String> str2 = new ArrayList();
        str2.add("第一");
        str2.add("第二");
        str2.add("第三");
        str2.add("第四");
        List<String> str3 = new ArrayList();
        str3.add("法国");
        str3.add("加拿大");
        str3.add("中国");
        str3.add("德国");
        List<String> str4 = new ArrayList();
        str4.add("80x100");
        str4.add("100x120");
        str4.add("120x140");
        str4.add("150x180");

        ScreenTypeInfo info1 = new ScreenTypeInfo("颜色", str1);
        ScreenTypeInfo info2 = new ScreenTypeInfo("类型", str2);
        ScreenTypeInfo info3 = new ScreenTypeInfo("国家", str3);
        ScreenTypeInfo info4 = new ScreenTypeInfo("尺寸", str4);
        data.add(info1);
        data.add(info2);
        data.add(info3);
        data.add(info4);
        mAdapter = new ScreenTypeAdapter(context, data);

        screen_type_lv.setAdapter(mAdapter);

        screen_type_lv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                mAdapter.update(groupPosition);
                return false;
            }
        });
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }


}

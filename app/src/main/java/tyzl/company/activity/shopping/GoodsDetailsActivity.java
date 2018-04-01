package tyzl.company.activity.shopping;

import android.app.FragmentTransaction;
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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import tyzl.company.R;
import tyzl.company.activity.WebViewActivity;
import tyzl.company.activity.address.CreateAddressActivity;
import tyzl.company.adapter.GoodsSpecTypeAdapter;
import tyzl.company.entity.AddressInfo;
import tyzl.company.entity.BannerInfo;
import tyzl.company.entity.GoodsDetailsInfo;
import tyzl.company.entity.GoodsInfo;
import tyzl.company.entity.OrderSumInfo;
import tyzl.company.entity.ShoppingCartInfo;
import tyzl.company.entity.SpecGoodsPriceInfo;
import tyzl.company.entity.SpecInfo;
import tyzl.company.entity.SpecTypeInfo;
import tyzl.company.global.Config;
import tyzl.company.main.BaseActivity;
import tyzl.company.pullview.AbRefreshUtil;
import tyzl.company.utils.AbImageUtil;
import tyzl.company.utils.AbJsonUtil;
import tyzl.company.utils.AbLogUtil;
import tyzl.company.utils.AbStringUtil;
import tyzl.company.utils.AbToastUtil;
import tyzl.company.viewPager.CycleViewPager;
import tyzl.company.viewPager.ViewFactory;
import tyzl.company.volley.IRequest;
import tyzl.company.volley.RequestListener;
import tyzl.company.volley.RequestParams;
import tyzl.company.weight.AlertDialog;
import tyzl.company.weight.TitleView;

/**
 * Created by geek on 2016/11/3.
 * 商品详情
 */
public class GoodsDetailsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, GoodsSpecTypeAdapter.OnSelectSpecListener {

    private TitleView titleView;
    private CycleViewPager cycleViewPager;
    private List<ImageView> views = new ArrayList<>();
    private GoodsInfo info;
    private Button confirm_bid;
    private Button add_shopping_cart;
    private ImageView remove;//减少
    private ImageView add;//增加
    private TextView goods_number;//购买数量
    private int number;//选择的商品数量
    private SwipeRefreshLayout swipe;
    private WebView webView;
    private TextView goods_name;
    private RelativeLayout relative_goods;
    private GoodsDetailsInfo goods;
    private ProgressBar progressBar;
    private ImageView nodata_img;
    private ImageView network_img;
    private TextView goods_price;
    private View view;
    private View main;
    private RelativeLayout select_spec_relative;
    private ImageView space_close_img;
    private PopupWindow window;
    private OrderSumInfo selectedOrderSumInfo;
    private ListView type_lv;
    private GoodsSpecTypeAdapter adapter;
    private List<SpecTypeInfo> spec_data = new ArrayList<>();
    private TextView tv_goods_price;
    private TextView tv_goods_number;
    private TextView tv_goods_spec;
    private SpecGoodsPriceInfo checkPriceInfo;
    private ImageView goods_spec_icon;
    private TextView tv_goods_spec_default;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_goods_detail);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }

    /**
     * 跳转到  CreateAddressActivity
     *
     * @param context
     */
    public static void startActivity(Context context, GoodsInfo info) {
        Intent intent = new Intent(context, GoodsDetailsActivity.class);
        intent.putExtra("info", info);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        Intent intent = getIntent();
        info = (GoodsInfo) intent.getSerializableExtra("info");
        if (info != null) {
            titleView.setTitle(info.getGoods_name());
        } else {
            titleView.setTitle("商品详情");
        }
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initView() {
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        addCycleViewPager();
        main = findViewById(R.id.main);
        confirm_bid = (Button) findViewById(R.id.confirm_bid);
        add_shopping_cart = (Button) findViewById(R.id.add_shopping_cart);
        goods_name = (TextView) findViewById(R.id.goods_name);
        goods_price = (TextView) findViewById(R.id.goods_price);
        remove = (ImageView) findViewById(R.id.remove);
        add = (ImageView) findViewById(R.id.add);
        goods_number = (TextView) findViewById(R.id.goods_number);
        tv_goods_spec_default = (TextView) findViewById(R.id.tv_goods_spec_default);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        network_img = (ImageView) findViewById(R.id.network_img);
        nodata_img = (ImageView) findViewById(R.id.nodata_img);

        relative_goods = (RelativeLayout) findViewById(R.id.relative_goods);
        webView = (WebView) findViewById(R.id.webView);

        select_spec_relative = (RelativeLayout) findViewById(R.id.select_spec_relative);
        setWebView();
    }

    @Override
    protected void setData() {
        progressBar.setVisibility(View.GONE);
        AbRefreshUtil.setSwipeRefresh(context, swipe, this);
        sendHttp();
    }

    @Override
    protected void setEvent() {
        confirm_bid.setOnClickListener(this);
        add_shopping_cart.setOnClickListener(this);
        remove.setOnClickListener(this);
        add.setOnClickListener(this);
        network_img.setOnClickListener(this);
        nodata_img.setOnClickListener(this);
        select_spec_relative.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(nodata_img)) {
            sendHttp();
            nodata_img.setVisibility(View.GONE);
        } else if (v.equals(network_img)) {
            sendHttp();
            network_img.setVisibility(View.GONE);
        }
        if (v.equals(confirm_bid)) {
            goShopping();
        } else if (v.equals(add_shopping_cart)) {
            addCart();
        } else if (v.equals(select_spec_relative)) {
            showPopWindow();
        } else if (v.equals(space_close_img)) {
            window.dismiss();
        } else if (v.equals(remove)) {
            getNumber();
            if (number > 1) {
                goods_number.setText((number - 1) + "");
                if (number == 2) {
                    remove.setSelected(false);
                } else {
                    remove.setSelected(true);
                }
                add.setSelected(true);
            } else {
                remove.setSelected(false);
            }
        } else if (v.equals(add)) {
            getNumber();
            if (number < goods.getStore_count()) {
                goods_number.setText((number + 1) + "");
                if (number + 1 == goods.getStore_count()) {
                    add.setSelected(false);
                } else {
                    add.setSelected(true);
                }
                remove.setSelected(true);
            } else {
                AbToastUtil.showToast(context, "库存不足");
                add.setSelected(false);
            }
        }

    }

    /**
     * 点击购买
     */
    private void goShopping() {
        List<ShoppingCartInfo> selectedList = new ArrayList<>();
        ShoppingCartInfo info = new ShoppingCartInfo();
        info.setGoods_num(3);
        info.setImgUrl(goods.getOriginal_img());
        info.setGoods_name(goods.getGoods_name());
        selectedList.add(info);
        FirmOrderActivity.startActivity(this, selectedList);
    }

    private void addShoppingCar() {
        final AlertDialog dialog = new AlertDialog(this);
        dialog.setBtCancel("继续购物");
        dialog.setBtConfirm("去结算");
        dialog.showDialog("添加购物车成功", "共" + number + "件", new AlertDialog.DialogOnClickListener() {
            @Override
            public void onPositiveClick() {
                dialog.dismiss();
            }

            @Override
            public void onNegativeClick() {
                dialog.dismiss();
            }
        });
    }

    private void getNumber() {
        String tv_number = goods_number.getText().toString();
        if (!AbStringUtil.isEmpty(tv_number)) {
            number = Integer.parseInt(tv_number);
        }
    }

    private void addCycleViewPager() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        cycleViewPager = new CycleViewPager();
        transaction.add(R.id.viewPager_content, cycleViewPager);
        transaction.commitAllowingStateLoss();
        cycleViewPager.setSwipeRefreshLayout(swipe);
    }

    public void onBannerClick(List<BannerInfo> list) {
        if (isFinishing()) {
            return;
        }
        if (cycleViewPager == null) {
            addCycleViewPager();
        }
        if (views.size() != 0) {
            views.clear();
        }
        if (list.size() > 1) {
            //当图片大于1的时候需要循环滑动
            //将最后一个ImageView添加进来
            views.add(ViewFactory.getImageView(this, list.get(list.size() - 1).getImgUrl()));
            for (int i = 0; i < list.size(); i++) {
                views.add(ViewFactory.getImageView(this, list.get(i).getImgUrl()));
            }
            // 将第一个ImageView添加进来
            views.add(ViewFactory.getImageView(this, list.get(0).getImgUrl()));
        } else {
            //只有一张图片的时候禁止滑动
            for (int i = 0; i < list.size(); i++) {
                views.add(ViewFactory.getImageView(this, list.get(i).getImgUrl()));
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
                    WebViewActivity.startWebActivity(GoodsDetailsActivity.this, info.getLink(), info.getTitle());
                }
            }
        }

    };


    private void setWebView() {
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.getSettings().setTextZoom(230);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
    }

    @Override
    public void onRefresh() {
        sendHttp();
    }

    /**
     * 添加地址
     */
    private void addAddress() {
        final AlertDialog dialog = new AlertDialog(this);
        dialog.setBtConfirm("去添加");
        dialog.showDialog("您还没有填写收货地址哦！" + "\n" + "现在去添加吧", new AlertDialog.DialogOnClickListener() {
            @Override
            public void onPositiveClick() {
                dialog.dismiss();
                CreateAddressActivity.startActivity(context);
            }

            @Override
            public void onNegativeClick() {
                dialog.dismiss();
            }
        });
    }

    /**
     * 获取商品详情
     */
    private void sendHttp() {
        params.put("goods_id", info.getGoods_id());
        http.post(Config.URL_GOODS_INFO, params, progressTitle, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e("json", json);
                swipe.setRefreshing(false);
                if (AbJsonUtil.isSuccess(json)) {
                    goods = (GoodsDetailsInfo) AbJsonUtil.fromJsonMall(json, "goods", GoodsDetailsInfo.class);
                    TypeToken typeToken1 = new TypeToken<List<BannerInfo>>() {
                    };
                    List<BannerInfo> gallery = (List<BannerInfo>) AbJsonUtil.fromJsonMall(json, typeToken1, "gallery");
                    if (goods != null) {
                        progressTitle = null;
                        setValue(goods);
                    } else {
                        AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                    }
                    if (AbStringUtil.isListEmpty(gallery)) {
                        onBannerClick(gallery);
                    }
                    relative_goods.setVisibility(View.VISIBLE);
                } else {
                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                }
                AbRefreshUtil.hintView(goods, false, network_img, nodata_img, progressBar);
            }

            @Override
            public void requestError(String message) {
                swipe.setRefreshing(false);
                AbToastUtil.showToast(context, message);
                AbRefreshUtil.hintView(goods, true, network_img, nodata_img, progressBar);
            }
        });

        getSpaceTypeInfo();
    }

    /**
     * 设置产品参数
     *
     * @param goods
     */
    private void setValue(GoodsDetailsInfo goods) {
        webView.loadData(goods.getGoods_content(), "text/html; charset=UTF-8", null);
        goods_name.setText(goods.getGoods_name());
        goods_price.setText("￥" + goods.getShop_price());
        if (goods.getStore_count() < 1) {
            add_shopping_cart.setEnabled(false);
            confirm_bid.setEnabled(false);
            add_shopping_cart.setText("已抢完");
            confirm_bid.setText("已抢完");
            add.setSelected(false);
        } else {
            add.setSelected(true);
            add_shopping_cart.setEnabled(true);
            confirm_bid.setEnabled(true);
            add_shopping_cart.setText("加入购物车");
            confirm_bid.setText("立即购买");
        }
    }

    /**
     * 显示规格参数弹窗界面
     */
    private void showPopWindow() {
        // TODO Auto-generated method stub
        view = LayoutInflater.from(this).inflate(R.layout.select_spec_layout, null);

        initPopView();
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00808080);
        window.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.my_popshow_anim_style);
        //设置在底部显示
        window.showAtLocation(main, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        backgroundAlpha(0.5f);
    }

    /**
     * 初始化规格参数弹窗界面
     */
    private void initPopView() {
        type_lv = (ListView) view.findViewById(R.id.type_lv);
        space_close_img = (ImageView) view.findViewById(R.id.space_close_img);
        goods_spec_icon = (ImageView) view.findViewById(R.id.goods_spec_icon);
        tv_goods_price = (TextView) view.findViewById(R.id.tv_goods_price);
        tv_goods_number = (TextView) view.findViewById(R.id.tv_goods_number);
        tv_goods_spec = (TextView) view.findViewById(R.id.tv_goods_spec);

        space_close_img.setOnClickListener(this);
        adapter = new GoodsSpecTypeAdapter(context, R.layout.goods_spec_type_item, spec_data);
        type_lv.setAdapter(adapter);
        adapter.setSelectSpecListener(this);

        setCheckSpecPriceInfo(checkPriceInfo);
    }

    /**
     * 获取规格参数
     */
    private void getSpaceTypeInfo() {
        params.put("goods_id", info.getGoods_id());
        http.post(Config.URL_GOOD_SPEC, params, new RequestListener() {
                    @Override
                    public void requestSuccess(String json) {
                        AbLogUtil.e(TAG, json);
                        if (AbJsonUtil.isSuccess(json)) {
                            TypeToken typeToken = new TypeToken<List<SpecTypeInfo>>() {
                            };
                            List<SpecTypeInfo> typeInfos = (List<SpecTypeInfo>) AbJsonUtil.fromJsonMall(json, typeToken, "goods");
                            if (AbStringUtil.isListEmpty(typeInfos)) {
                                if (spec_data.size() > 0) {
                                    spec_data.clear();
                                }
                                spec_data.addAll(typeInfos);
                                select_spec_relative.setVisibility(View.VISIBLE);
                                setDefault();
                            } else {
                                select_spec_relative.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void requestError(String message) {
                    }
                }

        );
    }

    /**
     * 设置默认选择的规格参数
     */
    private void setDefault() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < spec_data.size(); i++) {
            SpecTypeInfo typeInfo = spec_data.get(i);
            SpecInfo specInfo = typeInfo.getData().get(0);
            AbLogUtil.e("info" + i, specInfo.toString());
            String item = specInfo.getItem();
            String item_id = specInfo.getItem_id();
            if (!AbStringUtil.isEmpty(item)) {
                buffer.append(item_id).append("_");
            }
        }
        key = buffer.substring(0, buffer.length() - 1);
        getGoodsSpec(key);
    }

    /**
     * 获取商品规格默认图片和
     *
     * @param key
     */
    private void getGoodsSpec(String key) {
        RequestParams params = new RequestParams();
        params.put("key", key);
        http.post(Config.URL_GET_GOODS_SPCINFO, params, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e("json", json);
                if (AbJsonUtil.isSuccess(json)) {
                    SpecGoodsPriceInfo goodsPriceInfo = (SpecGoodsPriceInfo) AbJsonUtil.fromJson(json, SpecGoodsPriceInfo.class);
                    if (goodsPriceInfo != null) {
                        checkPriceInfo = goodsPriceInfo;
                        tv_goods_spec_default.setText(checkPriceInfo.getKey_name());
                    }
                }
            }

            @Override
            public void requestError(String message) {
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


    /**
     * 创建订单
     */
    private void createOrder() {
        progressTitle = "创建订单...";
        getNumber();
        RequestParams params = new RequestParams();
//        params.put("user_id", MyApplication.mApp.getID());
        params.put("goods_id", goods.getGoods_id());
        params.put("goods_num", number + "");
        params.put("real_price", "0");
        IRequest.post(context, Config.CREATE_ORDER_URL2, params, progressTitle, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e(TAG, json);
                if (AbJsonUtil.isSuccess(json)) {
                    TypeToken typeToken = new TypeToken<List<ShoppingCartInfo>>() {
                    };
                    List<ShoppingCartInfo> selectedList = (List<ShoppingCartInfo>) AbJsonUtil.fromJsonMall(json, typeToken, "cartList");
                    selectedOrderSumInfo = (OrderSumInfo) AbJsonUtil.fromJsonMall(json, "totalPrice", OrderSumInfo.class);
                    AddressInfo selectAddressInfo = (AddressInfo) AbJsonUtil.fromJsonMall(json, "addressList", AddressInfo.class);
                    if (selectAddressInfo == null) {
                        addAddress();
                    } else {
                        if (AbStringUtil.isListEmpty(selectedList)) {
                            FirmOrderActivity.startActivity(GoodsDetailsActivity.this, selectedList, selectedOrderSumInfo, selectAddressInfo);
                        }
                    }
                } else {
                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                }
            }

            @Override
            public void requestError(String message) {
                AbToastUtil.showToast(context, message);
            }
        });

    }


    /**
     * 选择商品参数规格回调
     *
     * @param specGoodsPriceInfo
     */
    @Override
    public void onSelectSpecListener(SpecGoodsPriceInfo specGoodsPriceInfo) {
        this.checkPriceInfo = specGoodsPriceInfo;
        tv_goods_spec_default.setText(specGoodsPriceInfo.getKey_name());
        setCheckSpecPriceInfo(checkPriceInfo);
    }

    /**
     * 设置商品规格具体的数据
     *
     * @param specGoodsPriceInfo
     */
    private void setCheckSpecPriceInfo(SpecGoodsPriceInfo specGoodsPriceInfo) {
        if (specGoodsPriceInfo != null) {
            tv_goods_price.setText("￥" + specGoodsPriceInfo.getPrice());
            tv_goods_number.setText("库存：" + specGoodsPriceInfo.getStore_count() + "件");
            tv_goods_spec.setText(specGoodsPriceInfo.getKey_name());
            if (!AbStringUtil.isEmpty(specGoodsPriceInfo.getImgUrl())) {
                AbImageUtil.glideImageList(specGoodsPriceInfo.getImgUrl(), goods_spec_icon, R.drawable.loading_img, R.drawable.load_fail);
            }
        }
    }

    /**
     * 添加到购物车
     */
    private void addCart() {
        getNumber();
        RequestParams params = new RequestParams();
        params.put("goods_id", goods.getGoods_id());
        params.put("goods_num", number + "");
        if (checkPriceInfo != null) {
            params.put("key", checkPriceInfo.getKey());
        }
        http.post(Config.URL_ADD_CART, params, "添加中...", new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e(TAG + "==>addCart", json);
                if (AbJsonUtil.isSuccess(json)) {
                    addShoppingCar();
                } else {
                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                }
            }

            @Override
            public void requestError(String message) {
                AbToastUtil.showToast(context, message);
            }
        });

    }
}

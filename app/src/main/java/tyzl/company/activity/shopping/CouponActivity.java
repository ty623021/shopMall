package tyzl.company.activity.shopping;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import tyzl.company.R;
import tyzl.company.adapter.CouponAdapter;
import tyzl.company.entity.CouponInfo;
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
 * 优惠券
 */
public class CouponActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, LoadListView.OnLoadingListener {

    private TitleView titleView;
    private CouponAdapter adapter;
    private List<CouponInfo> list = new ArrayList<>();
    private LoadListView listView;
    private SwipeRefreshLayout swipe;
    private ImageView network_img;
    private ProgressBar progressBar;
    private ImageView nodata_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_myorder);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }

    /**
     * 跳转到 FirmOrderActivity
     *
     * @param context
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CouponActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setTitle("选择优惠券");
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initView() {
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        listView = (LoadListView) findViewById(R.id.listView);
        adapter = new CouponAdapter(this, R.layout.coupon_item_layout, list);
        listView.setAdapter(adapter);
        listView.setSwipe(swipe);
        listView.setOnLoadingListener(this);

        network_img = (ImageView) findViewById(R.id.network_img);
        nodata_img = (ImageView) findViewById(R.id.nodata_img);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    @Override
    protected void setData() {
        AbRefreshUtil.setSwipeRefresh(context, swipe, this);
        sendHttp();
    }

    @Override
    protected void setEvent() {
        network_img.setOnClickListener(this);
        nodata_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(nodata_img)) {
            sendHttp();
            progressBar.setVisibility(View.VISIBLE);
            nodata_img.setVisibility(View.GONE);
        } else if (v.equals(network_img)) {
            sendHttp();
            progressBar.setVisibility(View.VISIBLE);
            network_img.setVisibility(View.GONE);
        }
    }

    private int totalPage, nextPage = 1, max = 10;
    private boolean isRefresh = true;//是否下拉刷新

    private void sendHttp() {
        params.put("page", nextPage + "");
        http.post(Config.URL_GETINVESTSUBJECTON, params, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e(TAG, json);
                listView.hideFooter();
                if (AbJsonUtil.isSuccess(json)) {
                    nextPage = AbJsonUtil.getInteger(json, "nextPage");
                    totalPage = AbJsonUtil.getInteger(json, "totalPages");
                    TypeToken typeToken = new TypeToken<List<CouponInfo>>() {
                    };
//                  List<CouponInfo> data = (List<CouponInfo>) AbJsonUtil.fromJson(json, typeToken, "data");

                    List<CouponInfo> data = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        CouponInfo info = new CouponInfo("" + i, "酒店套房优惠券" + i, 10 * (i + 1), "2017/" + i + "/12", false);
                        data.add(info);
                    }
                    if (AbStringUtil.isListEmpty(data)) {
                        if (isRefresh && list.size() > 0) {
                            list.clear();
                        }
                        list.addAll(data);
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
                AbToastUtil.showToast(context, message);
                AbRefreshUtil.hintView(adapter, true, network_img, nodata_img, progressBar);
            }
        });

    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        nextPage = 1;
        sendHttp();
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
}

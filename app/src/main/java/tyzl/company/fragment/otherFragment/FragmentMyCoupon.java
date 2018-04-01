package tyzl.company.fragment.otherFragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import tyzl.company.R;
import tyzl.company.adapter.CenterMyCouponAdapter;
import tyzl.company.entity.CouponInfo;
import tyzl.company.global.Config;
import tyzl.company.global.Constant;
import tyzl.company.main.BaseFragment;
import tyzl.company.pullview.AbRefreshUtil;
import tyzl.company.utils.AbJsonUtil;
import tyzl.company.utils.AbLogUtil;
import tyzl.company.utils.AbStringUtil;
import tyzl.company.utils.AbToastUtil;
import tyzl.company.volley.RequestListener;
import tyzl.company.weight.LoadListView;

/**
 * 我的优惠券的fragment的页面
 * Created by hjy on 2017/3/22.
 */

public class FragmentMyCoupon extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, LoadListView.OnLoadingListener {
    private View view;
    private FragmentMyCoupon mActivity;
    private SwipeRefreshLayout swipe;
    private LoadListView listView;
    private CenterMyCouponAdapter adapter;
    private List<CouponInfo> list = new ArrayList<>();
    ;
    private ImageView network_img;
    private ProgressBar progressBar;
    private ImageView nodata_img;
    private String type;
    private int countNum;
    private int totalPage, nextPage = 1, max = 10;
    private boolean isRefresh = true;//是否下拉刷新


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_coupon, null);
        mActivity = this;
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getString("type");
        }
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void initView() {
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        listView = (LoadListView) view.findViewById(R.id.listView);

        adapter = new CenterMyCouponAdapter(context, R.layout.center_my_coupon_item, list);
        listView.setAdapter(adapter);
        listView.setSwipe(swipe);
        listView.setOnLoadingListener(this);


        network_img = (ImageView) view.findViewById(R.id.network_img);
        nodata_img = (ImageView) view.findViewById(R.id.nodata_img);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void setEvent() {
        network_img.setOnClickListener(this);
        nodata_img.setOnClickListener(this);

    }

    @Override
    protected void setData() {
        AbRefreshUtil.setSwipeRefresh(context, swipe, this);
        progressTitle = null;
        sendHttp();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(network_img)) {
            sendHttp();
            progressBar.setVisibility(View.VISIBLE);
            network_img.setVisibility(View.GONE);
        } else if (v.equals(nodata_img)) {
            sendHttp();
            progressBar.setVisibility(View.VISIBLE);
            nodata_img.setVisibility(View.GONE);
        }
    }

    /**
     * 发送请求
     */
    private void sendHttp() {
        params.put("type", type);
        params.put("page", nextPage + "");
        http.post(Config.URL_GETCOUPON, params, progressTitle, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e("GETCOUPON", json);
                if (AbJsonUtil.isSuccess(json)) {
                    TypeToken typeToken = new TypeToken<List<CouponInfo>>() {
                    };
                    nextPage = AbJsonUtil.getInteger(json, "nextPage");
                    totalPage = AbJsonUtil.getInteger(json, "totalPages");
                    countNum = AbJsonUtil.getInteger(json, "countNum");

                    AbLogUtil.e("countNum-------", countNum + "");
                    AbLogUtil.e("Page", nextPage + "===" + totalPage + "-----");
                    List<CouponInfo> data = (List<CouponInfo>) AbJsonUtil.fromJson(json, typeToken, "result");
                    if (AbStringUtil.isListEmpty(data)) {
                        if (isRefresh && list.size() > 0) {
                            list.clear();
                        }
                        progressTitle = null;
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
        swipe.setRefreshing(false);
    }

    @Override
    public void loading() {
        isRefresh = false;
        AbLogUtil.e("loading", nextPage + "-------" + totalPage);
        if (AbRefreshUtil.isLoading(nextPage, totalPage, listView)) {
            sendHttp();
            AbLogUtil.e("loadingsendHttp", nextPage + "-------" + totalPage);
        } else {
            AbToastUtil.showToast(context, Constant.LOADED);
        }
    }


    /**
     * 回调接口，用来传记数值给目标activity,经过中介（父类activity）再向目标值传递
     *
     * @author asus
     */
    public interface callbackValue {
        public void sendDataOfRecord(int kinds, String type);
    }


}

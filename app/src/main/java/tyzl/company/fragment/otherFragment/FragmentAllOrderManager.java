package tyzl.company.fragment.otherFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import tyzl.company.R;
import tyzl.company.adapter.OrderManagerAdapter;
import tyzl.company.entity.OrderManagerInfo;
import tyzl.company.global.Config;
import tyzl.company.global.Constant;
import tyzl.company.main.BaseFragment;
import tyzl.company.pullview.AbPullToRefreshView;
import tyzl.company.pullview.AbRefreshUtil;
import tyzl.company.utils.AbJsonUtil;
import tyzl.company.utils.AbLogUtil;
import tyzl.company.utils.AbStringUtil;
import tyzl.company.utils.AbToastUtil;
import tyzl.company.volley.RequestListener;


/**
 *
 * Created by hjy on 2017/3/10.
 */

public class FragmentAllOrderManager extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, AbPullToRefreshView.OnFooterLoadListener {
    private View view;
    private FragmentActivity mActivity;
    private List<OrderManagerInfo> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private OrderManagerAdapter adapter;
    private String type;
    private SwipeRefreshLayout swipe;
    private ImageView network_img;
    private ImageView nodata_img;
    private ProgressBar progressBar;
    private AbPullToRefreshView pull;
    private int nextPage = 1, totalPages, max = 10;
    private boolean isRefresh = true;
    private boolean isFirstHttp = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_manager, null);
        mActivity = getActivity();
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getString("type");
        }
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);

        network_img = (ImageView) view.findViewById(R.id.network_img);
        nodata_img = (ImageView) view.findViewById(R.id.nodata_img);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        pull = (AbPullToRefreshView) view.findViewById(R.id.pull);

        adapter = new OrderManagerAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        AbRefreshUtil.initRefresh(pull, swipe, this, this);

    }


    private void setHttp() {
        params.put("order_type", type);
        params.put("nextPage", nextPage + "");
        http.post(Config.URL_ORDERMANAGER, params, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e("json_orderManager", json);
                if (AbJsonUtil.isSuccess(json)) {
                    TypeToken typeToken = new TypeToken<List<OrderManagerInfo>>() {
                    };
                    nextPage = AbJsonUtil.getInteger(json, "nextPage");
                    totalPages = AbJsonUtil.getInteger(json, "totalPages");
                    List<OrderManagerInfo> dataList = (List<OrderManagerInfo>) AbJsonUtil.fromJson(json, typeToken, "result");
                    if (AbStringUtil.isListEmpty(dataList)) {
                        if (isRefresh && list.size() > 0) {
                            list.clear();
                        }
                        list.addAll(dataList);
                        adapter.notifyDataSetChanged();
                    }

                } else {
                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                }
                AbRefreshUtil.onLoadComplete(pull, swipe, nextPage, totalPages);
                AbRefreshUtil.hintView(adapter, false, network_img, nodata_img, progressBar);
            }

            @Override
            public void requestError(String message) {
                AbRefreshUtil.onLoadComplete(pull, swipe, nextPage, totalPages);
                AbToastUtil.showToast(context, message);
                AbRefreshUtil.hintView(adapter, true, network_img, nodata_img, progressBar);
            }
        });
    }


    @Override
    protected void setData() {
        if ("0".equals(type)) {
            setHttp();
        }
    }

    @Override
    protected void setEvent() {
        nodata_img.setOnClickListener(this);
        network_img.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.equals(network_img)) {
            setHttp();
            network_img.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else if (v.equals(nodata_img)) {
            setHttp();
            nodata_img.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        nextPage = 1;
        setHttp();
    }


    @Override
    public void onFooterLoad(AbPullToRefreshView view) {
        isRefresh = false;
        if (AbRefreshUtil.isPullLoading(nextPage, totalPages, pull)) {
            setHttp();
        } else {
            AbToastUtil.showToast(context, Constant.LOADED);
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirstHttp) {
            if (type != null) {
                isFirstHttp = false;
                setHttp();
            }
        }
    }
}

package tyzl.company.fragment.otherFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import tyzl.company.R;
import tyzl.company.adapter.ShopTypeRecycleAdapter;
import tyzl.company.entity.HomeTypeInfo;
import tyzl.company.global.Config;
import tyzl.company.main.BaseFragment;
import tyzl.company.pullview.AbRefreshUtil;
import tyzl.company.utils.AbJsonUtil;
import tyzl.company.utils.AbStringUtil;
import tyzl.company.utils.AbToastUtil;
import tyzl.company.volley.RequestListener;

/**
 * 商品二级分类
 */
public class FragmentGoodsType extends BaseFragment {
    private ArrayList<HomeTypeInfo> list = new ArrayList<>();
    private RecyclerView listView;
    private ShopTypeRecycleAdapter adapter;
    private View view;
    private FragmentActivity mActivity;
    private ProgressBar progressBar;
    private ImageView network_img;
    private ImageView nodata_img;
    private TextView top_type;
    private HomeTypeInfo homeTypeInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_goods_type, null);
        mActivity = getActivity();
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        network_img = (ImageView) view.findViewById(R.id.network_img);
        nodata_img = (ImageView) view.findViewById(R.id.nodata_img);
        listView = (RecyclerView) view.findViewById(R.id.listView);
        Bundle bundle = getArguments();
        homeTypeInfo = (HomeTypeInfo) bundle.getSerializable("homeTypeInfo");

        top_type = (TextView) view.findViewById(R.id.top_type);
        top_type.setText(homeTypeInfo.getName());
        GridLayoutManager layoutManager = new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false);
        listView.setLayoutManager(layoutManager);
        adapter = new ShopTypeRecycleAdapter(getActivity(), list);
        listView.setAdapter(adapter);
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

    private void sendHttp() {
        params.put("parent_id", homeTypeInfo.getId() + "");
        http.post(Config.URL_GOODS_TOW_LIST, params, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                if (AbJsonUtil.isSuccess(json)) {
                    TypeToken typeToken = new TypeToken<List<HomeTypeInfo>>() {
                    };
                    List<HomeTypeInfo> result = (List<HomeTypeInfo>) AbJsonUtil.fromJson(json, typeToken, "result");
                    if (AbStringUtil.isListEmpty(result)) {
                        if (list.size() > 0) {
                            list.clear();
                        }
                        list.addAll(result);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                }
                AbRefreshUtil.hintView(adapter, false, network_img, nodata_img, progressBar);
            }

            @Override
            public void requestError(String message) {
                AbRefreshUtil.hintView(adapter, true, network_img, nodata_img, progressBar);
                AbToastUtil.showToast(context, message);
            }
        });
    }

}

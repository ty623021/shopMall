package tyzl.company.activity.address;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import tyzl.company.R;
import tyzl.company.adapter.SelectAddressAdapter;
import tyzl.company.entity.AddressInfo;
import tyzl.company.global.Config;
import tyzl.company.main.BaseActivity;
import tyzl.company.pullview.AbRefreshUtil;
import tyzl.company.utils.AbJsonUtil;
import tyzl.company.utils.AbLogUtil;
import tyzl.company.utils.AbStringUtil;
import tyzl.company.utils.AbToastUtil;
import tyzl.company.volley.RequestListener;
import tyzl.company.weight.TitleView;

/**
 * Created by hjy on 2016/11/5.
 * 选择收货地址
 */
public class SelectAddressActivity extends BaseActivity implements SelectAddressAdapter.OnSelectAddressClick, SwipeRefreshLayout.OnRefreshListener {

    private static int requestCode = 1;
    private TitleView titleView;
    private SelectAddressAdapter adapter;
    private List<AddressInfo> list = new ArrayList<>();
    private ListView listView;
    private Button add_address;
    private String id;//选中的ID
    private SwipeRefreshLayout swipe;
    private ImageView network_img;
    private ProgressBar progressBar;
    private ImageView nodata_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_select_address);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }

    /**
     * 跳转到 FirmOrderActivity
     *
     * @param context
     */
    public static void startActivity(Activity context, String position) {
        Intent intent = new Intent(context, SelectAddressActivity.class);
        intent.putExtra("ID", position);
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 跳转到 FirmOrderActivity
     *
     * @param context
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SelectAddressActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setTitle("选择收货地址");
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        titleView.setRightTextButton("管理", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManageAddressActivity.startActivity(context);
            }
        });
        titleView.showRightTextButton();

        id = getIntent().getStringExtra("ID");

    }

    @Override
    protected void initView() {
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        network_img = (ImageView) findViewById(R.id.network_img);
        nodata_img = (ImageView) findViewById(R.id.nodata_img);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        listView = (ListView) findViewById(R.id.listView);
        add_address = (Button) findViewById(R.id.add_address);
        adapter = new SelectAddressAdapter(this, R.layout.select_address_item, list);
        listView.setAdapter(adapter);
        adapter.setSelectAddressClick(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CreateAddressActivity.startActivity(context);
            }
        });
    }

    @Override
    protected void setData() {
        AbRefreshUtil.setSwipeRefresh(context, swipe, this);
    }

    private void sendHttp() {
        http.post(Config.URL_GET_ADDRESS_LIST, params, progressTitle, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e(TAG, json);
                swipe.setRefreshing(false);
                if (AbJsonUtil.isSuccess(json)) {
                    TypeToken typeToken = new TypeToken<List<AddressInfo>>() {
                    };
                    List<AddressInfo> addressInfos = (List<AddressInfo>) AbJsonUtil.fromJson(json, typeToken, "result");
                    if (list.size() > 0) {
                        list.clear();
                    }
                    if (AbStringUtil.isListEmpty(addressInfos)) {
                        progressTitle = null;
                        list.addAll(addressInfos);
                        for (AddressInfo info : list) {
                            if (info.getAddress_id().equals(id)) {
                                info.setIsSelect(true);
                                break;
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    AbToastUtil.showToast(context, AbJsonUtil.getError(json));
                }
                AbRefreshUtil.hintView(adapter, false, network_img, nodata_img, progressBar);
            }

            @Override
            public void requestError(String message) {
                swipe.setRefreshing(false);
                AbToastUtil.showToast(context, message);
                AbRefreshUtil.hintView(adapter, true, network_img, nodata_img, progressBar);
            }

        });
    }

    @Override
    protected void setEvent() {
        add_address.setOnClickListener(this);
        nodata_img.setOnClickListener(this);
        network_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(add_address)) {
            CreateAddressActivity.startActivity(context);
        } else if (v.equals(nodata_img)) {
            sendHttp();
            progressBar.setVisibility(View.VISIBLE);
            nodata_img.setVisibility(View.GONE);
        } else if (v.equals(network_img)) {
            progressBar.setVisibility(View.VISIBLE);
            network_img.setVisibility(View.GONE);
            sendHttp();
        }
    }

    @Override
    public void selectAddressClick(AddressInfo info) {
        Intent data = new Intent();
        data.putExtra("info", info);
        setResult(requestCode, data);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        swipe.setRefreshing(true);
        sendHttp();
    }

    @Override
    public void onRefresh() {
        sendHttp();
    }
}

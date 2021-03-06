package tyzl.company.activity.address;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import tyzl.company.R;
import tyzl.company.adapter.CityAdapter;
import tyzl.company.entity.CityInfo;
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
 * 选择地区
 */
public class AreaActivity extends BaseActivity {
    private TitleView title;
    private ListView lv_list;
    private List<CityInfo> list = new ArrayList<>();
    private CityAdapter mAdapter;
    private static Activity mContext;
    private String source;
    private CityInfo cityInfo;
    private ImageView network_img;
    private ImageView nodata_img;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_list);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(title, true, Color.parseColor(Config.colorPrimary));

    }

    @Override
    public void onClick(View v) {
        if (v.equals(network_img)) {
            network_img.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            setData();
        } else if (v.equals(nodata_img)) {
            nodata_img.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            setData();
        }
    }


    /**
     * 跳转到 SelectCityActivity
     *
     * @param context
     */
    public static void startActivity(Activity context, CityInfo cityInfo, String source) {
        mContext = context;
        Intent intent = context.getIntent();
        intent.setClass(context, AreaActivity.class);
        intent.putExtra("source", source);
        intent.putExtra("cityInfo", cityInfo);
        context.startActivity(intent);
    }


    @Override
    protected void initTitle() {
        title = (TitleView) findViewById(R.id.title);
        title.setTitle("选择地区");
        title.setLeftImageButton(R.drawable.back);
        title.showLeftButton(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });
        source = getIntent().getStringExtra("source");
        cityInfo = (CityInfo) getIntent().getSerializableExtra("cityInfo");
    }

    @Override
    protected void initView() {
        network_img = (ImageView) findViewById(R.id.network_img);
        nodata_img = (ImageView) findViewById(R.id.nodata_img);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        lv_list = (ListView) findViewById(R.id.listView);
        mAdapter = new CityAdapter(this, R.layout.select_province_item, list);
        lv_list.setAdapter(mAdapter);

        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setAreaValue(position);
            }
        });
    }

    private void setAreaValue(int position) {
        CityInfo areaInfo = list.get(position);
        Intent intent = getIntent();
        if (source.equals("NewAddress")) {
            intent.setClass(this, CreateAddressActivity.class);
        }
        intent.putExtra("areaInfo", areaInfo);
        startActivity(intent);
        mContext.finish();
        finish();
    }


    @Override
    protected void setData() {
        params.put("id", cityInfo.getId());
        http.post(context, Config.URL_GET_AREA, params, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e(TAG, json.toString());
                if (AbJsonUtil.isSuccess(json)) {
                    TypeToken typeToken = new TypeToken<List<CityInfo>>() {
                    };
                    List<CityInfo> cityInfos = (List<CityInfo>) AbJsonUtil.fromJson(json, typeToken, "result");
                    if (AbStringUtil.isListEmpty(cityInfos)) {
                        if (list.size() > 0) {
                            list.clear();
                        }
                        list.addAll(cityInfos);
                        mAdapter.notifyDataSetChanged();
                    }
                } else {
                    AbToastUtil.showToast(AreaActivity.this, AbJsonUtil.getError(json));
                }
                AbRefreshUtil.hintView(mAdapter, false, network_img, nodata_img, progressBar);
            }

            @Override
            public void requestError(String message) {
                AbRefreshUtil.hintView(mAdapter, true, network_img, nodata_img, progressBar);
                AbToastUtil.showToast(AreaActivity.this, message);
            }
        });
    }

    @Override
    protected void setEvent() {
        network_img.setOnClickListener(this);
        nodata_img.setOnClickListener(this);
    }


}

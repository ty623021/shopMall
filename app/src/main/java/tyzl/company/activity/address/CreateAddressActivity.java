package tyzl.company.activity.address;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import tyzl.company.R;
import tyzl.company.entity.AddressInfo;
import tyzl.company.entity.CityInfo;
import tyzl.company.global.Config;
import tyzl.company.main.BaseActivity;
import tyzl.company.utils.AbJsonUtil;
import tyzl.company.utils.AbLogUtil;
import tyzl.company.utils.AbStringUtil;
import tyzl.company.utils.AbToastUtil;
import tyzl.company.utils.AbViewUtil;
import tyzl.company.utils.AddSpaceTextWatcher;
import tyzl.company.volley.RequestListener;
import tyzl.company.weight.TitleView;


/**
 * Created by geek on 2016/11/3.
 * 新建收货地址
 */
public class CreateAddressActivity extends BaseActivity {

    private TitleView titleView;

    private EditText et_detailed_address;
    private RelativeLayout relative_region;
    private TextView tv_region;
    private EditText et_consignee_phone;
    private EditText et_consignee_name;
    private AddSpaceTextWatcher asEditTexts;
    private CityInfo provinceInfo;
    private CityInfo cityInfo;
    private CityInfo areaInfo;
    private String detailed_address;
    private String consignee_phone;
    private String consignee_name;
    private AddressInfo info;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_new_address);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));

    }

    /**
     * 跳转到 CreateAddressActivity
     *
     * @param context
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CreateAddressActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转到 CreateAddressActivity
     *
     * @param context
     */
    public static void startActivity(Context context, AddressInfo info) {
        Intent intent = new Intent(context, CreateAddressActivity.class);
        intent.putExtra("info", info);
        context.startActivity(intent);
    }


    @Override
    protected void initTitle() {
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setTitle("新建收货地址");
        titleView.setLeftImageButton(R.drawable.back);
        titleView.showLeftButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        titleView.setRightTextButton("保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAddress();
            }
        });
        titleView.showRightTextButton();
        info = (AddressInfo) getIntent().getSerializableExtra("info");

    }

    private void saveAddress() {
        if (!AbStringUtil.isEmpty(consignee_name)) {
            if (consignee_name.length() >= 2) {
                if (!AbStringUtil.isEmpty(consignee_phone)) {
                    if (AbStringUtil.isMobileNo(consignee_phone)) {
                        if (provinceInfo != null || info != null) {
                            if (!AbStringUtil.isEmpty(detailed_address)) {
                                sendHttp();
                            } else {
                                AbToastUtil.showToast(context, "详细地址不能为空");
                            }
                        } else {
                            AbToastUtil.showToast(context, "请选择区域");
                        }
                    } else {
                        AbToastUtil.showToast(context, "收货人手机号格式错误");
                    }
                } else {
                    AbToastUtil.showToast(context, "收货人手机号不能为空");
                }
            } else {
                AbToastUtil.showToast(context, "收货人姓名2-10位");
            }
        } else {
            AbToastUtil.showToast(context, "收货人姓名不能为空");
        }
    }

    private void sendHttp() {
        params.put("consignee", consignee_name);
        params.put("mobile", consignee_phone);
        if (info != null) {
            params.put("address_id", info.getAddress_id());
            params.put("areaStr", info.getProvince_id() + "-" + info.getCity_id() + "-" + info.getDistrict_id());
            url = Config.URL_EDIT_ADDRESS;
        } else {
            params.put("areaStr", provinceInfo.getId() + "-" + cityInfo.getId() + "-" + areaInfo.getId());
            url = Config.URL_ADD_ADDRESS;
        }
        params.put("address", detailed_address);
//        if (cb_default_address.isChecked()) {
//            params.put("is_default", "1");
//        } else {
//            params.put("is_default", "0");
//        }
        http.post(url, params, "正在保存...", new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e(TAG, json.toString());
                if (AbJsonUtil.isSuccess(json)) {
                    finish();
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

    
    @Override
    protected void initView() {
//        cb_default_address = (CheckBox) findViewById(R.id.cb_default_address);
        et_detailed_address = (EditText) findViewById(R.id.et_detailed_address);
        et_consignee_phone = (EditText) findViewById(R.id.et_consignee_phone);
        et_consignee_name = (EditText) findViewById(R.id.et_consignee_name);
        relative_region = (RelativeLayout) findViewById(R.id.relative_region);
        tv_region = (TextView) findViewById(R.id.tv_region);

    }

    @Override
    protected void setData() {
        relative_region.setOnClickListener(this);
        if (info != null) {
            titleView.setTitle("编辑收货地址");
//            if (info.getIs_default().equals("1")) {
//                cb_default_address.setChecked(true);
//            } else {
//                cb_default_address.setChecked(false);
//            }
            et_detailed_address.setText(info.getAddress());
            et_consignee_phone.setText(info.getMobile());
            et_consignee_name.setText(info.getConsignee());
            tv_region.setText(AbStringUtil.getAddress(info.getProvince(), info.getCity(), info.getDistrict()));
            detailed_address = info.getAddress();
            consignee_phone = info.getMobile();
            consignee_name = info.getConsignee();
            AbViewUtil.setEditTextSelection(et_consignee_name);
        }
    }

    @Override
    protected void setEvent() {
        asEditTexts = new AddSpaceTextWatcher(et_consignee_phone, 13);
        asEditTexts.setSpaceType(AddSpaceTextWatcher.SpaceType.mobilePhoneNumberType);
        MyTextWatcher watcher = new MyTextWatcher();
        et_detailed_address.addTextChangedListener(watcher);
        et_consignee_phone.addTextChangedListener(watcher);
        et_consignee_name.addTextChangedListener(watcher);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(relative_region)) {
            ProvinceActivity.startActivity(context, "NewAddress");
        }
    }

    class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            detailed_address = et_detailed_address.getText().toString();
            consignee_phone = asEditTexts.getTextNotSpace();
            consignee_name = et_consignee_name.getText().toString();
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            provinceInfo = (CityInfo) intent.getSerializableExtra("provinceInfo");
            cityInfo = (CityInfo) intent.getSerializableExtra("cityInfo");
            areaInfo = (CityInfo) intent.getSerializableExtra("areaInfo");
            if (provinceInfo != null) {
                tv_region.setText(provinceInfo.getName() + " " + cityInfo.getName() + " " + areaInfo.getName());
            }
        }
    }
}

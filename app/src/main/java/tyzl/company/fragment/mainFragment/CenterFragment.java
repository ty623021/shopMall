
package tyzl.company.fragment.mainFragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import tyzl.company.R;
import tyzl.company.activity.WebViewActivity;
import tyzl.company.activity.address.ManageAddressActivity;
import tyzl.company.activity.center.AccountSettingActivity;
import tyzl.company.activity.center.MyCouponActivity;
import tyzl.company.activity.center.OrderMangerActivity;
import tyzl.company.activity.center.UserInformationActivity;
import tyzl.company.activity.center.UserLoginActivity;
import tyzl.company.activity.center.UserRegisterActivity;
import tyzl.company.entity.UserInfomationInfo;
import tyzl.company.glide.GlideImgManager;
import tyzl.company.global.Config;
import tyzl.company.main.BaseFragment;
import tyzl.company.main.MyApplication;
import tyzl.company.pullview.AbRefreshUtil;
import tyzl.company.utils.AbJsonUtil;
import tyzl.company.utils.AbLogUtil;
import tyzl.company.utils.AbStringUtil;
import tyzl.company.utils.AbToastUtil;
import tyzl.company.volley.RequestListener;

/**
 * 个人中心
 */
public class CenterFragment extends BaseFragment implements
        GlideImgManager.OnGlideSuccessListener, SwipeRefreshLayout.OnRefreshListener {
    private View view;
    private RelativeLayout relative_user_information;
    private LinearLayout order_manager;
    private LinearLayout waitting_pay_liner;
    private LinearLayout waiting_delivery_liner;
    private LinearLayout account_paid_liner;
    private RelativeLayout address_manager;
    private RelativeLayout my_bank_manager;
    private RelativeLayout my_coupon;
    private RelativeLayout account_setting;
    private RelativeLayout about_we;
    private Button register;//注册
    private Button login;//登录
    private UserInfomationInfo info;
    private ImageView head_img;
    private TextView phone;
    private TextView user_name;
    private GlideImgManager glideImgManager;
    private SwipeRefreshLayout swipe;
    private boolean isRefresh;//是否下拉刷新
    private LinearLayout sended_linear, wait_pay_linear, wait_send_linear;
    private TextView wait_pay_amount, wait_send_amount, sended_amount;

    private LinearLayout is_login_linear, no_login_linear;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_person, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void initTitle() {
    }

    @Override
    protected void setData() {
        AbRefreshUtil.setSwipeRefresh(context, swipe, this);
        if (MyApplication.mApp.hasLogin()) {
            getUserInformationHttp();
        }

    }

    @Override
    protected void setEvent() {
        relative_user_information.setOnClickListener(this);
        order_manager.setOnClickListener(this);
        waitting_pay_liner.setOnClickListener(this);
        waiting_delivery_liner.setOnClickListener(this);
        account_paid_liner.setOnClickListener(this);
        address_manager.setOnClickListener(this);
        my_bank_manager.setOnClickListener(this);
        my_coupon.setOnClickListener(this);
        account_setting.setOnClickListener(this);
        about_we.setOnClickListener(this);
        register.setOnClickListener(this);
        login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.equals(relative_user_information)) {
            if (MyApplication.mApp.hasLogin()) {
                UserInformationActivity.startActivity(context);//个人资料
            } else {
                UserLoginActivity.startActivity(context);
            }
        } else if (v.equals(order_manager)) {
            if (MyApplication.mApp.hasLogin()) {
                OrderMangerActivity.startActivity(context, 0);//我的订单
            } else {
                UserLoginActivity.startActivity(context);
            }

        } else if (v.equals(waitting_pay_liner)) {
            if (MyApplication.mApp.hasLogin()) {
                OrderMangerActivity.startActivity(context, 1);//待付款
            } else {
                UserLoginActivity.startActivity(context);
            }

        } else if (v.equals(waiting_delivery_liner)) {
            if (MyApplication.mApp.hasLogin()) {
                OrderMangerActivity.startActivity(context, 2);//待发货
            } else {
                UserLoginActivity.startActivity(context);
            }

        } else if (v.equals(account_paid_liner)) {
            if (MyApplication.mApp.hasLogin()) {
                OrderMangerActivity.startActivity(context, 3);//已付款
            } else {
                UserLoginActivity.startActivity(context);
            }

        } else if (v.equals(address_manager)) {
            if (MyApplication.mApp.hasLogin()) {
                ManageAddressActivity.startActivity(context);//地址管理
            } else {
                UserLoginActivity.startActivity(context);
            }

        } else if (v.equals(my_bank_manager)) {

        } else if (v.equals(my_coupon)) {
            if (MyApplication.mApp.hasLogin()) {
                MyCouponActivity.startActivity(context);
            } else {
                UserLoginActivity.startActivity(context);
            }


        } else if (v.equals(account_setting)) {
            if (MyApplication.mApp.hasLogin()) {
                AccountSettingActivity.startActivity(context, info.getMobile());//账户管理
            } else {
                UserLoginActivity.startActivity(context);
            }
        } else if (v.equals(about_we)) {
            WebViewActivity.startWebActivity(getActivity(), Config.URL_ABOUTUS, "关于我们");
        } else if (v.equals(register)) {
            UserRegisterActivity.startActivity(context);//登录
        } else if (v.equals(login)) {
            UserLoginActivity.startActivity(context);//注册
        }
    }


    @Override
    protected void initView() {
        relative_user_information = (RelativeLayout) view.findViewById(R.id.relative_user_information);
        order_manager = (LinearLayout) view.findViewById(R.id.order_manager);
        waitting_pay_liner = (LinearLayout) view.findViewById(R.id.waitting_pay_liner);
        waiting_delivery_liner = (LinearLayout) view.findViewById(R.id.waiting_delivery_liner);
        account_paid_liner = (LinearLayout) view.findViewById(R.id.account_paid_liner);
        address_manager = (RelativeLayout) view.findViewById(R.id.address_manager);
        my_bank_manager = (RelativeLayout) view.findViewById(R.id.my_bank_manager);
        my_coupon = (RelativeLayout) view.findViewById(R.id.my_coupon);
        account_setting = (RelativeLayout) view.findViewById(R.id.account_setting);
        about_we = (RelativeLayout) view.findViewById(R.id.about_we);

        register = (Button) view.findViewById(R.id.register);
        login = (Button) view.findViewById(R.id.login);


        head_img = (ImageView) view.findViewById(R.id.img);
        phone = (TextView) view.findViewById(R.id.phone);
        user_name = (TextView) view.findViewById(R.id.user_name);


        is_login_linear = (LinearLayout) view.findViewById(R.id.is_login);
        no_login_linear = (LinearLayout) view.findViewById(R.id.no_login);


        sended_linear = (LinearLayout) view.findViewById(R.id.sended_linear);
        wait_pay_linear = (LinearLayout) view.findViewById(R.id.wait_pay_linear);
        wait_send_linear = (LinearLayout) view.findViewById(R.id.wait_send_linear);
        wait_pay_amount = (TextView) view.findViewById(R.id.wait_pay_amount);
        wait_send_amount = (TextView) view.findViewById(R.id.wait_send_amount);
        sended_amount = (TextView) view.findViewById(R.id.sended_amount);


        glideImgManager = new GlideImgManager();
        glideImgManager.setOnGlideSuccessListener(this);

        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);

        isLoginView();

    }

    /**
     * 个人中心在已登录时的显示
     * 个人中心未登录时的显示
     */

    private void isLoginView() {
        if (MyApplication.mApp.hasLogin()) {
            //已登录
            is_login_linear.setVisibility(View.VISIBLE);
            no_login_linear.setVisibility(View.INVISIBLE);
        } else {
            //未登录
            is_login_linear.setVisibility(View.INVISIBLE);
            no_login_linear.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 获取个人信息
     */
    private void getUserInformationHttp() {
        http.post(Config.URL_GETUSERINFO, params, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e("json_center", json);
                if (AbJsonUtil.isSuccess(json)) {
                    info = (UserInfomationInfo) AbJsonUtil.fromJson(json, UserInfomationInfo.class);
                    if (info != null) {
                        setValue();
                    } else {
                        head_img.setImageResource(R.drawable.center_user_img);

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
     * 个人资料的信息的相应设置
     */
    private void setValue() {


        glideImgManager.glideLoader(info.gethead_pic(), R.drawable.center_user_img, R.drawable.center_user_img, head_img, 0);
        if (!AbStringUtil.isEmpty(info.getMobile())){
            phone.setText(info.getMobile());
        }
        if (!AbStringUtil.isEmpty(info.getNickname())) {
            user_name.setText(info.getNickname());
        } else {
            user_name.setText("呢称");
        }
        if (info.getDaifu() != 0) {
            wait_pay_linear.setVisibility(View.VISIBLE);
            wait_pay_amount.setText(info.getDaifu() + "");
        } else {
            wait_pay_linear.setVisibility(View.INVISIBLE);
        }

        if (info.getDaifa() != 0) {
            wait_send_linear.setVisibility(View.VISIBLE);
            wait_send_amount.setText(info.getDaifa() + "");
        } else {
            wait_send_linear.setVisibility(View.INVISIBLE);
        }

        if (info.getYifu() != 0) {
            sended_linear.setVisibility(View.VISIBLE);
            sended_amount.setText(info.getYifu() + "");
        } else {
            sended_linear.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        isLoginView();
        if (MyApplication.mApp.hasLogin()) {
            getUserInformationHttp();
        } else {
            head_img.setImageResource(R.drawable.center_user_img);
        }

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onGlideSuccessListener() {

    }

    //下拉刷新重写的方法
    @Override
    public void onRefresh() {

        if (MyApplication.mApp.hasLogin()) {
            isRefresh = true;
            getUserInformationHttp();

        }
        swipe.setRefreshing(false);
    }
}

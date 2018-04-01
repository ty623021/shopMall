package tyzl.company.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tyzl.company.R;
import tyzl.company.fragment.mainFragment.CenterFragment;
import tyzl.company.fragment.mainFragment.HomeFragment;
import tyzl.company.fragment.mainFragment.ShoppingFragment;
import tyzl.company.fragment.mainFragment.TypeFragment;
import tyzl.company.global.Config;
import tyzl.company.main.BaseActivity;
import tyzl.company.main.MyApplication;
import tyzl.company.utils.AbToastUtil;
import tyzl.company.weight.TitleView;
import yicheng.android.ui.materialdesignlibrary.views.LayoutRipple;

public class MainActivity extends BaseActivity {
    private ImageView ivHome, ivType, ivPersonalCenter, ivShopping;
    private TextView tvHome, tvType, tvPersonalCenter, tvShopping;
    private HomeFragment homeFragment;
    private TypeFragment typeFragment;
    private ShoppingFragment shopFragment;
    private CenterFragment centerFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private LayoutRipple tab_home, tab_type, tab_shopping, tab_personal_center;


    private TitleView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, false, Color.parseColor(Config.colorPrimary));
    }


//    @Override
//    protected void initTitle() {
//        MPermissions.requestPermissions(this, WRITE_EXTERNAL_STORAGE_REQUEST_CODE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//    }
//
//    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;
//
//    @PermissionGrant(WRITE_EXTERNAL_STORAGE_REQUEST_CODE)
//    public void requestContactSuccess() {
//
//    }
//
//    @PermissionDenied(WRITE_EXTERNAL_STORAGE_REQUEST_CODE)
//    public void requestContactFailed() {
//        AbToastUtil.showToast(this, "您禁止了读取存储权限，有些图片将不能被获取");
//        AppUtil.getAppDetailSettingIntent();
//    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void initView() {

        titleView = (TitleView) findViewById(R.id.title);
        ivHome = (ImageView) findViewById(R.id.tab_home_icon);
        tvHome = (TextView) findViewById(R.id.tab_home_text);

        ivType = (ImageView) findViewById(R.id.tab_type_icon);
        tvType = (TextView) findViewById(R.id.tab_type_text);

        ivShopping = (ImageView) findViewById(R.id.tab_shopping_icon);
        tvShopping = (TextView) findViewById(R.id.tab_shopping_text);

        ivPersonalCenter = (ImageView) findViewById(R.id.tab_personal_center_icon);
        tvPersonalCenter = (TextView) findViewById(R.id.tab_personal_center_text);


        tab_home = (LayoutRipple) findViewById(R.id.tab_home);
        tab_type = (LayoutRipple) findViewById(R.id.tab_type);
        tab_shopping = (LayoutRipple) findViewById(R.id.tab_shopping);
        tab_personal_center = (LayoutRipple) findViewById(R.id.tab_personal_center);

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void setData() {
        fragmentManager = getSupportFragmentManager();
        switchTab(0);

    }

    private void initTab() {
        ivHome.setSelected(false);
        ivType.setSelected(false);
        ivShopping.setSelected(false);
        ivPersonalCenter.setSelected(false);

        tvType.setSelected(false);
        tvShopping.setSelected(false);
        tvPersonalCenter.setSelected(false);
        tvHome.setSelected(false);

    }

    @Override
    protected void setEvent() {
        tab_home.setOnClickListener(this);
        tab_personal_center.setOnClickListener(this);
        tab_shopping.setOnClickListener(this);
        tab_type.setOnClickListener(this);
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (typeFragment != null) {
            transaction.hide(typeFragment);
        }
        if (shopFragment != null) {
            transaction.hide(shopFragment);
        }
        if (centerFragment != null) {
            transaction.hide(centerFragment);
        }

    }

    public void switchTab(int index) {
        initTab();
        transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (index) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.container, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                ivHome.setSelected(true);
                tvHome.setSelected(true);
                break;
            case 1:
                if (typeFragment == null) {
                    typeFragment = new TypeFragment();
                    transaction.add(R.id.container, typeFragment);
                } else {
                    transaction.show(typeFragment);
                }
                ivType.setSelected(true);
                tvType.setSelected(true);
                break;
            case 2:
                if (shopFragment == null) {
                    shopFragment = new ShoppingFragment();
                    transaction.add(R.id.container, shopFragment);
                } else {
                    // 如果indexFragment不为空，则直接将它显示出来
                    transaction.show(shopFragment);
                }
                ivShopping.setSelected(true);
                tvShopping.setSelected(true);
                break;
            case 3:
                if (centerFragment == null) {
                    centerFragment = new CenterFragment();
                    transaction.add(R.id.container, centerFragment);
                } else {
                    // 如果indexFragment不为空，则直接将它显示出来
                    transaction.show(centerFragment);
                }
                ivPersonalCenter.setSelected(true);
                tvPersonalCenter.setSelected(true);
                break;
        }
        transaction.commitAllowingStateLoss();
    }


    @Override
    public void onClick(View v) {
        if (v.equals(tab_home)) {
            switchTab(0);
        } else if (v.equals(tab_type)) {
            switchTab(1);
        } else if (v.equals(tab_shopping)) {
            switchTab(2);
        } else if (v.equals(tab_personal_center)) {
            switchTab(3);
        }

    }

    // 再次点击返回键退出
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                AbToastUtil.showToast(MainActivity.this, "再次点击返回键退出");
                exitTime = System.currentTimeMillis();
            } else {
                MyApplication.exit();
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 跳转到 MainActivity
     *
     * @param context
     * @param currPager 选中的页面
     */
    public static void startMainActivity(Activity context, int currPager) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("currPager", currPager);
        context.startActivity(intent);
        context.finish();
    }

    /**
     * 跳转到 MainActivity
     *
     * @param context
     * @param currPager 选中的页面
     */
    public static void startMainActivity(Activity context, int currPager, String status) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("currPager", currPager);
        intent.putExtra("status", status);
        context.startActivity(intent);
        context.finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            int currPager = intent.getIntExtra("currPager", 0);
            switchTab(currPager);
            String status = intent.getStringExtra("status");
            if (status != null) {
                if (status.equals("register")) {

                }
            }
        }
    }


}

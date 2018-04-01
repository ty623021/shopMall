package tyzl.company.main;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import tyzl.company.global.ActivityManager;
import tyzl.company.global.Constant;
import tyzl.company.volley.IRequest;
import tyzl.company.volley.RequestParams;


public abstract class BaseActivity extends BaseTranslucentActivity implements OnClickListener {
    protected String TAG = this.getClass().getSimpleName();
    protected Context context;
    protected IRequest http;
    protected RequestParams params;
    protected String progressTitle = Constant.LOADING;//进度条文字 如果为null则不显示加载进度loading

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //锁定屏幕
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context = this;
        http = new IRequest(this);
        params = new RequestParams();
        ActivityManager.getInstance().addActivity(this);
        initView();
        initTitle();
        setEvent();
        setData();
    }

    protected abstract void initView();

    protected abstract void initTitle();

    protected abstract void setEvent();

    protected abstract void setData();


    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart(TAG); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
//        MobclickAgent.onResume(this);  //统计时长
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
//        MobclickAgent.onPageEnd(TAG);
    }

    /**
     * 描述：Activity结束.
     *
     * @see android.app.Activity#finish()
     */
    @Override
    public void finish() {
        ActivityManager.getInstance().removeActivity(this);
        super.finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (TAG.equals("MyBorrowMoneyActivity")) {

        } else {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
                View v = getCurrentFocus();
                if (!TAG.equals("PayOrderActivity") && !TAG.equals("PhonePayOrderActivity")) {
                    if (isShouldHideInput(v, ev)) {
                        hideSoftInput(v.getWindowToken());
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}

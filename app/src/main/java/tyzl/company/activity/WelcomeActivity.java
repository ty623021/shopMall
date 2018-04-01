package tyzl.company.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import tyzl.company.R;
import tyzl.company.main.BaseActivity;
import tyzl.company.main.MyApplication;
import tyzl.company.utils.AbLogUtil;


/****
 * 欢迎界面
 */
public class WelcomeActivity extends BaseActivity {

    private static final int CHECK_CONFIG = 1001;
    private static final int GOTO_NEXT = 1002;

    private static final long SPLASH_DELAY_MILLIS = 1000;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CHECK_CONFIG:
                    // checkConfig();
                    break;
                case GOTO_NEXT:
                    gotoNext();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_welcome);
        super.onCreate(savedInstanceState);
        //开启友盟统计
        startMob();
    }

    /**
     * 友盟统计
     */
    private void startMob() {
        AbLogUtil.openAll();
    }

    private void gotoNext() {
        if (MyApplication.mApp.isFirstIn()) {
            //第一次登录的时候 和版本更新的时候
            MyApplication.mApp.setFirstIn();
            GuideActivity.startActivity(this);
            finish();
        } else {
            goGuide();
        }
    }

    private void goGuide() {
        if (MyApplication.mApp.hasLogin()) {
            MainActivity.startMainActivity(this, 0);
        } else {
            MainActivity.startMainActivity(this, 0);
        }
        this.overridePendingTransition(R.anim.in_from_right, R.anim.scale_out_from_left);
    }


    @Override
    protected void initTitle() {
        // TODO Auto-generated method stub

    }


    @Override
    protected void initView() {
        mHandler.sendEmptyMessageDelayed(GOTO_NEXT, SPLASH_DELAY_MILLIS);
        icon = (ImageView) findViewById(R.id.splash_image_icon);
    }


    @Override
    protected void setData() {
        // TODO Auto-generated method stub

    }


    @Override
    protected void setEvent() {

    }


    @Override
    public void onClick(View v) {

    }
}


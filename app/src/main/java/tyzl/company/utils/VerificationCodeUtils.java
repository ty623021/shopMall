package tyzl.company.utils;

import android.app.Activity;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import tyzl.company.activity.service.SMSContentObserver;
import tyzl.company.global.Config;
import tyzl.company.volley.IRequest;
import tyzl.company.volley.RequestListener;
import tyzl.company.volley.RequestParams;
import tyzl.company.weight.CountdownProgressBar;
import tyzl.company.weight.EditTextWithDel;


/**
 * Created by geek on 2016/7/17.
 * 获取验证码
 */
public class VerificationCodeUtils {

    private final Activity activity;
    private Button get_verification_code;//获取验证码按钮
    private CountdownProgressBar countdownProgressBar;//倒计时进度条
    private RelativeLayout relativeVoice;//语音验证码的布局
    private OnCheckVerify onCheckVerify;//验证验证码的回调接口
    private SMSContentObserver sms;
    private EditTextWithDel et_verification_code;

    public VerificationCodeUtils(Activity activity, Button verification_code, CountdownProgressBar countdown, EditTextWithDel et_verification_code) {
        this.get_verification_code = verification_code;
        this.countdownProgressBar = countdown;
        this.activity = activity;
        this.et_verification_code = et_verification_code;
        countdownProgressBar.setCountdownListener(new CountdownProgressBar.CountdownListener() {
            @Override
            public void onTheEnd() {
                get_verification_code.setVisibility(View.VISIBLE);
                get_verification_code.setText("重新获取");
                countdownProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onOther() {
//                relativeVoice.setVisibility(View.VISIBLE);
            }
        });
//        registerContentObservers();
    }


    //     * @param isVoice   是否获取语音验证码

    /**
     * 获取验证码
     * 找回登陆密码验证码发送
     *
     * @param userPhone 用户手机号
     */
    public void getPassWordCode(IRequest http, String userPhone, int type) {
        if (AbStringUtil.isEmpty(userPhone)) {
            AbToastUtil.showToast(activity, "请输入手机号");
            return;
        }
        if (!AbStringUtil.isMobileNo(userPhone)) {
            AbToastUtil.showToast(activity, "手机号码格式错误");
            return;
        }
        RequestParams params = new RequestParams();
        params.put("mobile", userPhone);
        params.put("type", type + "");
//        params.put("voice", isVoice + "");
        String title = "短信发送中...";
//        if (isVoice) {
//            title = "语音发送中...";
//        }
        http.post(Config.URL_REGISTER_MESSAGE_CODE, params, title, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                boolean success = AbJsonUtil.isSuccess(json);
                if (success) {
                    AbToastUtil.showToast(activity, "短信验证码发送成功");
                    get_verification_code.setVisibility(View.GONE);
                    countdownProgressBar.setVisibility(View.VISIBLE);
                    countdownProgressBar.setAnimProgress(60);
                    //是否是新用户
//                    checkIsVoice(isVoice);
                } else {
                    AbToastUtil.showToast(activity, AbJsonUtil.getError(json));
                }
            }

            @Override
            public void requestError(String message) {
                AbToastUtil.showToast(activity, message);
            }
        });
    }

    //**
//
//// if (isVoice) {
//    //语音验证码
//    AbToastUtil.showToast(activity, "请接听021-31590966来电获得验证码");
//}

    /**
     * 发送短信验证码
     */
    private void checkCode() {
        AbToastUtil.showToast(activity, "短信验证码发送成功");
        get_verification_code.setVisibility(View.GONE);
        countdownProgressBar.setVisibility(View.VISIBLE);
        countdownProgressBar.setAnimProgress(60);

    }


//    * @param isVoice   是否获取语音验证码
//    * @param operate   中文信息，用于显示在短信上 例如：您正在${operate}，本次的动态验证码为XXXX

    /**
     * 获取验证码接口
     *
     * @param userPhone 用户手机号
     */
    public void getVerCode(IRequest http, String userPhone, int type) {
        if (AbStringUtil.isEmpty(userPhone)) {
            AbToastUtil.showToast(activity, "请输入手机号");
            return;
        }
        if (!AbStringUtil.isMobileNo(userPhone)) {
            AbToastUtil.showToast(activity, "手机号码格式错误");
            return;
        }
        RequestParams params = new RequestParams();
        params.put("mobile", userPhone);
        params.put("type", type + "");
//        params.put("operate", operate);
//        params.put("voice", isVoice + "");
        String title = "短信发送中...";
//        if (isVoice) {
//            title = "语音发送中...";
//        }
        http.post(Config.URL_REGISTER_MESSAGE_CODE, params, title, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                boolean success = AbJsonUtil.isSuccess(json);
                if (success) {
//                    checkIsVoice(isVoice);
                    AbToastUtil.showToast(activity, "短信验证码发送成功");
                } else {
                    AbToastUtil.showToast(activity, AbJsonUtil.getError(json));
                }
            }

            @Override
            public void requestError(String message) {
                AbToastUtil.showToast(activity, message);
            }
        });
    }


    //   * @param isVoice   是否获取语音验证码

    /**
     * 获取验证码
     * 获取短信验证码
     *
     * @param userPhone 用户手机号
     */
    public void getVerificationCode(IRequest http, String userPhone, int type) {
        if (AbStringUtil.isEmpty(userPhone)) {
            AbToastUtil.showToast(activity, "请输入手机号");
            return;
        }
        if (!AbStringUtil.isMobileNo(userPhone)) {
            AbToastUtil.showToast(activity, "手机号码格式错误");
            return;
        }
        RequestParams params = new RequestParams();
        params.put("mobile", userPhone);


        params.put("type", type + "");
//        params.put("voice", isVoice + "");
        String title = "短信发送中...";
//        if (isVoice) {
//            title = "语音发送中...";
//        }

        http.post(Config.URL_REGISTER_MESSAGE_CODE, params, title, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e("URL_REGISTER_MESSAGE_CODE", json.toString());
                boolean success = AbJsonUtil.isSuccess(json);
                if (success) {
                    checkCode();
                } else {
                    AbToastUtil.showToast(activity, AbJsonUtil.getError(json));
                }
            }

            @Override
            public void requestError(String message) {
                AbToastUtil.showToast(activity, message);
                AbLogUtil.e("requestError", message);
            }
        });
    }


    /**
     * 验证验证码
     *
     * @param http
     * @param mobileM          手机号
     * @param verificationCode 验证码
     * @param complete         确认按钮
     */
    public void checkVerifyOn(IRequest http, String mobileM, int type, String verificationCode, final Button complete) {
        if (!AbStringUtil.isMobileNo(mobileM)) {
            AbToastUtil.showToast(activity, "手机号码格式错误");
            return;
        }
        if (AbStringUtil.isEmpty(verificationCode)) {
            AbToastUtil.showToast(activity, "请输入验证码");
            return;
        }
        RequestParams params = new RequestParams();
        params.put("mobile", mobileM);
        params.put("type", type + "");

        params.put("vcode", verificationCode);
        complete.setEnabled(false);
        http.post(Config.URL_CHECKVCODE, params, "校验中...", new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e("校验中", json.toString());
                complete.setEnabled(true);
                if (AbJsonUtil.isSuccess(json)) {
                    if (onCheckVerify != null) {
                        onCheckVerify.onCheckVerify(true);
                    }
                } else {
                    AbToastUtil.showToast(activity, AbJsonUtil.getError(json));
                }
            }

            @Override
            public void requestError(String message) {
                complete.setEnabled(true);
                AbToastUtil.showToast(activity, message);
            }
        });
    }

    /**
     * 回调接口
     */
    public interface OnCheckVerify {
        void onCheckVerify(boolean is);
    }

    public void setOnCheckVerify(OnCheckVerify onCheckVerify) {
        this.onCheckVerify = onCheckVerify;
    }

    /**
     * 注册短信监听
     */
    private void registerContentObservers() {
        // ”表“内容观察者 ，通过测试我发现只能监听此Uri -----> content://sms
        // 监听不到其他的Uri 比如说 content://sms/outbox
        sms = new SMSContentObserver(activity, mHandler);
        // 注册短信变化监听
        activity.getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, sms);
    }

    private static final int MSG_OUTBOXCONTENT = 2;
    // 线程管理时间
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_OUTBOXCONTENT:
                    String outbox = sms.getstrContent();
                    et_verification_code.setText(outbox);
                    break;
                default:
                    break;
            }
        }

    };
}

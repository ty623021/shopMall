package tyzl.company.utils;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import tyzl.company.volley.IRequest;
import tyzl.company.volley.RequestParams;


/**
 * Created by geek on 2016/9/10.
 */
public class JavaScriptObj {

    private Context context;
    private IRequest http;
    private RequestParams params;
    private String content;

    public JavaScriptObj(Context context) {
        this.context = context;
        http = new IRequest(context);
        params = new RequestParams();
    }

    @JavascriptInterface
    public void toAndroidActivity(String view, String value) {
        if (!AbStringUtil.isEmpty(view)) {
            if (view.equals("register")) {
                toRegisterActivity();
            } else if (view.equals("login")) {
                toLoginActivity();
            } else if (view.equals("investmentDetail")) {

            } else if (view.equals("inviteFriend")) {
                inviteFriend();
            }
        }
    }

    /**
     * 实现根据H5传递的参数跳转到对应的APP界面
     *
     * @param view 要跳转的界面 必须是activity的全类名
     *             com.rongteng.Activity.main.MainActivity
     */
    @JavascriptInterface
    public void toActivity(String view) {
        try {
            Class<?> aClass = Class.forName(view);
            Intent intent = new Intent();
            intent.setClass(context, aClass);
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 跳转到注册页面
     */
    @JavascriptInterface
    public void toRegisterActivity() {
        if (context != null) {
        }
    }

    /**
     * 跳转到登录页面
     */
    @JavascriptInterface
    public void toLoginActivity() {
        if (context != null) {
        }
    }

    /**
     * 获取分享的内容
     *
     * @param content
     * @return
     */
    @JavascriptInterface
    public void setShareContent(String content) {
        AbLogUtil.e("content", content + "");
        this.content = content;
        if (onShareClick != null) {
            onShareClick.onShareClick(content);
        }
    }

    public String getContent() {
        return content;
    }

    @JavascriptInterface
    public void inviteFriend() {

    }

    public interface OnShareClick {
        void onShareClick(String content);
    }

    private OnShareClick onShareClick;

    public void setOnShareClick(OnShareClick onShareClick) {
        this.onShareClick = onShareClick;
    }
}

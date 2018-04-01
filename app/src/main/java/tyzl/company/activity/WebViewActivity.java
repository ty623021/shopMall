package tyzl.company.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.util.HashMap;

import tyzl.company.R;
import tyzl.company.global.Config;
import tyzl.company.main.BaseActivity;
import tyzl.company.main.MyApplication;
import tyzl.company.utils.AbLogUtil;
import tyzl.company.utils.AbStringUtil;
import tyzl.company.utils.JavaScriptObj;
import tyzl.company.weight.MyProgressBar;
import tyzl.company.weight.TitleView;


public class WebViewActivity extends BaseActivity implements JavaScriptObj.OnShareClick {
    //    private String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.43 BIDUBrowser/6.x Safari/537.31";
    private static final String ACCEPT_ENCODING = "Accept-Encoding";
    private static final String RONGTENG = "rongteng";
    private static final String ANDROID = "rongteng-android";
    //    private static final String USER_AGENT = "User-Agent";
    private WebView webview;
    private String titleshow;
    private String url;
    private TitleView titleView;
    private MyProgressBar progress_bar_horizontal;
    private String viewTitle;//当前页面的标题
    private String viewUrl;//当前页面的URL地址
    private HashMap<String, String> map;
    private JavaScriptObj javaScriptObj;
    private String content;
    private String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_webview);
        super.onCreate(savedInstanceState);
        setOrChangeTranslucentColor(titleView, true, Color.parseColor(Config.colorPrimary));
    }


    /**
     * 跳转到WebViewActivity
     *
     * @param context 指定跳转的activity
     * @param url     url
     * @param title   标题
     */
    public static void startWebActivity(Activity context, String url, String title) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    private boolean setTitleView() {
        Intent intent = this.getIntent();
        url = intent.getStringExtra("url");
        titleshow = intent.getStringExtra("title");
        tag = intent.getStringExtra("tag");
        if (TextUtils.isEmpty(url)) {
            return true;
        }
        url = url.trim();
        if (AbStringUtil.isEmpty(titleshow)) {
            titleView.setTitle("小诸葛金服");
        } else {
            titleView.setTitle(titleshow);
        }

        titleView.setLeftImageButton(R.drawable.back);

        titleView.showLeftButton(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webview.canGoBack()) {
                    webview.goBack();
                } else {
                    isSource();
                    finish();
                }
            }
        });

        titleView.setRightTextButton("分享", new OnClickListener() {
            @Override
            public void onClick(View v) {
//                MyShareSdk shareSdk = new MyShareSdk(context);
//                if (content == null) {
//                    content = viewTitle;
//                }
//                shareSdk.showShare(null, viewTitle, viewUrl, content);
            }
        });
        return false;
    }

    private void isShowLeftOrRight(boolean is) {
        if (is) {
            titleView.hiddenRightTextButton();
            titleView.hiddenLeftImageButton();
            titleView.showRightImageButton(R.drawable.close_img, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    WebViewActivity.this.finish();
                }
            });
            if (webview != null && webview.canGoBack()) {
                titleView.showRightImageButton();
            } else {
                titleView.hiddenRightImageButton();
            }
        } else {
            titleView.showRightTextButton();
            titleView.hiddenRightImageButton();
            titleView.showLeftImageDeleteBtn(R.drawable.close_img, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    WebViewActivity.this.finish();
                }
            });
            if (webview != null && webview.canGoBack()) {
                titleView.showLeftImageButton();
            } else {
                titleView.hiddenLeftImageButton();
            }
        }
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void initView() {
        titleView = (TitleView) findViewById(R.id.title);
        if (setTitleView()) return;
        progress_bar_horizontal = (MyProgressBar) findViewById(R.id.progress_bar_horizontal);
        webview = (WebView) findViewById(R.id.webview);
        setWebView();
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                viewTitle = title;
                if (viewTitle != null) {
                    titleView.setTitle(viewTitle);
                }

            }

            public void onProgressChanged(WebView view, int newProgress) {
                if (progress_bar_horizontal.getVisibility() != View.VISIBLE) {
                    progress_bar_horizontal.setVisibility(View.VISIBLE);
                }
                progress_bar_horizontal.setAnimProgress2(newProgress);
            }

        });

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                viewUrl = url;
                AbLogUtil.e("viewUrl", url);
            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url, map);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (flagUrl.equals(url)) {
                    isShowShare(false);
                } else {
                    isShowShare(true);
                }
                super.onPageFinished(view, url);
            }
        });

    }

    /**
     * 判断是否显示分享
     *
     * @param isShow
     */
    private void isShowShare(boolean isShow) {

        isShowLeftOrRight(isShow);
    }

    private void setWebView() {
        webview.getSettings().setJavaScriptEnabled(true);

        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setBuiltInZoomControls(true);

        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);

        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        /**
         * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
         * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
         */
        webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);

        map = new HashMap<>();
        map.put(ACCEPT_ENCODING, "gzip");
        map.put(RONGTENG, ANDROID);
        map.put("x-auth-token", MyApplication.mApp.getSession().get("TOKEN"));
        javaScriptObj = new JavaScriptObj(this);
        javaScriptObj.setOnShareClick(this);
        webview.addJavascriptInterface(javaScriptObj, "android");
        AbLogUtil.e("Url", url);
        webview.loadUrl(url, map);
//        webview.loadUrl("file:///android_asset/index.html");
//        webview.loadUrl("http://192.168.0.27:8084/web/test.html");
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        } else {
            isSource();
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void isSource() {
        if ("push".equals(tag)) {
//            MainActivity.startMainActivity(WebViewActivity.this, 0);
        }
    }

    private String flagUrl = "";

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setEvent() {

    }


    @Override
    protected void initTitle() {

    }

    @Override
    public void onShareClick(String content) {
        //标记分享的页面
        flagUrl = viewUrl;
        this.content = content;
    }

    /**
     * 清除WebView缓存
     */
    public void clearWebViewCache(){

        //清理Webview缓存数据库
        try {
            deleteDatabase("webview.db");
            deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //WebView 缓存文件
        File appCacheDir = new File(getFilesDir().getAbsolutePath()+"/webcache");
        Log.e(TAG, "appCacheDir path="+appCacheDir.getAbsolutePath());

        File webviewCacheDir = new File(getCacheDir().getAbsolutePath()+"/webviewCache");
        Log.e(TAG, "webviewCacheDir path="+webviewCacheDir.getAbsolutePath());

        //删除webview 缓存目录
        if(webviewCacheDir.exists()){
            this.deleteFile(webviewCacheDir);
        }
        //删除webview 缓存 缓存目录
        if(appCacheDir.exists()){
            deleteFile(appCacheDir);
        }
    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            Log.e(TAG, "delete file no exists " + file.getAbsolutePath());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearWebViewCache();

    }
}

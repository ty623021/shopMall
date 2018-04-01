package tyzl.company.main;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.Environment;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import okhttp3.OkHttpClient;
import tyzl.company.R;
import tyzl.company.entity.HomeTypeInfo;
import tyzl.company.entity.UserAccountInfo;
import tyzl.company.entity.UserRegisterInfo;
import tyzl.company.global.ActivityManager;
import tyzl.company.global.Config;
import tyzl.company.global.Constant;
import tyzl.company.utils.AbJsonUtil;
import tyzl.company.volley.InitSSLSocketFactory;

public class MyApplication extends Application {
    private String TAG = this.getClass().getSimpleName();
    private static Context context;
    public static MyApplication mApp;
    private Session session;
    private UserAccountInfo user;// 当前用户 如果为null表示未登录
    private boolean downLoad;
    private String msg;

    public boolean isDownLoad() {
        return downLoad;
    }

    public void setDownLoad(boolean downLoad) {
        this.downLoad = downLoad;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        context = getApplicationContext();
        this.session = new SharedPreferencesSession(this);
        initImageLoader();
        initGlide();
        initOkHttp();
    }

    public static Context getContext() {
        return context;
    }

    /**
     * 退出
     */
    public static void exit() {
        ActivityManager.getInstance().clearAllActivity();
    }

    /**
     * 获取渠道号
     */
    public static String getMetaData() {
        String msg = "no";
        try {
            ApplicationInfo appInfo = mApp.getPackageManager().getApplicationInfo(mApp.getPackageName(),
                    PackageManager.GET_META_DATA);
            msg = appInfo.metaData.getString("UMENG_CHANNEL");
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return msg;
    }

    private void initImageLoader() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true)
                .showImageOnFail(R.drawable.default_banner).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .diskCache(new UnlimitedDiskCache(new File(Environment
                        .getExternalStorageDirectory()
                        + File.separator
                        + Config.base_data_path
                        + File.separator
                        + Config.images_cache_path)))
                .defaultDisplayImageOptions(options). // 上面的options对象，一些属性配置
                build();
        ImageLoader.getInstance().init(config); // 初始化
    }

    private void initOkHttp() {
        InputStream caInput = new ByteArrayInputStream(InitSSLSocketFactory.load.getBytes());
        InputStream[] inputStreams = new InputStream[]{caInput};
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(inputStreams, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

    private void initGlide() {
        //设置主题
        // ThemeConfig.CYAN
        /**
         * 配置选择图片的样式
         setTitleBarTextColor//标题栏文本字体颜色
         setTitleBarBgColor//标题栏背景颜色
         setTitleBarIconColor//标题栏icon颜色，如果设置了标题栏icon，设置setTitleBarIconColor将无效
         setCheckNornalColor//选择框未选颜色
         setCheckSelectedColor//选择框选中颜色
         setCropControlColor//设置裁剪控制点和裁剪框颜色
         setFabNornalColor//设置Floating按钮Nornal状态颜色
         setFabPressedColor//设置Floating按钮Pressed状态颜色

         setIconBack//设置返回按钮icon
         setIconCamera//设置相机icon
         setIconCrop//设置裁剪icon
         setIconRotate//设置选择icon
         setIconClear//设置清楚选择按钮icon（标题栏清除选择按钮）
         setIconFolderArrow//设置标题栏文件夹下拉arrow图标
         setIconDelete//设置多选编辑页删除按钮icon
         setIconCheck//设置checkbox和文件夹已选icon
         setIconFab//设置Floating按钮icon
         setEditPhotoBgTexture//设置图片编辑页面图片margin外背景
         setIconPreview设置预览按钮icon
         setPreviewBg设置预览页背景
         */
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarBgColor(Color.rgb(75, 174, 254))
                .setTitleBarTextColor(Color.WHITE)
                .setFabNornalColor(Color.rgb(75, 174, 254))
                .setFabPressedColor(Color.rgb(10, 123, 218))
                .setCheckSelectedColor(Color.rgb(75, 174, 254))
                .build();
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();
        //配置imageloader
        GlideImageLoader imageloader = new GlideImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(context, imageloader, theme)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);
    }

    /**
     * 保存用户登录注册信息
     *
     * @param user
     */
    public void saveUserRegister(UserRegisterInfo user) {
        session.set("token", user.getToken());
        session.set("mobile", user.getUser_phone());
        session.putBoolean("isLogin", true);

    }
    // 退出清空session
    public void logout() {
        this.session.set("token", "");
        this.session.set("mobile","");

        //根据ID清除手势密码
//        this.session.set(getID(), "");
//        this.session.set("id", "");
//        this.session.set("JSESSIONID", "");
//        this.session.set("username", "");
//        this.session.putBoolean("isSkip", false);
//        this.session.putBoolean("isBindBank", false);
//        this.session.putBoolean("isBanSubName", false);
    }



    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }


    public UserAccountInfo getUser() {
        return user;
    }

    public void setUser(UserAccountInfo user) {
        this.user = user;
    }


    /**
     * 获取保存的手机号码
     *
     * @return
     */
    public String getUserPhone() {
        return session.get("mobile");
    }


    /**
     * 获取保存的用户TOKEN
     *
     * @return
     */
    public String getToken() {
        return session.get("token");
    }

    /**
     * 返回 true:已登录 ,false 没登录
     */
    @SuppressLint("NewApi")
    public boolean hasLogin() {
        if (TextUtils.isEmpty(session.get("token"))) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * 判断是否首次启动app
     *
     * @return true 代表用户是第一次登录 false 代表不是第一次登录
     */
    public boolean isFirstIn() {
        String appCurVesion = this.session.get(Constant.APP_CUR_VERSION);
        if (appCurVesion != null && appCurVesion.equals(getAppVersion())) {
            return false;
        }
        return true;
    }

    public void setFirstIn() {
        this.setSessionVal(Constant.APP_CUR_VERSION, getAppVersion());
    }

    // 获取当前app的版本号
    public String getAppVersion() {
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo packInfo;
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    // 依据key value 存储数据
    public void setSessionVal(String key, String value) {
        this.session.set(key, value);
    }


    /**
     * 保存分类标题
     *
     * @param list
     */
    public void saveHomeTypeInfoList(List<HomeTypeInfo> list) {
        String json = AbJsonUtil.toJson(list);
        setSessionVal("typeInfoList", json);
    }

    /**
     * 获取分类标题
     *
     * @return
     */
    public List<HomeTypeInfo> getHomeTypeInfoList() {
        TypeToken typeToken = new TypeToken<List<HomeTypeInfo>>() {
        };
        String typeInfoList = this.session.get("typeInfoList");
        List<HomeTypeInfo> objects = (List<HomeTypeInfo>) AbJsonUtil.fromJson(typeInfoList, typeToken);
        return objects;
    }

}

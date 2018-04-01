package tyzl.company.volley;

import android.content.Context;

import java.io.File;

import tyzl.company.global.Config;
import tyzl.company.global.Constant;
import tyzl.company.main.MyApplication;
import tyzl.company.utils.AbDateUtil;
import tyzl.company.utils.AbLogUtil;
import tyzl.company.utils.AbMd5;
import tyzl.company.utils.AppUtil;


/**
 * Created by Administrator on 2015/3/11.
 */
public class IRequest {

    private final Context context;

    public IRequest(Context context) {
        this.context = context;
    }


//    ====================================推荐使用start================================================


    /**
     * 设置小诸葛网络请求
     *
     * @param params
     */
    private static void setParams(RequestParams params) {
//        params.put("appKey", Constant.APPKEY);
//        params.put("appVersion", AppUtil.getAppVersionName(MyApplication.getContext()));
        params.put("token", MyApplication.mApp.getToken());
        String time = AbDateUtil.getCurrentDate(AbDateUtil.DATE_FORMAT_YMDHMS);
        String time_stamp = AbDateUtil.getTime(time);
        params.put("time", time_stamp);
        String sign = AbMd5.MD5(Constant.APPSECRET+ time_stamp);
        params.remove();
        params.put("sign", sign);
    }

    /**
     * 返回String post
     *
     * @param url
     * @param params
     * @param l
     */
    public void post(String url, RequestParams params, RequestListener l) {
        if (params != null) {
            setParams(params);
        }
        RequestManager.post(url, context, params, l);
    }

    /**
     * 返回String 带进度条 post
     *
     * @param url
     * @param params
     * @param progressTitle
     * @param l
     */
    public void post(String url, RequestParams params,
                     String progressTitle, RequestListener l) {
        if (params != null) {
            setParams(params);
        }
        RequestManager.post(url, context, params, progressTitle, l);
    }


    /**
     * 返回String 带进度条 post
     *
     * @param url
     * @param params
     * @param l
     */
    public void post(Context context, String url, RequestParams params,
                     RequestListener l) {
        if (params != null) {
            setParams(params);
        }
        RequestManager.post(url, context, params, l);
    }


    /**
     * 返回String 带进度条 post
     * 上传文件
     *
     * @param url
     * @param params
     * @param l
     */
    public void upLoadPost(String url, String fileKey, File file, RequestParams params,
                           RequestListener l) {
        if (params != null) {
            setParams(params);
        }
        RequestManager.upLoadPost(url, fileKey, file, context, params, l);
    }


    /**
     * 返回String 带进度条 post
     *
     * @param context
     * @param url
     * @param params
     * @param progressTitle
     * @param l
     */
    public static void post(Context context, String url, RequestParams params,
                            String progressTitle, RequestListener l) {
        if (params != null) {
            setParams(params);
        }
        RequestManager.post(url, context, params, progressTitle, l);
    }

//  =======================================推荐使用end=============================================

}

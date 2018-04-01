package tyzl.company.volley;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import okhttp3.Call;
import tyzl.company.utils.AbJsonUtil;
import tyzl.company.utils.AbLogUtil;

/**
 * Created by Administrator on 2015/3/11.
 */
@SuppressLint("NewApi")
public class RequestManager {
    private static Context context;
    private static String url;

    private RequestManager() {
    }

    /**
     * 返回String
     * 上传文件
     *
     * @param url      接口
     * @param tag      上下文
     * @param params   post需要传的参数
     * @param listener 回调
     */
    public static void upLoadPost(String url, String fileKey, File file, Object tag, RequestParams params,
                                  final RequestListener listener) {
        if (tag != null) {
            context = (Context) tag;
        }
        final ResponseListener responseListener = responseListener(listener, false, null);
        OkHttpUtils
                .post()//
                .addFile(fileKey, file.getName(), file)
                .url(url)//
                .params(params.getUrlParams())//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        responseListener.onErrorResponse(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        responseListener.onResponse(response);
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        responseListener.inProgress(progress, total, id);
                    }
                });
    }

    /**
     * 返回String
     *
     * @param url      接口
     * @param tag      上下文
     * @param params   post需要传的参数
     * @param listener 回调
     */
    public static void post(String url, Object tag, RequestParams params,
                            final RequestListener listener) {
        if (tag != null) {
            context = (Context) tag;
        }
        final ResponseListener responseListener = responseListener(listener, false, null);
        OkHttpUtils
                .post()//
                .url(url)//
                .params(params.getUrlParams())//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        responseListener.onErrorResponse(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        responseListener.onResponse(response);
                    }
                });
    }

    /**
     * 返回String 带进度条
     *
     * @param url           接口
     * @param tag           上下文
     * @param params        post需要传的参数
     * @param progressTitle 进度条文字  可以不传
     * @param listener      回调
     */
    public static void post(String url, Object tag, RequestParams params,
                            String progressTitle, RequestListener listener) {
        RequestManager.url = url;
        if (tag != null) {
            context = (Context) tag;
        }
        final ResponseListener responseListener;

        if (progressTitle != null) {
            LoadingFragment dialog = new LoadingFragment();
            dialog.show(((FragmentActivity) tag).getSupportFragmentManager(), "Loading");
            dialog.setMsg(progressTitle);
            responseListener = responseListener(listener, true, dialog);
        } else {
            responseListener = responseListener(listener, false, null);
        }

        OkHttpUtils
                .post()//
                .url(url)//
                .params(params.getUrlParams())//
                .build()//
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        responseListener.onErrorResponse(e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        responseListener.onResponse(response);
                    }
                });
    }


    /**
     * 判断网络请求的状态
     *
     * @param data
     */
    private static void isSuccess(String data) {
        if (!AbJsonUtil.isSuccess(data)) {

        }
    }


    public interface ResponseListener<T> {
        void onResponse(T var1);

        void onErrorResponse(Exception var1);

        void inProgress(float progress, long total, int id);
    }

    protected static ResponseListener responseListener(
            final RequestListener l, final boolean flag, final LoadingFragment p) {

        return new ResponseListener<String>() {
            @Override
            public void onResponse(String data) {
                l.requestSuccess(data);
                isSuccess(data);
                if (flag) {
                    if (p != null) {
                        if (p.getShowsDialog()) {
                            p.dismiss();
                        }
                    }
                }
            }

            @Override
            public void onErrorResponse(Exception e) {
                AbLogUtil.e("Exception",e.toString());
                String message = ErrorHelper.getMessage(e);
                l.requestError(message);
                if (flag) {
                    if (p != null) {
                        if (p.getShowsDialog()) {
                            p.dismiss();
                        }
                    }
                }
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                l.inProgress(progress, total, id);
            }
        };
    }
}

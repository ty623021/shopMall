package tyzl.company.utils;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import tyzl.company.entity.BannerInfo;
import tyzl.company.global.Config;
import tyzl.company.volley.IRequest;
import tyzl.company.volley.RequestListener;
import tyzl.company.volley.RequestParams;

/**
 * Created by geek on 2016/7/20.
 * 根据code获取banner图
 */
public class BannerUtil {

    private final Context activity;
    private final IRequest http;
    private final String code;
    private OnBannerClick onBannerClick;

    public BannerUtil(Context activity, IRequest http, String code) {
        this.activity = activity;
        this.http = http;
        this.code = code;
    }

    /**
     * 首页banner数据
     */
    public void getBanner() {
        RequestParams params = new RequestParams();
        params.put("code", code);
        http.post(Config.URL_INDEX_BANNER, params, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AbLogUtil.e("getBanner", json.toString());
                if (AbJsonUtil.isSuccess(json)) {
                    Type type = new TypeToken<List<BannerInfo>>() {
                    }.getType();
                    List<BannerInfo> picList = (List<BannerInfo>) AbJsonUtil.fromJson(json, type, "result");
                    if (AbStringUtil.isListEmpty(picList)) {
                        if (onBannerClick != null) {
                            onBannerClick.onBannerClick(picList);
                        }
                    }
                }
            }

            @Override
            public void requestError(String message) {
            }
        });
    }

    public interface OnBannerClick {
        void onBannerClick(List<BannerInfo> list);
    }

    public void setOnBannerClick(OnBannerClick onBannerClick) {
        this.onBannerClick = onBannerClick;
    }
}

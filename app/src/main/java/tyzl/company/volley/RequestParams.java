package tyzl.company.volley;

import java.io.File;
import java.util.TreeMap;

/**
 * Created by Administrator on 2015/3/11.
 */
public class RequestParams {


    protected TreeMap<String, String> urlParams;

    public RequestParams() {
        init();
    }

    public RequestParams(String key, String value) {
        init();
        put(key, value);
    }

    public TreeMap<String, String> getUrlParams() {
        return urlParams;
    }

    public void setUrlParams(TreeMap<String, String> urlParams) {
        this.urlParams = urlParams;
    }

    private void init() {
        urlParams = new TreeMap<String, String>();
    }

    /**
     * @param key
     * @param value
     */
    public void put(String key, String value) {
        if (key != null && value != null) {
            urlParams.put(key, value);
        }
    }


    /**
     * 请求时必须先删除原来的签名
     */
    public void remove() {
        urlParams.remove("sign");
    }

    //
    @Override
    public String toString() {
        return "RequestParams{" +
                "urlParams=" + urlParams.toString() +
                '}';
    }
}

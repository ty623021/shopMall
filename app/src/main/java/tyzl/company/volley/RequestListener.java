package tyzl.company.volley;

/**
 * Created by Administrator on 2015/3/11.
 * 网络请求接口回调
 */
public abstract class RequestListener {

    /**
     * 成功
     */
    public abstract void requestSuccess(String json);

    /**
     * 错误
     */
    public abstract void requestError(String message);

    /**
     * 上传文件进度回调
     *
     * @param progress
     * @param total
     * @param id
     */
    public void inProgress(float progress, long total, int id) {

    }

}

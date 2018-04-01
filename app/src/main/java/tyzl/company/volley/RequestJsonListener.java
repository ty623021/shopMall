package tyzl.company.volley;

/**
 * Created by Administrator on 2015/3/11.
 */
public interface RequestJsonListener<T> {
    /**
     * 成功
     */
    void requestSuccess(T result);

    /**
     * 错误
     */
    void requestError(Exception e);
}

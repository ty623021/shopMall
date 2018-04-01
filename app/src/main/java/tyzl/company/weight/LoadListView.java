package tyzl.company.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import tyzl.company.R;


/**
 * 自定义listview  添加上拉加载更多
 *
 * @author Administrator
 */
public class LoadListView extends ListView implements AbsListView.OnScrollListener {

    private OnLoadingListener onLoadingClick;
    private int maxNumber = 10;
    private LinearLayout linear_footer;
    private TextView tv_loading;
    private ProgressBar progressbar;
    private View view;
    private SwipeRefreshLayout swipe;

    public SwipeRefreshLayout getSwipe() {
        return swipe;
    }

    public void setSwipe(SwipeRefreshLayout swipe) {
        this.swipe = swipe;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public OnLoadingListener getLoadingListener() {
        return onLoadingClick;
    }

    public void setOnLoadingListener(OnLoadingListener onLoadingClick) {
        this.onLoadingClick = onLoadingClick;
    }

    public LoadListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        setOnScrollListener(this);
    }

    private void initView(Context context) {
        view = View.inflate(context, R.layout.footer_view, null);
        linear_footer = (LinearLayout) view.findViewById(R.id.linear_footer);
        tv_loading = (TextView) view.findViewById(R.id.tv_loading);
        progressbar = (ProgressBar) view.findViewById(R.id.progressbar);
        addFooter();
    }

    public void addFooter() {
        addFooterView(view);
    }

    public void hideFooter() {
        linear_footer.setVisibility(GONE);
        isLoading = true;
        if (swipe != null) {
            swipe.setRefreshing(false);
        }
    }

    public LoadListView(Context context) {
        super(context);
    }

    @SuppressLint("Instantiatable")
    public LoadListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    private boolean isLoading = true;

    public boolean isLoading() {
        return isLoading;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (isLoading && totalItemCount >= maxNumber && getLastVisiblePosition() + 1 == totalItemCount) {
            //到了最底部
            linear_footer.setVisibility(VISIBLE);
            if (tv_loading != null) {
                tv_loading.setText("加载更多");
                if (onLoadingClick != null) {
                    isLoading = false;
                    handler.sendEmptyMessageDelayed(100, 500);
                }
            }
        }
    }

    public interface OnLoadingListener {
        void loading();
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 100) {
                onLoadingClick.loading();
            }
            return false;
        }
    });
}

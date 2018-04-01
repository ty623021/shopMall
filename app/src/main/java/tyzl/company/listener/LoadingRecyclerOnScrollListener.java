package tyzl.company.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import tyzl.company.utils.AbLogUtil;

/**
 * RecyclerView 上拉加载更多
 */
public abstract class LoadingRecyclerOnScrollListener extends
        RecyclerView.OnScrollListener {

    private int previousTotal = 0;
    private boolean loading = true;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private int currentPage = 1;

    private LinearLayoutManager mLinearLayoutManager;

    public LoadingRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        AbLogUtil.e("firstVisibleItem", firstVisibleItem + "  visibleItemCount=" + visibleItemCount + "  totalItemCount=" + totalItemCount + "");
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading
                && (totalItemCount - visibleItemCount) <= firstVisibleItem) {
            currentPage++;
            onLoadMore(currentPage);
            loading = true;
        }
    }

    public abstract void onLoadMore(int currentPage);
}
package tyzl.company.pullview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import tyzl.company.R;
import tyzl.company.weight.LoadListView;

/**
 * Created by geek on 2016/7/22.
 * 处理刷新操作类
 */
public class AbRefreshUtil {
    /**
     * 初始化下拉刷新
     *
     * @param pull     下拉刷新控件
     * @param listener 下拉刷新监听
     */
    public static void initRefresh(AbPullToRefreshView pull, AbPullToRefreshView.OnHeaderRefreshListener listener) {
        pull.setLoadMoreEnable(false);
        pull.clearFooter();
        pull.setOnHeaderRefreshListener(listener);
        //是否直接下拉刷新
//        pull.headerRefreshing();
    }

    /**
     * 初始化下拉刷新和上拉加载
     *
     * @param pull            拉刷新控件
     * @param refreshListener 下拉刷新监听
     * @param loadListener    上拉加载监听
     */
    public static void initRefresh(AbPullToRefreshView pull, AbPullToRefreshView.OnHeaderRefreshListener refreshListener, AbPullToRefreshView.OnFooterLoadListener loadListener) {
//        pull.setLoadMoreEnable(false);
//        pull.clearFooter();
        pull.setOnHeaderRefreshListener(refreshListener);
        pull.setOnFooterLoadListener(loadListener);
        //是否直接下拉刷新
//        pull.headerRefreshing();
    }

    /**
     * 初始化上拉加载
     *
     * @param pull         拉刷新控件
     * @param loadListener 上拉加载监听
     */
    public static void initRefresh(AbPullToRefreshView pull, AbPullToRefreshView.OnFooterLoadListener loadListener) {
        pull.setOnFooterLoadListener(loadListener);
        pull.setPullRefreshEnable(false);
    }


    /**
     * 初始化
     * AbPullToRefreshView 上拉加载
     * SwipeRefreshLayout  下拉刷新
     *
     * @param pull         拉刷新控件
     * @param loadListener 上拉加载监听
     */
    public static void initRefresh(AbPullToRefreshView pull, SwipeRefreshLayout swipe, SwipeRefreshLayout.OnRefreshListener refreshListener, AbPullToRefreshView.OnFooterLoadListener loadListener) {
        //上拉加载
        pull.setOnFooterLoadListener(loadListener);
        pull.setPullRefreshEnable(false);

        //下拉刷新
        swipe.setColorSchemeResources(R.color.button_color);
        swipe.setSize(SwipeRefreshLayout.DEFAULT);
        swipe.setProgressBackgroundColor(R.color.white);
        // 这句话是为了，第一次进入页面的时候显示加载进度条
//        swipe.setProgressViewOffset(true, -50, 100);
//        swipe.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, context.getResources()
//                .getDisplayMetrics()));
        swipe.setOnRefreshListener(refreshListener);
//        swipe.setRefreshing(true);
    }

    /**
     * 初始化 SwipeRefresh 下拉刷新
     *
     * @param context
     * @param swipe
     * @param refreshListener
     */
    public static void setSwipeRefresh(Context context, SwipeRefreshLayout swipe, SwipeRefreshLayout.OnRefreshListener refreshListener) {
        swipe.setColorSchemeResources(R.color.button_color);
        swipe.setSize(SwipeRefreshLayout.DEFAULT);
        swipe.setProgressBackgroundColor(R.color.white);
        // 这句话是为了，第一次进入页面的时候显示加载进度条
//        swipe.setProgressViewOffset(true, -50, 100);
//        swipe.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, context.getResources()
//                .getDisplayMetrics()));
        swipe.setOnRefreshListener(refreshListener);
//        swipe.setRefreshing(true);
    }


    /**
     * 加载完成的时候，关闭加载动画 可以为空
     *
     * @param pull  上拉加载控件
     * @param swipe 下拉刷新控件
     */
    public static void onLoadComplete(AbPullToRefreshView pull, SwipeRefreshLayout swipe, int nextPage, int totalPage) {
        if (pull != null) {
            pull.onFooterLoadFinish();
            if (totalPage == 0 || nextPage > totalPage) {
                pull.setLoadMoreEnable(false);
                pull.clearFooter();
            }
        }
        if (swipe != null) {
            swipe.setRefreshing(false);
        }
    }

    /**
     * 判断是否需要加载更多
     *
     * @param nextPage     下一页
     * @param totalPage    列表的总数
     * @param loadListView 上拉加载更多
     */
    public static boolean isLoading(int nextPage, int totalPage, LoadListView loadListView) {
        //当nextPage大于等于请求到的总页数的时候，关闭上拉加载的功能
        if (totalPage == 0 || nextPage > totalPage) {
            loadListView.hideFooter();
            loadListView.setIsLoading(false);
        } else {
            return true;
        }
        return false;
    }


    /**
     * 判断是否需要加载更多
     *
     * @param nextPage  下一页
     * @param totalPage 列表的总数
     * @param
     */
    public static boolean isPullLoading(int nextPage, int totalPage, AbPullToRefreshView pull) {
        //当nextPage大于等于请求到的总页数的时候，关闭上拉加载的功能
        if (totalPage == 0 || nextPage > totalPage) {
            pull.setLoadMoreEnable(false);
            pull.onFooterLoadFinish();
            pull.clearFooter();
        } else {
            return true;
        }
        return false;
    }

    /**
     * 判断是否需要加载更多
     *
     * @param nextPage  下一页
     * @param totalPage 列表的总数
     */
    public static boolean isLoad(int nextPage, int totalPage) {
        //当偏移量大于等于请求到的总数的时候，关闭上拉加载的功能
        if (totalPage == 0 || nextPage >= totalPage) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 用recycleView列表页面的网络请求
     *
     * @param adapter     适配器
     * @param isNetwork   是否显示网络链接失败
     * @param network     包裹网络加载失败和无数据的布局
     * @param nodata      没有请求到数据
     * @param progressBar 进度条
     */
    public static void hintView(RecyclerView.Adapter adapter, boolean isNetwork, ImageView network, ImageView nodata, ProgressBar progressBar) {
        progressBar.setVisibility(View.GONE);
        if (adapter.getItemCount() == 0) {
            network.setVisibility(View.VISIBLE);
            if (isNetwork) {
                network.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.GONE);
            } else {
                network.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
            }
        } else {
            network.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
        }
    }

    /**
     * 用listView列表页面的网络请求
     *
     * @param adapter     适配器
     * @param isNetwork   是否显示网络链接失败
     * @param network     包裹网络加载失败和无数据的布局
     * @param nodata      没有请求到数据
     * @param progressBar 进度条
     */
    public static void hintView(Adapter adapter, boolean isNetwork, ImageView network, ImageView nodata, ProgressBar progressBar) {
        progressBar.setVisibility(View.GONE);
        if (adapter.getCount() == 0) {
            network.setVisibility(View.VISIBLE);
            if (isNetwork) {
                network.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.GONE);
            } else {
                network.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
            }
        } else {
            network.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
        }
    }

    /**
     * 用PagerFragment列表页面的网络请求
     *
     * @param adapter     适配器
     * @param isNetwork   是否显示网络链接失败
     * @param network     包裹网络加载失败和无数据的布局
     * @param nodata      没有请求到数据
     * @param progressBar 进度条
     */
    public static void hintView(PagerAdapter adapter, boolean isNetwork, ImageView network, ImageView nodata, ProgressBar progressBar) {
        progressBar.setVisibility(View.GONE);
        if (adapter.getCount() == 0) {
            network.setVisibility(View.VISIBLE);
            if (isNetwork) {
                network.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.GONE);
            } else {
                network.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
            }
        } else {
            network.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
        }
    }


    /**
     * 用于一般页面的网络请求
     *
     * @param obj         网络请求道的对象
     * @param network     包裹网络加载失败和无数据的布局
     * @param nodata      没有请求到数据
     * @param progressBar 网络加载失败的图片
     */
    public static void hintView(Object obj, boolean isNetwork, ImageView network, ImageView nodata, ProgressBar progressBar) {
        progressBar.setVisibility(View.GONE);
        if (obj != null) {
            nodata.setVisibility(View.GONE);
            network.setVisibility(View.GONE);
        } else {
            if (isNetwork) {
                nodata.setVisibility(View.GONE);
                network.setVisibility(View.VISIBLE);
            } else {
                nodata.setVisibility(View.VISIBLE);
                network.setVisibility(View.GONE);
            }
        }
    }

    public static void hintViewWithRecyclerAdapter(boolean isNetwork, RecyclerView.Adapter adapter, View notice, ImageView nodata_img, ImageView network_img, ProgressBar progressBar) {
        if (adapter.getItemCount() == 0) {
            progressBar.setVisibility(View.GONE);
            if (isNetwork) {
                network_img.setVisibility(View.VISIBLE);
            } else {
                nodata_img.setVisibility(View.VISIBLE);
            }
        } else {
            notice.setVisibility(View.GONE);
        }

    }

}

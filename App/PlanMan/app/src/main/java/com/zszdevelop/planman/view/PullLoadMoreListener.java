package com.zszdevelop.planman.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by jimmy on 15/12/14.
 */
public abstract class PullLoadMoreListener extends RecyclerView.OnScrollListener {

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = false;
    //list到达 最后一个item的时候 触发加载
    private int visibleThreshold = 1;
    // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;
    //默认第一页
    private int current_page = 1;

    private LinearLayoutManager mLinearLayoutManager;

    public PullLoadMoreListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && totalItemCount > visibleItemCount && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            current_page++;

            onLoadMore(current_page);

            loading = true;
        }
    }

    public int getVisibleThreshold() {
        return visibleThreshold;
    }

    public void setVisibleThreshold(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    public int getCurrentPage() {
        return current_page;
    }

    public void setCurrentPage(int currentPage) {
        this.current_page = current_page;
    }

    public abstract void onLoadMore(int currentPage);
}

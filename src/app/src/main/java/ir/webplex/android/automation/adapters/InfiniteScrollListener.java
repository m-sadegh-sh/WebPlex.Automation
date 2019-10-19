package ir.webplex.android.automation.adapters;

import android.widget.AbsListView;

public abstract class InfiniteScrollListener implements AbsListView.OnScrollListener {
    private boolean mHasMorePage = true;
    private int mPageSize;
    private int mCurrentPageIndex = 0;
    private int mItemCount = 0;
    private boolean mIsLoading = true;

    public InfiniteScrollListener(int pageSize) {
        mPageSize = pageSize;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (!mHasMorePage)
            return;

        if (mIsLoading && totalItemCount > mItemCount) {
            mCurrentPageIndex++;
            mItemCount = totalItemCount;
            mIsLoading = false;
        }

        if (!mIsLoading && totalItemCount - visibleItemCount <= firstVisibleItem + mPageSize) {
            onLoadNextPage();
            mIsLoading = true;
        }
    }

    public void setHasMorePage(boolean hasMorePage) {
        mHasMorePage = hasMorePage;
    }

    public void reset() {
        mHasMorePage = true;
        mCurrentPageIndex = 0;
        mItemCount = 0;
        mIsLoading = true;
    }

    public boolean isInFirstPage() {
        return mCurrentPageIndex == 0;
    }

    public abstract void onLoadNextPage();

    public int getCurrentPageIndex() {
        return mCurrentPageIndex;
    }

    public int getPageSize() {
        return mPageSize;
    }
}
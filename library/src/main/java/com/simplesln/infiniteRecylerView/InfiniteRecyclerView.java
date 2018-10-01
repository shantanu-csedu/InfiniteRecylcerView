package com.simplesln.infiniteRecylerView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class InfiniteRecyclerView extends RecyclerView{
    private OnLoadMoreListener onLoadMoreListener;
    private boolean loadMore;
    private boolean loading;
    private int loadMoreOffset = 0;

    public InfiniteRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public InfiniteRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InfiniteRecyclerView(Context context) {
        super(context);
    }

    public void setLoadMoreOffset(int loadMoreOffset){
        this.loadMoreOffset = loadMoreOffset;
    }

    public void loadingDone() {
        loading = false;
        if(getAdapter() != null){
            ((InfiniteRecyclerViewAdapter)getAdapter()).removeLoadingView();
            getAdapter().notifyDataSetChanged();
        }
    }

    private OnScrollListener onScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int totalItem = getLayoutManager().getItemCount();
            int lastVisibleItem = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition() +1 ;//get nth item, not position
            if(!loading && totalItem <= (lastVisibleItem + loadMoreOffset)){
                if(onLoadMoreListener != null){
                    onLoadMoreListener.onLoadMore();
                    if(getAdapter() != null){
                        ((InfiniteRecyclerViewAdapter)getAdapter()).addLoadingView();
                        (getAdapter()).notifyItemInserted(getAdapter().getItemCount());
                    }
                    loading = true;
                }
            }
        }
    };

    public void setAdapter(InfiniteRecyclerViewAdapter adapter) {
        super.setAdapter(adapter);
    }

    public void noMoreToLoad() {
        loadMore = false;
        removeOnScrollListener(onScrollListener);
    }

    public void setLoadMoreResolver(OnLoadMoreListener onLoadMoreListener) {
        loadMore = true;
        addOnScrollListener(onScrollListener);
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener{
        void onLoadMore();
    }
}

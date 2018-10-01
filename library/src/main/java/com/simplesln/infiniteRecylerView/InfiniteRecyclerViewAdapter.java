package com.simplesln.infiniteRecylerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public abstract class InfiniteRecyclerViewAdapter<T> extends RecyclerView.Adapter<InfiniteRecyclerViewHolder>{
    private final Context context;
    private OnItemClickLisener onItemClickListener;
    private List<T> values = new ArrayList<>();
    private View loadingView;
    private boolean loading;
    private int VIEW_TYPE_LOADING = 987620;

    public InfiniteRecyclerViewAdapter(Context context){
        this.context = context;
    }

    public void setLoadingView(View loadingView){
        this.loadingView = loadingView;
    }

    public void setOnItemClickListener(OnItemClickLisener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public final int getItemViewType(int position) {
        if(loading && position == values.size()){
            return VIEW_TYPE_LOADING;
        }
        return getItemViewHolderType(position);
    }

    protected int getItemViewHolderType(int position){
        return 0;
    }

    @NonNull
    @Override
    public final InfiniteRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_LOADING){
            return new InfiniteRecyclerViewHolder(getLoadingView());
        }
        return onCreateItemViewHolder(parent,viewType);
    }

    private View getLoadingView(){
        if(loadingView == null){
            loadingView = getDefaultLoadingView(context);
        }
        return loadingView;
    }


    protected abstract InfiniteRecyclerViewHolder onCreateItemViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public final void onBindViewHolder(@NonNull final InfiniteRecyclerViewHolder holder, final int position) {
        if(loading && position == values.size()){
            //nothing to do actually
        }
        else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(holder.itemView, position);
                    }
                }
            });
            onBindItemViewHolder(holder, position);
        }
    }

    public void addLoadingView(){
        loading = true;
    }

    public void removeLoadingView(){
        loading = false;
    }

    protected abstract void onBindItemViewHolder(@NonNull InfiniteRecyclerViewHolder holder, int position);

    public Context getContext() {
        return context;
    }

    @Override
    public final int getItemCount() {
        return values.size() + (loading ? 1 : 0);
    }

    public void add(T item){
        values.add(item);
    }

    public void add(int position,T item){
        values.add(position,item);
    }

    public void addAll(List<T> items){
        values.addAll(items);
    }

    public void remove(T item){
        values.remove(item);
    }

    public void remove(int position){
        if(values.size() > position){
            values.remove(position);
        }
    }

    public T getItem(int position){
        return values.get(position);
    }

    public List<T> getItems(){
        return values;
    }

    public void removeAll(){
        values.clear();
    }

    public View getDefaultLoadingView(Context context) {
        FrameLayout frameLayout = new FrameLayout(context);
        ProgressBar progressBar = new ProgressBar(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        frameLayout.addView(progressBar,params);
        return frameLayout;
    }
}

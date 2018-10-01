package com.example.infiterecylerviewdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.simplesln.infiniteRecylerView.InfiniteRecyclerViewAdapter;
import com.simplesln.infiniteRecylerView.InfiniteRecyclerViewHolder;

public class MyAdapter extends InfiniteRecyclerViewAdapter<String>{

    public MyAdapter(Context context) {
        super(context);
    }

    @Override
    protected InfiniteRecyclerViewHolder onCreateItemViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(
                LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false)
        );
    }

    @Override
    protected void onBindItemViewHolder(@NonNull InfiniteRecyclerViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        String item = getItem(position);
        myViewHolder.text.setText(item);
    }

    public class MyViewHolder extends InfiniteRecyclerViewHolder{
        TextView text;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
        }
    }
}

package com.example.infiterecylerviewdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;
import com.simplesln.infiniteRecylerView.InfiniteRecyclerView;
import com.simplesln.infiniteRecylerView.OnItemClickLisener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnItemClickLisener, InfiniteRecyclerView.OnLoadMoreListener {
    private MyAdapter adapter;
    private InfiniteRecyclerView infiniteRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new MyAdapter(this);
        infiniteRecyclerView = findViewById(R.id.list);
        infiniteRecyclerView.setHasFixedSize(true);
        infiniteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        infiniteRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        infiniteRecyclerView.setLoadMoreResolver(this);


        new DataLoader().execute();
    }

    @Override
    public void onItemClick(View view, int position) {
        String item = adapter.getItem(position);
        Toast.makeText(this, "item " + item, Toast.LENGTH_SHORT).show();
    }

    private List<String> getData(){
        Random random = new Random();
        List<String> data = new ArrayList<>();
        for(int i  = 0;i<5;i++){
            data.add(String.valueOf(random.nextInt(100000)));
        }
        return data;
    }

    @Override
    public void onLoadMore() {
        new DataLoader().execute();
    }

    private class DataLoader extends AsyncTask<Void,Void,List<String>>{

        @Override
        protected List<String> doInBackground(Void... voids) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getData();
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            infiniteRecyclerView.loadingDone();
            adapter.addAll(strings);
            adapter.notifyItemRangeInserted(adapter.getItemCount() - strings.size(),strings.size());

            if(adapter.getItemCount() >= 20){
                infiniteRecyclerView.noMoreToLoad();
            }
        }
    }
}

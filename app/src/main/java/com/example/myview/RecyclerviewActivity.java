package com.example.myview;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.myview.adapter.MyAdapter;
import com.example.myview.databinding.ActivityRecyclerViewBinding;
import com.example.myview.manager.FullyLinearLayoutManager;
import com.lei.lib.java.rxcache.RxCache;
import com.lei.lib.java.rxcache.converter.GsonConverter;
import com.lei.lib.java.rxcache.mode.CacheMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luchunhao on 2018/4/25.
 */

public class RecyclerviewActivity extends AppCompatActivity {

    private static final String TAG = "RecyclerviewActivity";

    private ActivityRecyclerViewBinding binding;
    private MyAdapter adapter;
    private List<String> list = new ArrayList<>();
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_recycler_view);

        for (int i = 0; i < 50; i++) {
            list.add("text===" + i);
        }

        adapter = new MyAdapter(this, list);

//        binding.recyclerView.setHasFixedSize(true);
//        binding.recyclerView.setNestedScrollingEnabled(false);
//        layoutManager = new LinearLayoutManager(this){
//            @Override
//            public boolean canScrollVertically() {
//                return true;
//            }
//
//            @Override
//            public void onMeasure(@NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state, int widthSpec, int heightSpec) {
//                super.onMeasure(recycler, state, widthSpec, heightSpec);
//            }
//        };
        layoutManager = new FullyLinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClick(TextView v) {
                v.setText(v.getText() + "\n1111111111");
            }

        });

        //initRxCache();
    }

    private void initRxCache() {
        new RxCache.Builder()
                .setDebug(true)   //开启debug，开启后会打印缓存相关日志，默认为true
                .setConverter(new GsonConverter())  //设置转换方式，默认为Gson转换
                .setCacheMode(CacheMode.BOTH)   //设置缓存模式，默认为二级缓存
                .setMemoryCacheSizeByMB(50)   //设置内存缓存的大小，单位是MB
                .setDiskCacheSizeByMB(100)    //设置磁盘缓存的大小，单位是MB
                .setDiskDirName("RxCache")    //设置磁盘缓存的文件夹名称
                .build();
    }

}

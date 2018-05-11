package com.example.myview;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.example.myview.adapter.MyAdapter;
import com.example.myview.databinding.ActivityRecyclerViewBinding;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_recycler_view);

        for (int i = 0; i < 100; i++) {
            list.add("text===" + i);
        }

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        adapter = new MyAdapter(this, list);
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClick(TextView v) {
                v.setText(v.getText() + "\n1111111111");
            }

        });
    }

}

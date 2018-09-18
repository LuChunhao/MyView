package com.example.myview.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.example.myview.R;
import com.example.myview.adapter.FoldRecyclerAdapter;
import com.example.myview.adapter.MyAdapter;
import com.example.myview.adapter.MyExpandableAdapter;
import com.example.myview.databinding.ActivityFoldRecyclerViewBinding;
import com.example.myview.databinding.ItemFoldListBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luchunhao on 2018/9/13.
 */

public class FoldRecyclerViewActivity extends AppCompatActivity {

    private ActivityFoldRecyclerViewBinding binding;
    private MyExpandableAdapter mAdapter;
    private FoldRecyclerAdapter adapter;
    private LinearLayoutManager manager;
    private List<String> groupArray = new ArrayList<>();
    private List<List<String>> childArray = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fold_recycler_view);
        initData();
        initView();
    }

    private void initData() {
        groupArray.add("飞机");
        groupArray.add("汽车");
        groupArray.add("自行车");

        // 添加child
        List<String> child1 = new ArrayList<>();
        child1.add("播音747");
        child1.add("马航");
        child1.add("国航");
        child1.add("海南航空");
        child1.add("东方航空");

        List<String> child2 = new ArrayList<>();
        child2.add("奔驰");
        child2.add("宝马");
        child2.add("雪佛兰");
        child2.add("大众");
        child2.add("法拉利");
        child2.add("兰博基尼");
        child2.add("迈巴赫");
        child2.add("奥迪");
        child2.add("劳斯莱斯");
        child2.add("英菲尼迪");
        child2.add("凯迪拉克");

        List<String> child3 = new ArrayList<>();
        child3.add("捷安特");
        child3.add("美利达");
        child3.add("永久");
        child3.add("凤凰");
        child3.add("老爷车");

        childArray.add(child1);
        childArray.add(child2);
        childArray.add(child3);

    }

    private void initView() {
        //mAdapter = new MyExpandableAdapter(this, groupArray, childArray);
        adapter = new FoldRecyclerAdapter(this, groupArray, childArray);
        //binding.listView.setAdapter(mAdapter);

        manager = new LinearLayoutManager(this);
        binding.mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //binding.mainRecyclerView.setAdapter(adapter);

        nestedScrollView();

    }

    /**
     * 用NestedScrollView实现
     */
    private void nestedScrollView() {
        for (int i = 0; i < groupArray.size(); i++) {
            final ItemFoldListBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.item_fold_list, binding.llContainer, false);
            itemBinding.tvGroupTitle.setText(groupArray.get(i));
            itemBinding.itemRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            itemBinding.itemRecyclerView.setAdapter(new MyAdapter(this, childArray.get(i)));
            itemBinding.tvGroupTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemBinding.itemRecyclerView.getVisibility() == View.VISIBLE) {
                        itemBinding.itemRecyclerView.setVisibility(View.GONE);
                    } else {
                        itemBinding.itemRecyclerView.setVisibility(View.VISIBLE);
                    }
                }
            });
            binding.llContainer.addView(itemBinding.getRoot());
        }
    }
}

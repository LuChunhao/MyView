package com.example.myview.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myview.R;
import com.example.myview.activity.NestedHoverTabActivity;
import com.example.myview.adapter.MyAdapter;
import com.example.myview.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luchunhao on 2018/7/9.
 */

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private MyAdapter adapter;
    private List<String> list = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private NestedHoverTabActivity mActivity;

    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        for (int i = 0; i < 50; i++) {
            list.add("text===" + i);
        }
        mActivity = (NestedHoverTabActivity) getActivity();
        adapter = new MyAdapter(mActivity, list);
        layoutManager = new LinearLayoutManager(mActivity);
        binding.fragmentRecyclerView.setLayoutManager(layoutManager);
        binding.fragmentRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        binding.fragmentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.fragmentRecyclerView.setAdapter(adapter);

        binding.fragmentRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int firstVisiblePosition = layoutManager.findFirstCompletelyVisibleItemPosition();
                    if (firstVisiblePosition == 0) {
                        mActivity.setExpanded(true, true);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }
}

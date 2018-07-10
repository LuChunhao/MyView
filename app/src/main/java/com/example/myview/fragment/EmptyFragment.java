package com.example.myview.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myview.R;
import com.example.myview.adapter.MyAdapter;
import com.example.myview.databinding.FragmentEmptyBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luchunhao on 2018/7/9.
 */

public class EmptyFragment extends Fragment {

    private FragmentEmptyBinding binding;
    private MyAdapter adapter;
    private List<String> list = new ArrayList<>();

    public EmptyFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_empty, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}

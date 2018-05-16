package com.example.myview;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.myview.bean.DataBean;
import com.example.myview.databinding.ActivityViewBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by luchunhao on 2018/4/16.
 */

public class ViewActivity extends AppCompatActivity {

    private ActivityViewBinding binding;
    private List<DataBean> mDataHistogramTotal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view);

        binding.btChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int num = random.nextInt(100);
                binding.btChange.setText(String.valueOf(num));
                binding.intervalView.setCurrentProgress(num);

                binding.slideLayout.invalidate();
            }
        });

        initData();
    }

    private void initData() {
        mDataHistogramTotal = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i <= 20; i++) {
            mDataHistogramTotal.add(new DataBean("name" + i, random.nextInt(100)));
        }

        binding.mHistogramView.setDataTotal(mDataHistogramTotal);
        binding.mHistogramView.setPaints(Color.WHITE, Color.GRAY, Color.RED, Color.BLACK);
    }
}

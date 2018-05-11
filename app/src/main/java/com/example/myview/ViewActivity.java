package com.example.myview;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.myview.bean.DataBean;
import com.example.myview.databinding.ActivityViewBinding;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by luchunhao on 2018/4/16.
 */

public class ViewActivity extends AppCompatActivity {

    private ActivityViewBinding binding;
    private HashMap<Integer, DataBean> mDataHistogramTotal;

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
        mDataHistogramTotal = new HashMap<>();
        Random random = new Random();
        for (int i = 1; i <= 20; i++) {
            mDataHistogramTotal.put(i,new DataBean("name" + i, random.nextInt(100)));
        }
//        mDataHistogramTotal.put(1, new DataBean("海萌", 100));
//        mDataHistogramTotal.put(2, new DataBean("涛涛", 18));
//        mDataHistogramTotal.put(3, new DataBean("火风", 17));
//        mDataHistogramTotal.put(4, new DataBean("周杰伦", 16));
//        mDataHistogramTotal.put(5, new DataBean("王宝强", 15));
//        mDataHistogramTotal.put(6, new DataBean("林俊杰", 14));
//        mDataHistogramTotal.put(7, new DataBean("孙悟空", 11));
//        mDataHistogramTotal.put(8, new DataBean("钟航", 10));
//        mDataHistogramTotal.put(9, new DataBean("小明", 8));
//        mDataHistogramTotal.put(10, new DataBean("小红", 5));

        binding.mHistogramView.setDataTotal(mDataHistogramTotal);
        binding.mHistogramView.setPaints(Color.WHITE, Color.GRAY, Color.RED, Color.BLACK);
    }
}

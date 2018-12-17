package com.example.myview;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myview.bean.DataBean;
import com.example.myview.databinding.ActivityViewBinding;
import com.example.myview.view.SplitImageView;
import com.example.myview.view.impl.OnLabelClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by luchunhao on 2018/4/16.
 */

public class ViewActivity extends AppCompatActivity {

    private static final String TAG = "ViewActivity";

    private ActivityViewBinding binding;
    private List<DataBean> mDataHistogramTotal;
    private List<String> labels;

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
        testImageClick();
        initSelectViewData();
    }

    /**
     * 热区图片点击
     */
    private void testImageClick() {
        List<String> urlList = new ArrayList<>();
        urlList.add("1111");
        urlList.add("2222");
        urlList.add("3333");
        urlList.add("4444");
        //binding.hotImageView.setUrlList(urlList);
        //binding.hotImageView.setOrientation(SplitImageView.Orientation.Vertical);
        binding.hotImageView.setOnWhereClickListener(new SplitImageView.OnWhereClickListener() {
            @Override
            public void onClick(View v, String url) {
                Log.d(TAG, "onClick: url===" + url);
            }
        });
    }

    /**
     * 条件选择view填充数据
     */
    private void initSelectViewData() {
        labels = new ArrayList<>();
        labels.add("花哪儿记账");
        labels.add("给未来写封信");
        labels.add("密码键盘");
        labels.add("抬手唤醒");
        labels.add("Cutisan");
        labels.add("记-专注创作");
        labels.add("我也不知道我是谁");
        labels.add("崩崩崩");
        labels.add("Android");
        labels.add("开发");

        binding.selectView.setLabels(labels);
        binding.selectView.setOnLabelClickListener(new OnLabelClickListener() {
            @Override
            public void onClick(int index, View v, Object s) {
                Toast.makeText(ViewActivity.this, labels.get(index), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

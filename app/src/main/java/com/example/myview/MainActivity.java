package com.example.myview;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myview.activity.AppWebView;
import com.example.myview.activity.FoldRecyclerViewActivity;
import com.example.myview.activity.GlideImageActivity;
import com.example.myview.activity.NestedHoverTabActivity;
import com.example.myview.activity.TenWebview;
import com.example.myview.activity.WebActivity;
import com.example.myview.databinding.ActivityMainBinding;
import com.noober.background.BackgroundLibrary;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BackgroundLibrary.inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.btMyView.setOnClickListener(this);
        binding.btRecyclerView.setOnClickListener(this);
        binding.btWebView.setOnClickListener(this);
        binding.btGlideImage.setOnClickListener(this);
        binding.btTabLayout.setOnClickListener(this);
        binding.btFoldRecyclerView.setOnClickListener(this);
        binding.btAppWebview.setOnClickListener(this);
        binding.btTencentWebview.setOnClickListener(this);

        ObjectAnimator animator = ObjectAnimator.ofFloat(binding.ivLauncher, "rotationY", 0, 360);
        //animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(500);
        animator.setRepeatCount(-1);
        animator.setRepeatMode(ObjectAnimator.REVERSE);
        animator.start();

        Matrix matrix = new Matrix();
        matrix.postRotate(90f);
        String string = "";
        float[] values = new float[9];
        matrix.getValues(values);
        for (int i = 0; i < values.length; i++) {
            string += "matrix.at" + i + "=" + values[i];
        }
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        Log.d("TEST", string);

        initBanner();
        
    }

    private void initBanner() {
        List<String> images = new ArrayList<>();
        String image1 = "http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg";
        String image2 = "http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg";
        String image3 = "http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg";
        String image4 = "http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg";
        String image5 = "http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg";
        images.add(image1);
        images.add(image2);
        images.add(image3);
        images.add(image4);
        images.add(image5);
        binding.banner.setImageLoader(new GlideImageLoader());
        binding.banner.setImages(images);
        binding.banner.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_my_view:
                startActivity(new Intent(MainActivity.this, ViewActivity.class));
                break;
            case R.id.bt_recycler_view:
                startActivity(new Intent(MainActivity.this, RecyclerviewActivity.class));
                break;

            case R.id.bt_web_view:
                startActivity(new Intent(MainActivity.this, WebActivity.class));
                break;

            case R.id.bt_glide_image:
                startActivity(new Intent(MainActivity.this, GlideImageActivity.class));
                break;

            case R.id.bt_tab_layout:
                startActivity(new Intent(MainActivity.this, NestedHoverTabActivity.class));
                break;
            case R.id.bt_fold_recycler_view:
                startActivity(new Intent(MainActivity.this, FoldRecyclerViewActivity.class));
                break;
            case R.id.bt_app_webview:
                startActivity(new Intent(MainActivity.this, AppWebView.class));
                break;
            case R.id.bt_tencent_webview:
                startActivity(new Intent(MainActivity.this, TenWebview.class));
                break;
        }
    }
}

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
//        String image1 = "http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg";
//        String image2 = "http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg";
//        String image3 = "http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg";
//        String image4 = "http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg";
//        String image5 = "http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg";
//        images.add(image1);
//        images.add(image2);
//        images.add(image3);
//        images.add(image4);
//        images.add(image5);

        images.add("http://shp.qpic.cn/ishow/2735012115/1548055014_-695593207_29388_sProdImgNo_8.jpg/0");
        images.add("http://shp.qpic.cn/ishow/2735012117/1548061233_587358052_15546_sProdImgNo_7.jpg/0");
        images.add("http://shp.qpic.cn/ishow/2735012115/1548055591_1186005513_11229_sProdImgNo_2.jpg/0");
        images.add("http://shp.qpic.cn/ishow/2735012115/1548054970_-695593207_26969_sProdImgNo_8.jpg/0");
        images.add("http://shp.qpic.cn/ishow/2735012115/1548054919_1186005513_15008_sProdImgNo_8.jpg/0");
        images.add("http://shp.qpic.cn/ishow/2735012115/1548054648_1186005513_7543_sProdImgNo_8.jpg/0");
        images.add("http://shp.qpic.cn/ishow/2735012111/1548041716_587358052_31760_sProdImgNo_8.jpg/0");
        images.add("http://shp.qpic.cn/ishow/2735011818/1547806092_1186005513_25911_sProdImgNo_8.jpg/0");
        images.add("http://shp.qpic.cn/ishow/2735011818/1547806010_704174346_24775_sProdImgNo_8.jpg/0");
        images.add("http://shp.qpic.cn/ishow/2735011818/1547805713_-888937974_10947_sProdImgNo_8.jpg/0");
        images.add("http://shp.qpic.cn/ishow/2735011817/1547805561_1186005513_9534_sProdImgNo_8.jpg/0");
        images.add("http://shp.qpic.cn/ishow/2735011815/1547798220_-695593207_3376_sProdImgNo_8.jpg/0");
        images.add("http://shp.qpic.cn/ishow/2735011815/1547797630_587358052_19346_sProdImgNo_8.jpg/0");
        images.add("http://shp.qpic.cn/ishow/2735011815/1547797006_-695593207_2609_sProdImgNo_8.jpg/0");
        images.add("http://shp.qpic.cn/ishow/2735011814/1547791774_-695593207_20786_sProdImgNo_8.jpg/0");
        images.add("http://shp.qpic.cn/ishow/2735011814/1547791837_587358052_13686_sProdImgNo_8.jpg/0");
        images.add("http://shp.qpic.cn/ishow/2735011117/1547197926_-888937974_22336_sProdImgNo_8.jpg/0");
        images.add("http://shp.qpic.cn/ishow/2735011016/1547110612_-888937974_7699_sProdImgNo_8.jpg/0");
        images.add("http://shp.qpic.cn/ishow/2735010915/1547020516_704174346_3495_sProdImgNo_8.jpg/0");
        images.add("http://shp.qpic.cn/ishow/2735010816/1546935490_-888937974_8713_sProdImgNo_8.jpg/0");
        images.add("http://shp.qpic.cn/ishow/2735010915/1547020064_-888937974_25739_sProdImgNo_8.jpg/0");

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

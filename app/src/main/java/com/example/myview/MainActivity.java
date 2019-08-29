package com.example.myview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.myview.activity.AppWebView;
import com.example.myview.activity.FoldRecyclerViewActivity;
import com.example.myview.activity.GlideImageActivity;
import com.example.myview.activity.ImageLoadActivity;
import com.example.myview.activity.NestedHoverTabActivity;
import com.example.myview.activity.TenWebview;
import com.example.myview.databinding.ActivityMainBinding;
import com.example.myview.kotlin.FirstActivity;
import com.example.myview.kotlin.TreeActivity;
import com.noober.background.BackgroundLibrary;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BackgroundLibrary.inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.floatButton.setOnClickListener(this);
        binding.btMyView.setOnClickListener(this);
        binding.btRecyclerView.setOnClickListener(this);
        binding.btWebView.setOnClickListener(this);
        binding.btGlideImage.setOnClickListener(this);
        binding.btTabLayout.setOnClickListener(this);
        binding.btFoldRecyclerView.setOnClickListener(this);
        binding.btAppWebview.setOnClickListener(this);
        binding.btTencentWebview.setOnClickListener(this);
        binding.btKotlin.setOnClickListener(this);
        binding.btTree.setOnClickListener(this);

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
            string += "matrix.at" + i + "=" + values[i] + "\t";
        }
        //Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        Log.d(TAG, string);

        initBanner();
        
    }

    private void initBanner() {
        final List<String> images = new ArrayList<>();
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

        binding.banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(MainActivity.this, ImageLoadActivity.class);
                intent.putExtra("imageUrl", images.get(position));
                startActivity(intent);
            }
        });
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
            case R.id.bt_kotlin:
                startActivity(new Intent(MainActivity.this, FirstActivity.class));
                break;
            case R.id.bt_tree:
                startActivity(new Intent(MainActivity.this, TreeActivity.class));
                break;
            case R.id.float_button:
                Toast.makeText(this, "属性动画", Toast.LENGTH_SHORT).show();
                ObjectAnimator animator = ObjectAnimator.ofFloat(binding.floatButton, "translationX", 0, 360);
                //animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setDuration(500);
//                animator.setRepeatCount(-1);
//                animator.setRepeatMode(ObjectAnimator.REVERSE);
                animator.start();
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        Log.d(TAG, "onAnimationEnd: " + binding.floatButton.getX());
                    }
                });
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createWebPrintJob(WebView webView) {

        // Get a PrintManager instance
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);

        // Get a print adapter instance
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();

        // Create a print job with name and adapter instance
        String jobName = getString(R.string.app_name) + " Document";
        PrintJob printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());


        // Save the job object for later status checking
        //mPrintJobs.add(printJob);
    }
}

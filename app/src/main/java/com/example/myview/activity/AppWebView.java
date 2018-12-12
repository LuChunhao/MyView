package com.example.myview.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.myview.R;
import com.example.myview.databinding.ActivityAppWebviewBinding;

/**
 * Created by luchunhao on 2018/9/18.
 */

public class AppWebView extends AppCompatActivity {

    private static final String TAG = "AppWebView";

    private ActivityAppWebviewBinding binding;
    private WebSettings webSettings;
    private String webUrl = "https://m.tbdress.com/sales/stylish-boots-h5-3783.html?flag=1";
    private long currentTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_app_webview);
        initView();
    }

    private void initView() {
        binding.webViewProgressBar.setMax(100);
        Log.d(TAG, "initView: -------------------");
        currentTime = System.currentTimeMillis();
        webSettings = binding.appWebview.getSettings();
        binding.appWebview.loadUrl(webUrl);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
//        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片

        Bundle data = new Bundle();
        data.putBoolean("standardFullScreen", false);
        //true表示标准全屏，false表示X5全屏；不设置默认false，
        data.putBoolean("supportLiteWnd", false);
        //false：关闭小窗；true：开启小窗；不设置默认true，
        data.putInt("DefaultVideoScreen", 2);
        //1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
        //binding.appWebview.getX5WebViewExtension().invokeMiscMethod("setVideoParams", data);

        //设置不用系统浏览器打开,直接显示在当前Webview
        binding.appWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        binding.appWebview.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onReceivedTitle(WebView view, String title) {
                Log.d(TAG, "onReceivedTitle: ----------------" + (System.currentTimeMillis() - currentTime));
                binding.tvTitle.setText(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                binding.webViewProgressBar.setProgress(newProgress);
                if(newProgress == 100){
                    binding.webViewProgressBar.setVisibility(View.GONE);
                }else{
                    binding.webViewProgressBar.setVisibility(View.VISIBLE);
                }
            }


        });

        //设置WebViewClient类
        binding.appWebview.setWebViewClient(new WebViewClient() {
            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        //在 Activity 销毁（ WebView ）的时候，先让 WebView 加载null内容，然后移除 WebView，再销毁 WebView，最后置空。
        if(binding.appWebview != null){
            binding.appWebview.removeAllViews();
            ViewParent parent = binding.appWebview.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(binding.appWebview);
            }
            binding.appWebview.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            binding.appWebview.getSettings().setJavaScriptEnabled(false);
            binding.appWebview.setTag(null);
            binding.appWebview.clearHistory();
            binding.appWebview.destroy();
        }
        super.onDestroy();
    }

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && binding.appWebview.canGoBack()) {
            binding.appWebview.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}

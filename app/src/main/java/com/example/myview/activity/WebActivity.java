package com.example.myview.activity;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.myview.R;
import com.example.myview.databinding.ActivityWebViewBinding;

/**
 * Created by luchunhao on 2018/6/12.
 */

public class WebActivity extends AppCompatActivity {

    private ActivityWebViewBinding binding;
    String url = "https://m1.tbdress.com/topic/h5-anniversarysale/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view);

//        binding.webView.getSettings().setBuiltInZoomControls(true);
//        binding.webView.getSettings().setJavaScriptEnabled(true);
//        binding.webView.getSettings().setUseWideViewPort(true);
//        binding.webView.getSettings().setLoadWithOverviewMode(true);
//        binding.webView.getSettings().setCursiveFontFamily("GillSans");
        binding.webView.setWebViewClient(mWebViewClient);
        binding.webView.setWebChromeClient(webChromeClient);
        binding.webView.loadUrl(url);
    }

    private WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            Uri uri = Uri.parse(url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            //6.0以上执行
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            //6.0以下执行
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            binding.webView.setVisibility(View.VISIBLE);
        }
    };

    private WebChromeClient webChromeClient = new WebChromeClient() {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (!TextUtils.isEmpty(title) && title.contains("-")) {
                binding.tvWebTitle.setText(title.substring(0, title.indexOf("-")));
            }

            super.onReceivedTitle(view, title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            binding.pbProgressBar.setProgress(newProgress);
            if (newProgress == 100) {
                binding.pbProgressBar.setVisibility(View.GONE);
            } else {
                binding.pbProgressBar.setVisibility(View.VISIBLE);
            }
        }

    };
}

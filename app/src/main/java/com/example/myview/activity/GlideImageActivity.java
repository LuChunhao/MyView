package com.example.myview.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.myview.R;
import com.example.myview.databinding.ActivityGlideImageBinding;

/**
 * Created by luchunhao on 2018/7/5.
 */

public class GlideImageActivity extends AppCompatActivity {

    private ActivityGlideImageBinding binding;
    private String imageUrl = "https://simages.tbdress.com/Upload/Image/2015/40/680-510//94c90a66-f503-47c3-a32a-98d9470ca00f.jpg";
    private String imageUrl_0 = "https://simages.tbdress.com/Upload/Image/2015/40/90-120//94c90a66-f503-47c3-a32a-98d9470ca00f.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_glide_image);
        displayImage(binding.ivGlideImage);
    }


    void displayImage(final ImageView imageView) {
        DrawableRequestBuilder thumbnailBuilder = Glide
                .with(imageView.getContext())
                .load(imageUrl_0);
                //.skipMemoryCache(true)
                //.diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(this)
                .load(imageUrl)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .thumbnail(thumbnailBuilder)
                //.skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }

}

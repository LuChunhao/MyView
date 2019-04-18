package com.example.myview.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myview.R;
import com.example.myview.databinding.ActivityImageLoadBinding;

/**
 * 查看大图activity
 */
public class ImageLoadActivity extends AppCompatActivity {

    private ActivityImageLoadBinding binding;
    private String imageUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_load);

        if (null != getIntent() && getIntent().hasExtra("imageUrl")) {
            imageUrl = getIntent().getStringExtra("imageUrl");
            Glide.with(this)
                    .load(imageUrl)
                    .into(binding.image);
        }

    }
}

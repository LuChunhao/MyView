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

import com.example.myview.activity.WebActivity;
import com.example.myview.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        binding.btMyView.setOnClickListener(this);
        binding.btRecyclerView.setOnClickListener(this);
        binding.btWebView.setOnClickListener(this);

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_my_view:
                startActivity(new Intent(MainActivity.this,ViewActivity.class));
                break;
            case R.id.bt_recycler_view:
                startActivity(new Intent(MainActivity.this,RecyclerviewActivity.class));
                break;

            case R.id.bt_web_view:
                startActivity(new Intent(MainActivity.this,WebActivity.class));
                break;
        }
    }
}

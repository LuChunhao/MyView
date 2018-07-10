package com.example.myview.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.myview.R;

/**
 * Created by luchunhao on 2018/6/25.
 */

public class LoadingViewLayout extends LinearLayout {

    private ViewGroup parent;
    private View point1;
    private View point2;
    private View point3;

    private ObjectAnimator o1;
    private ObjectAnimator o2;
    private ObjectAnimator o3;

    private int duration = 1000;

    public LoadingViewLayout(Context context) {
        this(context, null);
    }

    public LoadingViewLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingViewLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        parent = (ViewGroup) LinearLayout.inflate(context, R.layout.loading, this);
        point1 = parent.findViewById(R.id.point1);
        point2 = parent.findViewById(R.id.point2);
        point3 = parent.findViewById(R.id.point3);

        o1 = ObjectAnimator.ofFloat(point1, "alpha", 1f, 0.5f, 0.1f);
        o1.setDuration(duration);
        o1.setRepeatCount(ValueAnimator.INFINITE);
        o1.start();

        o2 = ObjectAnimator.ofFloat(point2, "alpha", 0.5f, 1f, 0.5f);
        o2.setDuration(duration);
        o2.setRepeatCount(ValueAnimator.INFINITE);
        o2.start();

        o3 = ObjectAnimator.ofFloat(point3, "alpha", 0.1f, 0.5f, 1f);
        o3.setDuration(duration);
        o3.setRepeatCount(ValueAnimator.INFINITE);
        o3.start();
    }

}

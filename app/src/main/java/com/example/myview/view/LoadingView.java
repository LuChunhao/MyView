package com.example.myview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.myview.DensityUtils;
import com.example.myview.R;

/**
 * Created by luchunhao on 2018/6/25.
 */

public class LoadingView extends View {

    private static final String TAG = "LoadingView";

    private Paint mPaint;
    private int interval_side;  // 圆点间距
    private int point_radius;   // 圆点半径
    private int point_count;    // 圆点个数

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingView);
        try {
            interval_side = typedArray.getDimensionPixelSize(R.styleable.LoadingView_interval_side, DensityUtils.dp2px(context, 5));
            point_radius = typedArray.getDimensionPixelSize(R.styleable.LoadingView_point_radius, DensityUtils.dp2px(context, 4.5f));
            point_count = typedArray.getInteger(R.styleable.LoadingView_point_count, 3);
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //heightMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.parseColor("#000000"));
        int startX = (getMeasuredWidth() - point_radius * 2 * point_count - (point_count - 1) * interval_side) / 2 + point_radius;
        for (int i = 0; i < point_count; i++) {
            canvas.drawCircle(startX + (point_radius * 2 + interval_side) * i, getMeasuredHeight() / 2, point_radius, mPaint);
        }
        Log.d(TAG, "onDraw: interval_side==" + interval_side + "\tpoint_radius==" + point_radius);
        //canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, point_radius, mPaint);
        //canvas.drawCircle(getMeasuredWidth() / 2 - interval_side - point_radius * 2, getMeasuredHeight() / 2, point_radius, mPaint);
        //canvas.drawCircle(getMeasuredWidth() / 2 + interval_side + point_radius * 2, getMeasuredHeight() / 2, point_radius, mPaint);
    }

}

package com.example.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.myview.bean.DataBean;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luchunhao on 2018/9/4.
 */

public class ExpressTrackView extends View {

    private Paint mPaint;   // 默认
    private Paint textPaint;   // 粗体
    private int intervalCount;  // 点数
    private List<DataBean> mList = new ArrayList<>();

    private int SpacNum = DensityUtil.dp2px(68);   // 点与点间距
    private int marginLeft = DensityUtil.dp2px(46);    //线距离左边位置
    private int radius = 10;     // 原点直径

    Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/GillSans.ttf");

    public ExpressTrackView(Context context) {
        this(context, null);
    }

    public ExpressTrackView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpressTrackView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(1);
        textPaint = new Paint();

        // 模拟点数据
        DataBean bean1 = new DataBean();
        bean1.setTime("09:21 Apr.3");
        bean1.setName("Referral Code");
        mList.add(bean1);
        mList.add(bean1);
        mList.add(bean1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //widthMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        setMeasuredDimension(getMeasuredWidth(), DensityUtil.dp2px(15) * 2 + (mList.size() - 1) * DensityUtil.dp2px(68));

    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 划线
        drawLine(canvas);
        // 画点
        drawPoint(canvas);
    }

    // 底线
    private void drawLine(Canvas canvas) {
        mPaint.setColor(Color.parseColor("#FFCCCCCC"));
        canvas.drawLine(marginLeft, DensityUtil.dp2px(15), marginLeft, (mList.size() - 1) * SpacNum + DensityUtil.dp2px(15), mPaint);
    }

    private void drawPoint(Canvas canvas) {
        // 起点
        mPaint.setColor(Color.parseColor("#FF333333"));
        canvas.drawCircle(marginLeft, DensityUtil.dp2px(10), radius, mPaint);
        // 写字

        textPaint.setTextSize(DensityUtil.dp2px(14));
        textPaint.setTypeface(typeface);
        textPaint.setColor(Color.parseColor("#ff333333"));

        // 计算文字位置
        Rect bounds = new Rect();
        String startText = mList.get(0).getTime();
        mPaint.getTextBounds(startText, 0, startText.length(), bounds);
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();

        canvas.drawText(startText, 0, DensityUtil.dp2px(10), textPaint);

        Path path = new Path();
        RectF rectF = new RectF(0, 0, DensityUtil.dp2px(32), DensityUtil.dp2px(33));
        path.addOval(rectF, Path.Direction.CCW);
        canvas.drawPath(path, textPaint); // 把 Path 也绘制出来，理解起来更方便
        canvas.drawTextOnPath("Hello HeCoder", path, 0, 0, textPaint);

        // 下面的点
        mPaint.setColor(Color.parseColor("#FFCCCCCC"));
        for (int i = 1; i < mList.size(); i++) {
            canvas.drawCircle(marginLeft, DensityUtil.dp2px(10) + i * SpacNum, radius, mPaint);

            canvas.drawText(mList.get(i).getTime(), 0, DensityUtil.dp2px(10) + i * SpacNum, textPaint);
        }


    }
}

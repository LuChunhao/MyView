package com.example.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.VelocityTracker;
import android.view.View;

import com.example.myview.DensityUtils;

/**
 * Created by luchunhao on 2018/4/16.
 * 区间图
 */

public class IntervalView extends View {
    private static final String TAG = "IntervalView";

    private int intervalCount = 3;
    private Paint mPaint;
    private Paint newPaint;
    private float currentProgress = 50;

    private int color_unselect_text = Color.parseColor("#abaaaa");// 文字未到达的颜色
    private int color_select_text = Color.parseColor("#393940");  // 文字已到达的颜色

    public IntervalView(Context context) {
        this(context, null);
    }

    public IntervalView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IntervalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        newPaint = new Paint();
        //mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(DensityUtils.dp2px(getContext(), 2));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure: widthMeasureSpec===" + widthMeasureSpec + "\theightMeasureSpec===" + heightMeasureSpec);
//        widthMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(),MeasureSpec.AT_MOST);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(getPaddingLeft() + getPaddingRight(), MeasureSpec.EXACTLY);
        //MeasureSpec.getMode(heightMeasureSpec);
        Log.d(TAG, "onMeasure: heightMeasureSpec222===" + heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 画背景线
        drawBackgroundLine(canvas);
        // 绘制进度线
        drawProgressLine(canvas);
        // 绘制点
        drawPointCount(canvas);

    }

    // 获取绘制线的起点X
    private float getLineStartX() {
        return getPaddingLeft();
    }

    // 得到绘制线的终点X
    private float getLineEndX() {
        return getMeasuredWidth() - getPaddingRight();
    }

    /**
     * 绘制点
     *
     * @param canvas
     */
    private void drawPointCount(Canvas canvas) {
        // 绘制起点
        newPaint.setColor(Color.BLACK);
        newPaint.setStrokeWidth(DensityUtils.dp2px(getContext(), 3));
        canvas.drawPoint(getLineStartX(), getLineStartX(), newPaint);
        drawTopText(canvas, "start", getLineStartX(), getLineStartX(), true, -1);
        drawBottomText(canvas, "start", getLineStartX(), getLineStartX(), true, -1);

        // 绘制中间的点
        for (int i = 1; i <= intervalCount; i++) {
            boolean isPassed = (100 / (intervalCount + 1) * i) > currentProgress ? false : true;
            canvas.drawPoint(getLineStartX() + ((getLineEndX() - getLineStartX()) / (intervalCount + 1)) * i, getLineStartX(), newPaint);
            drawTopText(canvas, "start" + i, getLineStartX() + ((getLineEndX() - getLineStartX()) / (intervalCount + 1)) * i, getLineStartX(), isPassed, 0);
            drawBottomText(canvas, "start" + i, getLineStartX() + ((getLineEndX() - getLineStartX()) / (intervalCount + 1)) * i, getLineStartX(), isPassed, 0);
        }

        // 绘制终点
        canvas.drawPoint(getLineEndX(), getLineStartX(), newPaint);
        drawTopText(canvas, "end", getLineEndX(), getLineStartX(), false, 1);
        drawBottomText(canvas, "end", getLineEndX(), getLineStartX(), false, 1);
    }

    /**
     * 绘制进度线
     *
     * @param canvas
     */
    private void drawProgressLine(Canvas canvas) {
        mPaint.setColor(Color.parseColor("#f44c4d"));
        canvas.drawLine(getLineStartX(), getLineStartX(),getLineStartX() + ((getLineEndX() - getLineStartX()) * (currentProgress / 100)), getLineStartX(), mPaint);
    }

    /**
     * 绘制背景线
     *
     * @param canvas
     */
    private void drawBackgroundLine(Canvas canvas) {
        mPaint.setColor(Color.parseColor("#eaeaea"));
        canvas.drawLine(getLineStartX(), getLineStartX(), getLineEndX(), getLineStartX(), mPaint);
    }

    /**
     * @param canvas
     * @param text
     * @param x        开始位置
     * @param y        结束位置
     * @param isPassed 是否已经过了该区间
     * @param type     -1 开始位置，0中间位置，1结尾位置
     */
    private void drawTopText(Canvas canvas, String text, float x, float y, boolean isPassed, int type) {
        mPaint.setTextSize(DensityUtils.dp2px(getContext(), 12));
        mPaint.setColor(isPassed ? color_select_text : color_unselect_text);
        mPaint.setTextAlign(Paint.Align.LEFT);

        // 计算文字位置
        Rect bounds = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), bounds);
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseLine = (fontMetrics.top + fontMetrics.bottom) / 2 - fontMetrics.top;

        float textX = 0;
        switch (type) {
            case -1:    // 开始文字,放在屏幕与第一个点中间位置
                textX = x / 2;
                break;
            case 0:     // 中间的文字
                textX = x - (bounds.right / 2);
                break;
            case 1:     // 结束的文字
                textX = getMeasuredWidth() - (getMeasuredWidth() - x) / 2 - bounds.right;
                break;
        }
        canvas.drawText(text, textX, y - baseLine, mPaint);
    }

    /**
     * @param canvas
     * @param text
     * @param x        开始位置
     * @param y        结束位置
     * @param isPassed 是否已经过了该区间
     * @param type     -1 开始位置，0中间位置，1结尾位置
     */
    private void drawBottomText(Canvas canvas, String text, float x, float y, boolean isPassed, int type) {
        mPaint.setTextSize(DensityUtils.dp2px(getContext(), 12));
        mPaint.setColor(isPassed ? color_select_text : color_unselect_text);
        mPaint.setTextAlign(Paint.Align.LEFT);

        // 计算文字位置
        Rect bounds = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), bounds);
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseLine = (fontMetrics.top + fontMetrics.bottom) / 2 - fontMetrics.top;

        float textX = 0;
        switch (type) {
            case -1:    // 开始文字,放在屏幕与第一个点中间位置
                textX = x / 2;
                break;
            case 0:     // 中间的文字
                textX = x - (bounds.right / 2);
                break;
            case 1:     // 结束的文字
                textX = getMeasuredWidth() - (getMeasuredWidth() - x) / 2 - bounds.right;
                break;
        }
        canvas.drawText(text, textX, y + baseLine * 2, mPaint);
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
        invalidate();
    }
}

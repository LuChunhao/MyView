package com.example.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.myview.DensityUtils;
import com.example.myview.bean.DataBean;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * Created by luchunhao on 2018/4/24.
 * 柱形图
 */

public class HistogramView extends View {

    private int width;  // 控件宽度
    private int height; // 控件高度
    private int moveWidth; // 控件可滑动的总宽度
    private Paint mBgPaint;
    private Paint mHistogramBgPaint;
    private Paint mHistogramPaint;
    private Paint mTextPaint;
    private HashMap<Integer, DataBean> dataTotal;
    private int lastX;
    private int lastY;


    public HistogramView(Context context) {
        this(context, null);
    }

    public HistogramView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHistogramBgPaint = new Paint();
        mHistogramPaint = new Paint();
        mTextPaint = new Paint();
        mBgPaint = new Paint();
        dataTotal = new HashMap<>();
    }


    public void setPaints(int bgColor, int histogramBgColor, int histogramColor, int textColor) {
        mBgPaint.setColor(bgColor);
        mBgPaint.setStyle(Paint.Style.FILL);
        mHistogramBgPaint.setColor(histogramBgColor);
        mHistogramBgPaint.setStyle(Paint.Style.FILL);
        mHistogramPaint.setColor(histogramColor);
        mHistogramPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(DensityUtils.dp2px(getContext(), 9));
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        invalidate();
    }

    public void setDataTotal(HashMap dataTotal) {
        this.dataTotal = dataTotal;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (dataTotal != null) {
            width = dataTotal.size() * DensityUtils.dp2px(getContext(), 40);
        }
        height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        moveWidth = width > DensityUtils.getScreenWidth(getContext()) ? width - DensityUtils.getScreenWidth(getContext()) : 0;
        setMeasuredDimension(width, height);
    }

    private int mHistogramHeight;
    int startX = DensityUtils.dp2px(getContext(), 20);
    int startY = DensityUtils.dp2px(getContext(), 20);
    int radius = 45;
    int greyValueAlignLeft = DensityUtils.dp2px(getContext(), 5);
    int nameAlignLeft = DensityUtils.dp2px(getContext(), 15);
    int greyValueAlignBottom = DensityUtils.dp2px(getContext(), 5);
    int nameAlignBottom = DensityUtils.dp2px(getContext(), 15);
    int histogramWidth = DensityUtils.dp2px(getContext(), 8);
    int xAddedNum = DensityUtils.dp2px(getContext(), 40);
    int histogramAlignTop = DensityUtils.dp2px(getContext(), 2);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mHistogramHeight = height - DensityUtils.dp2px(getContext(), 40);
        canvas.drawRect(0, startY, width, mHistogramHeight, mBgPaint);
        for (int key : dataTotal.keySet()) {
            String name = dataTotal.get(key).getName();
            int greyValue = dataTotal.get(key).getValue();
            canvas.drawText(greyValue + "%", startX + greyValueAlignLeft, startY - greyValueAlignBottom, mTextPaint);
            canvas.drawRect(startX, startY + histogramAlignTop, startX + histogramWidth, mHistogramHeight, mHistogramBgPaint);
            canvas.drawRect(startX, startY + (1f - greyValue / 100f) * (mHistogramHeight - startY - histogramAlignTop) + histogramAlignTop, startX + histogramWidth, mHistogramHeight, mHistogramPaint);
            canvas.save();
            canvas.rotate(-radius, startX, height);
            canvas.drawText(name, startX + nameAlignLeft, height - nameAlignBottom, mTextPaint);
            canvas.restore();
            startX += xAddedNum;
        }
        startX = DensityUtils.dp2px(getContext(), 20);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //计算移动的距离
                int offX = (x - lastX) / 5;
                int offY = y - lastY;
                Log.d(TAG, "ACTION_MOVE: offX=" + offX + "\tx=" + x + "\tmoveWidth=" + moveWidth + "\tgetScrollX=" + getScrollX());
                if (moveWidth > 0) {
                    if (offX < 0) { // 向左滑动
                        if (getScrollX() < moveWidth) {
                            scrollBy(-offX, 0);
                        }
                    } else { // 向右滑动
                        if (getScrollX() > 0) {
                            scrollBy(-offX, 0);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (getScrollX() < 0) {
                    scrollTo(0, 0);
                } else if (getScrollX() > moveWidth) {
                    scrollTo(moveWidth, 0);
                }
                break;

        }
        return true;
    }
}

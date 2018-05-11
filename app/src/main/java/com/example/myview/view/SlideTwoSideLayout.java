package com.example.myview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.example.myview.DensityUtils;
import com.example.myview.R;

/**
 * Created by luchunhao on 2018/4/17.
 * 可以左右滑动的view，边界有点问题
 */

public class SlideTwoSideLayout extends ViewGroup {

    private static final String TAG = "SlideTwoSideLayout";

    private int contentId;
    private int leftMenuId;
    private int rightMenuId;

    private View leftMenuView;
    private View rightMenuView;
    private View contentView;

    /**
     * 用于完成滚动操作的实例
     */
    private Scroller scroller;

    /**
     * 判定为拖动的最小移动像素数
     */
    private int mTouchSlop;

    /**
     * 手指按下时的屏幕坐标
     */
    private float mXDown;

    /**
     * 手指滑动时的屏幕坐标
     */
    private float mXMove;

    /**
     * 上次触发ACTION_MOVE事件时的屏幕坐标
     */
    private float mXLastMove;

    private int scrolledX;

    private int width = DensityUtils.dp2px(getContext(), 100);      //左右menu初始化宽度，单位dp

    public SlideTwoSideLayout(Context context) {
        this(context, null);
    }

    public SlideTwoSideLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideTwoSideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);

        scroller = new Scroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        // 获取TouchSlop值
        mTouchSlop = configuration.getScaledTouchSlop();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlideTwoSideLayout);
        contentId = typedArray.getResourceId(R.styleable.SlideTwoSideLayout_content_id, 0);
        leftMenuId = typedArray.getResourceId(R.styleable.SlideTwoSideLayout_left_menu_id, 0);
        rightMenuId = typedArray.getResourceId(R.styleable.SlideTwoSideLayout_right_menu_id, 0);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setClickable(true);
        int viewWidth = 0;
        int viewHeight = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childView.setClickable(true);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            viewWidth += childView.getMeasuredWidth();
            viewHeight = Math.max(viewHeight, childView.getMeasuredHeight());
        }
        setMeasuredDimension(viewWidth, viewHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // left==0top==482right==2140bottom==832
        Log.d(TAG, "onLayout: left==" + l + "top==" + t + "right==" + r + "bottom==" + b);
        int childCount = getChildCount();
        // 给各个view赋值
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (leftMenuView == null && childView.getId() == leftMenuId) {
                leftMenuView = childView;
                continue;
            }
            if (rightMenuView == null && childView.getId() == rightMenuId) {
                rightMenuView = childView;
                continue;
            }
            if (contentView == null && childView.getId() == contentId) {
                contentView = childView;
                continue;
            }
        }

        // 开始摆放各个view
        if (leftMenuView != null) {
            leftMenuView.layout(-leftMenuView.getMeasuredWidth(), 0, 0, leftMenuView.getMeasuredHeight());
        }

        if (contentView != null) {
            contentView.layout(0, 0, contentView.getMeasuredWidth(), contentView.getMeasuredHeight());
        }

        if (rightMenuView != null && contentView != null) {
            rightMenuView.layout(contentView.getMeasuredWidth(), 0, contentView.getMeasuredWidth() + rightMenuView.getMeasuredWidth(), rightMenuView.getMeasuredHeight());
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXDown = ev.getX();
                mXLastMove = mXDown;
                break;
            case MotionEvent.ACTION_MOVE:
                mXMove = ev.getX();
                float diff = Math.abs(mXMove - mXDown);
                // 当手指拖动值大于TouchSlop值时，认为应该进行滚动，拦截子控件的事件
                if (diff > mTouchSlop) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXDown = event.getX();
                mXLastMove = mXDown;
                break;
            case MotionEvent.ACTION_MOVE:
                mXMove = event.getX();
                scrolledX = (int) (mXLastMove - mXMove);
                Log.d(TAG, "getScrollX()===" + getScrollX() + "\tscrolledX===" + scrolledX + "\twidth===" + width + "\tMeasuredWidth===" + rightMenuView.getMeasuredWidth());
                if (Math.abs(getScrollX()) <= width) {
                    Log.d(TAG,"????????");
                    scrollBy(scrolledX,0);
                }
                mXLastMove = mXMove;
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "ACTION_UP---getScrollX()===" + getScrollX());
                Log.d(TAG, "ACTION_UP---move===" + (width - getScrollX()));
                if (getScrollX() > 0) { // 向左滑动,滑出 rightMenuView
                    if (rightMenuView != null) {
                        if (scrolledX > 0) {
                            scroller.startScroll(getScrollX(), 0,  width - getScrollX(), 0);
                        } else if (scrolledX < 0){
                            scrollTo(0,0);
                        }
                    }
                } else if (getScrollX() < 0){
                    if (leftMenuView != null) {
                        if (scrolledX < 0) {
                            scroller.startScroll(getScrollX(), 0, -getScrollX() - width, 0);
                        } else if (scrolledX > 0){
                            scrollTo(0,0);
                        }
                    }
                }
                invalidate();
                break;
        }

        return super.onTouchEvent(event);
    }

    // 2. 重写 computeScroll()
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {  // 动画没有结束
            Log.d(TAG, "computeScroll===" + scroller.getCurrX());
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged: w=" + w + "\th=" + h + "\toldw=" + oldw + "\toldh=" + oldh);
    }
}

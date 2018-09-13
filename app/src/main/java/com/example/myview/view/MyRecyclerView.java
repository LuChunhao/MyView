package com.example.myview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by luchunhao on 2018/9/13.
 */

public class MyRecyclerView extends RecyclerView {
    public MyRecyclerView(Context context) {
        this(context, null);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mTouchSlop = viewConfiguration.getScaledTouchSlop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
        //return false;
    }

    float startX;
    float startY;
    float mTouchSlop;
    private int downX, downY;

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        int action = ev.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                startY = ev.getY();
//                startX = ev.getX();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float endY = ev.getY();
//                float endX = ev.getX();
//                float distanceX = Math.abs(endX - startX);
//                float distanceY = Math.abs(endY - startY);
//
//                if (distanceX > mTouchSlop && distanceX > distanceY) {
//                    return false;
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                break;
//        }
//        //return super.onInterceptTouchEvent(ev);
//        return false;
//    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getRawX();
                downY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getRawY();
                // 判断是否滑动，若滑动就拦截事件
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
                break;
            default:
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    //    @Override
//    public boolean onTouchEvent(MotionEvent e) {
//        switch (e.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                getParent().requestDisallowInterceptTouchEvent(true);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                getParent().requestDisallowInterceptTouchEvent(true);
//                break;
//            case MotionEvent.ACTION_UP:
//
//                break;
//        }
//        return super.onTouchEvent(e);
//    }
}

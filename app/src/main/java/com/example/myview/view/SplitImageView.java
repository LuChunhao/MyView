package com.example.myview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

/**
 * Created by luchunhao on 2018/11/7.
 */

public class SplitImageView extends AppCompatImageView {

    private static final String TAG = "SplitImageView";
    private int selfWeight;
    private int selfHeight;

    private OnWhereClickListener mListener;
    private List<String> urlList;   // 跳转的url集合
    private int urlCount;           // 后台配置的url的数量
    private Orientation orientation;        // 平分方向,默认左右平分

    public enum Orientation{
        Vertical,   // 左右平分
        Horizontal  // 左右平分
    }

    public SplitImageView(Context context) {
        this(context, null);
    }

    public SplitImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SplitImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {

    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }


    /**
     * 设置点击事件
     * @param mListener
     */
    public void setOnWhereClickListener(OnWhereClickListener mListener) {
        this.mListener = mListener;
    }

    /**
     * 设置跳转链接集合
     * @param urlList
     */
    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
        if (this.urlList != null) {
            urlCount = this.urlList.size();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        selfWeight = getMeasuredWidth();
        selfHeight = getMeasuredHeight();
        Log.d(TAG, "onMeasure: selfWeight===" + selfWeight + "\tselfHeight===" + selfHeight);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "ACTION_DOWN: ");
                break;

            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "ACTION_MOVE: ");
                break;

            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                int x = (int) event.getX();
                int y = (int) event.getY();
                Log.d(TAG, "onTouchEvent: x===" + x + "\ty===" + y);
                if (null != mListener && urlCount > 0) {    // 满足条件才允许点击
                    for (int i = 0; i < urlCount; i++) {
                        if (null != orientation && orientation == Orientation.Vertical) {
                            if (y <= (selfHeight / urlCount) * (i + 1) && y >= (selfHeight / urlCount) * i) {
                                mListener.onClick(this, urlList.get(i));
                            }
                        } else {
                            if (x <= (selfWeight / urlCount) * (i + 1) && x >= (selfWeight / urlCount) * i) {
                                mListener.onClick(this, urlList.get(i));
                            }
                        }

                    }

                }
                break;
        }
        return true;
    }

    public interface OnWhereClickListener {
        void onClick(View v, String url);
    }
}

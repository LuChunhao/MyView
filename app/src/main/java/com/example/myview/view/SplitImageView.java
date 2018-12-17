package com.example.myview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.myview.bean.HotAreaListBean;

import java.util.List;

/**
 * Created by luchunhao on 2018/11/7.
 */

public class SplitImageView extends AppCompatImageView {

    private static final String TAG = "SplitImageView";
    private int selfWeight;     // 控件宽度
    private int selfHeight;     // 控件高度

    private OnWhereClickListener mListener;
    private int urlCount;           // 后台配置的url的数量
    private Orientation orientation;        // 平分方向,默认左右平分
    private SplitType splitType = SplitType.ORIENTATION;            // 点击类型，均分or坐标
    private int limit;  // 限制的平分数量
    private List<HotAreaListBean> hotAreaList;
    private int imageWidth;     // 图片宽度
    private int imageHeight;    // 图片高度

    public enum Orientation {
        VERTICAL,   // 左右平分
        HORIZONTAL  // 左右平分
    }

    public enum SplitType {
        ORIENTATION,    // 均分
        COORDINATE      // 坐标
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

    /**
     * 设置原始图片宽高
     *
     * @param imageWidth
     * @param imageHeight
     */
    public void setImageWidth(int imageWidth, int imageHeight) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    /**
     * 设置均分方向，水平方向均分、竖直方向均分
     *
     * @param orientation
     */
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    /**
     * 设置点击类型，均分or坐标
     *
     * @param type
     */
    public void setSplitType(SplitType type) {
        this.splitType = type;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * 设置点击事件
     *
     * @param mListener
     */
    public void setOnWhereClickListener(OnWhereClickListener mListener) {
        this.mListener = mListener;
    }

    /**
     * 设置跳转链接集合
     *
     * @param list
     */
    public void setUrlList(List<HotAreaListBean> list) {
        if (null == list) {
            return;
        }
        this.hotAreaList = list;
        urlCount = list.size();
        if (urlCount > limit && limit > 0) { // 限制只能配置三个
            urlCount = limit;
        }
    }

    public void setUrlList(String url) {
        urlCount = 1;
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
                if (null != mListener) {    // 满足条件才允许点击
                    if (splitType == SplitType.ORIENTATION && urlCount > 0) {   // 均分
                        for (int i = 0; i < urlCount; i++) {
                            if (null != orientation && orientation == Orientation.VERTICAL) {
                                if (y <= (selfHeight / urlCount) * (i + 1) && y >= (selfHeight / urlCount) * i) {
                                    mListener.onClick(this, hotAreaList.get(i).getHref());
                                }
                            } else {
                                if (x <= (selfWeight / urlCount) * (i + 1) && x >= (selfWeight / urlCount) * i) {
                                    mListener.onClick(this, hotAreaList.get(i).getHref());
                                }
                            }
                        }
                    } else if (splitType == SplitType.COORDINATE) { // 按坐标区域点击
                        if (null != hotAreaList && hotAreaList.size() > 0 && imageWidth > 0 && imageHeight > 0) {
                            try {
                                double ratioX = (double) selfWeight / imageWidth;
                                double ratioY = (double) selfHeight / imageHeight;
                                for (HotAreaListBean bean : hotAreaList) {
                                    String coords = bean.getCoords();
                                    String xy[] = coords.split(",");
                                    int startX = (int) (Integer.parseInt(xy[0]) * ratioX);
                                    int startY = (int) (Integer.parseInt(xy[1]) * ratioY);
                                    int endX = (int) (Integer.parseInt(xy[2]) * ratioX);
                                    int endY = (int) (Integer.parseInt(xy[3]) * ratioY);
                                    if (xy.length == 4) {
                                        if (x >= startX && x < endX && y >= startY && y < endY) { // 点击位置在热区范围内
                                            mListener.onClick(this, bean.getHref());
                                        }
                                    }
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
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

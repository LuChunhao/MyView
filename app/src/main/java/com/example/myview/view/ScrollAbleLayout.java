package com.example.myview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class ScrollAbleLayout extends LinearLayout {

    private final float DAMPING = 0.7f;

    private float mDownX;
    private float mDownY;
    private float mLastY;

    private int minY = 0;
    private int maxY = 0;
    private int mHeadHeight;
    private int mExpandHeight;
    private int mTouchSlop;
    private int mMinimumVelocity;
    private int mMaximumVelocity;
    // 方向
    private DIRECTION mDirection;
    private int mCurY;
    private int mLastScrollerY;
    private boolean needCheckUpdown;
    private boolean updown;//暂定：需上下滑的场景
    private boolean mDisallowIntercept;
    private boolean isClickHead;
    private boolean isClickHeadExpand;//点击的部位是否是header

    private View mHeadView;
    private ViewPager childViewPager;

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;


    private boolean hasMove = false;//单次触摸事件（从按下到离开）是否触发了上下滚动

    /**
     * 滑动方向 *
     */
    public enum DIRECTION {
        UP,  // 向上划
        DOWN // 向下划
    }


    public interface OnScrollListener {
        void onScroll(int currentY, int maxY);
    }

    // 滑动位置的监听
    private OnScrollListener onScrollListener;

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    // 滑动方向的监听
    private OnScrollDirectionListener directionListener;

    public interface OnScrollDirectionListener {
        void onScrollDirection(DIRECTION direction);
    }

    public void setOnScrollDirectionListener(OnScrollDirectionListener directionListener) {
        this.directionListener = directionListener;
    }

    private HeaderScrollHelper mHelper;

    public HeaderScrollHelper getHelper() {
        return mHelper;
    }

    public ScrollAbleLayout(Context context) {
        super(context);
        init(context);
    }

    public ScrollAbleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ScrollAbleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScrollAbleLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mHelper = new HeaderScrollHelper();
        mScroller = new Scroller(context);
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
    }

    /**
     * 获取当前视图是否已滚动到最下方
     *
     * @return
     */
    public boolean isSticked() {
        return mCurY == maxY;
    }

    /**
     * 扩大头部点击滑动范围
     *
     * @param expandHeight
     */
    public void setClickHeadExpand(int expandHeight) {
        mExpandHeight = expandHeight;
    }

    public int getMaxY() {
        return maxY;
    }

    public boolean isHeadTop() {
        return mCurY == minY;
    }

    public boolean canPtr() {
        return updown && mCurY == minY && mHelper.isTop();//canptr
    }

    public void requestScrollableLayoutDisallowInterceptTouchEvent(boolean disallowIntercept) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
        mDisallowIntercept = disallowIntercept;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float currentX = ev.getX();
        float currentY = ev.getY();
        float deltaY;
        int shiftX = (int) Math.abs(currentX - mDownX);
        int shiftY = (int) Math.abs(currentY - mDownY);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDisallowIntercept = false;
                needCheckUpdown = true;
                updown = true;
                mDownX = currentX;
                mDownY = currentY;
                mLastY = currentY;
                checkIsClickHead((int) currentY, mHeadHeight, getScrollY());
                checkIsClickHeadExpand((int) currentY, mHeadHeight, getScrollY());
                initOrResetVelocityTracker();
                mVelocityTracker.addMovement(ev);
                mScroller.forceFinished(true);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mDisallowIntercept) {
                    break;
                }

                initVelocityTrackerIfNotExists();
                mVelocityTracker.addMovement(ev);
                /*
                deltaY小于0说明在向上滑，需要判断里面嵌套的可滑动控件是否已经滑动到顶部，
                如果没有滑动到顶部，则优先滑动该子控件，最后才滑动自身
                */
                deltaY = mLastY - currentY;
                if (needCheckUpdown) {
                    if (shiftX > mTouchSlop && shiftX > shiftY) { // 左右滑动
                        needCheckUpdown = false;
                        updown = false;
                    } else if (shiftY > mTouchSlop && shiftY > shiftX) { //　上下滑动
                        needCheckUpdown = false;
                        updown = true;
                    }
                }
                mLastY = currentY;

                if (deltaY < 0) {
                    if (updown && shiftY > mTouchSlop && shiftY > shiftX &&
                            (mHelper.isTop() || isClickHeadExpand)) {
                        if (childViewPager != null) {
                            childViewPager.requestDisallowInterceptTouchEvent(true);
                        }

                        scrollBy(0, (int) (deltaY + 0.5));
                        hasMove = true;

                        //bannerView.getViewPager().setScrollable(false);
                    }
                } else if (updown && shiftY > mTouchSlop && shiftY > shiftX &&
                        (!isSticked() || mHelper.isTop() || isClickHeadExpand)) {
                    if (childViewPager != null) {
                        childViewPager.requestDisallowInterceptTouchEvent(true);
                    }

                    scrollBy(0, (int) (deltaY + 0.5));
                    hasMove = true;

                    //bannerView.getViewPager().setScrollable(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                hasMove = false;
                //bannerView.getViewPager().setScrollable(true);

                if (updown && shiftY > shiftX && shiftY > mTouchSlop) {
                    mVelocityTracker.computeCurrentVelocity((int) (1000 * DAMPING), mMaximumVelocity);
                    float yVelocity = -mVelocityTracker.getYVelocity() * DAMPING;
                    boolean dislowChild = false;
                    if (Math.abs(yVelocity) > mMinimumVelocity) {
                        mDirection = yVelocity > 0 ? DIRECTION.UP : DIRECTION.DOWN;
                        // 滑动方向
                        if (directionListener != null) {
                            directionListener.onScrollDirection(mDirection);
                        }
                        if ((mDirection == DIRECTION.UP && isSticked()) || (!isSticked() && getScrollY() == 0 && mDirection == DIRECTION.DOWN)) {
                            dislowChild = true;
                        } else {
                            mScroller.fling(0, getScrollY(), 0, (int) yVelocity, 0, 0, -Integer.MAX_VALUE, Integer.MAX_VALUE);
                            mScroller.computeScrollOffset();
                            mLastScrollerY = getScrollY();
                            invalidate();
                        }
                    }
                    if (!dislowChild && (isClickHead || !isSticked())) {
                        int action = ev.getAction();
                        ev.setAction(MotionEvent.ACTION_CANCEL);
                        boolean dispathResult = super.dispatchTouchEvent(ev);
                        ev.setAction(action);
                        return dispathResult;
                    }
                }
                //bannerView.getViewPager().setScrollable(true);
                break;
            case MotionEvent.ACTION_CANCEL:
                //bannerView.getViewPager().setScrollable(true);
                break;
            default:
                break;
        }
        super.dispatchTouchEvent(ev);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (hasMove) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private int getScrollerVelocity(int distance, int duration) {
        if (mScroller == null) {
            return 0;
        } else {
            return (int) mScroller.getCurrVelocity();
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            final int currY = mScroller.getCurrY();
            if (mDirection == DIRECTION.UP) {
                // 手势向上划
                if (isSticked()) {
                    int distance = mScroller.getFinalY() - currY;
                    int duration = calcDuration(mScroller.getDuration(), mScroller.timePassed());
                    mHelper.smoothScrollBy((int) (getScrollerVelocity(distance, duration) * (1 + DAMPING / 2)), (distance), duration);// 当滚动到底部后为了衔接继续滚动里面可以滚动的列表
                    mScroller.forceFinished(true);
                    return;
                } else {
                    scrollTo(0, currY);
                }
            } else {
                // 手势向下划
                if (mHelper.isTop() || isClickHeadExpand) {
                    int deltaY = (currY - mLastScrollerY);
                    int toY = getScrollY() + deltaY;
                    scrollTo(0, toY);
                    if (mCurY <= minY) {
                        mScroller.forceFinished(true);
                        return;
                    }
                }
                invalidate();
            }
            mLastScrollerY = currY;
        }
    }


    @Override
    public void scrollBy(int x, int y) {
        int scrollY = getScrollY();
        int toY = scrollY + y;
        if (toY >= maxY) {
            toY = maxY;
        } else if (toY <= minY) {
            toY = minY;
        }
        y = toY - scrollY;
        super.scrollBy(x, y);
    }

    /**
     * 整个控件的高度就是展示在屏幕上的高度加上header控件的高度，所以最多只能向下滑动header的高度
     *
     * @param x //因为不能左右滑，所以始终未0
     * @param y
     */
    @Override
    public void scrollTo(int x, int y) {
        if (y >= maxY) {
            y = maxY;
        } else if (y <= minY) {
            y = minY;
        }
        mCurY = y;
        if (onScrollListener != null) {
            onScrollListener.onScroll(y, maxY);//将该控件的滑动事件回调给监听者，同时将header（滑动的范围）传递出去
        }
        super.scrollTo(x, y);
    }

    private void initOrResetVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        } else {
            mVelocityTracker.clear();
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private void checkIsClickHead(int downY, int headHeight, int scrollY) {
        isClickHead = downY + scrollY <= headHeight;
    }

    private void checkIsClickHeadExpand(int downY, int headHeight, int scrollY) {
        if (mExpandHeight <= 0) {
            isClickHeadExpand = false;
        }
        isClickHeadExpand = downY + scrollY <= headHeight + mExpandHeight;
    }

    private int calcDuration(int duration, int timepass) {
        return duration - timepass;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mHeadView = getChildAt(0);
        measureChildWithMargins(mHeadView, widthMeasureSpec, 0, MeasureSpec.UNSPECIFIED, 0);
        maxY = mHeadView.getMeasuredHeight();
        mHeadHeight = mHeadView.getMeasuredHeight();
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec) + maxY, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onFinishInflate() {
        if (mHeadView != null && !mHeadView.isClickable()) {
            mHeadView.setClickable(true);
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt != null && childAt instanceof ViewPager) {
                childViewPager = (ViewPager) childAt;
            }
        }

        //bannerView = (TlzBannerView) this.findViewById(R.id.tbv_banner_homeFragment);

        super.onFinishInflate();
    }

    public void setCurrentScrollableContainer(HeaderScrollHelper.ScrollableContainer scrollableContainer) {
        mHelper.setCurrentScrollableContainer(scrollableContainer);
    }


    public boolean dispatchTouchEventSupper(MotionEvent e) {
        return super.dispatchTouchEvent(e);
    }

}

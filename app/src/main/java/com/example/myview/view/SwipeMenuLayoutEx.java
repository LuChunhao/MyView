package com.example.myview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.example.myview.DensityUtils;
import com.example.myview.R;

/**
 * Created by luchunhao on 2018/4/17.
 */

public class SwipeMenuLayoutEx extends ViewGroup {
    private static final String TAG = "SwipeMenuLayout";

    private OnLeftSlideListener leftSlideListener;
    private OnRightSlideListener rightSlideListener;

    private Scroller mScroller;

    private int mScaledTouchSlop;

    private int leftMenuId;

    private int rightMenuId;

    private View leftMenuView;

    private View rightMenuView;

    private View contentView;

    private int contentId;

    private boolean isSwipeing;

    // 静态类写入内存共享。用来判断当前界面是否有menu打开
    private static SwipeMenuLayoutEx swipeMenuLayout;

    private static State curState;

    private static boolean isTouching = false;

    private static int width = 70;      //左右menu初始化宽度，单位dp

    //关闭
    public static void smoothClose() {

        if (swipeMenuLayout != null) {
            // 调用已经打开的那个 SwipeMenuLayout 关闭方法
            swipeMenuLayout.handleSwipeMenu(State.CLOSE);
        }


    }

    public boolean isFavorite = false;  //购物车滑动时使用，判断当前商品是否已收藏

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    //设置左右滑是否打开
    private boolean isSwipeEnable;

    public boolean isSwipeEnable() {
        return isSwipeEnable;
    }

    public void setSwipeEnable(boolean swipeEnable) {
        isSwipeEnable = swipeEnable;
    }

    public SwipeMenuLayoutEx(Context context) {
        this(context, null);
    }

    public SwipeMenuLayoutEx(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeMenuLayoutEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttrs(context, attrs);

        isSwipeEnable = true;//设置左右滑功能默认为开

        // 1.创建 Scroller 对象
        mScroller = new Scroller(context);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mScaledTouchSlop = viewConfiguration.getScaledTouchSlop();
    }

    private void readAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwipeMenuLayout);
        try {
            leftMenuId = typedArray.getResourceId(R.styleable.SwipeMenuLayout_leftMenuId, 0);
            rightMenuId = typedArray.getResourceId(R.styleable.SwipeMenuLayout_rightMenuId, 0);
            contentId = typedArray.getResourceId(R.styleable.SwipeMenuLayout_contentId, 0);
        } finally {
            typedArray.recycle();

        }
    }

    /**
     * 测量方法可能会被调用多次
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setClickable(true);
        int viewHeight = 0;
        int viewWidth = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childView.setClickable(true);
            if (childView.getVisibility() == View.GONE) {
                continue;
            }
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            viewHeight = Math.max(viewHeight, childView.getMeasuredHeight());
            viewWidth += childView.getMeasuredWidth();
        }
        setMeasuredDimension(viewWidth, viewHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
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
            }
        }


        // 布局 leftMenu
        if (leftMenuView != null) {
            leftMenuView.layout(-leftMenuView.getMeasuredWidth(), 0, 0, leftMenuView.getMeasuredHeight());
        }
        // 布局 contentView
        if (contentView != null) {
            contentView.layout(0, 0, contentView.getMeasuredWidth(), contentView.getMeasuredHeight());
        }
        // 布局 rightMenu
        if (rightMenuView != null && contentView != null) {
            rightMenuView.layout(contentView.getMeasuredWidth(), 0,
                    contentView.getMeasuredWidth() + rightMenuView.getMeasuredWidth(), rightMenuView.getMeasuredHeight());
        }

    }

    // 2. 重写 computeScroll()
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {  // 动画没有结束
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //通知View重绘-invalidate()->onDraw()->computeScroll()
            postInvalidate();
        }
    }

    private PointF lastPoint;

    // 记录第一次触摸点的坐标，方便计算手指抬起时，总的滑动距离
    private PointF firstPoint;

    // 手指按下到抬起，总的滑动距离
    float finalDistance;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isSwipeEnable) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (isTouching) {
                        return false;
                    }
                    isTouching = true;
                    isSwipeing = false;
                    if (firstPoint == null) {
                        firstPoint = new PointF();
                    }
                    if (lastPoint == null) {
                        lastPoint = new PointF();
                    }
                    // 当前触摸的不是已经打开的那个 SwipeMenuLayout,则需要将打开的那个关闭掉。
                    if (swipeMenuLayout != null) {
                        if (swipeMenuLayout != this) {
                            // 调用已经打开的那个 SwipeMenuLayout 关闭方法
                            swipeMenuLayout.handleSwipeMenu(State.CLOSE);

                        }
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    firstPoint.set(ev.getX(), ev.getY());
                    lastPoint.set(ev.getX(), ev.getY());
                    break;
                case MotionEvent.ACTION_MOVE:
                    // 偏移量 = 当前坐标值 - 上次坐标值
                    int dx = (int) (ev.getX() - lastPoint.x);
                    int dy = (int) (ev.getY() - lastPoint.y);
                    // scrollBy 移动，正值内容向左移动，负值内容向右移动
                    if (Math.abs(dx) > Math.abs(dy)) {
                        scrollBy(-dx, 0);
                    }
                    // 边界限定
                    if (getScrollX() > 0) {  // 向左滑动,滑出 rightMenuView
                        if (rightMenuView != null) {  // 存在 rightMenuView,滑动距离不能超过 rightMenuView 宽度
                            if (getScrollX() > rightMenuView.getMeasuredWidth()) {
                                //scrollTo(StringUtil.dip2px(TlzTbDressApplication.getInstance().getApplicationContext(), 70), 0);
                                //动态修改rightMenuView的宽
                                LinearLayout.LayoutParams sp_params = new LinearLayout.LayoutParams(getScrollX(), LayoutParams.MATCH_PARENT);
                                rightMenuView.setBackgroundColor(Color.parseColor("#f7453f"));
                                rightMenuView.setLayoutParams(sp_params);
                                invalidate();
                            }


                        } else {  // 不存在 rightMenuView,禁止向左滑动
                            scrollTo(0, 0);
                        }
                    } else if (getScrollX() < 0) {  // 向右滑动，滑出 leftMenuView
                        if (leftMenuView != null) {  // 存在 leftMenuView,滑动距离不能大于 leftMenuView 宽度
                            if (getScrollX() < -leftMenuView.getMeasuredWidth()) {  // getScrollX()是负值，
                                //scrollTo(-leftMenuView.getMeasuredWidth(), 0);
                                LinearLayout.LayoutParams sp_params = new LinearLayout.LayoutParams(Math.abs(getScrollX()), LayoutParams.MATCH_PARENT);
                                if(isFavorite){
                                    leftMenuView.setBackgroundColor(Color.parseColor("#eeeeee"));
                                }else{
                                    leftMenuView.setBackgroundColor(Color.parseColor("#333333"));
                                }
                                leftMenuView.setLayoutParams(sp_params);
                                invalidate();
                            }
                        } else {
                            scrollTo(0, 0);
                        }
                    }
                    // 当水平滑动时，请求父控件不要拦截事件
                    if (Math.abs(dx) > mScaledTouchSlop) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    lastPoint.set(ev.getX(), ev.getY());
                    break;
                case MotionEvent.ACTION_UP:
                    int dx1 = (int) (ev.getX() - lastPoint.x);
                    int dy1 = (int) (ev.getY() - lastPoint.y);
                    float finalDistanceUp = ev.getX() - firstPoint.x;
                    // scrollBy 移动，正值内容向左移动，负值内容向右移动
                    if (Math.abs(dx1) > Math.abs(dy1)) {
                        scrollBy(-dx1, 0);
                    }

                    if (rightMenuView != null && rightMenuView.getMeasuredWidth() > DensityUtils.getScreenWidth(getContext())*0.5 && Math.abs(finalDistanceUp) > DensityUtils.getScreenWidth(getContext())*0.5) {
                        scrollTo(0, 0);
                        //发送删除操作 区分是购物车滑动还是收藏页滑动
                        if(null != leftSlideListener){
                            leftSlideListener.setOnLeftSlide();
                        }
                    } else if (leftMenuView != null && leftMenuView.getMeasuredWidth() > DensityUtils.getScreenWidth(getContext())*0.5 && Math.abs(finalDistanceUp) > DensityUtils.getScreenWidth(getContext())*0.5) {
                        scrollTo(0, 0);
                        //发送收藏删除操作，同时收藏商品
                        if(null != leftSlideListener){
                            rightSlideListener.setOnRightSlide();
                        }
                    }


                case MotionEvent.ACTION_CANCEL:
                    isTouching = false;
                    finalDistance = ev.getX() - firstPoint.x;
                    if (Math.abs(finalDistance) > mScaledTouchSlop) {
                        isSwipeing = true;
                    }
                    State state = isShouldOpenMenu(getScrollX());
                    handleSwipeMenu(state);
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isSwipeEnable) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    //滑动时拦截点击时间
                    if (Math.abs(finalDistance) > mScaledTouchSlop) {
                        // 当手指拖动值大于mScaledTouchSlop值时，认为应该进行滚动，拦截子控件的事件
                        return true;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    //滑动后不触发contentView的点击事件
                    if (isSwipeing) {
                        isSwipeing = false;
                        finalDistance = 0;
                        return true;
                    }
//
//                if (getX() < getScreenWidth() - rightMenuView.getMeasuredWidth()) {
//                    return true;
//                }
                    break;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void handleSwipeMenu(State state) {
        if (state == State.RIGHT_MENU_OPEN) {
            swipeMenuLayout = this;
            mScroller.startScroll(getScrollX(), 0,  DensityUtils.dp2px(getContext(), width) - getScrollX(), 0);
            curState = state;
        } else if (state == State.LEFT_MENU_OPEN) {
            swipeMenuLayout = this;
            // getScrollX() 为负值
            mScroller.startScroll(getScrollX(), 0, -getScrollX() - DensityUtils.dp2px(getContext(), width), 0);
            curState = state;
        } else if (state == State.CLOSE) {
            LinearLayout.LayoutParams sp_params = new LinearLayout.LayoutParams(DensityUtils.dp2px(getContext(), width), LayoutParams.MATCH_PARENT);
            if(null != leftMenuView){
                leftMenuView.setLayoutParams(sp_params);
            }
            if(null != rightMenuView){
                rightMenuView.setLayoutParams(sp_params);
            }
            mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0);
            swipeMenuLayout = null;
            curState = null;
        }
        //通知View重绘-invalidate()->onDraw()->computeScroll()
        invalidate();
    }

    private State isShouldOpenMenu(int scrollX) {
        // (1) scrollX > 0 : 表明现在处于 rightMenuView 打开状态，根据临界值决定是关闭，还是继续打开
        if (scrollX > 0) {
            if (finalDistance < 0) {        // 左滑
                if (rightMenuView != null && scrollX > mScaledTouchSlop) {
                    return State.RIGHT_MENU_OPEN;
                }
            } else if (finalDistance > 0) {  // 右滑
                if (rightMenuView != null && scrollX < rightMenuView.getMeasuredWidth() - mScaledTouchSlop) {
                    return State.CLOSE;
                }
            }
        } else if (scrollX < 0) { // (2)scrollX < 0:表明现在处于 leftMenuView 打开状态，根据临界值是否打开，还是关闭
            if (finalDistance < 0) { //  左滑
                if (leftMenuView != null && Math.abs(scrollX) > mScaledTouchSlop) {
                    return State.CLOSE;
                }
            } else if (finalDistance > 0) {  // 右滑
                if (leftMenuView != null && Math.abs(scrollX) > mScaledTouchSlop && !isFavorite) {
                    return State.LEFT_MENU_OPEN;
                }
            }
        }
        return State.CLOSE;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this == swipeMenuLayout) {
            swipeMenuLayout.handleSwipeMenu(curState);
        }

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this == swipeMenuLayout) {
            swipeMenuLayout.handleSwipeMenu(State.CLOSE);
        }
    }

    public int getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    enum State {
        LEFT_MENU_OPEN,
        RIGHT_MENU_OPEN,
        CLOSE
    }

    public interface OnLeftSlideListener{
        void setOnLeftSlide();
    }

    public interface OnRightSlideListener{
        void setOnRightSlide();
    }

    /**
     * 左滑删除
     * @param listener
     */
    public void setOnLeftSlideListener(OnLeftSlideListener listener){
        this.leftSlideListener = listener;
    }

    /**
     * 右滑收藏
     * @param listener
     */
    public void setOnRightSlideListener(OnRightSlideListener listener){
        this.rightSlideListener = listener;
    }
}

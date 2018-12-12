package com.example.myview.view;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by luchunhao on 2018/12/10.
 */

public class FlingBehavior extends AppBarLayout.Behavior {

    private boolean isPositive;
    AppBarLayoutScrollListem appBarLayoutScrollListem;

    public FlingBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        appBarLayoutScrollListem = new AppBarLayoutScrollListem(context);
    }


    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY) {
        if (target instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) target;
            recyclerView.removeOnScrollListener(appBarLayoutScrollListem);
            recyclerView.addOnScrollListener(appBarLayoutScrollListem);
            appBarLayoutScrollListem.coordinatorLayout = coordinatorLayout;
            appBarLayoutScrollListem.child = child;
            appBarLayoutScrollListem.target = target;
            appBarLayoutScrollListem.velocityX = velocityX;
            appBarLayoutScrollListem.velocityY = velocityY;
            appBarLayoutScrollListem.consumed = false;
        }
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY, boolean consumed) {
        if (velocityY > 0 && !isPositive || velocityY < 0 && isPositive) {
            velocityY = velocityY * -1;
        }
        if (target instanceof RecyclerView && velocityY < 0) {
            appBarLayoutScrollListem.coordinatorLayout = coordinatorLayout;
            appBarLayoutScrollListem.child = child;
            appBarLayoutScrollListem.target = target;
            appBarLayoutScrollListem.velocityX = velocityX;
            appBarLayoutScrollListem.velocityY = velocityY;
            appBarLayoutScrollListem.consumed = consumed;
            appBarLayoutScrollListem.totalDy = 0;
        }
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }


    public boolean onNestedFlingEx(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        isPositive = dy > 0;
    }

    public class AppBarLayoutScrollListem extends RecyclerView.OnScrollListener {
        AppBarLayout child;
        View target;
        CoordinatorLayout coordinatorLayout;
        float velocityX;
        float velocityY;
        boolean consumed;
        int totalDy = 0;

        public AppBarLayoutScrollListem(Context context) {
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                if (child != null && target != null && coordinatorLayout != null) {
                    if (target instanceof RecyclerView) {
                        RecyclerView recyclerView1 = (RecyclerView) target;
                        final View firstChild = recyclerView.getChildAt(0);
                        final int childAdapterPosition = recyclerView.getChildAdapterPosition(firstChild);
                        if (childAdapterPosition == 0 && firstChild.getY() == 0) {
                            int velocityFinal = (int) (velocityY / (float) (child.getMeasuredHeight() + Math.abs(totalDy)) * child.getMeasuredHeight());
                            onNestedFlingEx(coordinatorLayout, child, target, velocityX, velocityFinal, false);
                        }
                        recyclerView1.removeOnScrollListener(this);
                    }
                }
                clear();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            totalDy += dy;
        }

        public void clear() {
            totalDy = 0;
            child = null;
            target = null;
            coordinatorLayout = null;
            velocityX = 0;
            velocityY = 0;
            consumed = false;
        }


    }

}

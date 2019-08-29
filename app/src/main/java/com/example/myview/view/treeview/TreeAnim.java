package com.example.myview.view.treeview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Iterator;
import java.util.LinkedList;

public class TreeAnim extends View {

    private static final String TAG = "TreeAnim";

    private SnapShot snapShot;
    private Paint paint;
    private LinkedList<Branch> growingBranches = new LinkedList<>();
    private boolean hasEnd;
    private int scaleFactor = 2;
    private int data[][];

    // 二阶贝塞尔曲线公式    B(t) = (1-t)²*P0 + 2t(1-t)P1 + t²*P2    ,t=[0,1]
    public TreeAnim(Context context) {
        this(context, null);
    }

    public TreeAnim(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TreeAnim(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        growingBranches.add(getBranches());
        Log.d(TAG, "TreeAnim: " + Thread.currentThread().getName());
    }

    public void setScaleFactor(int scaleFactor) {
        this.scaleFactor = scaleFactor;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: " + Thread.currentThread().getName());
        drawBranches();
        canvas.drawBitmap(snapShot.bitmap, 0, 0, paint);
        if (!hasEnd) {
            invalidate();
        }
    }

    private void drawBranches() {
        if (!growingBranches.isEmpty()) {
            snapShot.canvas.save();
            snapShot.canvas.translate(getWidth() / 2 - data[0][2] * scaleFactor, getHeight() - data[0][3] * scaleFactor);
            LinkedList<Branch> tmpBranches = null;
            Iterator<Branch> iterator = growingBranches.iterator();
            while (iterator.hasNext()) {
                Branch branch = iterator.next();
                if (!branch.grow(snapShot.canvas, paint, scaleFactor)) {
                    // 单个树枝绘制完成
                    iterator.remove();
                    if (branch.childList != null) {
                        if (tmpBranches == null) {
                            tmpBranches = branch.childList;
                        } else {
                            tmpBranches.addAll(branch.childList);
                        }
                    }
                }
            }

            snapShot.canvas.restore();
            if (null != tmpBranches) {
                growingBranches.addAll(tmpBranches);
            }
            if (growingBranches.isEmpty()) {
                // 所有树枝全部绘制完成
                hasEnd = true;
            }
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout: " + Thread.currentThread().getName());
        snapShot = new SnapShot(Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888));
    }

    public Branch getBranches() {
        data = new int[][]{
                {0, -1, 217, 490, 252, 60, 182, 10, 30, 100},
                {1, 0, 222, 310, 137, 227, 22, 210, 13, 100},
                {2, 1, 132, 245, 116, 240, 76, 205, 2, 40},
                {3, 0, 232, 255, 282, 166, 362, 155, 12, 100},
                {4, 3, 260, 210, 330, 219, 343, 236, 3, 80},
                {5, 0, 217, 91, 219, 58, 216, 27, 3, 40},
                {6, 0, 228, 207, 95, 57, 10, 54, 9, 80},
                {7, 6, 109, 96, 65, 63, 53, 15, 2, 40},
                {8, 6, 180, 155, 117, 125, 77, 140, 4, 60},
                {9, 0, 228, 167, 290, 62, 360, 31, 6, 100},
                {10, 9, 272, 103, 328, 87, 330, 81, 2, 80}
        };
        int n = data.length;
        Branch[] branches = new Branch[n];
        for (int i = 0; i < n; i++) {
            branches[i] = new Branch(data[i]);
            int parent = data[i][1];
            if (parent != -1)
                branches[parent].addChild(branches[i]);
        }
        return branches[0];
    }
}

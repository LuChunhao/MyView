package com.example.myview.view.treeview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.LinkedList;

public class Branch {

    public static int branchColor = 0xff775533;


    private Point[] cp = new Point[3];
    private float radius;
    private float maxLength;
    private int currentLength;
    private float part;
    private float growX, growY;
    LinkedList<Branch> childList;

    public Branch(int[] data) {
        // id, parentId, bezier control points(3 points, in 6 columns), max radius, length
        // {0, -1, 217, 490, 252, 60, 182, 10, 30, 100},
        cp[0] = new Point(data[2], data[3]);
        cp[1] = new Point(data[4], data[5]);
        cp[2] = new Point(data[6], data[7]);
        radius = data[8];
        maxLength = data[9];
        part = 1f / maxLength;
    }

    public boolean grow(Canvas canvas, Paint paint, float scaleFactor) {
        if (currentLength < maxLength) {
            // 计算当期那需要绘制的位置
            bezier(currentLength * part);
            // 绘制
            draw(canvas, paint, scaleFactor);
            radius *= 0.97f;
            currentLength++;
            return true;
        } else {
            return false;
        }
    }

    private void draw(Canvas canvas, Paint paint, float scaleFactor) {
        paint.setColor(branchColor);
        canvas.save();
        canvas.scale(scaleFactor, scaleFactor);
        canvas.translate(growX, growY);
        canvas.drawCircle(0, 0, radius, paint);
        canvas.restore();
    }

    private void bezier(float t) {
        float c0 = (1 - t) * (1 - t);
        float c1 = 2 * t * (1 - t);
        float c2 = t * t;
        growX = c0 * cp[0].x + c1 * cp[1].x + c2 * cp[2].x;
        growY = c0 * cp[0].y + c1 * cp[1].y + c2 * cp[2].y;
    }

    public void addChild(Branch branch) {
        if (childList == null) {
            childList = new LinkedList<>();
        }
        childList.add(branch);
    }
}

package com.example.myview.view.treeview;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class SnapShot {
    Canvas canvas;
    Bitmap bitmap;

    public SnapShot(Bitmap bitmap) {
        this.bitmap = bitmap;
        canvas = new Canvas(bitmap);
    }
}

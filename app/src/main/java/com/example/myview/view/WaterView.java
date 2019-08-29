package com.example.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myview.R;

public class WaterView extends FrameLayout {

    // 定义一个textview
    private TextView textView;
    private PointF initPosition;
    // 手指移动后的坐标
    private PointF movePosition;
    private Paint paint;
    // 手指是否触摸到控件
    private boolean isClicked, isOut;
    private float mRadius = 40;
    // 存储连接桥路径的对象
    private Path path;
    // 爆炸 效果image
    private ImageView imageView;

    private int maxLength = 400;

    public void setmRadius(float mRadius) {
        this.mRadius = mRadius;
        invalidate();
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        invalidate();
    }

    public WaterView(Context context) {
        this(context, null);
    }

    public WaterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initPosition = new PointF(0, 0);
        movePosition = new PointF();

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);

        path = new Path();

        textView = new TextView(getContext());
        textView.setText("99+");
        textView.setTextColor(Color.WHITE);
        textView.setBackgroundResource(R.drawable.shape_text_bg);
        textView.setPadding(20, 20, 20, 20);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(layoutParams);
        this.addView(textView);

        imageView = new ImageView(getContext());
        imageView.setLayoutParams(layoutParams);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        initPosition.set(getWidth() / 2, getHeight() / 2);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        // 保存状态
        canvas.save();
        if (isClicked) {
            textView.setX(movePosition.x - textView.getWidth() / 2);
            textView.setY(movePosition.y - textView.getHeight() / 2);
            drawPath();
            if (!isOut) {
                // 起点的圆
                canvas.drawCircle(initPosition.x, initPosition.y, mRadius, paint);
                // 终点的圆
                canvas.drawCircle(movePosition.x, movePosition.y, mRadius, paint);
                // 画连接桥
                canvas.drawPath(path, paint);
            }

        } else {
            textView.setX(initPosition.x - textView.getWidth() / 2);
            textView.setY(initPosition.y - textView.getHeight() / 2);
        }


        // 恢复状态
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isOut) {
                    return true;
                }
                movePosition.set(initPosition.x, initPosition.y);
                // 判断点击位置是否在文本区域内
                Rect rect = new Rect();
                int[] location = new int[2];
                // 获取控件在屏幕中x y 坐标
                textView.getLocationOnScreen(location);
                rect.left = location[0];
                rect.top = location[1];
                rect.right = location[0] + textView.getWidth();
                rect.bottom = location[1] + textView.getHeight();
                if (rect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    isClicked = true;
                }

                break;
            case MotionEvent.ACTION_UP:
                isClicked = false;
                if (isOut) {
                    Toast.makeText(getContext(), "消失了", Toast.LENGTH_SHORT).show();
                    textView.setVisibility(GONE);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                movePosition.set(event.getX(), event.getY());
                break;
        }
        // 调用dispatchDraw
        postInvalidate();
        return true;
    }


    /**
     * 绘制连接桥的四个点，并且用贝塞尔曲线链接
     */
    private void drawPath() {
        // 获取终点与起点的x坐标的差值
        float widthX = movePosition.x - initPosition.x;
        // 获取终点与起点的Y坐标的差值
        float widthY = movePosition.y - initPosition.y;

        // 获取斜边的值
        float sqrt = (float) Math.sqrt(Math.pow(widthX, 2) + Math.pow(widthY, 2));
        mRadius = 40 - sqrt / 30;
        if (sqrt > maxLength) {
            isOut = true;
        } else {
            isOut = false;
        }

        // 得到三角形锐角的角度值/ 正切值
        double atan = Math.atan(widthY / widthX);
        // 获取offsetX的长度
        float offsetX = (float) (mRadius * Math.sin(atan));
        float offsetY = (float) (mRadius * Math.cos(atan));

        // 获取A坐标
        float AX = initPosition.x + offsetX;
        float AY = initPosition.y - offsetY;

        // 获取B坐标
        float BX = movePosition.x + offsetX;
        float BY = movePosition.y - offsetY;

        // 获取C坐标
        float CX = movePosition.x - offsetX;
        float CY = movePosition.y + offsetY;

        // 获取D坐标
        float DX = initPosition.x - offsetX;
        float DY = initPosition.y + offsetY;

        // 获取中心（转折）点坐标
        float conX = (initPosition.x + movePosition.x) / 2;
        float conY = (initPosition.y + movePosition.y) / 2;

        path.reset();
        // 将起点移动到A坐标
        path.moveTo(AX, AY);
        path.quadTo(conX, conY, BX, BY);
        path.lineTo(CX, CY);
        path.quadTo(conX, conY, DX, DY);
        path.lineTo(AX, AY);
    }
}

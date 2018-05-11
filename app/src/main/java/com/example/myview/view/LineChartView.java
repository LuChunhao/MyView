package com.example.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.myview.DensityUtils;
import com.example.myview.bean.DataBean;

import java.util.HashMap;

/**
 * Created by luchunhao on 2018/5/7.
 */

public class LineChartView extends View {



    public LineChartView(Context context) {
        this(context, null);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaintBg = new Paint();
        mPaintCoveredBg = new Paint();
        mPaintCircle = new Paint();
        mPaintChartLine = new Paint();
        mPaintDataLine = new Paint();
        mPaintTextDate = new Paint();
        mPaintTextValue = new Paint();
        mPaintFilledCircle = new Paint();
        path = new Path();

        dataTotal = new HashMap<>();
    }
    private int width;
    private int height;
    private float maxValue;//传入数据的最大值
    private int dataNum;//数据总数

    private Paint mPaintBg;//报表背景画笔
    private Paint mPaintCoveredBg;//用于画数据覆盖部分
    private Paint mPaintChartLine;//用于画报表的网格
    private Paint mPaintDataLine;//用于画数据连线
    private Paint mPaintTextDate;//用于画日期文本
    private Paint mPaintCircle;//用于画空心圆
    private Paint mPaintFilledCircle;//用于画实心圆
    private Paint mPaintTextValue;//用于画数据访问量值

    private Path path;

    private HashMap<Integer,DataBean> dataTotal;//用于得存储传入的总数据

    //用一个setPaints（）方法，设定所有画笔的属性，增加代码灵活性
    public void setPaints(int bgColor, int coveredBgColor, int chartLineColor
            , int dataLineColor, int textDateColor, int filledCircleColor, int circleColor, int textValueColor) {

        mPaintBg.setColor(bgColor);
        mPaintBg.setStyle(Paint.Style.FILL);

        mPaintCoveredBg.setColor(coveredBgColor);
        mPaintCoveredBg.setStyle(Paint.Style.FILL);

        mPaintCircle.setColor(circleColor);
        mPaintCircle.setStyle(Paint.Style.STROKE);
        mPaintCircle.setStrokeWidth(5);
        mPaintCircle.setAntiAlias(true);

        mPaintFilledCircle.setColor(filledCircleColor);
        mPaintFilledCircle.setStyle(Paint.Style.FILL);
        mPaintFilledCircle.setAntiAlias(true);

        mPaintChartLine.setColor(chartLineColor);
        mPaintChartLine.setStyle(Paint.Style.STROKE);
        mPaintChartLine.setAntiAlias(true);

        mPaintDataLine.setColor(dataLineColor);
        mPaintDataLine.setStyle(Paint.Style.STROKE);
        mPaintDataLine.setStrokeWidth(DensityUtils.dp2px(getContext(), 5));
        mPaintDataLine.setAntiAlias(true);

        mPaintTextDate.setColor(textDateColor);
        mPaintTextDate.setTextSize(DensityUtils.dp2px(getContext(), 10));
        mPaintTextDate.setTextAlign(Paint.Align.CENTER);
        mPaintTextDate.setAntiAlias(true);

        mPaintTextValue.setColor(textValueColor);
        mPaintTextValue.setTextSize(DensityUtils.dp2px(getContext(), 12));
        mPaintTextValue.setTextAlign(Paint.Align.CENTER);
        mPaintTextValue.setAntiAlias(true);

        //重绘
        invalidate();
    }


    //用于设定传入的总数据
    public void setDataTotal(HashMap<Integer, DataBean> dataTotal) {
        this.dataTotal = dataTotal;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //当总数据已经传入，即不为空时，根据总数据中数据个数设定view的总宽
        if (dataTotal != null) {
            width = (dataTotal.size() - 1) * xAddedNum + chartMarginHorizontal * 2;
            getMaxValue(dataTotal);
        }
        height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    /**
     * 用于得到总数据中最大数据
     * @param dataTotal 总数据
     */
    private void getMaxValue(HashMap<Integer, DataBean> dataTotal) {
        maxValue = 0;
        dataNum = 0;
        for (int key : dataTotal.keySet()) {
            if (dataTotal.get(key).getValue() > maxValue) {
                maxValue = dataTotal.get(key).getValue();
            }
            dataNum++;
        }
    }

    private int mChartHeight;//折线图的高
    private int mChartWidth;//折线图的
    private int startX = DensityUtils.dp2px(getContext(), 10);//开始绘制的x坐标
    private int startY = DensityUtils.dp2px(getContext(), 5);//开始绘制的y坐标
    private int chartMarginBottom = DensityUtils.dp2px(getContext(), 30);//折线图距离父控件底部距离
    private int chartMarginHorizontal = DensityUtils.dp2px(getContext(), 12);//折线图距离父控件左右的距离
    private int valueAlignLeft = DensityUtils.dp2px(getContext(), 0);//value参数文本距离左边距离
    private int dateAlignLeft = DensityUtils.dp2px(getContext(), 0);//date参数文本距离左边距离
    private int valueAlignBottom = DensityUtils.dp2px(getContext(), 5);//value参数文本距离底部距离
    private int dateAlignBottom = DensityUtils.dp2px(getContext(), 10);//date参数文本距离底部距离
    private int xAddedNum = DensityUtils.dp2px(getContext(), 60);//绘制折线图时每次移动的x轴距离
    private int yAddedNum;//绘制折线图时每次移动的y轴距离
    private boolean isDrawFirst;//是否是第一次绘制
    private float circleFilledRadius = DensityUtils.dp2px(getContext(), 5);//外圆半径
    private float circleRadius = DensityUtils.dp2px(getContext(), 3);//内圆半径

    private float firstX;//第一个点的x轴坐标
    private float firstY;//第一个点的y轴坐标

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        isDrawFirst = true;
        mChartHeight = height - chartMarginBottom;
        yAddedNum = mChartHeight / 4;
        mChartWidth = width - chartMarginHorizontal * 2;

        canvas.drawRect(startX, startY, startX + mChartWidth, startY + mChartHeight, mPaintBg);
        for (int key : dataTotal.keySet()) {
            float value = dataTotal.get(key).getValue();
            if (isDrawFirst) {
                //当第一次绘制时得到第一个点的横纵坐标
                firstX = startX;
                firstY = startY + (1f - value / ((int) maxValue * 1.5f)) * mChartHeight;
                path.moveTo(firstX, firstY);
                isDrawFirst = false;
            }
            //每循环一次，将path线性相位一次
            path.lineTo(startX, startY + (1f - value / ((int) maxValue * 1.5f)) * mChartHeight);
            startX += xAddedNum;
        }
        //重新给startX赋值
        startX = DensityUtils.dp2px(getContext(), 10);
        //画出折线
        canvas.drawPath(path, mPaintDataLine);
        //画出折线以下部分的颜色
        path.lineTo(startX + mChartWidth, startY + mChartHeight);
        path.lineTo(startX, startY + mChartHeight);
        path.lineTo(firstX, firstY);
        canvas.drawPath(path, mPaintCoveredBg);

        //画出每个点的圆圈，和对应的文本
        for (int key : dataTotal.keySet()) {
            int date = dataTotal.get(key).getDate();
            float value = dataTotal.get(key).getValue();
            canvas.drawCircle(startX, startY + (1f - value / ((int) maxValue * 1.5f)) * mChartHeight, circleFilledRadius, mPaintFilledCircle);
            canvas.drawCircle(startX, startY + (1f - value / ((int) maxValue * 1.5f)) * mChartHeight, circleRadius, mPaintCircle);
            canvas.drawText(date + "" , startX + dateAlignLeft, height - dateAlignBottom, mPaintTextDate);
            canvas.drawText(value + "" , startX + valueAlignLeft, startY + (1f - value / ((int) maxValue * 1.5f)) * mChartHeight - valueAlignBottom, mPaintTextValue);

            startX += xAddedNum;
        }

        //在次使startX回到初始值
        startX = DensityUtils.dp2px(getContext(), 10);
        /**
         * 画出网格
         */
        //竖线
        for (int i = 0; i < dataNum; i++) {
            canvas.drawLine(startX + i * xAddedNum, startY, startX + i * xAddedNum, startY + mChartHeight, mPaintChartLine);
        }

        //横线
        for (int i = 0; i < 5; i++) {
            canvas.drawLine(startX, startY + i * yAddedNum, startX + mChartWidth, startY + i * yAddedNum, mPaintChartLine);
        }

        path.reset();

    }
}

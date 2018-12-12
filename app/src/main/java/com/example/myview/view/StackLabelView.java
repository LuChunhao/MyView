package com.example.myview.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myview.R;
import com.example.myview.view.impl.OnLabelClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: @Kongzue
 * Github: https://github.com/kongzue/
 * Homepage: http://kongzue.com/
 * Mail: myzcxhh@live.cn
 * CreateTime: 2018/10/24 20:58
 */
public class StackLabelView extends RelativeLayout {
    
    private int textColor = 0;
    private int textSize = 0;
    private int paddingVertical = 0;
    private int paddingHorizontal = 0;
    private int itemMargin = 0;
    private boolean deleteButton = false;
    
    private OnLabelClickListener onLabelClickListener;
    private Context context;
    private List<String> labels;
    
    public StackLabelView(Context context) {
        super(context);
        this.context = context;
    }
    
    public StackLabelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        loadAttrs(context, attrs);
    }
    
    public StackLabelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        loadAttrs(context, attrs);
    }
    
    private void loadAttrs(Context context, AttributeSet attrs) {
        try {
            //默认值
            textColor = Color.argb(230, 0, 0, 0);
            textSize = dp2px(12);
            paddingVertical = dp2px(8);
            paddingHorizontal = dp2px(12);
            itemMargin = dp2px(4);
            deleteButton = false;
            
            //加载值
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StackLabel);
            textColor = typedArray.getColor(R.styleable.StackLabel_textColor, textColor);
            textSize = typedArray.getDimensionPixelOffset(R.styleable.StackLabel_textSize, textSize);
            paddingVertical = typedArray.getDimensionPixelOffset(R.styleable.StackLabel_paddingVertical, paddingVertical);
            paddingHorizontal = typedArray.getDimensionPixelOffset(R.styleable.StackLabel_paddingHorizontal, paddingHorizontal);
            itemMargin = typedArray.getDimensionPixelOffset(R.styleable.StackLabel_itemMargin, itemMargin);
            deleteButton = typedArray.getBoolean(R.styleable.StackLabel_deleteButton, deleteButton);
            typedArray.recycle();
        } catch (Exception e) {
        }
    }
    
    private int dp2px(float dpValue) {
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }
    
    private float px2dp(int pxValue) {
        return (pxValue / Resources.getSystem().getDisplayMetrics().density);
    }
    
    private List<View> items;
    private int newHeight = 0;
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        
        refreshViews();
        
        setMeasuredDimension(getMeasuredWidth(), newHeight);//设置宽高
    }
    
    private void refreshViews() {
        int maxWidth = getMeasuredWidth();
        
        if (labels != null && !labels.isEmpty()) {
            newHeight = 0;
            for (int i = 0; i < items.size(); i++) {
                View item = items.get(i);
                
                int mWidth = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                int mHeight = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                item.measure(mWidth, mHeight);
                
                int n_x = 0;
                int n_y = 0;
                int o_y = 0;
                
                if (i != 0) {
                    n_x = (int) items.get(i - 1).getX() + items.get(i - 1).getMeasuredWidth();
                    n_y = (int) items.get(i - 1).getY() + items.get(i - 1).getMeasuredHeight();
                    o_y = (int) items.get(i - 1).getY();
                }
                
                if (n_x + item.getMeasuredWidth() > maxWidth) {
                    n_x = 0;
                    o_y = n_y;
                }
                
                item.setY(o_y);
                item.setX(n_x);
                
                newHeight = (int) (item.getY() + item.getMeasuredHeight());
            }
        }
    }
    
    public List<String> getLabels() {
        return labels;
    }
    
    public StackLabelView setLabels(List<String> l) {
        labels = l;
        
        removeAllViews();
        if (labels != null && !labels.isEmpty()) {
            items = new ArrayList<>();
            
            newHeight = 0;
            for (int i = 0; i < labels.size(); i++) {
                View item = LayoutInflater.from(context).inflate(R.layout.layout_label, null, false);
                
                newHeight = item.getMeasuredHeight();
                
                addView(item);
                items.add(item);
            }
            
            initItem();
        }
        return this;
    }
    
    private void initItem() {
        if (labels.size() != 0) {
            for (int i = 0; i < items.size(); i++) {
                View item = items.get(i);
                
                String s = labels.get(i);
                LinearLayout boxLabel = item.findViewById(R.id.box_label);
                TextView txtLabel = item.findViewById(R.id.txt_label);
                ImageView imgDelete = item.findViewById(R.id.img_delete);
                
                txtLabel.setText(s);
                txtLabel.setTextColor(textColor);
                txtLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                
                boxLabel.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical);
                MarginLayoutParams p = (MarginLayoutParams) boxLabel.getLayoutParams();
                p.setMargins(itemMargin, itemMargin, itemMargin, itemMargin);
                boxLabel.requestLayout();
                
                if (deleteButton) {
                    imgDelete.setVisibility(VISIBLE);
                } else {
                    imgDelete.setVisibility(GONE);
                }
                
                int mWidth = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                int mHeight = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                item.measure(mWidth, mHeight);
                
                final int index = i;
                boxLabel.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onLabelClickListener != null)
                            onLabelClickListener.onClick(index, v, labels.get(index));
                    }
                });
            }
        }
    }
    
    public OnLabelClickListener getOnLabelClickListener() {
        return onLabelClickListener;
    }
    
    public StackLabelView setOnLabelClickListener(OnLabelClickListener onLabelClickListener) {
        this.onLabelClickListener = onLabelClickListener;
        return this;
    }
    
    public boolean isDeleteButton() {
        return deleteButton;
    }
    
    public StackLabelView setDeleteButton(boolean deleteButton) {
        this.deleteButton = deleteButton;
        initItem();
        return this;
    }
}

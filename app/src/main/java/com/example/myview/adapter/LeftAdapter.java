package com.example.myview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myview.R;

import java.util.ArrayList;
import java.util.List;

public class LeftAdapter extends RecyclerView.Adapter<LeftAdapter.MyViewHolder> {
    private Context mcontext;
//    private List<LeftBean.DataBean> list = new ArrayList<>();
    private List list = new ArrayList<>();
    public LeftAdapter(Context mcontext) {
        this.mcontext = mcontext;
    }

//    public void setList(List<LeftBean.DataBean> result) {
//        this.list = result;
//        notifyDataSetChanged();
//    }

    @Override
    public LeftAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.item_category_left, viewGroup, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(LeftAdapter.MyViewHolder myViewHolder, final int position) {
        //myViewHolder.left_name.setText(list.get(position).getName());


        //判断点击与不点击的背景
        //if (list.get(position).isClick()) {
        if (true) {
            myViewHolder.mlayout.setBackgroundColor(Color.parseColor("#d43c3c"));
            myViewHolder.left_name.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            myViewHolder.mlayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            myViewHolder.left_name.setTextColor(Color.parseColor("#222222"));
        }
    }

    @Override
    public int getItemCount() {
//        return list.size();
        return 0;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout mlayout;
        private final TextView left_name;

        public MyViewHolder(View itemView) {
            super(itemView);
            mlayout = itemView.findViewById(R.id.layout);
            left_name = itemView.findViewById(R.id.left_name);

            //点击条目
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemLeftClckListener.onItemLeftClck(getLayoutPosition());
                }
            });
        }
    }

    //接口回调
    public interface OnItemLeftClckListener {
        void onItemLeftClck(int position);
    }

    private OnItemLeftClckListener onItemLeftClckListener;

    public void setOnItemLeftClckListener(OnItemLeftClckListener onItemLeftClckListener) {
        this.onItemLeftClckListener = onItemLeftClckListener;
    }
}

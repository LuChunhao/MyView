package com.example.myview.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myview.R;
import com.example.myview.databinding.ItemTextBinding;

import java.util.List;

/**
 * Created by luchunhao on 2018/4/25.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ItemViewHolder> {

    private static final String TAG = "MyAdapter";

    private Context context;
    private List<String> list;
    private OnItemClickListener listener;

    public MyAdapter(Context context,List<String> list){
        this.context = context;
        this.list = list;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemTextBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_text,parent,false);
        ItemViewHolder viewHolder = new ItemViewHolder(binding.getRoot());
        viewHolder.binding = binding;
        viewHolder.setIsRecyclable(false);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        holder.binding.itemText.setText(list.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.setOnItemClick(holder.binding.itemText);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        private ItemTextBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener{
        void setOnItemClick(TextView v);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}

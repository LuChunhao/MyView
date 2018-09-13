package com.example.myview.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myview.R;
import com.example.myview.databinding.ItemFoldListBinding;

import java.util.List;

/**
 * Created by luchunhao on 2018/9/13.
 */

public class FoldRecyclerAdapter extends RecyclerView.Adapter<FoldRecyclerAdapter.FoldItemViewHolder> {

    private List<String> groupArray;
    private List<List<String>> childArray;
    private Context mContext;

    public FoldRecyclerAdapter(Context mContext, List<String> groupArray, List<List<String>> childArray) {
        this.mContext = mContext;
        this.groupArray = groupArray;
        this.childArray = childArray;
    }

    @Override
    public FoldItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemFoldListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_fold_list, parent, false);
        FoldItemViewHolder viewHolder = new FoldItemViewHolder(binding.getRoot());
        viewHolder.binding = binding;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final FoldItemViewHolder holder, int position) {
        holder.binding.tvGroupTitle.setText(groupArray.get(position));
        holder.binding.itemRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        holder.binding.itemRecyclerView.setAdapter(new MyAdapter(mContext, childArray.get(position)));
        holder.binding.tvGroupTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.binding.itemRecyclerView.getVisibility() == View.VISIBLE) {
                    holder.binding.itemRecyclerView.setVisibility(View.GONE);
                } else {
                    holder.binding.itemRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupArray.size();
    }

    class FoldItemViewHolder extends RecyclerView.ViewHolder{

        private ItemFoldListBinding binding;

        public FoldItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}

package com.bbq.androidknowledgeframework.lib.ui.row;

import android.content.Context;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver;

/**
 * 说明
 * Created by bangbang.qiu on 2021/1/27.
 */
public abstract class ExRowRecyclerViewAdapter extends Adapter<ViewHolder> {
    protected Context mContext;
    protected ExRowRepo mExRowRepo;
    private ExRowRepo mShowingRepo;
    private AdapterDataObserver mObserver = new AdapterDataObserver() {
        public void onChanged() {
            ExRowRecyclerViewAdapter.this.mShowingRepo = ExRowRecyclerViewAdapter.this.mExRowRepo.clone();
        }
    };

    public ExRowRecyclerViewAdapter(Context context) {
        this.mContext = context;
        this.mExRowRepo = new ExRowRepo();
        this.mShowingRepo = this.mExRowRepo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return this.mShowingRepo.checkViewType(viewType)
                ? this.mShowingRepo.getViewHolder(viewType).getViewHolder(parent) : null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder != null && holder.isRecyclable()) {
            ExRowBaseView row = this.mShowingRepo.getRow(position);
            if (row != null) {
                row.onBindViewHolder(holder, position);
            }

        }
    }

    @Override
    public int getItemCount() {
        return this.mShowingRepo.getCount();
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @Override
    public int getItemViewType(int position) {
        ExRowBaseView row = this.mShowingRepo.getRow(position);
        return row == null ? -1 : row.getViewType();
    }

    public void registerAdapterDataObserver(AdapterDataObserver observer) {
        try {
            super.registerAdapterDataObserver(observer);
            super.registerAdapterDataObserver(this.mObserver);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
        try {
            super.unregisterAdapterDataObserver(this.mObserver);
            super.unregisterAdapterDataObserver(observer);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }
}
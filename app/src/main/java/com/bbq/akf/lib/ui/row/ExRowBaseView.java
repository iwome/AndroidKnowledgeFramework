package com.bbq.akf.lib.ui.row;

import android.view.ViewGroup;

/**
 * 说明
 * Created by bangbang.qiu on 2021/1/27.
 */
public abstract class ExRowBaseView {
    public ExRowBaseView() {
    }

    public abstract int getViewType();

    public abstract ViewHolder getViewHolder(ViewGroup vg);

    public abstract void onBindViewHolder(ViewHolder vh, int position);
}

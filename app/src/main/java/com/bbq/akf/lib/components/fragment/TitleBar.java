package com.bbq.akf.lib.components.fragment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.bbq.akf.lib.utils.ConvertUtil;

/**
 * 说明
 * Created by bangbang.qiu on 2021/2/4.
 */
public class TitleBar extends Toolbar {
    private TextView toolbarTitle;
    private View toolbarShadow;
    private FrameLayout extendFrame;

    public TitleBar(Context context) {
        super(context);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setToolbarTitle(TextView toolbarTitle) {
        this.toolbarTitle = toolbarTitle;
    }

    public void setTitle(CharSequence title) {
        this.toolbarTitle.setText(title);
    }

    public void setTextColor(int color) {
        this.toolbarTitle.setTextColor(color);
    }

    public void setTextSize(float textSize) {
        this.toolbarTitle.setTextSize(textSize);
    }

    public View getToolbarShadow() {
        return this.toolbarShadow;
    }

    public void setToolbarShadow(View toolbarShadow) {
        this.toolbarShadow = toolbarShadow;
    }

    public void setExtendFrame(FrameLayout extendFrame) {
        this.extendFrame = extendFrame;
    }

    public void addExtendView(Context context, ViewGroup viewGroup) {
        this.extendFrame.setPadding(0, 0, ConvertUtil.getInstance().dipToPx(context, 12.0F), 0);
        this.extendFrame.addView(viewGroup);
        this.extendFrame.setVisibility(VISIBLE);
        this.toolbarTitle.setVisibility(GONE);
    }

    public void setExtendViewVisibility(Boolean visibility) {
        this.extendFrame.setVisibility(visibility ? VISIBLE : GONE);
        this.toolbarTitle.setVisibility(visibility ? GONE : VISIBLE);
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        this.toolbarShadow.setVisibility(visibility);
    }
}
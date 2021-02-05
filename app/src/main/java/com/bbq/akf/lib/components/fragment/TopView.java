package com.bbq.akf.lib.components.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.core.view.ViewCompat;

import com.bbq.akf.R;
import com.bbq.akf.lib.utils.ConvertUtil;

/**
 * 说明
 * Created by bangbang.qiu on 2021/2/4.
 */
public class TopView {
    private static final int MAX_PROGRESS = 100;
    public Context context;
    public LinearLayout rootView;
    public TitleBar titleBar;
    public View shadowView;
    public FrameLayout pageContainer;
    public View pageView;
    private LinearLayout progressContainer;
    private ProgressBar progressBar;
    private int currentProgress = 0;
    private boolean isAnimStart = false;

    public TopView(Context c) {
        this.context = c;
    }

    public void createLayout() {
        this.rootView = new LinearLayout(this.context);
        this.rootView.setOrientation(LinearLayout.VERTICAL);
        this.makeTitleBar();
        this.progressContainer = new LinearLayout(this.context);
        this.progressContainer.setVisibility(View.GONE);
        this.rootView.addView(this.progressContainer, new LinearLayout.LayoutParams(-1, ConvertUtil.getInstance().dipToPx(this.context, 2.0F)));
        this.pageContainer = new FrameLayout(this.context);
        if (Build.VERSION.SDK_INT >= 17) {
            this.pageContainer.setId(ViewCompat.generateViewId());
        }

        this.rootView.addView(this.pageContainer, new LinearLayout.LayoutParams(-1, -1));
    }

    public void showTitleBar(boolean show) {
        if (this.titleBar != null && this.shadowView != null) {
            if (show) {
                this.titleBar.setVisibility(View.VISIBLE);
                this.shadowView.setVisibility(View.VISIBLE);
            } else {
                this.titleBar.setVisibility(View.GONE);
                this.shadowView.setVisibility(View.GONE);
            }

        }
    }

    public int getPageContainerViewId() {
        return this.pageContainer == null ? 0 : this.pageContainer.getId();
    }

    private void makeTitleBar() {
        Resources r = this.context.getResources();
        this.titleBar = new TitleBar(this.context);
        this.titleBar.setBackgroundColor(r.getColor(R.color.color_white));
        int height = ConvertUtil.getInstance().dipToPx(this.context, 44.0F);
        this.titleBar.setMinimumHeight(height);
        TextView tvTitle = new TextView(this.context);
        tvTitle.setGravity(17);
        tvTitle.setTextColor(r.getColor(R.color.color_black));
        tvTitle.setTextSize(1, 18.0F);
        tvTitle.setSingleLine(true);
        this.titleBar.addView(tvTitle, new androidx.appcompat.widget.Toolbar.LayoutParams(-2, -2, 1));
        FrameLayout extendFrame = new FrameLayout(this.context);
        extendFrame.setVisibility(View.GONE);
        androidx.appcompat.widget.Toolbar.LayoutParams frameP = new androidx.appcompat.widget.Toolbar.LayoutParams(-1, -2);
        frameP.topMargin = ConvertUtil.getInstance().dipToPx(this.context, 6.0F);
        frameP.bottomMargin = ConvertUtil.getInstance().dipToPx(this.context, 6.0F);
        this.titleBar.addView(extendFrame, frameP);
        this.rootView.addView(this.titleBar, new LinearLayout.LayoutParams(-1, height));
        this.shadowView = new View(this.context);
        this.shadowView.setBackgroundColor(r.getColor(R.color.color_black));
        this.rootView.addView(this.shadowView, new LinearLayout.LayoutParams(-1, ConvertUtil.getInstance().dipToPx(this.context, 0.5F)));
        this.titleBar.setToolbarTitle(tvTitle);
        this.titleBar.setExtendFrame(extendFrame);
        this.titleBar.setToolbarShadow(this.shadowView);
    }

    public void makeContentView(@LayoutRes int layoutId, View view) {
        if (layoutId == 0) {
            this.pageView = view;
        } else {
            this.pageView = LayoutInflater.from(this.context).inflate(layoutId, this.pageContainer, false);
        }

        if (this.pageView != null) {
            this.pageContainer.addView(this.pageView);
        }

    }

    public void showProgressBar(boolean show) {
        if (this.progressBar == null) {
            this.initProgressBar();
        }

        this.progressContainer.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void initProgressBar() {
        if (this.progressBar == null) {
            try {
                this.progressContainer.setVisibility(View.VISIBLE);
                this.progressBar = new ProgressBar(this.context, (AttributeSet) null, 16842872);
                this.progressBar.setMax(100);
                this.progressBar.setProgressDrawable(this.context.getResources().getDrawable(R.drawable.webview_progressbar));
                this.progressContainer.addView(this.progressBar, new LinearLayout.LayoutParams(-1, ConvertUtil.getInstance().dipToPx(this.context, 2.0F)));
            } catch (Exception var2) {
                this.progressContainer.setVisibility(View.GONE);
            }

        }
    }

    public void progressUpdate(int newProgress) {
        if (this.progressBar != null) {
            this.currentProgress = this.progressBar.getProgress();
            if (newProgress >= 100 && !this.isAnimStart) {
                this.isAnimStart = true;
                this.progressBar.setProgress(newProgress);
                this.startDismissAnimation(this.progressBar.getProgress());
            } else {
                this.startProgressAnimation(newProgress);
            }

        }
    }

    private void startProgressAnimation(int newProgress) {
        ObjectAnimator animator = ObjectAnimator.ofInt(this.progressBar, "progress", new int[]{this.currentProgress, newProgress});
        animator.setDuration(300L);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    private void startDismissAnimation(final int progress) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(this.progressBar, "alpha", new float[]{1.0F, 0.0F});
        anim.setDuration(1500L);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fraction = valueAnimator.getAnimatedFraction();
                int offset = 100 - progress;
                TopView.this.progressBar.setProgress((int) ((float) progress + (float) offset * fraction));
            }
        });
        anim.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                TopView.this.progressBar.setProgress(100);
                TopView.this.progressBar.setVisibility(View.GONE);
                TopView.this.progressContainer.setVisibility(View.GONE);
                TopView.this.isAnimStart = false;
            }
        });
        anim.start();
    }
}

package com.bbq.akf.lib.components.webview;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.bbq.akf.lib.components.fragment.BaseFragment;

/**
 * 说明
 * Created by bangbang.qiu on 2021/2/4.
 */
public class WebFragment extends BaseFragment {
    protected ExWebView webView;

    public WebFragment() {
    }

    protected void exProcessOnCreateBefore(Bundle savedInstanceState) {
    }

    protected boolean exInterceptOnCreate(Bundle savedInstanceState) {
        return false;
    }

    protected int exInitLayout() {
        return 0;
    }

    protected View exInitLayoutView() {
        this.webView = new ExWebView(ExAppUtil.getApplicationContext());
        return this.webView;
    }

    protected void exInitView(View contentView) {
        WebSettings webSettings = this.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setSupportZoom(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setCacheMode(2);
        if (Build.VERSION.SDK_INT >= 26) {
            webSettings.setSafeBrowsingEnabled(false);
        }

        if (ExDeviceUtil.getInstance().getAndroidSDKVersion() < 19) {
            webSettings.setLoadsImagesAutomatically(false);
            this.webView.removeJavascriptInterface("searchBoxJavaBridge_");
            this.webView.removeJavascriptInterface("accessibility");
            this.webView.removeJavascriptInterface("accessibilityTraversal");
        } else {
            webSettings.setLoadsImagesAutomatically(true);
        }

        try {
            this.initWebView(this.webView);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    protected void exInitData() {
        super.exInitData();

        try {
            this.initWebViewData(this.webView);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    protected abstract void initWebView(ExWebView var1);

    protected abstract void initWebViewData(WebView var1);

    public void onDestroy() {
        try {
            if (this.webView != null) {
                this.webView.setVisibility(8);
                this.webView.removeAllViews();

                try {
                    ((ViewGroup)this.webView.getParent()).removeView(this.webView);
                } catch (Exception var12) {
                    var12.printStackTrace();
                } finally {
                    this.webView.destroy();
                    this.webView = null;
                }
            }
        } catch (Exception var14) {
            var14.printStackTrace();
        } finally {
            super.onDestroy();
        }

    }
}

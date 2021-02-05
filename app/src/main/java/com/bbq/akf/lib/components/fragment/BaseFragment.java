package com.bbq.akf.lib.components.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 说明
 * Created by bangbang.qiu on 2021/2/4.
 */
public abstract class BaseFragment extends Fragment {
    private ArrayList<BroadcastReceiver> localReceiverList;
    private ArrayList<BroadcastReceiver> systemReceiverList;
    private LocalBroadcastManager mLocalBroadcastManager;
    protected TopView exView;

    public BaseFragment() {
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate((Bundle)null);
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.exProcessOnCreateBefore(savedInstanceState);
        super.onCreateView(inflater, container, savedInstanceState);
        if (this.exInterceptOnCreate(savedInstanceState)) {
            return null;
        } else {
            this.exView = new TopView(inflater.getContext());
            this.exView.createLayout();
            this.exView.showTitleBar(false);
            this.exView.makeContentView(this.exInitLayout(), this.exInitLayoutView());
//            AnnotationBinder.bind(this, this.exView.pageView); todo 注解
            this.exInitBundle(this.getArguments());
            this.exInitToolbar(this.exView.titleBar);
            this.exInitView(this.exView.pageView);
            if (!this.exInterceptInit()) {
                this.exInitData();
            }

            return this.exView.rootView;
        }
    }

    protected abstract void exProcessOnCreateBefore(Bundle var1);

    protected abstract boolean exInterceptOnCreate(Bundle var1);

    protected void exInitBundle(Bundle bundle) {
    }

    protected abstract int exInitLayout();

    protected View exInitLayoutView() {
        return null;
    }

//    protected OperaCallBack exInitStatusView() {
//        return null;
//    }

    protected abstract boolean exInterceptInit();

    protected abstract void exInitView(View var1);

    protected void exInitData() {
    }

    protected void exInitToolbar(TitleBar titleBar) {
    }

    public void onDestroy() {
        super.onDestroy();
        this.unRegisterLocalReceiver();
        this.unRegisterSystemReceiver();
//        AnnotationBinder.unBind(this); todo 注解
    }

    protected void registerLocalReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        if (this.mLocalBroadcastManager == null) {
            this.mLocalBroadcastManager = LocalBroadcastManager.getInstance(this.getContext());
        }

        this.mLocalBroadcastManager.registerReceiver(receiver, filter);
        if (this.localReceiverList == null) {
            this.localReceiverList = new ArrayList();
        }

        this.localReceiverList.add(receiver);
    }

    protected void registerSystemReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        Context context = this.getContext();
        if (context != null) {
            context.registerReceiver(receiver, filter, "PRIVATE_BROADCAST_PERMISSION", new Handler());
            if (this.systemReceiverList == null) {
                this.systemReceiverList = new ArrayList();
            }

            this.systemReceiverList.add(receiver);
        }
    }

    protected void unRegisterLocalReceiver() {
        if (this.localReceiverList != null) {
            Iterator var1 = this.localReceiverList.iterator();

            while(var1.hasNext()) {
                BroadcastReceiver receiver = (BroadcastReceiver)var1.next();
                this.mLocalBroadcastManager.unregisterReceiver(receiver);
            }

        }
    }

    protected void unRegisterSystemReceiver() {
        Context context = this.getContext();
        if (context != null) {
            if (this.systemReceiverList != null) {
                Iterator var2 = this.systemReceiverList.iterator();

                while(var2.hasNext()) {
                    BroadcastReceiver receiver = (BroadcastReceiver)var2.next();
                    context.unregisterReceiver(receiver);
                }

            }
        }
    }

    protected void sendLocalBroadcast(Intent intent) {
        LocalBroadcastManager.getInstance(this.getContext()).sendBroadcast(intent);
    }

    protected void sendSystemBroadcast(Intent intent) {
        Context context = this.getContext();
        if (context != null) {
            context.sendBroadcast(intent);
        }
    }
}


package com.bbq.androidknowledgeframework.main.adapter

import android.content.Context
import com.bbq.androidknowledgeframework.lib.ui.row.ExRowRecyclerViewAdapter
import com.bbq.androidknowledgeframework.main.bean.Menu
import com.bbq.androidknowledgeframework.main.row.ItemRow
import com.bbq.androidknowledgeframework.main.row.TitleRow

/**
 * 说明
 * Created by bangbang.qiu on 2021/1/27.
 */

class MenuAdapter(context: Context) : ExRowRecyclerViewAdapter(context) {

    public fun setData(menus: List<Menu>) {
        mExRowRepo.clear()
        for (menu in menus) {
            mExRowRepo.addLast(TitleRow(menu.title, mContext))
            for (item in menu.item) {
                mExRowRepo.addLast(ItemRow(item, mContext))
            }
        }
        notifyDataSetChanged()
    }
}
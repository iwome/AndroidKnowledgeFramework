package com.bbq.akf.project.main.adapter

import android.content.Context
import com.bbq.akf.lib.ui.row.ExRowRecyclerViewAdapter
import com.bbq.akf.project.main.bean.Menu
import com.bbq.akf.project.main.row.ItemRow
import com.bbq.akf.project.main.row.TitleRow

/**
 * 说明
 * Created by bangbang.qiu on 2021/1/27.
 */

class MenuAdapter(context: Context) : ExRowRecyclerViewAdapter(context) {

    fun setData(menus: List<Menu>) {
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
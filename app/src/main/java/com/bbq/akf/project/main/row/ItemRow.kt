package com.bbq.akf.project.main.row

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bbq.akf.R
import com.bbq.akf.lib.ui.row.ExRowBaseView
import com.bbq.akf.lib.ui.row.ViewHolder
import com.bbq.akf.project.main.bean.Item
import com.bbq.akf.project.main.bean.MenuType

/**
 * 说明
 * Created by bangbang.qiu on 2021/2/4.
 */
class ItemRow(cItem: Item, cContext: Context) : ExRowBaseView() {
    private val item = cItem
    private val context = cContext

    override fun getViewType(): Int {
        return MenuType.Item.ordinal
    }

    override fun onBindViewHolder(vh: ViewHolder?, position: Int) {
        vh?.setText(R.id.tv_title, item.name)
    }

    override fun getViewHolder(vg: ViewGroup?): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_main_menu_item, vg, false), context
        )
    }
}
package com.bbq.androidknowledgeframework.main.row

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bbq.androidknowledgeframework.R
import com.bbq.androidknowledgeframework.lib.ui.row.ExRowBaseView
import com.bbq.androidknowledgeframework.lib.ui.row.ViewHolder
import com.bbq.androidknowledgeframework.main.bean.Item
import com.bbq.androidknowledgeframework.main.bean.MenuType

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
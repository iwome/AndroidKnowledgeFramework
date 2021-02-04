package com.bbq.akf.project.main.row

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bbq.akf.R
import com.bbq.akf.lib.ui.row.ExRowBaseView
import com.bbq.akf.lib.ui.row.ViewHolder
import com.bbq.akf.project.main.bean.MenuType
import com.bbq.akf.project.main.bean.Title

/**
 * 说明
 * Created by bangbang.qiu on 2021/2/4.
 */
class TitleRow(cTitle: Title, cContext: Context): ExRowBaseView() {

    private val title = cTitle
    private val context = cContext

    override fun getViewType(): Int {
        return MenuType.Title.ordinal
    }

    override fun onBindViewHolder(vh: ViewHolder?, position: Int) {
        vh?.setText(R.id.tv_title, title.name)
    }

    override fun getViewHolder(vg: ViewGroup?): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_main_menu_title, vg, false), context
        )
    }
}
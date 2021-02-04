package com.bbq.androidknowledgeframework.main.bean

/**
 * 说明
 * Created by bangbang.qiu on 2021/2/4.
 */
enum class MenuType(i: Int) {
    Item(0), Title(1)
}

data class Item(val name: String)
data class Title(val name: String)
data class Menu(val title: Title, val item: List<Item>)
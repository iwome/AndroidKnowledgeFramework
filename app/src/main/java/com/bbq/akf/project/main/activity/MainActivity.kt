package com.bbq.akf.project.main.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bbq.akf.R
import com.bbq.akf.project.main.adapter.MenuAdapter
import com.bbq.akf.project.main.bean.Item
import com.bbq.akf.project.main.bean.Menu
import com.bbq.akf.project.main.bean.Title
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = MenuAdapter(baseContext)
        recyclerview.adapter = adapter
        adapter.setData(getMenuList())
    }

    //todo mock
    private fun getMenuList(): List<Menu> {
        val title = Title("KOTLIN")
        val items = listOf<Item>(Item("类与对象"), Item("集合"), Item("协程"), Item("lambda"))
        return listOf(Menu(title, items))
    }
}
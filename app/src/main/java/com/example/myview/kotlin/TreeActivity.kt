package com.example.myview.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.myview.R
import com.example.myview.view.WaterView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.activity_tree.*

class TreeActivity : AppCompatActivity() {

    var waterView: WaterView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tree)
        //initView()
    }

//    fun initView() {
//        var layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//        layoutParams.gravity = Gravity.CENTER
//        waterView = WaterView(this)
//        waterView!!.layoutParams = layoutParams
//        frameLayout.removeAllViews()
//        frameLayout.addView(waterView)
//    }
}
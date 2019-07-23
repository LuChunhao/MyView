package com.example.myview.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.myview.R
import kotlinx.android.synthetic.main.activity_kotlin_first.*
import kotlinx.android.synthetic.main.item_text.view.*

class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_first)

        kotlinText.setOnClickListener {
            kotlinText.text = "This is my first kotlin activity"
        }
    }
}
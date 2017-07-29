package com.hail.kotlindemo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hail.kotlindemo.R

class SeeWebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_web_view)
        initView()
    }

    private fun initView() {
        var url = intent.getStringExtra("url")
        var desc = intent.getStringExtra("desc")

    }
}

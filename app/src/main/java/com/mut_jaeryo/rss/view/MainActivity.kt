package com.mut_jaeryo.rss.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.mut_jaeryo.rss.R
import com.mut_jaeryo.rss.data.RssViewModel

class MainActivity : AppCompatActivity() {


    val viewModel by lazy { ViewModelProviders.of(this).get(RssViewModel::class.java)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

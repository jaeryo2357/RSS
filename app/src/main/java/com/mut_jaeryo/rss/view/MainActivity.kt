package com.mut_jaeryo.rss.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.mut_jaeryo.rss.R
import com.mut_jaeryo.rss.data.RssViewModel
import com.mut_jaeryo.rss.databinding.ActivityMainBinding
import com.mut_jaeryo.rss.util.RssAdapter

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.run {
            lifecycleOwner = this@MainActivity
            vm = ViewModelProviders.of(this@MainActivity).get(RssViewModel::class.java)
            this.rssRecycler.adapter = RssAdapter()
        }
    }
}

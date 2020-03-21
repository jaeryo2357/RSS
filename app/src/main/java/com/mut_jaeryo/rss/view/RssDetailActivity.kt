package com.mut_jaeryo.rss.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.mut_jaeryo.rss.R
import com.mut_jaeryo.rss.data.RssData
import com.mut_jaeryo.rss.data.RssViewModel
import com.mut_jaeryo.rss.databinding.ActivityRssDetailBinding

class RssDetailActivity : AppCompatActivity() {

    private val binding:ActivityRssDetailBinding by lazy { DataBindingUtil.setContentView(this,R.layout.activity_rss_detail) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.item = intent.getSerializableExtra("item") as RssData
    }

    override fun onBackPressed() {
        if(binding.rssDetailWeb.canGoBack())
            binding.rssDetailWeb.goBack()
        else
            finish()
    }
}

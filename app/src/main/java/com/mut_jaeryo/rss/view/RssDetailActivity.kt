package com.mut_jaeryo.rss.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.mut_jaeryo.rss.R
import com.mut_jaeryo.rss.data.RssData
import com.mut_jaeryo.rss.databinding.ActivityRssDetailBinding

class RssDetailActivity : AppCompatActivity() {

    lateinit var binding:ActivityRssDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_rss_detail)
        binding.item = intent.getSerializableExtra("item") as RssData
    }

    override fun onBackPressed() {
        if(binding.rssDetailWeb.canGoBack())
            binding.rssDetailWeb.goBack()
        else
            finish()
    }
}

package com.mut_jaeryo.rss.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.mut_jaeryo.rss.R
import com.mut_jaeryo.rss.data.RssData
import com.mut_jaeryo.rss.data.RssViewModel
import com.mut_jaeryo.rss.databinding.ActivityMainBinding
import com.mut_jaeryo.rss.util.RssAdapter

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.run {
            lifecycleOwner = this@MainActivity
            val viewModel : RssViewModel = ViewModelProviders.of(this@MainActivity).get(RssViewModel::class.java)
            viewModel.loadRssToUrl(applicationContext.getString(R.string.rss_url))
            vm = viewModel
            this.rssRecycler.adapter = RssAdapter().apply {
                listener = object : RssAdapter.RssItemClickEvent{
                    override fun onClickItem(view: View, data:RssData){
                        val intent : Intent = Intent(this@MainActivity,RssDetailActivity::class.java)
                        intent.putExtra("item",data)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}

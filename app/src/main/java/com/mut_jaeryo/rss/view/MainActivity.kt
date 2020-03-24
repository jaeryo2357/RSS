package com.mut_jaeryo.rss.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mut_jaeryo.rss.R
import com.mut_jaeryo.rss.data.RssData
import com.mut_jaeryo.rss.data.RssViewModel
import com.mut_jaeryo.rss.databinding.ActivityMainBinding
import com.mut_jaeryo.rss.util.RssAdapter
import com.mut_jaeryo.rss.util.isInternetAvailable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private val viewModel: RssViewModel by lazy {
        ViewModelProviders.of(this@MainActivity).get(RssViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.run {
            lifecycleOwner = this@MainActivity
            vm = viewModel
            this.rssRecycler.adapter = RssAdapter().apply {
                listener = object : RssAdapter.RssItemClickEvent {
                    override fun onClickItem(view: View, data: RssData) {
                        val intent: Intent =
                            Intent(this@MainActivity, RssDetailActivity::class.java)
                        intent.putExtra("item", data)
                        startActivity(intent)
                    }
                }
            }
        }
        //Internet Check
        if(isInternetAvailable(applicationContext))
            viewModel.loadRssToUrl(applicationContext.getString(R.string.rss_url))
        else
            Toast.makeText(this@MainActivity,getString(R.string.internet_error),Toast.LENGTH_LONG).show()
        main_swipe_layout.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        if(!isInternetAvailable(applicationContext))
            Toast.makeText(this@MainActivity,getString(R.string.internet_error),Toast.LENGTH_LONG).show()
        else
            viewModel.onRssRefresh()
        main_swipe_layout.isRefreshing = false
    }
}

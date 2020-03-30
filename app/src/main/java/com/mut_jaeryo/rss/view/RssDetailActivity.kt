package com.mut_jaeryo.rss.view

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.mut_jaeryo.rss.R
import com.mut_jaeryo.rss.data.RssData
import com.mut_jaeryo.rss.databinding.ActivityRssDetailBinding
import kotlinx.android.synthetic.main.activity_rss_detail.*

class RssDetailActivity : AppCompatActivity() {

    lateinit var binding:ActivityRssDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_rss_detail)

        rss_detail_web.webViewClient = object : WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progressBar.visibility = View.VISIBLE
            }
            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = View.INVISIBLE
            }
        }
        binding.item = intent.getSerializableExtra("item") as RssData
    }

    override fun onBackPressed() {
        if(binding.rssDetailWeb.canGoBack())
            binding.rssDetailWeb.goBack()
        else
            finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

package com.mut_jaeryo.rss.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mut_jaeryo.rss.R
import com.mut_jaeryo.rss.data.RssData
import com.mut_jaeryo.rss.data.RssViewModel
import com.mut_jaeryo.rss.databinding.ActivityMainBinding
import com.mut_jaeryo.rss.util.RssAdapter
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

    fun isInternetAvailable(context:Context):Boolean{
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }
        return result
    }
}

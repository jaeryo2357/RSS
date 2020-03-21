package com.mut_jaeryo.rss.util

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.BindingAdapter

@BindingAdapter("app:loadUrl")
fun loadUrl(view:WebView,url:String?) {
    url?.let {
        view.run {
            settings.javaScriptEnabled = true
            loadUrl(url)
            webChromeClient = WebChromeClient()
            webViewClient = WebViewClient()
        }
    }
}
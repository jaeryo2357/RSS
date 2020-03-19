package com.mut_jaeryo.rss.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mut_jaeryo.rss.data.RssData

@BindingAdapter("app:replaceAll")
fun RecyclerView.replaceAll(list: List<RssData>?) {
    (this.adapter as? RssAdapter)?.run {
        list?.let {
            this.replaceAll(it)
        }
        notifyDataSetChanged()
    }
}
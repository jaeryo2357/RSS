package com.mut_jaeryo.rss.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("app:replaceAll")
fun RecyclerView.replaceAll(list: List<Any>?) {
    (this.adapter as? RssAdapter)?.run {
        replaceAll(list)
        notifyDataSetChanged()
    }
}
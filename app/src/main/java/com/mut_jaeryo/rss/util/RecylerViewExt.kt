package com.mut_jaeryo.rss.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.mut_jaeryo.rss.R
import com.mut_jaeryo.rss.data.RssData

//@BindingAdapter("app:replaceAll")
//fun RecyclerView.replaceAll(list: List<RssData>?) {
//    (this.adapter as? RssAdapter)?.run {
//        list?.let {
//            this.replaceAll(it)
//        }
//    }
//}

@BindingAdapter("app:addItem")
fun RecyclerView.addItem(item: ArrayList<RssData>?) {
    (this.adapter as? RssAdapter)?.run {
        item?.let {
            if(item.size>0)
            this.addItem(it[it.size-1])
        }
    }
}

@BindingAdapter("app:bindUri")
fun loadImage(view: ImageView, uri : String?) {
    uri?.let {
        Glide.with(view.context)
            .load(it)
            .thumbnail(0.1f)
            .dontAnimate()
            .centerCrop()
            .error(R.drawable.ic_launcher_background)
            .into(view)
    }
}

@BindingAdapter("app:bindKeyword")
fun addKeyword(view:ChipGroup,array : Array<String>?)
{
    array?.let {
        for (keyword in it) {
            if (keyword != "") {
                val chip = Chip(view.context).apply {
                    text = keyword
                    textSize = 12f
                }
                view.addView(chip)
            }
        }
    }
}

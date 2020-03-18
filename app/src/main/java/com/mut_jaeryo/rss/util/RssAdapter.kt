package com.mut_jaeryo.rss.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mut_jaeryo.rss.BR
import com.mut_jaeryo.rss.R
import com.mut_jaeryo.rss.data.RssData
import com.mut_jaeryo.rss.databinding.RssListBinding
import java.lang.Exception

class RssAdapter : RecyclerView.Adapter<RssAdapter.ViewHolder>()
{
    private val items = mutableListOf<RssData>()

    fun replaceAll(items: List<RssData>?)
    {
        items?.let {
            this.items.run {
                clear()
                addAll(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        object : ViewHolder(
            layoutResId = R.layout.rss_list,
            parent = parent
        ){}

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.onBindViewHolder(items[position])
    }


    abstract class ViewHolder(
        @LayoutRes layoutResId:Int,
        parent:ViewGroup
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(layoutResId,parent,false)
    ){

        private val binding: RssListBinding = DataBindingUtil.bind(itemView)!!

        fun onBindViewHolder(item:RssData?)
        {
            try {
                item?.let {
                    binding.item = item
                }
            }catch (e:Exception) {
                e.printStackTrace()
            }
        }
    }
}
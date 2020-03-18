package com.mut_jaeryo.rss.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RssViewModel : ViewModel(){

    private val _rssList = MutableLiveData<List<RssData>>()

    val rssList : LiveData<List<RssData>> = _rssList

    fun onRssRefresh(){ //_rssList 값 새로 불러오기

    }

}
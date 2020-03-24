package com.mut_jaeryo.rss.data

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mut_jaeryo.rss.R
import com.mut_jaeryo.rss.util.RssParser
import com.mut_jaeryo.rss.util.findValue
import kotlinx.coroutines.*
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import java.io.IOException
import java.net.Inet4Address
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory

class RssViewModel() : ViewModel(){

//    private val _rssList = MutableLiveData<List<RssData>>()
//    val rssList : LiveData<List<RssData>> = _rssList
    val rssList =  ObservableArrayList<RssData>()
    var url:String? = null

    private val job = Job()
    private val rssScope = CoroutineScope(Dispatchers.Main + job)

    fun loadRssToUrl(url:String) {
        this.url = url
        onRssRefresh()
    }

    override fun onCleared() { //viewModel이 소멸될 때, 코루틴도 종료
        super.onCleared()
        job.cancel()
    }


    fun onRssRefresh(){ //_rssList 값 새로 불러오기
        rssList.clear()
        rssScope.launch(Dispatchers.Main) { //UI 확인을 위해 메인쓰레드에서 실행
            val nodeList = withContext(Dispatchers.IO) {
               RssParser.fetchItemInRss(url!!)
            } //NodeList 객체를 반환
            nodeList?.let {
                val items = withContext(Dispatchers.Default){
                   RssParser.findValueInNode(it)
                }
                for(item in items) {
                    launch(Dispatchers.IO) {
                        RssParser.fetchContentInUrl(item)
                        launch(Dispatchers.Main){
                            rssList.add(item)
                        }
                    }
                }
            }
        }
    }
}
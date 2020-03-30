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

    private val _parseEnd = MutableLiveData<Boolean>(true)
    val rssList =  ObservableArrayList<RssData>()
    var url:String? = null
    var parseEnd : LiveData<Boolean> = _parseEnd

    private val job = Job()
    private val rssScope = CoroutineScope(Dispatchers.Main + job)

    fun loadRssToUrl(url:String) {
        this.url = url
        onRssRefresh()
    }

    override fun onCleared() { //viewModel 이 소멸될 때, 코루틴도 종료
        super.onCleared()
        job.cancel()
    }


    fun onRssRefresh(){ //_rssList 값 새로 불러오기
        rssList.clear()
        _parseEnd.postValue(false)
        rssScope.launch(Dispatchers.Main) { //UI 확인을 위해 메인쓰레드에서 실행
            val nodeList = withContext(Dispatchers.IO) {
       //         Log.d("RssTest","xml 파싱 시작")
               RssParser.fetchItemInRss(url!!)
            } //NodeList 객체를 반환
       //     Log.d("RssTest","nodeList 반환 완료")
            nodeList?.let {
                for(index in 0 until nodeList.length) {
                    launch {
               //         Log.d("RssTest","nodeList $index item 생성")
                        val item = RssParser.findValueInNode(nodeList.item(index) as Element)
                        launch(Dispatchers.IO) {
               //             Log.d("RssTest","nodeList $index jsoup 시작")
                            RssParser.fetchContentInUrl(item)
                            launch(Dispatchers.Main){
               //                 Log.d("RssTest","nodeList $index item 추가")
                                _parseEnd.postValue(true)
                                rssList.add(item)
                            }
                        }
                    }
                }
            }
        }
    }
}
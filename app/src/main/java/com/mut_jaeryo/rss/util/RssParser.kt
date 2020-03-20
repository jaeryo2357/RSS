package com.mut_jaeryo.rss.util

import android.util.DisplayMetrics
import android.util.Log
import com.mut_jaeryo.rss.data.RssData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import java.io.IOException
import java.lang.Exception
import java.net.SocketTimeoutException
import java.net.URL
import javax.net.ssl.SSLException
import javax.xml.parsers.DocumentBuilderFactory

object RssParser {
    suspend fun findValueInNode(nodeList: NodeList) : List<RssData>
    {
        val list = ArrayList<RssData>()
        for(index in 0 until nodeList.length)
        {
            val item = nodeList.item(index) as Element
//                        val title = item.getElementsByTagName("title").item(0).firstChild.nodeValue
            val title = item.findValue("title")
            val contentUrl = item.findValue("link")

       //     Log.d("RssTest item Title",title)
            list.add(RssData(title,contentUrl))
        }
        return list
    }

    suspend fun fetchContentInUrl(data : RssData)
    {
        try {
            val doc = Jsoup.connect(data.contentUrl).timeout(1000 * 10).get()  //타임아웃 무제한
            val contentData = doc.select("meta[property~=(og:image|og:description)]")

            for (content in contentData) {
                val property = content.attr("property")
                if (property == "og:image")
                    data.imageUrl = content.attr("content") ?: ""//이미지 추출
                else if(property == "og:description"){
                    data.content = content.attr("content") ?: "" // 본문 추출
                    withContext(Dispatchers.Default)
                    {
                        val allWord = data.content!!.split(" ")
                        val map = HashMap<String, Int>()

                        for (word in allWord) {
                            if(word.length >= 2) { //2단어 이상
                                val time = map[word] ?: 0
                                map[word] = time + 1
                            }
                        }
                        data.keywordList = map
                    }
                }
            }
        }catch (e: SSLException){//I/O error system call
            e.printStackTrace()
        }catch (e : SocketTimeoutException){ //사이트 속도 에러
            e.printStackTrace()
        }

    }

    //async는 try catch로 에러처리
    suspend fun fetchItemInRss(address :String) : NodeList?{ //Rss의 Item만 불러온다.
        return try {
            val url = URL(address)
            val builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            val document = builder.parse(url.openStream()).apply {
                documentElement.normalize() //공백등 제거
            }
            val nodeList = document.getElementsByTagName("item")

//            Log.d("RssTest node Size","${nodeList.length}")
            nodeList
        }catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}
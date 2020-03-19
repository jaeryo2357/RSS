package com.mut_jaeryo.rss.util

import android.util.Log
import com.mut_jaeryo.rss.data.RssData
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import java.io.IOException
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory

object RssParser {
    suspend fun findValueInNode(nodeList: NodeList) : List<RssData>
    {
        val list = ArrayList<RssData>()
        for(index in 0..nodeList.length)
        {
            val item = nodeList.item(index) as Element
//                        val title = item.getElementsByTagName("title").item(0).firstChild.nodeValue
            val title = item.findValue("title")
            val contentUrl = item.findValue("link")

            //Log.d("RssTest item Title",title)
        }
        return list
    }


    //async는 try catch로 에러처리
    suspend fun fetchItemInRss() : NodeList?{ //Rss의 Item만 불러온다.
        return try {
            val url = URL("https://news.google.com/rss?hl=ko&gl=KR&ceid=KR:ko")
            val builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            val document = builder.parse(url.openStream()).apply {
                documentElement.normalize() //공백등 제거
            }
            val nodeList = document.getElementsByTagName("item")

            Log.d("RssTest node Size","${nodeList.length}")
            nodeList
        }catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}
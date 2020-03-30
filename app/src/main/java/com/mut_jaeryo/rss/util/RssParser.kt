package com.mut_jaeryo.rss.util

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
import java.util.*
import javax.net.ssl.SSLException
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

object RssParser {
    suspend fun findValueInNode(node: Element): RssData {
        val item: RssData
//       val title = item.getElementsByTagName("title").item(0).firstChild.nodeValue
        val title = node.findValue("title")
        val contentUrl = node.findValue("link")
        //     Log.d("RssTest item Title",title)
        item = RssData(title, contentUrl)

        return item
    }

    suspend fun fetchContentInUrl(data: RssData) {
        try {
            val doc = Jsoup.connect(data.contentUrl).timeout(1000 * 10).get()  //타임아웃 무제한
            val contentData = doc.select("meta[property~=(og:image|og:description)]")

            for (content in contentData) {
                val property = content.attr("property")
                if (property == "og:image")
                    data.imageUrl = content.attr("content") ?: ""//이미지 추출
                else if (property == "og:description") {
                    data.content = content.attr("content") ?: "" // 본문 추출

                    withContext(Dispatchers.Default)
                    {
                        val regex = """\W+""".toRegex()  //\w 문자 or 숫자
                        val allWord = regex.split(data.content!!)
                        val map = HashMap<String, Int>()

                        for (word in allWord) {
                            if (word.length >= 2) { //2단어 이상
                                val time = map[word] ?: 0
                                map[word] = time + 1
                            }
                        }
                        //내림차순 정렬
                        val keyList = ArrayList<String>(map.keys)
                        keyList.sortWith(Comparator { v1, v2 ->
                            val compare = map[v2]!!.compareTo(map[v1]!!)
                            if (compare == 0) v1.compareTo(v2) //빈도수가 동일한 경우 문자열 오름차순
                            else compare
                        })
                        val keywordList = arrayOf("", "", "")

                        for (index in 0 until keyList.size) {
                            if (index > 2) break
                            keywordList[index] = keyList[index]
                        }
                        data.keywordList = keywordList
                    }
                }
            }
        } catch (e: SSLException) {//I/O error system call
            e.printStackTrace()
        } catch (e: SocketTimeoutException) { //사이트 속도 에러
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //async는 try catch로 에러처리
    suspend fun fetchItemInRss(address: String): NodeList? { //Rss의 Item만 불러온다.
        return try {
            val url = URL(address)
            val builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            val document = builder.parse(url.openStream()).apply {
                documentElement.normalize() //공백등 제거
            }
            val nodeList = document.getElementsByTagName("item")
//            Log.d("RssTest node Size","${nodeList.length}")
            nodeList
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}
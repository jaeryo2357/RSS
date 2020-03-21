package com.mut_jaeryo.rss.data

data class RssData(
    val title:String,
    val contentUrl:String
){
    var imageUrl:String? = null
    var content : String? = null //본문의 일부
    var keywordList : Array<String>? = null // 키워드 갯수 저장된 Map
}
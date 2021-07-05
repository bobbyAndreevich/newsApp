package com.example.domain.entities


data class News(val author : String?, val title : String?, val description: String?,
                val url : String, val urlToImage : String?,
                val publishedAt : String?) {
    var stringFilter : String? = null


    private val dateList = publishedAt!!.split('T', '-')
    val stringDate = "${dateList[2]}.${dateList[1]}.${dateList[0]}"
}
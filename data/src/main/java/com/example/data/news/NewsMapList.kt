package com.example.data.news

import com.google.gson.annotations.SerializedName




class NewsMapList {
    @SerializedName("articles")
    var news : List<NewsMap>? = null
}

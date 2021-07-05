package com.example.data.news

import retrofit2.Call


class ApiRepository(private val apiService: NewsService) {

    companion object{
        const val token = "da61334d117442e88022f301d873c9de"
    }

    suspend fun getNews(searchText : String): NewsMapList {
        return apiService.getNews(mapOf<String, String>(Pair("q", searchText), Pair("apiKey", token)))
    }

}
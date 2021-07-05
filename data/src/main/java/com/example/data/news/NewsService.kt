package com.example.data.news


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface NewsService {
    @GET("/v2/everything")
    suspend fun getNews(@QueryMap arg : Map<String, String>) : NewsMapList
}
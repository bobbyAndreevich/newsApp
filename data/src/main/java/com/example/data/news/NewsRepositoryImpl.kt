package com.example.data.news

import android.util.Log
import com.example.data.Database
import com.example.domain.NewsRepository
import com.example.domain.entities.News
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.net.UnknownHostException


class NewsRepositoryImpl(private val database: Database,
                         private val apiRepository: ApiRepository) : NewsRepository {

    private val filters = database.filterDao().getAllFilters()
    private val news = database.filterDao().getAllNews()


    override fun getNews(): Flow<List<News>> {
        return news.map {
            it.map{ NewsMap.toNews(it) }
        }
    }

    init {
        startLoadData()
    }





    private fun startLoadData() {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                while (filters.first().isNotEmpty()) {
                    filters.first()
                        .forEach {
                            getRemoteNews(it.name)
                        }
                    delay(60000)
                    }

                }
            }
        }

    private fun getRemoteNews(filterString: String) {
        GlobalScope.launch {
            withContext(Dispatchers.IO){
                try {
                    val remoteNews = apiRepository.getNews(filterString).news
                    remoteNews!!.forEach {
                        it.stringFilter = filterString
                    }
                    database.filterDao().insertNews(remoteNews.toList())
                }
                catch (e : retrofit2.HttpException){
                    Log.e("http exception", e.message!!)
                }
                catch (e : UnknownHostException){
                    Log.e("no connect", e.message!!)
                }
            }
        }

    }




}
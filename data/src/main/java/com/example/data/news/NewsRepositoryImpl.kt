package com.example.data.news

import android.util.Log
import com.example.data.Database
import com.example.domain.NewsRepository
import com.example.domain.entities.News
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.NewsApiClient.ArticlesResponseCallback
import com.kwabenaberko.newsapilib.models.Article
import com.kwabenaberko.newsapilib.models.request.EverythingRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


class NewsRepositoryImpl(val database: Database,
                         val apiClient: NewsApiClient) : NewsRepository {

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


    private suspend fun getRemoteNews(filterString: String){
        apiClient.getEverything(
            EverythingRequest.Builder()
                .q(filterString)
                .build(),
            object : ArticlesResponseCallback {
                override fun onSuccess(response: ArticleResponse) {
                    GlobalScope.launch {
                        withContext(Dispatchers.IO){
                            database.filterDao()
                                .insertNews(response.articles
                                    .map { toNewsMap(it, filterString) })
                        }
                    }

                }

                override fun onFailure(throwable: Throwable) {
                    Log.e("message", throwable.message!!)
                }
            })
    }

    private fun toNewsMap(article: Article, filterString: String) : NewsMap =
        NewsMap(article.author, article.title, article.description, article.url,
            article.urlToImage, article.publishedAt, filterString)
}
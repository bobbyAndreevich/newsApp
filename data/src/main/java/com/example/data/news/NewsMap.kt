package com.example.data.news

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.entities.News

@Entity
class NewsMap(val author : String?, val title : String?, val description: String?,
              @PrimaryKey val url : String, val urlToImage : String?,
              val publishedAt : String?, val stringFilter : String?) {

    companion object {
        fun toMap(news: News) : NewsMap = NewsMap(news.author, news.title, news.description,
                                                  news.url, news.urlToImage, news.publishedAt, news.stringFilter)
        fun toNews(newsMap: NewsMap) : News = News(newsMap.author, newsMap.title, newsMap.description,
            newsMap.url, newsMap.urlToImage, newsMap.publishedAt,  newsMap.stringFilter)
    }
}

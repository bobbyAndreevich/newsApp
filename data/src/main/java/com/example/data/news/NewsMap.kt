package com.example.data.news

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.entities.News

@Entity
class NewsMap(val author : String?, val title : String?, val description: String?,
              @PrimaryKey val url : String, val urlToImage : String?,
              val publishedAt : String?) {
    var stringFilter : String? = null

    companion object {
        fun toMap(news: News) : NewsMap {
            val map = NewsMap(news.author, news.title, news.description,
                news.url, news.urlToImage, news.publishedAt)
            map.stringFilter = news.stringFilter
            return map
        }
        fun toNews(newsMap: NewsMap) : News {
            val news = News(newsMap.author, newsMap.title, newsMap.description,
                newsMap.url, newsMap.urlToImage, newsMap.publishedAt)
            news.stringFilter = newsMap.stringFilter
            return news
        }
    }
}

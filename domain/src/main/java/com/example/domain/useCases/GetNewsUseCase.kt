package com.example.domain.useCases

import com.example.domain.NewsRepository
import com.example.domain.entities.News
import kotlinx.coroutines.flow.Flow

class GetNewsUseCase(private val newsRepository: NewsRepository) {

    fun getNews(): Flow<List<News>> {
        return newsRepository.getNews()
    }
}
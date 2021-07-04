package com.example.domain

import com.example.domain.entities.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getNews() : Flow<List<News>>
}
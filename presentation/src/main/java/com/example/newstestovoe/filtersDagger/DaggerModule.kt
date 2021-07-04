package com.example.newstestovoe.filtersDagger

import androidx.room.Room
import android.content.Context
import com.example.data.news.NewsRepositoryImpl
import com.example.data.filter.FilterRepositoryImpl
import com.example.data.Database
import com.example.domain.FilterRepository
import com.example.domain.NewsRepository
import com.example.domain.useCases.*
import com.kwabenaberko.newsapilib.NewsApiClient
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class DaggerModule {

    @Provides
    fun provideAddFilterUseCase(filterRepository: FilterRepository) : AddFilterUseCase{
        return AddFilterUseCase(filterRepository, Dispatchers.IO)
    }
    @Provides
    fun provideUpdateFilterUseCase(filterRepository: FilterRepository) : UpdateFilterUseCase{
        return UpdateFilterUseCase(filterRepository, Dispatchers.IO)
    }
    @Provides
    fun provideDeleteFilterUseCase(filterRepository: FilterRepository) : DeleteFilterUseCase {
        return DeleteFilterUseCase(filterRepository, Dispatchers.IO)
    }
    @Provides
    fun provideGetFiltersUseCase(filterRepository: FilterRepository) : GetFiltersUseCase{
        return GetFiltersUseCase(filterRepository, Dispatchers.IO)
    }

    @Provides
    fun provideFilterRepository(database : Database) : FilterRepository{
        return FilterRepositoryImpl(database)
    }

    @Provides
    fun provideNewsRepository(database: Database, apiClient: NewsApiClient) : NewsRepository{
        return NewsRepositoryImpl(database, apiClient)
    }

    @Provides
    fun provideGetNewsUseCase(newsRepository: NewsRepository) : GetNewsUseCase{
        return GetNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideDataBase(context: Context) : Database {
        return Room.databaseBuilder(
            context,
            Database::class.java,
            "context"
        ).build()
    }


    @Provides
    fun provideApiClient() : NewsApiClient{
        return NewsApiClient("f43f4c7eabb642c3a31a5d5200b1f125")
    }
}
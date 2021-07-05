package com.example.newstestovoe.filtersDagger

import androidx.room.Room
import android.content.Context
import com.example.data.filter.FilterRepositoryImpl
import com.example.data.Database
import com.example.data.news.*
import com.example.domain.FilterRepository
import com.example.domain.NewsRepository
import com.example.domain.useCases.*
import com.google.gson.GsonBuilder
import com.kwabenaberko.newsapilib.NewsApiClient
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideNewsRepository(database: Database, apiClient: ApiRepository) : NewsRepository{
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
    fun provideApiRepository(newsService: NewsService ) : ApiRepository{
        return ApiRepository(newsService)
    }

    @Provides
    fun provideNewsService(retrofit: Retrofit) : NewsService{
        return retrofit.create(NewsService::class.java)
    }

    @Provides
    fun provideRetrofit() : Retrofit{
        val okHttpClient = OkHttpClient().newBuilder()
            .build()

        val gson = GsonBuilder().registerTypeAdapter(NewsMap::class.java, NewsMapJsonDeserializer()).create()

        return Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://newsapi.org/").build()
    }
}
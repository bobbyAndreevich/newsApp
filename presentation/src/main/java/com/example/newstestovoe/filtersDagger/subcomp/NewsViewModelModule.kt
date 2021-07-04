package com.example.newstestovoe.filtersDagger.subcomp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.useCases.GetFiltersUseCase
import com.example.domain.useCases.GetNewsUseCase
import com.example.newstestovoe.ui.newsFragment.NewsFragment
import com.example.newstestovoe.ui.newsFragment.NewsViewModel
import dagger.Module
import dagger.Provides

@Module
class NewsViewModelModule(private val newsFragment: NewsFragment) {

    @ViewModelScope
    @Provides
    fun provideNewsViewModel(
        getFiltersUseCase: GetFiltersUseCase,
        getNewsUseCase: GetNewsUseCase
    ): NewsViewModel{
        return ViewModelProvider(newsFragment, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return NewsViewModel(
                    getFiltersUseCase,
                    getNewsUseCase
                ) as T
            }
        }).get(NewsViewModel::class.java)
    }

}
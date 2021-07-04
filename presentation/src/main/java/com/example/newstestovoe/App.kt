package com.example.newstestovoe

import android.app.Application
import com.example.newstestovoe.filtersDagger.ApplicationComponent
import com.example.newstestovoe.filtersDagger.ContextModule
import com.example.newstestovoe.filtersDagger.DaggerApplicationComponent
import com.example.newstestovoe.filtersDagger.subcomp.*
import com.example.newstestovoe.ui.filterFragment.FiltersFragment
import com.example.newstestovoe.ui.newsFragment.NewsFragment
import com.example.newstestovoe.ui.redactorFragment.FilterRedactorFragment

class App : Application() {

    lateinit var applicationComponent : ApplicationComponent
        private set

    lateinit var redactorViewModelComponent: RedactorViewModelComponent
    lateinit var filtersViewModelComponent: FiltersViewModelComponent
    lateinit var newsViewModelComponent: NewsViewModelComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder()
            .contextModule(ContextModule(this)).build()
    }

    fun createViewModelRedactorComponent(filtersRedactorFragment: FilterRedactorFragment){
        redactorViewModelComponent = applicationComponent
            .getRedactorViewModelComponent().requestModule(
                RedactorViewModelModule(filtersRedactorFragment)
            ).build()
    }

    fun createViewModelFiltersComponent(filtersFragment: FiltersFragment){
        filtersViewModelComponent = applicationComponent
            .getFiltersViewModelComponent().requestModule(
                FiltersViewModelModule(filtersFragment)
            ).build()
    }

    fun createViewModelNewsComponent(newsFragment: NewsFragment){
        newsViewModelComponent = applicationComponent
            .getNewsViewModelComponent().requestModule(
                NewsViewModelModule(newsFragment)
            ).build()
    }
}
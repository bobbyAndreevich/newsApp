package com.example.newstestovoe.filtersDagger.subcomp

import com.example.newstestovoe.ui.newsFragment.NewsFragment
import dagger.Subcomponent


@ViewModelScope
@Subcomponent(modules = [NewsViewModelModule::class])
interface NewsViewModelComponent {

    @Subcomponent.Builder
    interface Builder{
        fun requestModule(module: NewsViewModelModule) : Builder
        fun build() : NewsViewModelComponent
    }

    fun injectNewsFragment(newsFragment: NewsFragment)
}
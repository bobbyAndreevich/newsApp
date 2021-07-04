package com.example.newstestovoe.filtersDagger

import com.example.domain.useCases.*
import com.example.newstestovoe.filtersDagger.subcomp.FiltersViewModelComponent
import com.example.newstestovoe.filtersDagger.subcomp.NewsViewModelComponent
import com.example.newstestovoe.filtersDagger.subcomp.RedactorViewModelComponent
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [DaggerModule::class, ContextModule::class])
interface ApplicationComponent {

    fun getAddFilterUseCase() : AddFilterUseCase
    fun getUpdateFilterUseCase() : UpdateFilterUseCase
    fun getDeleteFilterUseCase() : DeleteFilterUseCase
    fun getGetFiltersUseCase() : GetFiltersUseCase
    fun getNewsUseCase() : GetNewsUseCase

    fun getRedactorViewModelComponent() : RedactorViewModelComponent.Builder
    fun getFiltersViewModelComponent() : FiltersViewModelComponent.Builder
    fun getNewsViewModelComponent() : NewsViewModelComponent.Builder
}
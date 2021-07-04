package com.example.newstestovoe.filtersDagger.subcomp

import com.example.newstestovoe.ui.filterFragment.FiltersFragment
import dagger.Subcomponent


@ViewModelScope
@Subcomponent(modules = [FiltersViewModelModule::class])
interface FiltersViewModelComponent {

    @Subcomponent.Builder
    interface Builder {
        fun requestModule(module: FiltersViewModelModule) : Builder
        fun build(): FiltersViewModelComponent
    }

    fun injectFiltersFragment(filtersFragment: FiltersFragment)
}
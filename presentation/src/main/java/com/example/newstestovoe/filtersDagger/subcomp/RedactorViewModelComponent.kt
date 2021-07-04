package com.example.newstestovoe.filtersDagger.subcomp

import com.example.newstestovoe.ui.redactorFragment.FilterRedactorFragment
import dagger.Subcomponent
import javax.inject.Scope

@Scope
annotation class ViewModelScope

@ViewModelScope
@Subcomponent(modules =  [RedactorViewModelModule::class])
interface RedactorViewModelComponent {

    @Subcomponent.Builder
    interface Builder {
        fun requestModule(module: RedactorViewModelModule) : Builder
        fun build() : RedactorViewModelComponent
    }

    fun injectRedactorFragment(filterRedactorFragment: FilterRedactorFragment)
}
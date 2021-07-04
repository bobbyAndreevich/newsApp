package com.example.newstestovoe.filtersDagger.subcomp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.useCases.AddFilterUseCase
import com.example.domain.useCases.UpdateFilterUseCase
import com.example.newstestovoe.ui.redactorFragment.FilterRedactorFragment
import com.example.newstestovoe.ui.redactorFragment.FilterRedactorViewModel
import dagger.Module
import dagger.Provides

@Module
class RedactorViewModelModule(private val redactorFragment: FilterRedactorFragment) {

    @ViewModelScope
    @Provides
    fun provideRedactorViewModel(
        addFilterUseCase: AddFilterUseCase,
        updateFilterUseCase: UpdateFilterUseCase
    ) : FilterRedactorViewModel {

        return ViewModelProvider(redactorFragment, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return FilterRedactorViewModel(
                    addFilterUseCase,
                    updateFilterUseCase
                ) as T
            }
        }).get(FilterRedactorViewModel::class.java)
    }
}
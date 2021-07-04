package com.example.newstestovoe.filtersDagger.subcomp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.useCases.DeleteFilterUseCase
import com.example.domain.useCases.GetFiltersUseCase
import com.example.newstestovoe.ui.filterFragment.FiltersFragment
import com.example.newstestovoe.ui.filterFragment.FiltersViewModel
import dagger.Module
import dagger.Provides

@Module
class FiltersViewModelModule(private val filtersFragment: FiltersFragment) {

    @ViewModelScope
    @Provides
    fun provideFiltersViewModel(
        getFiltersUseCase: GetFiltersUseCase,
        deleteFilterUseCase: DeleteFilterUseCase
    ) : FiltersViewModel{

        return ViewModelProvider(filtersFragment, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return FiltersViewModel(
                    getFiltersUseCase,
                    deleteFilterUseCase
                ) as T
            }
        }).get(FiltersViewModel::class.java)
    }
}
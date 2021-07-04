package com.example.domain.useCases

import com.example.domain.FilterRepository
import kotlinx.coroutines.CoroutineDispatcher

class GetFiltersUseCase (private val filterRepository: FilterRepository,
                         private val dispatcher : CoroutineDispatcher
) {

    fun getFilters() = filterRepository.getFiltersData()

}
package com.example.domain.useCases

import com.example.domain.FilterRepository
import com.example.domain.entities.Filter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UpdateFilterUseCase (private val filterRepository: FilterRepository,
                           private val dispatcher : CoroutineDispatcher
){

    suspend fun updateFilter(filter: Filter){
        withContext(dispatcher){
            filterRepository.updateFilter(filter)
        }
    }
}
package com.example.domain.useCases

import com.example.domain.FilterRepository
import com.example.domain.entities.Filter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AddFilterUseCase(private val filterRepository: FilterRepository,
                       private val dispatcher : CoroutineDispatcher){

    suspend fun addFilter(filter: Filter){
        withContext(dispatcher){
            filterRepository.addFilter(filter)
        }
    }
}
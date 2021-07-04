package com.example.domain.useCases

import com.example.domain.FilterRepository
import com.example.domain.entities.Filter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DeleteFilterUseCase (private val filterRepository: FilterRepository,
                           private val dispatcher : CoroutineDispatcher
){

    suspend fun deleteFilter(filter: Filter){
        withContext(dispatcher){
            filterRepository.deleteFilter(filter)
        }
    }
}
package com.example.domain

import com.example.domain.entities.Filter
import kotlinx.coroutines.flow.Flow

interface FilterRepository {

    fun addFilter(filter: Filter)

    fun deleteFilter(filter: Filter)

    fun updateFilter(filter: Filter)

    fun getFiltersData() : Flow<List<Filter>>
}
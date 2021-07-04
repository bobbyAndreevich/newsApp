package com.example.data.filter

import com.example.data.Database
import com.example.domain.FilterRepository
import com.example.domain.entities.Filter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FilterRepositoryImpl(private val database: Database) : FilterRepository {

    private val filters = database.filterDao().getAllFilters()


    override fun addFilter(filter: Filter) {
        val filterMap = FilterMap.toMap(filter)
        val newId = database.filterDao().insertFilter(filterMap)
        filter.id = newId
    }

    override fun deleteFilter(filter: Filter) {
        val filterMap = FilterMap.toMap(filter)
        database.filterDao().deleteNewsWithFilter(filter.name)
        database.filterDao().deleteFilter(filterMap)
    }

    override fun updateFilter(filter: Filter) {
        val filterMap = FilterMap.toMap(filter)
        database.filterDao().updateFilter(filterMap)
    }

    override fun getFiltersData(): Flow<List<Filter>> = filters.map {
        it.map { FilterMap.toFilter((it))}
    }
}
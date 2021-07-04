package com.example.data.filter

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.entities.Filter

@Entity
class FilterMap(val name : String, val description: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    companion object{
        fun toMap(filter : Filter) : FilterMap {
            val filterMap = FilterMap(filter.name, filter.description)

            if (filter.id != null)
                filterMap.id = filter.id
            return filterMap
        }

        fun toFilter(filterMap: FilterMap) : Filter {
            val filter = Filter(filterMap.name, filterMap.description)
            filter.id = filterMap.id
            return filter
        }
    }
}
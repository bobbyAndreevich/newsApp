package com.example.data.filter

import androidx.room.*
import com.example.data.NewsMap
import kotlinx.coroutines.flow.Flow

@Suppress("AndroidUnresolvedRoomSqlReference")
@Dao
interface DatabaseDao {

    @Query("SELECT * FROM FilterMap")
    fun getAllFilters() : Flow<List<FilterMap>>

    @Query("DELETE FROM NewsMap WHERE stringFilter LIKE :stringFilter")
    fun deleteNewsWithFilter(stringFilter: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFilter(habit: FilterMap) : Long

    @Delete
    fun deleteFilter(habit: FilterMap)

    @Update
    fun updateFilter(habit: FilterMap)

    @Query("SELECT * FROM NewsMap")
    fun getAllNews() : Flow<List<NewsMap>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(newsMap: List<NewsMap>)

    @Delete
    fun deleteNews(newsMap: NewsMap)
}
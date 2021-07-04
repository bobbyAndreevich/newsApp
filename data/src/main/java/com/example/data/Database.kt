package com.example.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.filter.FilterMap
import com.example.data.news.NewsMap

@Database(entities = [NewsMap::class, FilterMap::class], version = 1)
abstract class Database : RoomDatabase(){
    abstract fun filterDao() : DatabaseDao
}
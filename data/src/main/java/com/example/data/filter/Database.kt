package com.example.data.filter

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.NewsMap

@Database(entities = [NewsMap::class, FilterMap::class], version = 1)
abstract class Database : RoomDatabase(){
    abstract fun filterDao() : DatabaseDao
}
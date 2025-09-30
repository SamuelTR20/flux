package com.example.flux.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.flux.data.local.CategoryDao
import com.example.flux.data.local.Converters
import com.example.flux.data.local.LogEventDao
import com.example.flux.data.model.Category
import com.example.flux.data.model.LogEvent

@Database(entities = [Category::class, LogEvent::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun logEventDao(): LogEventDao
}
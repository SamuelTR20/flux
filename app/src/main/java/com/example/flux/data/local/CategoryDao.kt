package com.example.flux.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flux.data.model.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories")
    suspend fun getAll(): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category): Long

    @Delete
    suspend fun delete(category: Category)
}
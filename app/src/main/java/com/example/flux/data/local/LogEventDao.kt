package com.example.flux.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flux.data.model.LogEvent

@Dao
interface LogEventDao {
    @Query("SELECT * FROM log_events ORDER BY date DESC")
    suspend fun getAll(): List<LogEvent>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: LogEvent): Long

    @Query("UPDATE log_events SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Long, status: String)
}
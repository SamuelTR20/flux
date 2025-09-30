package com.example.flux.repository

import com.example.flux.data.local.LogEventDao
import com.example.flux.data.model.LogEvent

class LogEventRepository(private val logEventDao: LogEventDao) {

    suspend fun insertEvent(event: LogEvent): Long {
        return logEventDao.insert(event)
    }

    suspend fun getAllEvents(): List<LogEvent> = logEventDao.getAll()

    suspend fun updateStatus(id: Long, status: String) {
        logEventDao.updateStatus(id, status)
    }

    // Aquí podrías agregar sincronización con Google Sheets, etc.
}
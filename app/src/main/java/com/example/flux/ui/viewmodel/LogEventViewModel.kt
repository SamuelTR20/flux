package com.example.flux.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flux.data.model.LogEvent
import com.example.flux.repository.LogEventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LogEventViewModel(private val repository: LogEventRepository) : ViewModel() {
    private val _logs = MutableStateFlow<List<LogEvent>>(emptyList())
    val logs: StateFlow<List<LogEvent>> = _logs

    init {
        fetchLogs()
    }

    private fun fetchLogs() {
        viewModelScope.launch {
            _logs.value = repository.getAllEvents()
        }
    }
}
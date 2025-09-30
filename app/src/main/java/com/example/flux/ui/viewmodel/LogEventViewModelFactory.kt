package com.example.flux.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flux.repository.LogEventRepository

class LogEventViewModelFactory(
    private val repository: LogEventRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LogEventViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LogEventViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

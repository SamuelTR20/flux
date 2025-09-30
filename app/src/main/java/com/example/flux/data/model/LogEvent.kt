package com.example.flux.data.model

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "log_events")
data class LogEvent(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val date: Long,
    val amount: Double,
    val description: String,
    val categoryId: Long,
    val status: String
)

@Composable
fun IndexView(logs: List<LogEvent>) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(logs) { log ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(text = "Fecha: ${dateFormat.format(Date(log.date))}")
                Text(text = "Descripción: ${log.description}")
                Text(text = "Estado: ${log.status}")
            }
        }
    }
}

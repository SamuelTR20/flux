package com.example.flux.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
    val keywords: List<String> // Puedes serializar/deserializar esta lista con TypeConverter
)
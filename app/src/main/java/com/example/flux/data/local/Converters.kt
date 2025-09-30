package com.example.flux.data.local

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromString(value: String): List<String> = value.split(";")
    @TypeConverter
    fun fromList(list: List<String>): String = list.joinToString(";")
}
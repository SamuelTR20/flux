package com.example.flux.data.database
import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private const val DATABASE_NAME = "flux_db"

    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
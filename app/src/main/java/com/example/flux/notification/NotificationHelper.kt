package com.example.flux.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import android.util.Log

object NotificationHelper {

    private const val CHANNEL_ID = "test_channel_id"
    private const val CHANNEL_NAME = "Test Channel"

    fun sendTestNotification(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH // Importancia alta para banner
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Notificación de prueba")
            .setContentText("Esta notificación aparece como banner")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)  // sonido, vibración y luces
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }
}

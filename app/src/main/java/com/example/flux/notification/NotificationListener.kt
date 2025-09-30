package com.example.flux.notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.room.Room
import com.example.flux.data.database.AppDatabase
import com.example.flux.data.database.DatabaseProvider
import com.example.flux.data.model.LogEvent
import com.example.flux.repository.CategoryRepository
import com.example.flux.repository.LogEventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationListener : NotificationListenerService() {

    private lateinit var logEventRepository: LogEventRepository
    private lateinit var categoryRepository: CategoryRepository

    override fun onCreate() {
        super.onCreate()
        val db = DatabaseProvider.getDatabase(applicationContext)
        logEventRepository = LogEventRepository(db.logEventDao())
        categoryRepository = CategoryRepository(db.categoryDao())
    }


    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        sbn?.let {
            val notification = it.notification
            val extras = notification.extras

            val title = extras.getString("android.title") ?: "Sin título"
            val text = extras.getCharSequence("android.text")?.toString() ?: ""
            val subText = extras.getCharSequence("android.subText")?.toString() ?: ""

            Log.d("NotificationListener", "Notificación de ${it.packageName}:")
            Log.d("NotificationListener", "Título: $title")
            Log.d("NotificationListener", "Texto: $text")
            Log.d("NotificationListener", "Subtexto: $subText")

            // Guardar la notificación como LogEvent con cantidad 0 y estado iniciado
            CoroutineScope(Dispatchers.IO).launch {
                val logEvent = LogEvent(
                    date = System.currentTimeMillis(),
                    amount = 0.0,
                    description = "$title - $text - $subText",
                    categoryId = 0L,
                    status = "iniciado"
                )
                logEventRepository.insertEvent(logEvent)
            }
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
        sbn?.let {
            Log.d("NotificationListener", "Notification Removed: ${it.packageName}")
        }
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        Log.d("NotificationListener", "Notification Listener Connected")

        val activeNotifications = activeNotifications
        activeNotifications.forEach { sbn ->
            val pkg = sbn.packageName
            val title = sbn.notification.extras.getString("android.title")
            val text = sbn.notification.extras.getCharSequence("android.text")
            Log.d("animo", "Notificación activa de $pkg - Título: $title, Texto: $text")
        }
    }
}

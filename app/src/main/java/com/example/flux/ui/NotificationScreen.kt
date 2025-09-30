package com.example.flux.ui

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.flux.notification.NotificationHelper
import com.example.flux.utils.PermissionUtils

@Composable
fun NotificationScreen(onRequestPermission: () -> Unit) {
    val context = LocalContext.current
    var hasNotificationPermission by remember { mutableStateOf(PermissionUtils.checkNotificationPermission(context)) }
    var hasListenerPermission by remember { mutableStateOf(PermissionUtils.isNotificationServiceEnabled(context)) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            if (hasNotificationPermission) {
                NotificationHelper.sendTestNotification(context)
            } else {
                onRequestPermission()
            }
        }) {
            Text(text = "Enviar Notificación de Prueba")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!hasListenerPermission) {
            Button(onClick = {
                val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
                context.startActivity(intent)
            }) {
                Text(text = "Habilitar acceso a notificaciones")
            }
        }
    }
}

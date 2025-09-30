package com.example.flux.ui.view.records

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flux.data.model.IndexView
import com.example.flux.data.model.LogEvent


@Composable
fun LogEventScreen(
    logs: List<LogEvent>,
    hasNotificationPermission: Boolean,
    onRequestPermission: () -> Unit,
    onOpenSettings: () -> Unit,
    onSendTestNotification: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Lista de registros
        if (logs.isNotEmpty()) {
            IndexView(logs = logs)
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay registros disponibles")
            }
        }

        // Botones de notificación (en la parte inferior)
        if (!hasNotificationPermission || true) { // Siempre mostrar por ahora para testing
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                if (!hasNotificationPermission) {
                    Button(
                        onClick = onOpenSettings,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text("Habilitar permisos de notificación")
                    }
                }

                Button(
                    onClick = {
                        if (hasNotificationPermission) {
                            onSendTestNotification()
                        } else {
                            onRequestPermission()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text("Enviar Notificación de Prueba")
                }
            }
        }
    }
}


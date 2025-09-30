package com.example.flux

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flux.data.database.DatabaseProvider
import com.example.flux.notification.NotificationHelper
import com.example.flux.repository.LogEventRepository
import com.example.flux.ui.theme.FluxTheme
import com.example.flux.ui.viewmodel.LogEventViewModel
import com.example.flux.ui.viewmodel.LogEventViewModelFactory
import com.example.flux.ui.view.records.LogEventScreen

class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Permiso concedido", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Permiso para notificaciones denegado", Toast.LENGTH_SHORT).show()
        }
    }

    private var hasNotificationPermission by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkNotificationPermission()
        setContent {
            FluxTheme {
                MainContent()
            }
        }
    }

    private fun checkNotificationPermission() {
        hasNotificationPermission =
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
    }

    @Composable
    fun MainContent() {
        val context = LocalContext.current

        val db = remember { DatabaseProvider.getDatabase(context) }
        val repository = remember { LogEventRepository(db.logEventDao()) }
        val factory = remember { LogEventViewModelFactory(repository) }
        val logViewModel: LogEventViewModel = viewModel(factory = factory)

        val logs by logViewModel.logs.collectAsState()

        Log.d("miau", logs.toString())

        var selectedTab by remember { mutableIntStateOf(0) }

        Scaffold(
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Records") },
                        label = { Text("Records") }
                    )
                    NavigationBarItem(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        icon = { Icon(Icons.Outlined.Folder, contentDescription = "Categories") },
                        label = { Text("Categories") }
                    )
                    NavigationBarItem(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        icon = { Icon(Icons.Outlined.Settings, contentDescription = "Settings") },
                        label = { Text("Settings") }
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (selectedTab) {
                    0 -> {
                        LogEventScreen(
                            logs = logs,
                            hasNotificationPermission = hasNotificationPermission,
                            onRequestPermission = { requestNotificationPermission() },
                            onOpenSettings = {
                                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                                    putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                                }
                                startActivity(intent)
                            },
                            onSendTestNotification = { NotificationHelper.sendTestNotification(context) }
                        )
                    }
                    1 -> {
                        Text("Categories Screen", modifier = Modifier.align(Alignment.Center))
                    }
                    2 -> {
                        Text("Settings Screen", modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }

    @Composable
    fun INFO(x0: String, x1: String) {
        TODO("Not yet implemented")
    }

    private fun requestNotificationPermission() {
        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
}

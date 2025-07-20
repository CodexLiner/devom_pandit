package com.devom.app

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.devom.app.firebase.FirebaseAuthenticationManager
import com.russhwolf.settings.set
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.init

class AppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { App() }
        FirebaseAuthenticationManager.init(this)
        requestNotificationPermission()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        FileKit.init(this)
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                println("Notification permission already granted")
                requestContactsPermission()
            }
        } else {
            requestContactsPermission()
        }
    }

    private fun requestContactsPermission() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            contactsPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
        } else {
            println("Contacts permission already granted")
        }
    }

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            settings[NOTIFICATION_PERMISSION_GRANTED] = isGranted
            requestContactsPermission() // Request contacts after notification result
        }

    private val contactsPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            // Handle contacts permission result if needed
        }
}

@Preview
@Composable
fun AppPreview() {
    App()
}

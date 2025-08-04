package com.devom.pandit.firebase

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import co.touchlab.kermit.Logger
import com.devom.pandit.NOTIFICATION_PERMISSION_GRANTED
import com.devom.pandit.settings
import com.devom.pandit.R
import com.devom.pandit.UNREAD_NOTIFICATION
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.russhwolf.settings.set


class AndroidFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Logger.d("FIREBASE_ACCESS_TOKEN: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Logger.d("FIREBASE_NOTIFICATION: ")
        message.notification?.let {
            settings[UNREAD_NOTIFICATION] = true
            MyFirebaseMessagingService.onNewNotification.invoke()
            if (settings.getBoolean(NOTIFICATION_PERMISSION_GRANTED, true)) showNotification(it.title, it.body)
        }
    }

    private fun showNotification(title: String?, message: String?) {
        val builder =
            NotificationCompat.Builder(this, "devom_pandit_app_channel").setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setContentTitle(title ?: "Title").setContentText(message ?: "Message")
                .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager.notify(0, builder.build())
    }

}

actual object MyFirebaseMessagingService {
    actual var onNewNotification: (() -> Unit) = {}
    actual fun getToken(onToken: (String , String) -> Unit) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    onToken(token , "ANDROID")
                }
        }
    }
}
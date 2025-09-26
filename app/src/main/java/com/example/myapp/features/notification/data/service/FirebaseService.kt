package com.example.myapp.features.notification.data.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

import androidx.core.app.NotificationManagerCompat
import com.example.myapp.MainActivity

class FirebaseService : FirebaseMessagingService () {
    companion object {
        val TAG = FirebaseService::class.java.simpleName

        private const val CHANNEL_ID = "notification_channel"//
        private const val NOTIFICATION_ID = 1//
    }


    //

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }
//    override fun onNewToken(token: String) {
//        Log.d(TAG, "Refreshed token: $token")
//
//        // If you want to send messages to this application instance or
//        // manage this apps subscriptions on the server side, send the
//        // FCM registration token to your app server.
//        sendRegistrationToServer(token)
//    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) { // ...
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        val targetScreen = remoteMessage.data["target_screen"]
        val extraData = remoteMessage.data["extra_data"]

        Log.d(TAG, "Target screen: $targetScreen")
        Log.d(TAG, "Extra data: $extraData")

        // Mostrar notificación con datos de navegación
        val title = remoteMessage.notification?.title ?: "Notificación"
        val body = remoteMessage.notification?.body ?: ""

        showNotification(title, body, targetScreen, extraData)
        // Also if you intend on generating your own notifications as a result of a received FCM
// message, here is where that should be initiated. See sendNotification method below.

    }

    private fun showNotification(
        title: String,
        body: String,
        targetScreen: String?,
        extraData: String?
    ) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP

            // Agregar datos de navegación al intent
            targetScreen?.let { putExtra("target_screen", it) }
            extraData?.let { putExtra("extra_data", it) }
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Cambia por tu icono
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notificaciones de la App"
            val descriptionText = "Canal para notificaciones push"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")
        // Aquí puedes enviar el token a tu servidor si es necesario
    }

}

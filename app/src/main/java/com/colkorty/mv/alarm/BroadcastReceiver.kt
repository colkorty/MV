package com.colkorty.mv.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.colkorty.mv.R
import com.colkorty.mv.main.ROULETTE_ITEMS_1
import com.colkorty.mv.main.ROULETTE_ITEMS_2
import com.colkorty.mv.main.ROULETTE_ITEMS_3

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "cute_notification_channel"

        val channel = NotificationChannel(
            channelId,
            "Cute Notifications",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Не забывай, что ты:")
            .setContentText(ROULETTE_ITEMS_1.random() + " " + ROULETTE_ITEMS_2.random() + " " + ROULETTE_ITEMS_3.random())
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }
}
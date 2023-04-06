package com.example.bootscounter.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.bootscounter.R
import com.example.bootscounter.database.AppDatabase
import kotlinx.coroutines.coroutineScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NotificationWorker(private val context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams), KoinComponent {

    private val database: AppDatabase by inject()

    private val bootDao = database.bootDao()

    private val tag = NotificationWorker::class.java.name

    override suspend fun doWork(): Result = coroutineScope {
        try {
            showNotification(prepareNotificationMassage())
            Result.success()
        } catch (e: Exception) {
            Log.d(tag, "Failure: ${e.message}")
            Result.failure()
        }
    }

    private fun showNotification(message: String) {
        val notificationBuilder = NotificationCompat
            .Builder(context, context.getString(R.string.default_notification_channel_id))
            .setContentTitle(message)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        NotificationManagerCompat.from(context).notify(0, notificationBuilder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            context.getString(R.string.default_notification_channel_id),
            context.getString(R.string.app_name),
            NotificationManager.IMPORTANCE_HIGH
        )
        context.getSystemService(NotificationManager::class.java)
            ?.createNotificationChannel(channel)
    }

    private fun prepareNotificationMassage(): String {
        val bootList = bootDao.getAll()
        return when (bootList.size) {
            0 -> context.getString(R.string.no_boots_detected)
            1 -> context.getString(R.string.detected_boots) + "${bootList.last().timestamp}"
            else -> context.getString(R.string.last_boots_delta) +
                    (bootList[bootList.size - 1].timestamp - bootList[bootList.size - 2].timestamp).toString()
        }
    }
}
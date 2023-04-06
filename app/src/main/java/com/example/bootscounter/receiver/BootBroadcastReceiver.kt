package com.example.bootscounter.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.bootscounter.database.AppDatabase
import com.example.bootscounter.database.model.Boot
import com.example.bootscounter.worker.NotificationWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.TimeUnit

class BootBroadcastReceiver : BroadcastReceiver(), KoinComponent {

    private val database: AppDatabase by inject()
    private val bootDao = database.bootDao()
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            saveBootEvent()
            context?.let {
                scheduleNotification(it)
            }
        }
    }

    private fun scheduleNotification(context: Context) {
        val workManager = WorkManager.getInstance(context)
        val work = PeriodicWorkRequestBuilder<NotificationWorker>(15, TimeUnit.MINUTES)
        workManager.enqueue(work.build())
    }

    private fun saveBootEvent() {
        scope.launch {
            bootDao.insert(Boot(System.currentTimeMillis()))
        }
    }

}
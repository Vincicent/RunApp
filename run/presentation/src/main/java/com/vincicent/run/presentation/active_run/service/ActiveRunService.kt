package com.vincicent.run.presentation.active_run.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.core.net.toUri
import com.vincicent.core.presentation.ui.formatted
import com.vincicent.run.domain.RunningTracker
import com.vincicent.run.presentation.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

class ActiveRunService: Service() {

    private val notificationManager by lazy {
        getSystemService<NotificationManager>()!!
    }

    private val baseNotification by lazy {
        NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(com.vincicent.core.presentation.designsystem.R.drawable.logo)
            .setContentTitle(getString(R.string.active_run))
            .setOnlyAlertOnce(true)
    }

    private val runningTracker by inject<RunningTracker>()

    private var serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        when(intent?.action) {
            ACTION_START -> {
                val activityClass = intent.getStringExtra(EXTRA_ACTIVITY_CLASS)
                    ?: throw IllegalArgumentException("No activity class provided")

                start(Class.forName(activityClass))
            }
            ACTION_STOP -> stop()
        }
        return START_STICKY
    }

    private fun start(activityClass: Class<*>) {
        if (!isServiceActive) {
            isServiceActive = true
            createNotificationChannel()
        }

        val activityIntent = Intent(applicationContext, activityClass).apply {
            data = DEEPLINK_ACTIVE_RUN.toUri()
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        val pendingIntent = TaskStackBuilder.create(applicationContext).run {
            addNextIntentWithParentStack(activityIntent)
            getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
        }
        val notification = baseNotification
            .setContentText("00:00:00")
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)

        updateNotification()
    }

    fun stop() {
        stopSelf()
        isServiceActive = false
        serviceScope.cancel()

        serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    }

    private fun updateNotification() {
        runningTracker.elapsedTime.onEach { elapsedTime ->
            val notification = baseNotification
                .setContentText(elapsedTime.formatted())
                .build()

            notificationManager.notify(1, notification)
        }.launchIn(serviceScope)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =  NotificationChannel(
                CHANNEL_ID,
                getString(R.string.active_run),
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        var isServiceActive = false
        private const val CHANNEL_ID = "active_run"
        const val DEEPLINK_ACTIVE_RUN = "runapp://active_run"

        private const val ACTION_START = "ACTION_START"
        private const val ACTION_STOP = "ACTION_STOP"

        private const val EXTRA_ACTIVITY_CLASS = "EXTRA_ACTIVITY_CLASS"

        fun createStartIntent(context: Context, activityClass: Class<*>): Intent {
            return Intent(context, ActiveRunService::class.java).apply {
                action = ACTION_START
                putExtra(EXTRA_ACTIVITY_CLASS, activityClass.name)
            }
        }

        fun createStopIntent(context: Context): Intent {
            return Intent(context, ActiveRunService::class.java).apply {
                action = ACTION_STOP
            }
        }
    }
}
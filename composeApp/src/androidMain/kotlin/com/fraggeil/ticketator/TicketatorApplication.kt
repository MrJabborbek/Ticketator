package com.fraggeil.ticketator

import android.app.Application
import com.fraggeil.ticketator.core.data.di.initKoin
import com.mmk.kmpnotifier.notification.NotificationImage
import com.mmk.kmpnotifier.notification.Notifier
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.PayloadData
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import org.koin.android.ext.koin.androidContext
import kotlin.random.Random

class TicketatorApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin{
            androidContext(this@TicketatorApplication)
        }

        NotifierManager.initialize(
            configuration = NotificationPlatformConfiguration.Android(
                notificationIconResId = R.drawable.ticketator_logo,
                showPushNotification = true
            )
        )
    }
}
package com.fraggeil.ticketator

import android.app.Application
import com.fraggeil.ticketator.core.data.di.initKoin
import org.koin.android.ext.koin.androidContext

class TicketatorApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin{
            androidContext(this@TicketatorApplication)
        }
    }
}
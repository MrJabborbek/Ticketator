package com.fraggeil.ticketator.core.data.di

import com.fraggeil.ticketator.core.domain.DialPhoneNumber
import com.fraggeil.ticketator.core.domain.OpenUrlInBrowser
import com.fraggeil.ticketator.core.domain.imageLoader
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single { androidApplication() }
        single { DialPhoneNumber(get()) }
        single { OpenUrlInBrowser(get()) }
        single { imageLoader(get()) }
    }
package com.fraggeil.ticketator.core.data.di

import coil3.PlatformContext
import com.fraggeil.ticketator.core.domain.DialPhoneNumber
import com.fraggeil.ticketator.core.domain.OpenUrlInBrowser
import com.fraggeil.ticketator.core.domain.imageLoader
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single { PlatformContext.INSTANCE }
        single { DialPhoneNumber() }
        single { OpenUrlInBrowser() }
        single { imageLoader(get()) }
    }
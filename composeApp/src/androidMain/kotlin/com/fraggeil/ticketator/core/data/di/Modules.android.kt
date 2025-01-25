package com.fraggeil.ticketator.core.data.di

import com.fraggeil.ticketator.core.domain.DialPhoneNumber
import com.fraggeil.ticketator.core.data.LocationService
import com.fraggeil.ticketator.core.data.network.HttpClientFactory
import com.fraggeil.ticketator.core.domain.OpenUrlInBrowser
import com.fraggeil.ticketator.core.domain.imageLoader
import com.fraggeil.ticketator.data.database.DatabaseFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single { androidApplication() }
        single { DialPhoneNumber(get()) }
        single { OpenUrlInBrowser(get()) }
        single { imageLoader(get()) }
        single { DatabaseFactory(get()) }
        single { LocationService(get()) }
        single<HttpClientEngine> { OkHttp.create() }
        single { HttpClientFactory.create(get()) }

    }
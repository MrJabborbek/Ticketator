package com.fraggeil.ticketator.core.data.di

import coil3.PlatformContext
import com.fraggeil.ticketator.core.data.CustomGeocoder
import com.fraggeil.ticketator.core.domain.DialPhoneNumber
import com.fraggeil.ticketator.core.data.LocationService
import com.fraggeil.ticketator.core.data.network.HttpClientFactory
import com.fraggeil.ticketator.core.domain.OpenUrlInBrowser
import com.fraggeil.ticketator.core.domain.geocoder.network.KtorRemotePlacesDataSource
import com.fraggeil.ticketator.core.domain.geocoder.network.RemotePlacesDataSource
import com.fraggeil.ticketator.core.domain.imageLoader
import com.fraggeil.ticketator.data.database.DatabaseFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single { PlatformContext.INSTANCE }
        single { DialPhoneNumber() }
        single { OpenUrlInBrowser() }
        single { imageLoader(get()) }
        single { DatabaseFactory() }
        single { LocationService() }
        single<HttpClientEngine> { OkHttp.create() }
        single { HttpClientFactory.create(get()) }

        singleOf(::KtorRemotePlacesDataSource).bind<RemotePlacesDataSource>()
        single { CustomGeocoder(
            apiKey = "AIzaSyBetCt0piP5xjqn9Hj3LBKI1MdYxR1ZjjE",
            remotePlacesDataSource = get()
        ) }

    }
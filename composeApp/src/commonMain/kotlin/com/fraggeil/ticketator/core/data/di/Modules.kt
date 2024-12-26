package com.fraggeil.ticketator.core.data.di

import com.fraggeil.ticketator.core.domain.Platform
import com.fraggeil.ticketator.presentation.screen.HomeViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
//    single {
//        get<ImageLoader>().create()
//    }
//    single { ::DefaultCategoriesRepository }
    single { Platform() }
//    singleOf(::DefaultCategoriesRepository).bind<CategoriesRepository>()


    viewModelOf(::HomeViewModel)
}
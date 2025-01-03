package com.fraggeil.ticketator.core.data.di

import androidx.compose.material3.SnackbarHostState
import com.fraggeil.ticketator.core.domain.Platform
import com.fraggeil.ticketator.presentation.SelectedPostViewModel
import com.fraggeil.ticketator.presentation.screens.home_screen.HomeViewModel
import com.fraggeil.ticketator.presentation.screens.post_screen.PostViewModel
import com.fraggeil.ticketator.presentation.screens.start_screen.StartViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
//    single {
//        get<ImageLoader>().create()
//    }
//    single { ::DefaultCategoriesRepository }
    single { Platform() }
//    singleOf(::DefaultCategoriesRepository).bind<CategoriesRepository>()

    single { SnackbarHostState() }

    viewModelOf(::HomeViewModel)
    viewModelOf(::PostViewModel)
    viewModelOf(::StartViewModel)


    viewModelOf(::SelectedPostViewModel)
}
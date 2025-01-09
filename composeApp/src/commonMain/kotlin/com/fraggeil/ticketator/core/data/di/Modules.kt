package com.fraggeil.ticketator.core.data.di

import androidx.compose.material3.SnackbarHostState
import com.fraggeil.ticketator.core.domain.Platform
import com.fraggeil.ticketator.presentation.SelectedFilterViewModel
import com.fraggeil.ticketator.presentation.SelectedJourneyViewModel
import com.fraggeil.ticketator.presentation.SelectedPostViewModel
import com.fraggeil.ticketator.presentation.screens.card_info_screen.CardInfoViewModel
import com.fraggeil.ticketator.presentation.screens.home_screen.HomeViewModel
import com.fraggeil.ticketator.presentation.screens.otp_screen.OtpViewModel
import com.fraggeil.ticketator.presentation.screens.passengers_info_screen.PassengersInfoViewModel
import com.fraggeil.ticketator.presentation.screens.post_screen.PostViewModel
import com.fraggeil.ticketator.presentation.screens.search_results_screen.SearchResultsViewModel
import com.fraggeil.ticketator.presentation.screens.select_seat_screen.SelectSeatViewModel
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
    viewModelOf(::SearchResultsViewModel)
    viewModelOf(::SelectSeatViewModel)
    viewModelOf(::PassengersInfoViewModel)
    viewModelOf(::CardInfoViewModel)
    viewModelOf(::OtpViewModel)


    viewModelOf(::SelectedPostViewModel)
    viewModelOf(::SelectedFilterViewModel)
    viewModelOf(::SelectedJourneyViewModel)

}
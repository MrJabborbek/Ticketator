package com.fraggeil.ticketator.core.data.di

import androidx.compose.material3.SnackbarHostState
import com.fraggeil.ticketator.core.domain.Platform
import com.fraggeil.ticketator.data.repository.DefaultHomeRepository
import com.fraggeil.ticketator.data.repository.DefaultLoginRepository
import com.fraggeil.ticketator.data.repository.DefaultOtpPaymentRepository
import com.fraggeil.ticketator.data.repository.DefaultProfileRepository
import com.fraggeil.ticketator.data.repository.DefaultSearchResultsRepository
import com.fraggeil.ticketator.data.repository.DefaultSelectSeatRepository
import com.fraggeil.ticketator.data.repository.DefaultSendDataToCheckRepository
import com.fraggeil.ticketator.domain.repository.HomeRepository
import com.fraggeil.ticketator.domain.repository.LoginRepository
import com.fraggeil.ticketator.domain.repository.OtpPaymentRepository
import com.fraggeil.ticketator.domain.repository.ProfileRepository
import com.fraggeil.ticketator.domain.repository.SearchResultsRepository
import com.fraggeil.ticketator.domain.repository.SelectSeatRepository
import com.fraggeil.ticketator.domain.repository.SendDataToCheckRepository
import com.fraggeil.ticketator.presentation.SelectedFilterViewModel
import com.fraggeil.ticketator.presentation.SelectedJourneyViewModel
import com.fraggeil.ticketator.presentation.SelectedPhoneNumberAndTokenViewModel
import com.fraggeil.ticketator.presentation.SelectedPostViewModel
import com.fraggeil.ticketator.presentation.screens.card_info_screen.CardInfoViewModel
import com.fraggeil.ticketator.presentation.screens.home_screen.HomeViewModel
import com.fraggeil.ticketator.presentation.screens.login_screen.LoginViewModel
import com.fraggeil.ticketator.presentation.screens.otp_payment_screen.OtpPaymentViewModel
import com.fraggeil.ticketator.presentation.screens.otp_screen.OtpViewModel
import com.fraggeil.ticketator.presentation.screens.passengers_info_screen.PassengersInfoViewModel
import com.fraggeil.ticketator.presentation.screens.post_screen.PostViewModel
import com.fraggeil.ticketator.presentation.screens.profile_edit_screen.ProfileEditViewModel
import com.fraggeil.ticketator.presentation.screens.profile_screen.ProfileViewModel
import com.fraggeil.ticketator.presentation.screens.search_results_screen.SearchResultsViewModel
import com.fraggeil.ticketator.presentation.screens.select_seat_screen.SelectSeatViewModel
import com.fraggeil.ticketator.presentation.screens.start_screen.StartViewModel
import com.fraggeil.ticketator.presentation.screens.tickets_screen.TicketsViewModel
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
    singleOf(::DefaultProfileRepository).bind<ProfileRepository>()
    singleOf(::DefaultLoginRepository).bind<LoginRepository>()
    singleOf(::DefaultHomeRepository).bind<HomeRepository>()
    singleOf(::DefaultSearchResultsRepository).bind<SearchResultsRepository>()
    singleOf(::DefaultSelectSeatRepository).bind<SelectSeatRepository>()
    singleOf(::DefaultSendDataToCheckRepository).bind<SendDataToCheckRepository>()
    singleOf(::DefaultOtpPaymentRepository).bind<OtpPaymentRepository>()


    single { SnackbarHostState() }

    viewModelOf(::HomeViewModel)
    viewModelOf(::PostViewModel)
    viewModelOf(::StartViewModel)
    viewModelOf(::SearchResultsViewModel)
    viewModelOf(::SelectSeatViewModel)
    viewModelOf(::PassengersInfoViewModel)
    viewModelOf(::CardInfoViewModel)
    viewModelOf(::OtpPaymentViewModel)
    viewModelOf(::OtpViewModel)
    viewModelOf(::TicketsViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::ProfileEditViewModel)

    viewModelOf(::SelectedPostViewModel)
    viewModelOf(::SelectedFilterViewModel)
    viewModelOf(::SelectedJourneyViewModel)
    viewModelOf(::SelectedPhoneNumberAndTokenViewModel)
}
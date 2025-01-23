package com.fraggeil.ticketator.presentation.screens.home_screen

import com.fraggeil.ticketator.domain.model.uzbekistan.District
import com.fraggeil.ticketator.domain.model.Post
import com.fraggeil.ticketator.domain.model.uzbekistan.Region

sealed interface HomeAction {
    data object OnBackClicked : HomeAction
    data object OnNotificationClicked : HomeAction
    data object OnSearchClicked : HomeAction
    data object OnReverseDistrictsClicked : HomeAction
    data object OnOneWayClicked : HomeAction
    data object OnRoundTripClicked : HomeAction
    data class OnDistrictFromSelected(val district: District) : HomeAction
    data class OnRegionFromSelected(val region: Region) : HomeAction
    data class OnDistrictToSelected(val district: District) : HomeAction
    data class OnRegionToSelected(val region: Region) : HomeAction
    data class OnPostClicked(val post: Post) : HomeAction
    data class OnDateFromSelected(val date: Long) : HomeAction
    data class OnDateToSelected(val date: Long) : HomeAction

    data object OnPermissionRequired : HomeAction
    data object OnLocationClicked : HomeAction


}
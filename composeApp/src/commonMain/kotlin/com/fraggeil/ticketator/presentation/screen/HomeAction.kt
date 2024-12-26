package com.fraggeil.ticketator.presentation.screen

import com.fraggeil.ticketator.domain.model.District
import com.fraggeil.ticketator.domain.model.Post

sealed interface HomeAction {
    data object OnBackClicked : HomeAction
    data object OnNotificationClicked : HomeAction
    data object OnSearchClicked : HomeAction
    data object OnReverseDistrictsClicked : HomeAction
    data object OnOneWayClicked : HomeAction
    data object OnRoundTripClicked : HomeAction
    data class OnDistrictFromSelected(val district: District) : HomeAction
    data class OnDistrictToSelected(val district: District) : HomeAction
    data class OnPostClicked(val post: Post) : HomeAction

}
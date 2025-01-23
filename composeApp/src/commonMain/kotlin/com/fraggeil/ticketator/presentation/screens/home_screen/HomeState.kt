package com.fraggeil.ticketator.presentation.screens.home_screen

import com.fraggeil.ticketator.core.domain.DateTimeUtil
import com.fraggeil.ticketator.domain.model.uzbekistan.District
import com.fraggeil.ticketator.domain.model.Filter
import com.fraggeil.ticketator.domain.model.FilterType
import com.fraggeil.ticketator.domain.model.Post
import com.fraggeil.ticketator.domain.model.uzbekistan.Region

data class HomeState(
//    val snackbarHostState: SnackbarHostState = SnackbarHostState(),
    val isLoadingPosts: Boolean = true,
    val isLoadingFromRegions: Boolean = true,
    val isLoadingToRegions: Boolean = false,
    val isLoadingCurrentLocation: Boolean = true,
    val isSearching: Boolean = true,
    val isThereNewNotifications: Boolean = false,
    val error: String? = null,
    val filter: Filter = Filter(
        fromRegion = null,//FakeData.regions[0],
        toRegion = null,//FakeData.regions[1],
        fromDistrict = null,//FakeData.regions[0].districts.random(),
        toDistrict = null,//FakeData.regions[1].districts.random(),
        dateGo = DateTimeUtil.nowMilliseconds(),
        dateBack = null,
        type = FilterType.ONE_WAY
    ),
    val fromRegions: List<Region> = emptyList(),
    val toRegions: List<Region> = emptyList(),
    val posts: List<Post> = emptyList(),
    val currentLocation: Pair<Region, District>? = null,
    val location: String = ""
)



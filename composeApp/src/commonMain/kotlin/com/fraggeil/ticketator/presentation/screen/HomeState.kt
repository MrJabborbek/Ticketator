package com.fraggeil.ticketator.presentation.screen

import com.fraggeil.ticketator.domain.FakeData
import com.fraggeil.ticketator.domain.model.District
import com.fraggeil.ticketator.domain.model.Filter
import com.fraggeil.ticketator.domain.model.FilterType
import com.fraggeil.ticketator.domain.model.Post
import com.fraggeil.ticketator.domain.model.Region

data class HomeState(
    val isLoadingPosts: Boolean = true,
    val isLoadingRegions: Boolean = true,
    val isLoadingCurrentLocation: Boolean = true,
    val isSearching: Boolean = true,
    val isThereNewNotifications: Boolean = false,
    val error: String? = null,
    val filter: Filter = Filter(
        fromRegion = FakeData.regions.random(),
        toRegion = FakeData.regions.random(),
        fromDistrict = FakeData.regions.random().districts.random(),
        toDistrict = FakeData.regions.random().districts.random(),
        dateGo = 123131321231,
        dateBack = 1213132132132,
        type = FilterType.ONE_WAY
    ),
    val regions: List<Region> = emptyList(),
    val posts: List<Post> = emptyList(),
    val currentLocation: Pair<Region, District>? = null
)

package com.fraggeil.ticketator.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fraggeil.ticketator.core.domain.Constants
import com.fraggeil.ticketator.domain.FakeData
import com.fraggeil.ticketator.domain.model.FilterType
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class HomeViewModel: ViewModel() {
    private var fetchAllRegionsJob: Job? = null
    private var fetchAllPostsJob: Job? = null
    private var observeCurrentLocationJob: Job? = null
    private var observeIsThereNewNotificationsJob: Job? = null

    private var isInitialized = false
    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .onStart {
            if(!isInitialized){
                isInitialized = true
                fetchAllRegions()
                fetchAllPosts()
                observeCurrentLocation()
                observeIsThereNewNotifications()
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _state.value
        )
    
    fun onAction(action: HomeAction){
        when(action){
            HomeAction.OnBackClicked -> {}
            is HomeAction.OnDistrictFromSelected -> {
                _state.update { it.copy(filter = it.filter.copy(from = action.district)) }
            }
            is HomeAction.OnDistrictToSelected -> {
                _state.update { it.copy(filter = it.filter.copy(to = action.district)) }
            }
            HomeAction.OnNotificationClicked -> {}
            HomeAction.OnOneWayClicked -> {
                _state.update { it.copy(filter = it.filter.copy(type = FilterType.ONE_WAY)) }
            }
            HomeAction.OnRoundTripClicked -> {
                _state.update { it.copy(filter = it.filter.copy(type = FilterType.ROUND_TRIP)) }
            }
            is HomeAction.OnPostClicked -> {}
            HomeAction.OnReverseDistrictsClicked -> {
                _state.update {
                    it.copy(
                        filter = it.filter.copy(
                            from = it.filter.to,
                            to = it.filter.from
                        )
                    )
                }
            }
            HomeAction.OnSearchClicked -> {}
        }
    }

    private fun fetchAllRegions(){
        fetchAllRegionsJob?.cancel()
        _state.update { it.copy(isLoadingRegions = true) }
        fetchAllRegionsJob = viewModelScope.launch {
            delay(Constants.FAKE_DELAY_TO_TEST)
            _state.update { it.copy(isLoadingRegions = false, regions = FakeData.regions) }
        }
    }

    private fun fetchAllPosts(){
        fetchAllPostsJob?.cancel()
        _state.update { it.copy(isLoadingPosts = true) }
        fetchAllPostsJob = viewModelScope.launch {
            delay(Constants.FAKE_DELAY_TO_TEST)
            _state.update { it.copy(isLoadingPosts = false, posts = FakeData.posts) }
        }
    }

    private fun observeCurrentLocation(){
        observeCurrentLocationJob?.cancel()
        _state.update { it.copy(isLoadingCurrentLocation = true) }
        observeCurrentLocationJob = viewModelScope.launch {
            delay(Constants.FAKE_DELAY_TO_TEST)
            val region = FakeData.regions.random()
            val district = region.districts.random()
            _state.update { it.copy(isLoadingCurrentLocation = false, currentLocation = Pair(region, district)) }
        }
    }

    private fun observeIsThereNewNotifications(){
        observeIsThereNewNotificationsJob?.cancel()
        _state.update { it.copy(isThereNewNotifications = true) }
        observeIsThereNewNotificationsJob = viewModelScope.launch {
            delay(Constants.FAKE_DELAY_TO_TEST)
            _state.update { it.copy(isThereNewNotifications = Random.nextBoolean()) }
        }
    }
}
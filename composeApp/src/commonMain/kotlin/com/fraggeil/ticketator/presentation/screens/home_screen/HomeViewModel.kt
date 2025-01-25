package com.fraggeil.ticketator.presentation.screens.home_screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fraggeil.ticketator.core.domain.Constants
import com.fraggeil.ticketator.core.domain.moko_permission.permissions.DeniedAlwaysException
import com.fraggeil.ticketator.core.domain.moko_permission.permissions.DeniedException
import com.fraggeil.ticketator.core.domain.moko_permission.permissions.Permission
import com.fraggeil.ticketator.core.domain.moko_permission.permissions.PermissionState
import com.fraggeil.ticketator.core.domain.moko_permission.permissions.PermissionsController
import com.fraggeil.ticketator.core.domain.moko_permission.permissions.RequestCanceledException
import com.fraggeil.ticketator.core.domain.result.DataError
import com.fraggeil.ticketator.core.domain.result.onError
import com.fraggeil.ticketator.core.domain.result.onSuccess
import com.fraggeil.ticketator.domain.model.Filter
import com.fraggeil.ticketator.domain.model.FilterType
import com.fraggeil.ticketator.domain.model.uzbekistan.Region
import com.fraggeil.ticketator.domain.repository.HomeRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val snackbarHostState: SnackbarHostState,
    private val homeRepository: HomeRepository,
): ViewModel() {
    private var controller: PermissionsController? = null

    private var fetchAllRegionsJob: Job? = null
    private var fetchToRegionsJob: Job? = null
    private var fetchAllPostsJob: Job? = null
    private var fetchCurrentLocationJob: Job? = null
    private var observeIsThereNewNotificationsJob: Job? = null

    private var isInitialized = false
    private val _oneTimeState = Channel<HomeOneTimeState>()
    val oneTimeState = _oneTimeState.receiveAsFlow()

    var permissionState by mutableStateOf(PermissionState.NotDetermined)
        private set

    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .onStart {
            if(!isInitialized){
                isInitialized = true
                fetchAllRegions()
                fetchAllPosts()
                fetchCurrentLocation(false)
                observeIsThereNewNotifications()
//
//
//                viewModelScope.launch {
//                    val geocoder = createGeocoder()
//                    val places = geocoder.reverse(41.311081, 69.240515).getOrNull()
//                    println("Location permission: place: $places")
//
//                }

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
                _state.update { it.copy(filter = it.filter.copy(fromDistrict = action.district)) }
            }
            is HomeAction.OnDistrictToSelected -> {
                _state.update { it.copy(filter = it.filter.copy(toDistrict = action.district)) }
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
                if (_state.value.filter.fromDistrict == null || _state.value.filter.fromRegion == null || _state.value.filter.toDistrict == null || _state.value.filter.toRegion == null) return

                _state.update {
                    it.copy(
                        filter = it.filter.copy(
                            fromDistrict = it.filter.toDistrict,
                            fromRegion = it.filter.toRegion,
                            toDistrict = it.filter.fromDistrict,
                            toRegion = it.filter.fromRegion
                        )
                    )
                }
            }
            HomeAction.OnSearchClicked -> viewModelScope.launch{
                _state.value.filter.let { filter ->
                    if (filter.fromDistrict == null){
                        showSnackbar()
                        return@let
                    }
                    if (filter.toDistrict == null){
                        showSnackbar()
                        return@let
                    }
                    if (filter.dateGo == null){
                        showSnackbar()
                        return@let
                    }
                    if (filter.type == FilterType.ROUND_TRIP && filter.dateBack == null){
                        showSnackbar()
                        return@let
                    }
                    search(filter)
                }
            }
            is HomeAction.OnRegionFromSelected -> {
                _state.update { it.copy(filter = it.filter.copy(fromRegion = action.region)) }
                fetchToRegions(action.region)
            }
            is HomeAction.OnRegionToSelected -> {
                _state.update { it.copy(filter = it.filter.copy(toRegion = action.region)) }
            }

            is HomeAction.OnDateFromSelected -> {
                _state.update { it.copy(filter = it.filter.copy(dateGo = action.date)) }

                if (state.value.filter.dateBack == null) return
                if (action.date <= state.value.filter.dateBack!!) return
                _state.update { it.copy(filter = it.filter.copy(dateBack = null)) }
            }

            is HomeAction.OnDateToSelected -> {
                _state.update { it.copy(filter = it.filter.copy(dateBack = action.date)) }
            }

            HomeAction.OnPermissionRequired -> {
                provideOrRequestLocationPermission()
            }

            HomeAction.OnLocationClicked -> {
                if (controller == null) return
                fetchCurrentLocation(true)
            }
        }
    }

    fun provideController(controller: PermissionsController){
        this.controller = controller
        viewModelScope.launch {
            permissionState = controller.getPermissionState(Permission.COARSE_LOCATION)
        }
    }

    private fun provideOrRequestLocationPermission() {
        if (controller == null) return
        viewModelScope.launch {
            try {
                controller!!.providePermission(Permission.COARSE_LOCATION)
                permissionState = PermissionState.Granted
            }catch (e: DeniedAlwaysException){
                permissionState = PermissionState.DeniedAlways
                controller?.openAppSettings()
            }catch (e: DeniedException){
                permissionState = PermissionState.Denied
            }catch (e: RequestCanceledException){
                e.printStackTrace()
            }
        }
    }

    private fun fetchAllRegions(){
        fetchAllRegionsJob?.cancel()
        _state.update { it.copy(isLoadingFromRegions = true) }
        fetchAllRegionsJob = viewModelScope.launch {
            homeRepository.getRegions()
                .onSuccess { regions ->
                    _state.update { it.copy(fromRegions = regions, isLoadingFromRegions = false) }
                }
                .onError { _state.update { it.copy(error = "Error fetching regions") } }
        }
    }
    private fun fetchToRegions(fromRegion: Region) {
        fetchToRegionsJob?.cancel()
        _state.update { it.copy(isLoadingToRegions = true) }
        fetchToRegionsJob = viewModelScope.launch {
            homeRepository.getToRegions(fromRegion)
                .onSuccess { regions ->
                    _state.update {
                        it.copy(
                            isLoadingToRegions = false,
                            toRegions = regions,
                            filter = it.filter.copy(
                                toRegion = if (regions.contains(it.filter.toRegion)) it.filter.toRegion else null,
                                toDistrict = if (regions.contains(it.filter.toRegion)) it.filter.toDistrict else null
                            )
                        )
                    }
                }
                .onError { _state.update { it.copy(error = "Error fetching regions") } }
        }
    }

    private fun fetchAllPosts(){
        fetchAllPostsJob?.cancel()
        _state.update { it.copy(isLoadingPosts = true) }
        fetchAllPostsJob = viewModelScope.launch {
            homeRepository.getAllPosts()
                .onSuccess { posts ->
                    _state.update { it.copy(isLoadingPosts = false, posts = posts) }
                }
                .onError { _state.update { it.copy(error = "Error fetching posts") } }
        }
    }

    private fun fetchCurrentLocation(isActionsAllowed: Boolean) {
        fetchCurrentLocationJob?.cancel()
        _state.update { it.copy(isLoadingCurrentLocation = true, location = "Loading...") }
        fetchCurrentLocationJob = viewModelScope.launch {
            homeRepository.fetchCurrentLocation()
                .onSuccess { data ->
                    _state.update {
                        it.copy(
                            isLoadingCurrentLocation = false,
                            location = data
                        )
                    }
                }
                .onError { error ->
                    when (error) {
                        is DataError.LocationError.NO_PERMISSION -> {
                            if (isActionsAllowed) provideOrRequestLocationPermission()
                            _state.update {
                                it.copy(location = error.placeHolder ?:"No permission")
                            }
                        }
                        is DataError.LocationError.NO_GPS ->{
                            if (isActionsAllowed) _oneTimeState.send(HomeOneTimeState.NavigateToGpsSettings)
                            _state.update {
                                it.copy(location = error.placeHolder ?:"Turn on GPS")
                            }
                        }
                        is DataError.LocationError.UNKNOWN -> {
                            _state.update {
                                it.copy(location = error.placeHolder ?: "Error fetching location")
                            }
                        }

                        is DataError.LocationError.NO_GEOLOCATION -> {
                            _state.update {
                                it.copy(location = error.placeHolder ?:"Error fetching location")
                            }
                        }
                    }
                }
        }
    }

    private fun observeIsThereNewNotifications(){
        observeIsThereNewNotificationsJob?.cancel()
        _state.update { it.copy(isThereNewNotifications = true) }
        observeIsThereNewNotificationsJob = homeRepository.observeIsThereNewNotifications()
            .onStart {
                delay(Constants.FAKE_DELAY_TO_TEST)
            }
            .onEach { data ->
                _state.update { it.copy(isThereNewNotifications = data) }
            }
            .launchIn(viewModelScope)
    }

    private suspend fun showSnackbar(){
//        _state.value.snackbarHostState.showSnackbar("Fill all the fields")
        snackbarHostState.showSnackbar("Fill all the fields")
    }

    private suspend fun search(filter: Filter){
        _oneTimeState.send(HomeOneTimeState.NavigateToSearchResults(filter))
    }
}
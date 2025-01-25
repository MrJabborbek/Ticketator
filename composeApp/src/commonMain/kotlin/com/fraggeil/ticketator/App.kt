package com.fraggeil.ticketator

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.fraggeil.ticketator.core.domain.Constants
import com.fraggeil.ticketator.core.presentation.Strings
import com.fraggeil.ticketator.core.presentation.Strings.value
import com.fraggeil.ticketator.core.theme.AppTheme
import com.fraggeil.ticketator.core.theme.BG_White
import com.fraggeil.ticketator.core.theme.Blue
import com.fraggeil.ticketator.core.theme.BlueDark
import com.fraggeil.ticketator.core.theme.White
import com.fraggeil.ticketator.presentation.Route
import com.fraggeil.ticketator.presentation.SelectedFilterViewModel
import com.fraggeil.ticketator.presentation.SelectedJourneyViewModel
import com.fraggeil.ticketator.presentation.SelectedPhoneNumberAndTokenViewModel
import com.fraggeil.ticketator.presentation.SelectedPostViewModel
import com.fraggeil.ticketator.presentation.SelectedTicketsViewModel
import com.fraggeil.ticketator.presentation.screens.card_info_screen.CardInfoAction
import com.fraggeil.ticketator.presentation.screens.card_info_screen.CardInfoScreenRoot
import com.fraggeil.ticketator.presentation.screens.card_info_screen.CardInfoViewModel
import com.fraggeil.ticketator.presentation.screens.home_screen.HomeScreenRoot
import com.fraggeil.ticketator.presentation.screens.home_screen.HomeViewModel
import com.fraggeil.ticketator.presentation.screens.login_screen.LoginScreenRoot
import com.fraggeil.ticketator.presentation.screens.login_screen.LoginViewModel
import com.fraggeil.ticketator.presentation.screens.otp_payment_screen.OtpPaymentAction
import com.fraggeil.ticketator.presentation.screens.otp_payment_screen.OtpPaymentScreenRoot
import com.fraggeil.ticketator.presentation.screens.otp_payment_screen.OtpPaymentViewModel
import com.fraggeil.ticketator.presentation.screens.otp_screen.OtpAction
import com.fraggeil.ticketator.presentation.screens.otp_screen.OtpScreenRoot
import com.fraggeil.ticketator.presentation.screens.otp_screen.OtpViewModel
import com.fraggeil.ticketator.presentation.screens.passengers_info_screen.PassengersInfoAction
import com.fraggeil.ticketator.presentation.screens.passengers_info_screen.PassengersInfoScreenRoot
import com.fraggeil.ticketator.presentation.screens.passengers_info_screen.PassengersInfoViewModel
import com.fraggeil.ticketator.presentation.screens.post_screen.PostAction
import com.fraggeil.ticketator.presentation.screens.post_screen.PostScreenRoot
import com.fraggeil.ticketator.presentation.screens.post_screen.PostViewModel
import com.fraggeil.ticketator.presentation.screens.profile_edit_screen.ProfileEditScreenRoot
import com.fraggeil.ticketator.presentation.screens.profile_edit_screen.ProfileEditViewModel
import com.fraggeil.ticketator.presentation.screens.profile_screen.ProfileScreenRoot
import com.fraggeil.ticketator.presentation.screens.profile_screen.ProfileViewModel
import com.fraggeil.ticketator.presentation.screens.search_results_screen.SearchResultsAction
import com.fraggeil.ticketator.presentation.screens.search_results_screen.SearchResultsScreenRoot
import com.fraggeil.ticketator.presentation.screens.search_results_screen.SearchResultsViewModel
import com.fraggeil.ticketator.presentation.screens.select_seat_screen.SelectSeatAction
import com.fraggeil.ticketator.presentation.screens.select_seat_screen.SelectSeatScreenRoot
import com.fraggeil.ticketator.presentation.screens.select_seat_screen.SelectSeatViewModel
import com.fraggeil.ticketator.presentation.screens.start_screen.StartScreenRoot
import com.fraggeil.ticketator.presentation.screens.start_screen.StartViewModel
import com.fraggeil.ticketator.presentation.screens.tickets_screen.TicketsAction
import com.fraggeil.ticketator.presentation.screens.tickets_screen.TicketsScreenRoot
import com.fraggeil.ticketator.presentation.screens.tickets_screen.TicketsViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import ticketator.composeapp.generated.resources.Res
import ticketator.composeapp.generated.resources.ic_main_selected
import ticketator.composeapp.generated.resources.ic_main_unselected
import ticketator.composeapp.generated.resources.person
import ticketator.composeapp.generated.resources.person_filled
import ticketator.composeapp.generated.resources.ticket
import ticketator.composeapp.generated.resources.ticket_filled

@Composable
@Preview
fun App(
    appViewModel: AppViewModel = koinViewModel<AppViewModel>()
) {
    val appState by appViewModel.state.collectAsStateWithLifecycle()
    if (appState.isLoading){

    }else{
        AppTheme {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            fun navigate(route: Route, shouldSaveState: Boolean, restore: Boolean = false){
                if (shouldSaveState) {
                    navController.navigate(route.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
//                    inclusive = false
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }else{
                    navController.navigate(route.route){
                        if (restore){
                            popUpTo(navController.graph.findStartDestination().id)
                        }
                    }
                }
            }

            Scaffold(
                modifier = Modifier
                    .fillMaxSize().imePadding(),
//                .statusBarsPadding(),
                containerColor = BG_White,
                snackbarHost = {
                    SnackbarHost(koinInject())
                },
                bottomBar = {
                    if ( navBackStackEntry?.destination?.route in listOf(Route.Home.route, Route.Tickets.route, Route.Profile.route)){
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                            colors = CardDefaults.cardColors().copy(containerColor = White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
                        ){
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ){
                                NavigationBar(
                                    modifier = Modifier.widthIn(max = 480.dp),
                                    containerColor = Color.Transparent,
                                    contentColor = BlueDark,
                                    tonalElevation = 50.dp
                                ) {
                                    MyNavigationBarItem(
                                        text = Strings.Home.value(),
                                        selectedPainter = painterResource(Res.drawable.ic_main_selected), //TODO
                                        unselectedPainter = painterResource(Res.drawable.ic_main_unselected),
                                        onClick = {
                                            if (navBackStackEntry?.destination?.route != Route.Home.route){
                                                navigate(Route.Home,true)
                                            }
                                        },
                                        isSelected = navBackStackEntry?.destination?.route == Route.Home.route
                                    )
                                    MyNavigationBarItem(
                                        text = Strings.Tickets.value(),
                                        selectedPainter = painterResource(Res.drawable.ticket_filled),
                                        unselectedPainter = painterResource(Res.drawable.ticket),
                                        onClick = {
                                            if (navBackStackEntry?.destination?.route != Route.Tickets.route){
                                                navigate(Route.Tickets,false)
                                            }
                                        },
                                        isSelected = navBackStackEntry?.destination?.route == Route.Tickets.route
                                    )
                                    MyNavigationBarItem(
                                        text = Strings.Profile.value(),
                                        selectedPainter = painterResource(Res.drawable.person_filled),
                                        unselectedPainter = painterResource(Res.drawable.person),
                                        onClick = {
                                            if (navBackStackEntry?.destination?.route != Route.Profile.route){
                                                navigate(Route.Profile,false)
                                            }
                                        },
                                        isSelected = navBackStackEntry?.destination?.route == Route.Profile.route
                                    )
                                }
                            }
                        }
                    }
                }
            ) { paddings ->
                Box(
                    modifier = Modifier
//                    .padding(paddings)
//                    .padding(bottom = 56.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    NavHost(
                        modifier = Modifier
                            .widthIn(max = Constants.MAX_ITEM_WIDTH_IN_DP)
                            .fillMaxSize(),
                        navController = navController,
                        startDestination = Route.TicketatorGraph.route,
                        enterTransition = { EnterTransition.None },
                        exitTransition  = { ExitTransition.None },
                        popEnterTransition   = { EnterTransition.None },
                        popExitTransition    = { ExitTransition.None },
                    ) {
                        navigation(
                            route = Route.TicketatorGraph.route,
                            startDestination = (if (appState.appSettings.isLaunched) Route.Home.route else Route.Start.route).also {
                                println("start destination: $it // ${appState.appSettings}")
                            },
//                        startDestination = Route.SelectSeat.route,
                        ) {

                            composable(
                                route = Route.Home.route,
//                        exitTransition = { slideOutHorizontally() },
//                        popEnterTransition = { slideInHorizontally() }
                            ) {
                                val viewModel = koinViewModel<HomeViewModel>()
                                val selectedPostViewModel = it.sharedKoinViewModel<SelectedPostViewModel>(navController)
                                val selectedFilterViewModel = it.sharedKoinViewModel<SelectedFilterViewModel>(navController)
                                LaunchedEffect(true) {
                                    selectedPostViewModel.onSelectItem(null)
                                    selectedFilterViewModel.onSelectItem(null)
                                }

                                HomeScreenRoot(
                                    viewModel = viewModel,
                                    navigateToPost = { post ->
                                        selectedPostViewModel.onSelectItem(post)
                                        navigate(Route.Post, false)
                                    },
                                    navigateToSearchResults = {filter ->
                                        selectedFilterViewModel.onSelectItem(filter)
                                        navigate(Route.SearchResults, false)
                                    }
                                )
                            }
                            composable(
                                route = Route.Post.route
                            ){

                                val viewModel = koinViewModel<PostViewModel>()
                                val selectedPostViewModel = it.sharedKoinViewModel<SelectedPostViewModel>(navController)

                                val selectedPost by selectedPostViewModel.state.collectAsStateWithLifecycle()
                                LaunchedEffect(selectedPost){
                                    selectedPost?.let { post ->
                                        viewModel.onAction(PostAction.OnSelectedPostChange(post))
                                    }
                                }

                                PostScreenRoot(
                                    viewModel = viewModel,
                                    onBackClicked = {
                                        navController.navigateUp()
                                    }
                                )
                            }
                            composable(
                                route = Route.Start.route
                            ){
                                val viewModel = koinViewModel<StartViewModel>()
                                StartScreenRoot(
                                    viewModel = viewModel,
                                    navigateToHome = {
                                        navigate(Route.Home, true)
                                    }
                                )
                            }
                            composable(
                                route = Route.SearchResults.route
                            ) {
                                val viewModel = koinViewModel<SearchResultsViewModel>()
                                val selectedJourneyViewModel = it.sharedKoinViewModel<SelectedJourneyViewModel>(navController)
                                val selectedFilterViewModel = it.sharedKoinViewModel<SelectedFilterViewModel>(navController)
                                val filterState by selectedFilterViewModel.state.collectAsStateWithLifecycle()

                                LaunchedEffect(filterState){
                                    filterState?.let {
                                        viewModel.onAction(SearchResultsAction.OnFilterSelected(it))
                                    }
                                }
                                LaunchedEffect(true) {
                                    selectedJourneyViewModel.onSelectItem(null)
                                }
                                SearchResultsScreenRoot(
                                    viewModel = viewModel,
                                    navigateBack = {
                                        navController.navigateUp()
                                    },
                                    navigateToJourneyDetails = { journey ->
                                        selectedJourneyViewModel.onSelectItem(journey)
                                        navigate(Route.SelectSeat, false)
                                    }
                                )
                            }
                            composable(
                                route = Route.SelectSeat.route
                            ) {
                                val viewModel = koinViewModel<SelectSeatViewModel>()
                                val selectedJourneyViewModel = it.sharedKoinViewModel<SelectedJourneyViewModel>(navController)
                                val selectedJourney by selectedJourneyViewModel.state.collectAsState()
                                LaunchedEffect(selectedJourney){
                                    selectedJourney?.let {
                                        viewModel.onAction(SelectSeatAction.OnJourneySelected(selectedJourney!!))
                                    }
                                }
                                SelectSeatScreenRoot(
                                    viewModel = viewModel,
                                    navigateBack = {
                                        navController.navigateUp()
                                    },
                                    navigateToPassengersInfo = { journey ->
                                        selectedJourneyViewModel.onSelectItem(journey)
                                        navigate(Route.PassengersInfo, false)
                                    }
                                )
                            }
                            composable(
                                route = Route.PassengersInfo.route
                            ) {
                                val viewModel = koinViewModel<PassengersInfoViewModel>()
                                val selectedJourneyViewModel = it.sharedKoinViewModel<SelectedJourneyViewModel>(navController)
                                val selectedJourney by selectedJourneyViewModel.state.collectAsState()
                                LaunchedEffect(selectedJourney){
                                    selectedJourney?.let {
                                        viewModel.onAction(PassengersInfoAction.OnJourneySelected(selectedJourney!!))
                                    }
                                }
                                PassengersInfoScreenRoot(
                                    viewModel = viewModel,
                                    navigateBack = {
                                        navController.navigateUp()
                                    },
                                    navigateToCardInfo = {
                                        selectedJourneyViewModel.onSelectItem(it)
                                        navigate(Route.CardInfo, false)
                                    }
                                )
                            }
                            composable(
                                route = Route.CardInfo.route
                            ) {
                                val viewModel = koinViewModel<CardInfoViewModel>()
                                val selectedJourneyViewModel = it.sharedKoinViewModel<SelectedJourneyViewModel>(navController)
                                val selectedPhoneNumberAndTokenViewModel = it.sharedKoinViewModel<SelectedPhoneNumberAndTokenViewModel>(navController)
                                val selectedJourney by selectedJourneyViewModel.state.collectAsState()
                                LaunchedEffect(selectedJourney){
                                    selectedJourney?.let {
                                        viewModel.onAction(CardInfoAction.OnJourneySelected(selectedJourney!!))
                                    }
                                }
                                LaunchedEffect(true){
                                    selectedPhoneNumberAndTokenViewModel.onSelectItem(null)
                                }

                                CardInfoScreenRoot(
                                    viewModel = viewModel,
                                    navigateBack = {
                                        navController.navigateUp()
                                    },
                                    navigateToEnterCode = { token, phoneNumber ->
                                        selectedPhoneNumberAndTokenViewModel.onSelectItem(Pair(token, phoneNumber))
                                        navigate(Route.OtpPayment, false)
                                    }
                                )
                            }

                            composable(
                                route = Route.OtpPayment.route
                            ) {
                                val viewModel = koinViewModel<OtpPaymentViewModel>()
                                val selectedPhoneNumberAndTokenViewModel = it.sharedKoinViewModel<SelectedPhoneNumberAndTokenViewModel>(navController)
                                val selectedTicketsViewModel = it.sharedKoinViewModel<SelectedTicketsViewModel>(navController)
                                val selectedPhoneNumberAndToken by selectedPhoneNumberAndTokenViewModel.state.collectAsState()
                                LaunchedEffect(selectedPhoneNumberAndToken){
                                    selectedPhoneNumberAndToken?.let {
                                        viewModel.onAction(OtpPaymentAction.OnPhoneNumberChanged(token = selectedPhoneNumberAndToken!!.first, number = selectedPhoneNumberAndToken!!.second))
                                    }
                                }
                                LaunchedEffect(true){
                                    selectedTicketsViewModel.onSelectItem(null)
                                }
                                OtpPaymentScreenRoot(
                                    viewModel = viewModel,
                                    navigateBack = {
                                        navController.navigateUp()
                                    },
                                    navigateToTickets = { tickets ->
                                        selectedTicketsViewModel.onSelectItem(tickets)
                                        navigate(Route.Tickets, true)
                                    }
                                )
                            }
                            composable(
                                route = Route.Otp.route
                            ) {
                                val viewModel = koinViewModel<OtpViewModel>()
                                val selectedPhoneNumberAndTokenViewModel = it.sharedKoinViewModel<SelectedPhoneNumberAndTokenViewModel>(navController)
                                val selectedPhoneNumberAndToken by selectedPhoneNumberAndTokenViewModel.state.collectAsState()
                                LaunchedEffect(selectedPhoneNumberAndToken){
                                    selectedPhoneNumberAndToken?.let {
                                        viewModel.onAction(OtpAction.OnPhoneNumberChanged(number = selectedPhoneNumberAndToken!!.second, token = selectedPhoneNumberAndToken!!.first))
                                    }
                                }
                                OtpScreenRoot(
                                    viewModel = viewModel,
                                    navigateBack = {
                                        navController.navigateUp()
                                    },
                                    navigateToHome = {
                                        navigate(Route.Home, false)
                                    }
                                )
                            }
                            composable(
                                route = Route.Tickets.route
                            ) {
                                val viewModel = koinViewModel<TicketsViewModel>()
                                val selectedTicketsViewModel = it.sharedKoinViewModel<SelectedTicketsViewModel>(navController)
                                val selectedTickets by selectedTicketsViewModel.state.collectAsState()
                                LaunchedEffect(selectedTickets){
                                    selectedTickets?.let {
                                        viewModel.onAction(TicketsAction.OnInitialTicketsSelected(selectedTickets!!))
                                    }
                                }
                                TicketsScreenRoot(
                                    viewModel = viewModel,
                                    navigateBack = {
                                        navController.navigateUp()
                                    },
                                )
                            }
                            composable(route = Route.Profile.route){
                                val viewModel = koinViewModel<ProfileViewModel>()
                                ProfileScreenRoot(
                                    viewModel = viewModel,
                                    navigateBack = {
                                        navController.navigateUp()
                                    },
                                    navigateToLogin = {
                                        navigate(Route.Login, false)
                                    },
                                    navigateToMain = {
                                        navigate(route = Route.Home, true)
                                    },
                                    navigateToEditProfile = {
                                        navigate(Route.ProfileEdit, false)
                                    },
                                    navigateToAboutApp = {
//                                    navigate(Route.AboutApp.routeName, false)
                                    },
                                    navigateToTermsAndConditions = {
//                                    navigate(Route.TermsAndConditions.routeName, false)
                                    }
                                )
                            }
                            composable(
                                route = Route.Login.route
                            ){

                                val viewModel = koinViewModel<LoginViewModel>()
                                val selectedPhoneNumberViewModel = it.sharedKoinViewModel<SelectedPhoneNumberAndTokenViewModel>(navController)
                                LaunchedEffect(true){
                                    selectedPhoneNumberViewModel.onSelectItem(null)
                                }
                                LoginScreenRoot(
                                    viewModel = viewModel,
                                    navigateBack = {
                                        navController.navigateUp()
                                    },
                                    navigateToOtp = {phoneNumber, token ->
                                        selectedPhoneNumberViewModel.onSelectItem(Pair(token, phoneNumber))
                                        navigate(Route.Otp, false)
                                    },
                                    navigateToTermsAndConditions = {
//                                    navigate(Route.TermsAndConditions.routeName, false)
                                    }
                                )
                            }
                            composable(
                                route = Route.ProfileEdit.route
                            ){
                                val viewModel = koinViewModel<ProfileEditViewModel>()
                                ProfileEditScreenRoot(
                                    viewModel = viewModel,
                                    navigateBack = {
                                        navController.navigateUp()
                                    },
                                    navigateToMain = {
                                        navigate(Route.Home, true)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private inline fun <reified T: ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}

@Composable
fun RowScope.MyNavigationBarItem(
    text: String,
    selectedIcon: ImageVector? = null,
    unselectedIcon: ImageVector? = selectedIcon,
    selectedPainter: Painter? = null,
    unselectedPainter: Painter? = selectedPainter,
    onClick: () -> Unit,
    isSelected: Boolean,
    isBadgeVisible: Boolean = false,
){
    NavigationBarItem(
        icon = {
            BadgedBox(
                badge = {
                    if (isBadgeVisible) {
                        Badge()
                    }
                }
            ){
                if (selectedIcon != null && unselectedIcon != null){
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = if (isSelected) selectedIcon else unselectedIcon,
                        contentDescription = null
                    )
                } else if (selectedPainter != null && unselectedPainter != null){
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = if (isSelected) selectedPainter else unselectedPainter,
                        contentDescription = null
                    )
                }
            }
        },
        label = { Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                letterSpacing = 0.sp
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        ) },
        selected = isSelected,
        onClick = { onClick() },
        colors = NavigationBarItemDefaults.colors().copy(
            selectedIconColor = Blue,
            unselectedIconColor = BlueDark,
            selectedTextColor = Blue,
            unselectedTextColor = BlueDark,
            selectedIndicatorColor = Color.Transparent
        )
    )
}

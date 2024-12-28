package com.fraggeil.ticketator

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.fraggeil.ticketator.presentation.screen.HomeScreenRoot
import com.fraggeil.ticketator.presentation.screen.HomeViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import ticketator.composeapp.generated.resources.Res
import ticketator.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    AppTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        fun navigate(route: Route, shouldSaveState: Boolean, restore: Boolean = false){
            if (shouldSaveState) {
                navController.navigate(route) {
                    popUpTo(navController.graph.findStartDestination().id) {
//                    inclusive = false
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }else{
                navController.navigate(route){
                    if (restore){
                        popUpTo(navController.graph.findStartDestination().id)
                    }
                }
            }
        }

        Scaffold(
            modifier = Modifier.fillMaxSize().statusBarsPadding(),
            containerColor = BG_White,
            bottomBar = {
                if ( navBackStackEntry?.destination?.route in listOf(Route.Home.route)){
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                        colors = CardDefaults.cardColors().copy(containerColor = White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
                    ){
                        NavigationBar(
                            modifier = Modifier.widthIn(max = 480.dp),
                            containerColor = Color.Transparent,
                            contentColor = BlueDark,
                            tonalElevation = 50.dp
                        ) {
                            MyNavigationBarItem(
                                text = Strings.Home.value(),
                                selectedPainter = painterResource(Res.drawable.compose_multiplatform), //TODO
                                unselectedPainter = painterResource(Res.drawable.compose_multiplatform),
                                onClick = {
                                    if (navBackStackEntry?.destination?.route != Route.Home.route){
                                        navigate(Route.Home,true)
                                    }
                                },
                                isSelected = navBackStackEntry?.destination?.route == Route.Home.route
                            )
                        }
                    }
                }
            }
        ) { paddings ->
            Box(
                modifier = Modifier
//                    .padding(paddings)
                    .padding(bottom = 56.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                NavHost(
                    modifier = Modifier
                        .widthIn(max = Constants.MAX_ITEM_WIDTH_IN_DP.dp)
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
                        startDestination = Route.Home.route,
                    ) {

                        composable(
                            route = Route.Home.route,
//                        exitTransition = { slideOutHorizontally() },
//                        popEnterTransition = { slideInHorizontally() }
                        ) {

                            val viewModel = koinViewModel<HomeViewModel>()

//                            val selectedPostViewModel =
//                                it.sharedKoinViewModel<SelectedPostViewModel>(navController)
//                            LaunchedEffect(true) {
//                                selectedPostViewModel.onSelectItem(null)
//                            }

                            HomeScreenRoot(
                                viewModel = viewModel,
//                                navigateToPost = { post ->
//                                    selectedPostViewModel.onSelectItem(post)
//                                    navigate(Route.Post.routeName, false)
//                                },
                            )
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

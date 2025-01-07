package com.fraggeil.ticketator.presentation.screens.home_screen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fraggeil.ticketator.core.domain.DateTimeUtil
import com.fraggeil.ticketator.core.domain.DateTimeUtil.convertUtcToLocalMillis
import com.fraggeil.ticketator.core.domain.FormattedDateStyle
import com.fraggeil.ticketator.core.domain.toFormattedDate
import com.fraggeil.ticketator.core.presentation.Sizes
import com.fraggeil.ticketator.core.presentation.Strings
import com.fraggeil.ticketator.core.presentation.Strings.value
import com.fraggeil.ticketator.core.presentation.components.MyButton
import com.fraggeil.ticketator.core.presentation.components.MyButtonStyle
import com.fraggeil.ticketator.core.presentation.components.MyCalendarDateSelector
import com.fraggeil.ticketator.core.presentation.components.MyCircularButton
import com.fraggeil.ticketator.core.presentation.components.MyDepartureItem
import com.fraggeil.ticketator.core.presentation.components.MyInfoButton
import com.fraggeil.ticketator.core.presentation.components.PostsCarousel
import com.fraggeil.ticketator.core.presentation.components.SelectItemAndSubItemDialog
import com.fraggeil.ticketator.core.presentation.components.changeScrollStateByMouse
import com.fraggeil.ticketator.core.theme.AppTypography
import com.fraggeil.ticketator.core.theme.BG_White
import com.fraggeil.ticketator.core.theme.Blue
import com.fraggeil.ticketator.core.theme.BlueDark
import com.fraggeil.ticketator.core.theme.BlueDarkSecondary
import com.fraggeil.ticketator.core.theme.White
import com.fraggeil.ticketator.domain.FakeData
import com.fraggeil.ticketator.domain.model.Filter
import com.fraggeil.ticketator.domain.model.FilterType
import com.fraggeil.ticketator.domain.model.Post
import org.jetbrains.compose.resources.painterResource
import ticketator.composeapp.generated.resources.Res
import ticketator.composeapp.generated.resources.uzbekistan

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel,
    navigateToPost: (Post) -> Unit,
    navigateToSearchResults: (Filter) -> Unit
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(true){
        viewModel.oneTimeState.collect{oneTimeState ->
            when(oneTimeState){
                is HomeOneTimeState.NavigateToSearchResults -> navigateToSearchResults(oneTimeState.filter)
            }
        }
    }

    HomeScreen(
        state = state,
        onAction = {
            when (it){
                is HomeAction.OnPostClicked -> {
                    navigateToPost(it.post)
                }
                else -> Unit
            }
            viewModel.onAction(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit
) {
    val isSelectFromVisible = remember { mutableStateOf(false) }
    val isSelectToVisible = remember { mutableStateOf(false) }
    val fromDatePickerState = remember { mutableStateOf(false) }
    val toDatePickerState = remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize().background(BG_White)) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.35f)
                .clip(RoundedCornerShape(bottomEnd = 32.dp, bottomStart = 32.dp))
                .background(color = BlueDark, shape = RoundedCornerShape(bottomEnd = 32.dp, bottomStart = 32.dp))
                .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 48.dp),
            contentAlignment = Alignment.Center
        ){
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(Res.drawable.uzbekistan),
                contentDescription = null,
                colorFilter = ColorFilter.tint(BlueDarkSecondary)
            )
        }
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .changeScrollStateByMouse(isVerticalScroll = true, scrollState = scrollState)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row(
                modifier = Modifier
                    .padding(horizontal = Sizes.horizontal_out_padding)
                    .padding(top = Sizes.vertical_inner_padding).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Column(
                   modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start
                ){
                    Row(
                        modifier = Modifier.height(IntrinsicSize.Max),
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = White
                        )
                        Text(
                            text = Strings.Location.value(),
                            color = White,
                            style = AppTypography().bodyMedium,
                            maxLines = 1,
                        )
                    }
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
//                        text = "${state.currentLocation?.second?.name}, ${state.currentLocation?.first?.name}",
                        text = "Dhaka, Bangladesh",
                        color = White,
                        style = AppTypography().titleLarge.copy(fontWeight = FontWeight.Medium),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                MyCircularButton(
                    icon = painterResource(Res.drawable.uzbekistan),
                    onClick = {onAction(HomeAction.OnNotificationClicked)}
                )
            }
            Card(
                modifier = Modifier
                    .padding(horizontal = Sizes.horizontal_out_padding)
                    .padding(top = 32.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors().copy(containerColor = White),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = Sizes.cardElevation)
            ){
                Column(modifier = Modifier
                    .padding(Sizes.horizontal_inner_padding)
                    .fillMaxWidth()
                ){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ){
                        MyButton(
                            style = MyButtonStyle.SMALL,
                            text = "One-way",
                            onClick = { onAction(HomeAction.OnOneWayClicked) },
                            isSelected = state.filter.type == FilterType.ONE_WAY
                        )
                        MyButton(
                            style = MyButtonStyle.SMALL,
                            text = "Round trip",
                            onClick = { onAction(HomeAction.OnRoundTripClicked) },
                            isSelected = state.filter.type == FilterType.ROUND_TRIP
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(top = Sizes.vertical_inner_padding)
                            .fillMaxWidth()
                    ){
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ){
                            MyDepartureItem(
                                icon = Icons.Default.Menu,
                                label = Strings.From.value(),
                                text = state.filter.fromDistrict?.name,
                                abbr = state.filter.fromDistrict?.abbr,
                                onClick = { isSelectFromVisible.value = true },
                                isLoading = state.isLoadingFromRegions,
                                isEnabled = true
                            )
                            MyDepartureItem(
                                modifier = Modifier.padding(top = 12.dp),
                                icon = Icons.Default.Home,
                                label = Strings.To.value(),
                                text = state.filter.toDistrict?.name,
                                abbr = state.filter.toDistrict?.abbr,
                                onClick = { isSelectToVisible.value = true },
                                isLoading = state.isLoadingToRegions,
                                isEnabled = state.filter.fromDistrict != null
                            )
                        }
                        IconButton(
                            modifier = Modifier
                                .padding(end = Sizes.horizontal_inner_padding)
                                .align(Alignment.CenterEnd)
                                .size(48.dp)
                                .background(
                                    color = Blue,
                                    shape = CircleShape
                                ),
                            onClick = {onAction(HomeAction.OnReverseDistrictsClicked)},
                        ){
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = null,
                                tint = White,
                            )
                        }

                    }
                    MyInfoButton(
                        modifier = Modifier.padding(top = Sizes.vertical_inner_padding).fillMaxWidth(),
                        label = "Departure",
                        text = state.filter.dateGo?.toFormattedDate(style = FormattedDateStyle.Words) ?: "Select",
                        onClick = {
                            fromDatePickerState.value = true
                        }
                    )
                    Box(
                        modifier = Modifier
                            .animateContentSize(
                                animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
                            ),
                    ){
                        if (state.filter.type == FilterType.ROUND_TRIP){
                            MyInfoButton(
                                modifier = Modifier.padding(top = Sizes.vertical_inner_padding).fillMaxWidth(),
                                label = "Return",
                                text = state.filter.dateBack?.toFormattedDate(style = FormattedDateStyle.Words) ?: "Select",
                                onClick = {
                                    toDatePickerState.value = true
                                }
                            )
                        }
                    }
                    MyButton(
                        modifier = Modifier.padding(top = Sizes.vertical_inner_padding).fillMaxWidth(),
                        text = "Search flights",
                        onClick = {onAction(HomeAction.OnSearchClicked)},
                        style = MyButtonStyle.FILLED
                    )
                }

            }
            Spacer(modifier = Modifier.height(Sizes.vertical_inner_padding))
            if (state.isLoadingPosts){
                PostsCarousel(
                    modifier = Modifier.fillMaxWidth(),
                    isLoading = state.isLoadingPosts,
                    posts = FakeData.fakePosts,
                    onClick = { },
                )
            }else{
                PostsCarousel(
                    modifier = Modifier.fillMaxWidth(),
                    isLoading = state.isLoadingPosts,
                    posts = state.posts,
                    onClick = { onAction(HomeAction.OnPostClicked(it)) }
                )
            }
            Spacer(modifier = Modifier.height(Sizes.default_bottom_padding_double))
        }
    }



    SelectItemAndSubItemDialog(
        title = "From",
        items = state.fromRegions,
        selectedItem = state.filter.fromRegion,
        selectedSubItem = state.filter.fromDistrict,
        getSubItems = {it.districts},
        itemToString = {it.name},
        subItemToString = {item -> item.name},
        onSubItemSelected = {
            onAction(HomeAction.OnDistrictFromSelected(it))
         },
        onItemSelected = {
            onAction(HomeAction.OnRegionFromSelected(it))
        },
        isLoading = state.isLoadingFromRegions,
        isVisible = isSelectFromVisible
    )
    SelectItemAndSubItemDialog(
        title = "To",
        items = state.toRegions,
        selectedItem = state.filter.toRegion,
        selectedSubItem = state.filter.toDistrict,
        getSubItems = {it.districts},
        itemToString = {it.name},
        subItemToString = {item -> item.name},
        onSubItemSelected = {
            onAction(HomeAction.OnDistrictToSelected(it))
        },
        onItemSelected = {
            onAction(HomeAction.OnRegionToSelected(it))
        },
        isLoading = state.isLoadingToRegions,
        isVisible = isSelectToVisible
    )

    MyCalendarDateSelector(
        minimalSelectableDate = DateTimeUtil.getToday().first,
        onDateSelected = { onAction(HomeAction.OnDateFromSelected(it)) },
        currentSelectedDate = state.filter.dateGo,
        isVisible = fromDatePickerState
    )
    if (state.filter.type == FilterType.ROUND_TRIP && state.filter.dateGo != null){
        MyCalendarDateSelector(
            minimalSelectableDate = state.filter.dateGo,
            onDateSelected = { onAction(HomeAction.OnDateToSelected(it)) },
            currentSelectedDate = state.filter.dateBack,
            isVisible = toDatePickerState
        )
    }
}
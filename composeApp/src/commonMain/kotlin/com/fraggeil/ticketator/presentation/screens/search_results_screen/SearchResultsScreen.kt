package com.fraggeil.ticketator.presentation.screens.search_results_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.stopScroll
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fraggeil.ticketator.core.domain.DateTimeUtil
import com.fraggeil.ticketator.core.domain.FormattedDateStyle
import com.fraggeil.ticketator.core.domain.UiType
import com.fraggeil.ticketator.core.domain.getUiType
import com.fraggeil.ticketator.core.domain.rememberScreenSizeInfo
import com.fraggeil.ticketator.core.domain.setFromTimeToBeginningOfTheDay
import com.fraggeil.ticketator.core.domain.toFormattedDate
import com.fraggeil.ticketator.core.presentation.Sizes
import com.fraggeil.ticketator.core.presentation.Sizes.default_bottom_padding
import com.fraggeil.ticketator.core.presentation.Sizes.horizontal_inner_padding
import com.fraggeil.ticketator.core.presentation.Sizes.vertical_inner_padding
import com.fraggeil.ticketator.core.presentation.Sizes.vertical_out_padding
import com.fraggeil.ticketator.core.presentation.components.MyButton
import com.fraggeil.ticketator.core.presentation.components.MyCalendarDateSelector
import com.fraggeil.ticketator.core.presentation.components.MyCircularButton
import com.fraggeil.ticketator.core.presentation.components.changeScrollStateByMouse
import com.fraggeil.ticketator.core.theme.AppTypography
import com.fraggeil.ticketator.core.theme.Blue
import com.fraggeil.ticketator.core.theme.BlueDark
import com.fraggeil.ticketator.core.theme.BlueDarkSecondary
import com.fraggeil.ticketator.core.theme.White
import com.fraggeil.ticketator.domain.FakeData
import com.fraggeil.ticketator.domain.model.Journey
import com.fraggeil.ticketator.presentation.screens.search_results_screen.components.Dots
import com.fraggeil.ticketator.presentation.screens.search_results_screen.components.JourneyListItem
import org.jetbrains.compose.resources.painterResource
import ticketator.composeapp.generated.resources.Res
import ticketator.composeapp.generated.resources.ic_close
import ticketator.composeapp.generated.resources.uzbekistan

@Composable
fun SearchResultsScreenRoot(
    viewModel: SearchResultsViewModel,
    navigateBack: () -> Unit,
    navigateToJourneyDetails: (Journey) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    SearchResultsScreen(
        state = state,
        onAction = {
            when(it){
                SearchResultsAction.OnBackClicked -> navigateBack()
                is SearchResultsAction.OnJourneyClicked -> navigateToJourneyDetails(it.journey)
                else -> {}
            }
            viewModel.onAction(it)
        }
    )
}


@Composable
fun SearchResultsScreen(
    state: SearchResultsState,
    onAction: (SearchResultsAction) -> Unit
) {
    val uiType = rememberScreenSizeInfo().getUiType()
    val calendarDateVisibility = remember { mutableStateOf(false) }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(BlueDark)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.35f)
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
//        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize(),
//                .changeScrollStateByMouse(isVerticalScroll = true, scrollState = scrollState)
//                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            state.filter?.let {
                Row(
                    modifier = Modifier
                        .padding(horizontal = Sizes.horizontal_out_padding)
                        .padding(top = vertical_inner_padding).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    MyCircularButton(
                        icon = painterResource(Res.drawable.ic_close),
                        onClick = { onAction(SearchResultsAction.OnBackClicked) }
                    )
                    Text(
                        text = "Journey Results",
                        color = White,
                        style = AppTypography().headlineSmall.copy(fontWeight = FontWeight.Medium),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    MyCircularButton(
                        icon = painterResource(Res.drawable.uzbekistan),
                        onClick = { onAction(SearchResultsAction.OnBackClicked) }
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(horizontal = Sizes.horizontal_out_padding)
                        .padding(top = vertical_out_padding).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = state.filter.fromDistrict?.abbr?.uppercase() ?: "",
                            color = White,
                            style = AppTypography().headlineLarge.copy(fontWeight = FontWeight.SemiBold),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${state.filter.fromDistrict?.name}, ${state.filter.fromRegion?.name}",
                            color = White,
                            style = AppTypography().bodyMedium.copy(fontWeight = FontWeight.Normal),
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Dots(
                        modifier = Modifier.weight(2f),
                        iconColor = White,
                        dotsColor = White,
                        pinColor = Blue
                    )
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = state.filter.toDistrict?.abbr?.uppercase() ?: "",
                            color = White,
                            style = AppTypography().headlineLarge.copy(fontWeight = FontWeight.SemiBold),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${state.filter.toDistrict?.name}, ${state.filter.toRegion?.name}",
                            color = White,
                            style = AppTypography().bodyMedium.copy(fontWeight = FontWeight.Normal),
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Text(
                    modifier = Modifier.padding(top = vertical_out_padding),
                    text = "${state.journeys.size} Results Found".takeIf { !state.isLoadingJourneys } ?: "Loading Results...",
                    color = White,
                    style = AppTypography().bodyLarge.copy(fontWeight = FontWeight.Normal),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                val scrollState = rememberScrollState()
                LaunchedEffect(state.filter.dateGo){
                    scrollState.scrollTo(0)
                }
//                val scrollState = remember(state.filter.dateGo){
//                    ScrollState(0)
//                }
                Row(
                    modifier = Modifier
                        .padding(top = vertical_inner_padding)
                        .fillMaxWidth()
                        .changeScrollStateByMouse(
                            isVerticalScroll = false,
                            scrollState = scrollState,
                            scope = rememberCoroutineScope()
                        )
                        .horizontalScroll(scrollState),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(Sizes.horizontal_out_padding))
                    (-1..3).map {
                        val date = state.filter.dateGo!!.setFromTimeToBeginningOfTheDay()!! + it * 24 * 60 * 60 * 1000L
                        if (date >= DateTimeUtil.getToday().first){
                            MyButton(
                                text = date.toFormattedDate(
                                    style = FormattedDateStyle.Words,
                                    hoursEnabled = false
                                ),
                                onClick = {
                                    onAction(SearchResultsAction.OnDateClicked(date))
                                },
                                isSelected = it == 0
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(Sizes.horizontal_out_padding))
                }
                if (!state.isLoadingJourneys && !state.isLoading && state.journeys.isEmpty()){
                    Column(
                        modifier = Modifier
                            .padding(top = vertical_inner_padding)
                            .weight(1f)
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        Text(
                            modifier = Modifier.padding(horizontal = default_bottom_padding),
                            text = "No Results Found :( Maybe you can try another date?",
                            color = White,
                            style = AppTypography().titleMedium.copy(fontWeight = FontWeight.SemiBold),
                            textAlign = TextAlign.Center,
                            maxLines = 3,
                        )
                        MyButton(
                            modifier = Modifier.padding(top = vertical_out_padding),
                            text = "Select Another Date",
                            onClick = {
                                calendarDateVisibility.value = true
                            }
                        )
                        Spacer(modifier = Modifier.height(Sizes.default_bottom_padding_double))
                    }
                }else{
                    val listState = rememberLazyGridState()
                    LazyVerticalGrid(
                        modifier = Modifier
                            .padding(top = vertical_inner_padding)
                            .fillMaxWidth()
                            .changeScrollStateByMouse(
                                isVerticalScroll = true,
                                scrollState = listState,
                                isLoading = state.isLoadingJourneys
                            ),
                        state = listState,
                        columns = GridCells.Fixed(if (uiType == UiType.COMPACT) 1 else 2),
                        contentPadding = PaddingValues(
                            start = horizontal_inner_padding,
                            end = horizontal_inner_padding,
//                        top = vertical_inner_padding,
                            bottom = default_bottom_padding
                        ),
                        horizontalArrangement = Arrangement.spacedBy(
                            12.dp,
                            Alignment.CenterHorizontally
                        ),
                        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
                    ) {
                        if (state.isLoadingJourneys) {
                            items(10) {
                                JourneyListItem(
                                    modifier = Modifier.fillMaxWidth(),
                                    isLoading = true,
                                    journey = FakeData.fakeJourneys[0],
                                    onClick = {}
                                )
                            }
                        }else{
                            items(items = state.journeys) {
                                JourneyListItem(
                                    modifier = Modifier.fillMaxWidth(),
                                    journey = it,
                                    onClick = { onAction(SearchResultsAction.OnJourneyClicked(it)) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    MyCalendarDateSelector(
        minimalSelectableDate = DateTimeUtil.getToday().first,
        onDateSelected = { onAction(SearchResultsAction.OnDateClicked(it))},
        currentSelectedDate = state.filter?.dateGo,
        isVisible = calendarDateVisibility
    )
}
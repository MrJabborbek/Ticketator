package com.fraggeil.ticketator.presentation.screens.tickets_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fraggeil.ticketator.core.domain.ShareFileModel
import com.fraggeil.ticketator.core.domain.UiType
import com.fraggeil.ticketator.core.domain.getUiType
import com.fraggeil.ticketator.core.domain.imageBitmapToByteArray
import com.fraggeil.ticketator.core.domain.rememberScreenSizeInfo
import com.fraggeil.ticketator.core.domain.rememberShareManager
import com.fraggeil.ticketator.core.domain.toFormattedDate
import com.fraggeil.ticketator.core.presentation.Sizes
import com.fraggeil.ticketator.core.presentation.Sizes.default_bottom_padding_double
import com.fraggeil.ticketator.core.presentation.Sizes.horizontal_out_padding
import com.fraggeil.ticketator.core.presentation.Sizes.vertical_inner_padding
import com.fraggeil.ticketator.core.presentation.Sizes.vertical_out_padding
import com.fraggeil.ticketator.core.presentation.StatusBarColors
import com.fraggeil.ticketator.core.presentation.components.MyButton
import com.fraggeil.ticketator.core.presentation.components.changeScrollStateByMouse
import com.fraggeil.ticketator.core.theme.AppTypography
import com.fraggeil.ticketator.core.theme.BlueDark
import com.fraggeil.ticketator.core.theme.White
import com.fraggeil.ticketator.domain.FakeData
import com.fraggeil.ticketator.presentation.screens.tickets_screen.components.TicketItem
import io.github.vinceglb.filekit.compose.rememberFileSaverLauncher
import kotlinx.coroutines.launch

@Composable
fun TicketsScreenRoot(
    viewModel: TicketsViewModel,
    navigateBack: () -> Unit,
) {
    StatusBarColors(isDarkText = false)
    val state by viewModel.state.collectAsStateWithLifecycle()
    TicketsScreen(
        state = state,
        onAction = {
            when(it){
                TicketsAction.OnBuyTicketButtonClicked -> navigateBack()
                else -> Unit
            }
            viewModel.onAction(it)
        }
    )
}

@Composable
fun TicketsScreen(
    state: TicketsState,
    onAction: (TicketsAction) -> Unit
) {
    val saveFileToStorageLauncher = rememberFileSaverLauncher{}
    val shareManager = rememberShareManager()
    val uiType = rememberScreenSizeInfo().getUiType()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize()
            .background(BlueDark),
    ) {
        val scrollState = rememberLazyGridState()
        LazyVerticalGrid(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .changeScrollStateByMouse(
                    scrollState = scrollState,
                    isVerticalScroll = true
                ),
            state = scrollState,
            contentPadding = PaddingValues(start = horizontal_out_padding, end = horizontal_out_padding,  bottom = default_bottom_padding_double),
            horizontalArrangement = Arrangement.spacedBy(Sizes.horizontal_inner_padding),
            verticalArrangement = Arrangement.spacedBy(vertical_inner_padding),
            columns = GridCells.Fixed(if (uiType == UiType.COMPACT) 1 else if (uiType == UiType.MEDIUM) 2 else 3)
        ) {
            item(span = { GridItemSpan(if (uiType == UiType.COMPACT) 1 else if (uiType == UiType.MEDIUM) 2 else 3) }) {
                Box(modifier = Modifier.statusBarsPadding())
            }
            if (state.initialTickets.isNotEmpty()) {
                item(span = { GridItemSpan(if (uiType == UiType.COMPACT) 1 else if (uiType == UiType.MEDIUM) 2 else 3) }) {
                    Text(
                        text = "Your tickets",
                        style = AppTypography().titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = White
                    )
                }

                items(state.initialTickets) { ticket ->
                    TicketItem(
                        modifier = Modifier.fillMaxWidth(),
                        ticket = ticket,
                        onClick = { onAction(TicketsAction.OnTicketClicked(ticket)) },
                        onShareClick = {
                            coroutineScope.launch {
                                it.imageBitmapToByteArray().let { bytes ->
                                    shareManager.shareFile(
                                        ShareFileModel(
                                            fileName = "ticket-${ticket.passenger.seat}-${ticket.journey.timeStart.toFormattedDate()}.png",
                                            bytes = bytes
                                        )
                                    )
                                }
                            }
                        },
                        onDownloadClick = {
                            coroutineScope.launch {
                                it.imageBitmapToByteArray().let { bytes ->
                                    saveFileToStorageLauncher.launch(
                                        baseName = "ticket-${ticket.passenger.seat}-${ticket.journey.timeStart.toFormattedDate()}",
                                        extension = "png",
                                        bytes = bytes
                                    )
                                }
                            }
                        },
                    )
                }
                item(span = { GridItemSpan(if (uiType == UiType.COMPACT) 1 else if (uiType == UiType.MEDIUM) 2 else 3) }) {
                    Box {
                        MyButton(
                            modifier = Modifier.align(Alignment.Center).widthIn(max = 400.dp).fillMaxWidth(),
                            text = "Show old tickets",
                            onClick = { onAction(TicketsAction.OnShowAllTicketsClicked) }
                        )
                    }
                }
            } else if (state.isLoading){
                items(10){
                    TicketItem(
                        isLoading = true,
                        modifier = Modifier.fillMaxWidth(),
                        ticket = FakeData.fakeTickets[0],
                        onClick = {},
                        onShareClick = {},
                        onDownloadClick = {},
                    )
                }
            } else  {
                item(span = { GridItemSpan(if (uiType == UiType.COMPACT) 1 else if (uiType == UiType.MEDIUM) 2 else 3) }) {
                    Text(
                        text = "Waiting for journey",
                        style = AppTypography().titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = White
                    )
                }
                if (state.comingTickets.isEmpty()) {
                    item(span = { GridItemSpan(if (uiType == UiType.COMPACT) 1 else if (uiType == UiType.MEDIUM) 2 else 3) }) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "There are no ticket found that are waiting for journey :(",
                                style = AppTypography().bodyMedium,
                                textAlign = TextAlign.Center,
                                color = White

                            )
                            MyButton(
                                text = "Buy a ticket",
                                onClick = { onAction(TicketsAction.OnBuyTicketButtonClicked) }
                            )
                        }
                    }
                } else {
                    items(state.comingTickets) { ticket ->
                        TicketItem(
                            modifier = Modifier.fillMaxWidth(),
                            ticket = ticket,
                            onClick = { onAction(TicketsAction.OnTicketClicked(ticket)) },
                            onShareClick = {
                                coroutineScope.launch {
                                    it.imageBitmapToByteArray().let { bytes ->
                                        shareManager.shareFile(
                                            ShareFileModel(
                                                fileName = "ticket-${ticket.passenger.seat}-${ticket.journey.timeStart.toFormattedDate()}.png",
                                                bytes = bytes
                                            )
                                        )
                                    }
                                }
                            },
                            onDownloadClick = {
                                coroutineScope.launch {
                                    it.imageBitmapToByteArray().let { bytes ->
                                        saveFileToStorageLauncher.launch(
                                            baseName = "ticket-${ticket.passenger.seat}-${ticket.journey.timeStart.toFormattedDate()}",
                                            extension = "png",
                                            bytes = bytes
                                        )
                                    }
                                }
                            },
                        )
                    }
                }
                item(span = { GridItemSpan(if (uiType == UiType.COMPACT) 1 else if (uiType == UiType.MEDIUM) 2 else 3) }) {
                    Text(
                        text = "Old journeys",
                        style = AppTypography().titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        modifier = Modifier.padding(top = Sizes.vertical_inner_padding),
                        color = White

                    )
                }
                if (state.pastTickets.isEmpty()) {
                    item(span = { GridItemSpan(if (uiType == UiType.COMPACT) 1 else if (uiType == UiType.MEDIUM) 2 else 3) }) {
                        Text(
                            text = "You have not any old journeys yet.",
                            style = AppTypography().bodyMedium,
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Center,
                            color = White
                        )
                    }
                } else {
                    items(state.pastTickets) { ticket ->
                        TicketItem(
                            modifier = Modifier.fillMaxWidth(),
                            ticket = ticket,
                            onClick = { onAction(TicketsAction.OnTicketClicked(ticket)) },
                            onShareClick = {
                                coroutineScope.launch {
                                    it.imageBitmapToByteArray().let { bytes ->
                                        shareManager.shareFile(
                                            ShareFileModel(
                                                fileName = "ticket-${ticket.passenger.seat}-${ticket.journey.timeStart.toFormattedDate()}.png",
                                                bytes = bytes
                                            )
                                        )
                                    }
                                }
                            },
                            onDownloadClick = {
                                coroutineScope.launch {
                                    it.imageBitmapToByteArray().let { bytes ->
                                        saveFileToStorageLauncher.launch(
                                            baseName = "ticket-${ticket.passenger.seat}-${ticket.journey.timeStart.toFormattedDate()}",
                                            extension = "png",
                                            bytes = bytes
                                        )
                                    }
                                }
                            },
                        )
                    }
                }
            }
        }
    }
}
package com.fraggeil.ticketator.presentation.screens.tickets_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
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
import com.fraggeil.ticketator.core.presentation.Sizes.default_bottom_padding
import com.fraggeil.ticketator.core.presentation.Sizes.horizontal_out_padding
import com.fraggeil.ticketator.core.presentation.Sizes.vertical_inner_padding
import com.fraggeil.ticketator.core.presentation.Sizes.vertical_out_padding
import com.fraggeil.ticketator.core.presentation.components.MyButton
import com.fraggeil.ticketator.core.presentation.components.changeScrollStateByMouse
import com.fraggeil.ticketator.core.theme.Blue
import com.fraggeil.ticketator.core.theme.BlueDark
import com.fraggeil.ticketator.presentation.screens.tickets_screen.components.TicketItem
import io.github.vinceglb.filekit.compose.rememberFileSaverLauncher
import kotlinx.coroutines.launch

@Composable
fun TicketsScreenRoot(
    viewModel: TicketsViewModel,
    navigateBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    TicketsScreen(
        state = state,
        onAction = {
            when(it){
                TicketsAction.OnBackClicked -> navigateBack()
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


    val screenSizeInfo = rememberScreenSizeInfo()
    val uiType = screenSizeInfo.getUiType()

//    val graphicsLayer = rememberGraphicsLayer()
    val coroutineScope = rememberCoroutineScope()
//    var bitmap by remember { mutableStateOf<ImageBitmap?>(null) }

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
            contentPadding = PaddingValues(start = horizontal_out_padding, end = horizontal_out_padding, top = vertical_out_padding, bottom = default_bottom_padding),
            horizontalArrangement = Arrangement.spacedBy(Sizes.horizontal_inner_padding),
            verticalArrangement = Arrangement.spacedBy(vertical_inner_padding),
            columns = GridCells.Fixed(if (uiType == UiType.COMPACT) 1 else if (uiType == UiType.MEDIUM) 2 else 3)
        ) {
            items(state.tickets){ ticket ->
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

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = Blue.copy(0.2f))
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
                ) {
                    MyButton(
                        modifier = Modifier.align(Alignment.CenterVertically).widthIn(max = 400.dp).fillMaxWidth(),
//                        modifier = Modifier.padding(top = vertical_out_padding, start = Sizes.horizontal_out_padding, end = Sizes.horizontal_out_padding).widthIn(max = 600.dp).fillMaxWidth(),
                        text = "Bosh sahifa",
                        onClick = {
                            onAction(TicketsAction.OnBackClicked)
                        },
                    )
                }
            }
    }
}
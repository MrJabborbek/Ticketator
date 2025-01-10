package com.fraggeil.ticketator.core.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.fraggeil.ticketator.domain.FakeData
import com.fraggeil.ticketator.presentation.screens.tickets_screen.components.TicketItem
import kotlinx.coroutines.launch

@Composable
fun renderComposableToBitmap(
    onDraw: (ImageBitmap) -> Unit,
) {
    val graphicsLayer = rememberGraphicsLayer()
    val coroutineScope = rememberCoroutineScope()

    TicketItem(
        modifier = Modifier.width(350.dp)
            .drawWithContent {
            graphicsLayer.record {
                this@drawWithContent.drawContent()
            }
            drawLayer(graphicsLayer)
        }
            .onGloballyPositioned {
                coroutineScope.launch {
                    onDraw(graphicsLayer.toImageBitmap())
                }
            }
            .size(0.dp),
        ticket = FakeData.fakeTickets[0],
        onClick = {},
        onShareClick = {},
        onDownloadClick = {},
    )
}
package com.fraggeil.ticketator.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.unit.dp
import coil3.Bitmap
import kotlinx.coroutines.launch

@Composable
fun SaveComposableAsBitmap(
    content: @Composable () -> Unit,
    width: Int, // Custom width
    height: Int, // Custom height
    onBitmapReady: (ImageBitmap) -> Unit
) {
    val graphicsLayer = rememberGraphicsLayer()
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .size(width.dp, height.dp)
            .drawWithContent {
                graphicsLayer.record {
                    this@drawWithContent.drawContent()
                }
                drawLayer(graphicsLayer)
            }
            .clickable {
                coroutineScope.launch {
                    onBitmapReady(graphicsLayer.toImageBitmap())
                }
            },
        contentAlignment = Alignment.Center
    ){
        content()
    }
}
package com.fraggeil.ticketator.core.domain

import androidx.compose.ui.graphics.ImageBitmap

expect fun ImageBitmap.imageBitmapToByteArray(format: String = "png", quality: Int = 100): ByteArray

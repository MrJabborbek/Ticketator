package com.fraggeil.ticketator.core.domain

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import java.io.ByteArrayOutputStream

actual fun ImageBitmap.imageBitmapToByteArray(
    format: String,
    quality: Int,
): ByteArray {
    val bitmap = this.asAndroidBitmap()
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.valueOf(format.uppercase()), quality, stream)
    return stream.toByteArray()
}
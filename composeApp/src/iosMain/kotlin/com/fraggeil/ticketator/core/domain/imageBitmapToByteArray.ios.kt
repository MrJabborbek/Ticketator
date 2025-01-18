package com.fraggeil.ticketator.core.domain

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import org.jetbrains.skia.Image
import org.jetbrains.skia.EncodedImageFormat

actual fun ImageBitmap.imageBitmapToByteArray(
    format: String,
    quality: Int
): ByteArray {
    val skiaBitmap = this.asSkiaBitmap()
    val encodedFormat = when (format.lowercase()) {
        "png" -> EncodedImageFormat.PNG
        "jpeg", "jpg" -> EncodedImageFormat.JPEG
        "webp" -> EncodedImageFormat.WEBP
        else -> throw IllegalArgumentException("Unsupported format: $format")
    }
    return Image.makeFromBitmap(skiaBitmap).encodeToData(encodedFormat, quality)?.bytes
        ?: throw IllegalStateException("Failed to encode ImageBitmap")
}

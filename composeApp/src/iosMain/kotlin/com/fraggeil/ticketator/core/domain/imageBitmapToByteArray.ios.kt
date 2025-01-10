package com.fraggeil.ticketator.core.domain

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toUIImage
import platform.Foundation.*
import platform.UIKit.UIImage
import platform.UIKit.UIImagePNGRepresentation
import platform.UIKit.UIImageJPEGRepresentation

actual fun ImageBitmap.imageBitmapToByteArray(
    format: String,
    quality: Int,
): ByteArray {
    val uiImage = this.toUIImage() // Convert ImageBitmap to UIImage

    // Choose format: PNG or JPEG
    val nsData = when (format.lowercase()) {
        "png" -> UIImagePNGRepresentation(uiImage)
        "jpeg", "jpg" -> UIImageJPEGRepresentation(uiImage, quality.toFloat() / 100)
        else -> throw IllegalArgumentException("Unsupported format: $format")
    } ?: throw IllegalArgumentException("Failed to encode UIImage")

    // Convert NSData to ByteArray
    val byteArray = ByteArray(nsData.length.toInt())
    nsData.getBytes(byteArray.refTo(0), nsData.length)
    return byteArray
}
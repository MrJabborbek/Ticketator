package com.fraggeil.ticketator.core.domain

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image
import org.jetbrains.skiko.toBufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

actual fun ImageBitmap.imageBitmapToByteArray(
    format: String,
    quality: Int,
): ByteArray {
    val bufferedImage =this.asSkiaBitmap().toBufferedImage()
    val stream = ByteArrayOutputStream()
    ImageIO.write(bufferedImage, format, stream)
    return stream.toByteArray()
}
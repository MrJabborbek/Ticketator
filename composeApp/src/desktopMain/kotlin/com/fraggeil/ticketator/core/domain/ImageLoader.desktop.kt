package com.fraggeil.ticketator.core.domain

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.disk.DiskCache
import com.fraggeil.ticketator.core.presentation.Strings
import com.fraggeil.ticketator.core.presentation.Strings.value
import okio.Path.Companion.toOkioPath
import java.nio.file.Paths

actual fun imageLoader(context: PlatformContext): ImageLoader {
    val imageLoader = ImageLoader.Builder(context)
        .diskCache {
            DiskCache.Builder()
                .directory(Paths.get(System.getProperty("java.io.tmpdir"),
                    Strings.AppName.value()
                ).toOkioPath())
                .maxSizePercent(0.2)
                .build()
        }
        .build()
    return imageLoader
}

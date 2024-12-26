package com.fraggeil.ticketator.core.domain

import android.content.Context
import coil3.ImageLoader
import coil3.SingletonImageLoader

actual fun imageLoader(context: Context): ImageLoader {
    return SingletonImageLoader.get(context)
}
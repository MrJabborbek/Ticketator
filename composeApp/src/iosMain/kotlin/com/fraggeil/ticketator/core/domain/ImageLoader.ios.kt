package com.fraggeil.ticketator.core.domain

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader

actual fun imageLoader(context: PlatformContext):ImageLoader{
    return SingletonImageLoader.get(context)
}
package com.fraggeil.ticketator.core.domain

import coil3.ImageLoader
import coil3.PlatformContext


//@Composable
    expect fun imageLoader(context: PlatformContext):ImageLoader // If default, it is not uploading images when opened two apps that uses coil in windows


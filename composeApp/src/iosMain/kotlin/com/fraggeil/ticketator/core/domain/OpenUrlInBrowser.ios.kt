package com.fraggeil.ticketator.core.domain

import platform.Foundation.NSURL
import platform.UIKit.UIApplication
actual class OpenUrlInBrowser{
    actual fun open(url: String){
        val nsUrl = NSURL(string = url)
        UIApplication.sharedApplication.openURL(nsUrl)
    }
}
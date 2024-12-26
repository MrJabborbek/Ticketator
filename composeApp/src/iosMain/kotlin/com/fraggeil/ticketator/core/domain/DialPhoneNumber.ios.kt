package com.fraggeil.ticketator.core.domain

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual class DialPhoneNumber {
    actual fun dialPhoneNumber(phoneNumber: String) {
        try {
            NSURL.URLWithString("tel:$phoneNumber")?.let { phoneUrl ->
                if (UIApplication.sharedApplication.canOpenURL(phoneUrl)) {
                    UIApplication.sharedApplication.openURL(phoneUrl)
                } else {
                    throw Exception("Could not open phone URL")
                }
                //Add this in info.plist
//                <key>LSApplicationQueriesSchemes</key>
//                <array>
//                <string>tel</string>
//                </array>
            }
        } catch (e: Exception) {
            e.printStackTrace()
            //opening by browser
            val nsUrl = NSURL(string = "tel:$phoneNumber")
            UIApplication.sharedApplication.openURL(nsUrl)
        }
    }
}
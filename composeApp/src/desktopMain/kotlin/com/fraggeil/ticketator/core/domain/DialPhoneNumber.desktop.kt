package com.fraggeil.ticketator.core.domain

import java.awt.Desktop
import java.net.URI

actual class DialPhoneNumber {
    actual fun dialPhoneNumber(phoneNumber: String) {
        return try {
            val uri = URI("tel:$phoneNumber")
            Desktop.getDesktop().browse(uri)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
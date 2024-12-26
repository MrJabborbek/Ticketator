package com.fraggeil.ticketator.core.domain

import java.awt.Desktop
import java.net.URI

actual class OpenUrlInBrowser{
    actual fun open(url: String){
        val uri = URI(url)
        Desktop.getDesktop().browse(uri)
    }
}
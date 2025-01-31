package com.fraggeil.ticketator.core.domain

import android.content.Context
import android.content.Intent
import android.net.Uri

actual class OpenUrlInBrowser(private val context: Context){
    actual fun open(url: String){
        val webpage: Uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, webpage).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

        context.startActivity(intent)


    }
}
package com.fraggeil.ticketator.core.domain


import android.content.Context
import android.content.Intent

actual class DialPhoneNumber(val context: Context) {
    actual fun dialPhoneNumber(phoneNumber: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = android.net.Uri.parse("tel:$phoneNumber")
            context.startActivity(intent)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}
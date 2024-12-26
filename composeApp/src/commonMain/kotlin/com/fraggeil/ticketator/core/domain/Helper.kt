package com.fraggeil.ticketator.core.domain

fun String.formatWithSpacesNumber(separateEach: Int = 3): String {
    val parts = this.split(".") // Split the number into integer and decimal parts
    val integerPart = parts[0] // The part before the decimal point
    var formattedIntegerPart = ""

    // Format the integer part with spaces
    for (i in integerPart.indices) {
        if (i % separateEach == 0 && i != 0) {
            formattedIntegerPart = " $formattedIntegerPart"
        }
        formattedIntegerPart = integerPart[integerPart.length - i - 1] + formattedIntegerPart
    }

    // If there is a decimal part, add it back
    return if (parts.size > 1) {
        "$formattedIntegerPart.${parts[1]}"
    } else {
        formattedIntegerPart
    }
}

fun String.isValidPhoneNumber(): Boolean {
    if (this.length != 13) return false // for uzbekistan

    val phoneRegex = "^\\+?[1-9]\\d{1,14}\$".toRegex()
    return phoneRegex.matches(this)
}


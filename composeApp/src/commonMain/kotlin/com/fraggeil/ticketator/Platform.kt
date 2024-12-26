package com.fraggeil.ticketator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
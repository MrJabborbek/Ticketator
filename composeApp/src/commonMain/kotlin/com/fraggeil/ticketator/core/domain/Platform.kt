package com.fraggeil.ticketator.core.domain

expect fun Platform() : PlatformType
enum class PlatformType{
    ANDROID, IOS, DESKTOP
}
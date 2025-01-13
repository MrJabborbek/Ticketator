package com.fraggeil.ticketator.domain.model

data class Profile(
    val id: Int,
    val firstName: String = "",
    val lastName: String = "",
    val middleName: String = "",
    val phoneNumber: String,
    val uid: String? = null,
)

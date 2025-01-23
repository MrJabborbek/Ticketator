package com.fraggeil.ticketator.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProfileEntity(
    @PrimaryKey
    val id: Int,
    val firstName: String = "",
    val lastName: String = "",
    val middleName: String = "",
    val phoneNumber: String,
    val uid: String? = null,
)
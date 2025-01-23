package com.fraggeil.ticketator.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CardDataEntity(
    @PrimaryKey
    val cardNumber: String,
    val cardValidUntil: String,
)

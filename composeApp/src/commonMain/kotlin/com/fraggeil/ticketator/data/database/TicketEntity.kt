package com.fraggeil.ticketator.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TicketEntity(
    @PrimaryKey
    val ticketId: Long,
    val qrCodeLinkOrToken: String,
    val passengerName: String,
    val seat: Int,
    val phone: String,
    val buyTime: Long,
    val timeStart: Long,
    val timeArrival: Long,
    val fromRegion: String,
    val fromDistrict: String,
    val fromDistrictAbbr: String,
    val toRegion: String,
    val toDistrict: String,
    val toDistrictAbbr: String,
    val price: Int,
    val stopAt: String
)
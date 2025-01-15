package com.fraggeil.ticketator.domain.model

data class Ticket(
    val ticketId: Long,
    val qrCodeLinkOrToken: String,
    val passenger: Passenger,
    val buyTime: Long,
    val journey: Journey,
)

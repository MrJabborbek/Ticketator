package com.fraggeil.ticketator.data.mappers

import com.fraggeil.ticketator.data.database.TicketEntity
import com.fraggeil.ticketator.domain.model.Ticket
import com.fraggeil.ticketator.domain.model.uzbekistan.District
import com.fraggeil.ticketator.domain.model.uzbekistan.Region

fun TicketEntity.toTicket(): Ticket {
    val fromDistrict = District(id = 0, name = this.fromDistrict, abbr = this.fromDistrictAbbr)
    val fromRegion = Region(id = 0, name = this.fromRegion, districts = listOf(fromDistrict))
    val toDistrict = District(id = 0, name = this.toDistrict, abbr = this.toDistrictAbbr)
    val toRegion = Region(id = 0, name = this.toRegion, districts = listOf(toDistrict))

    return Ticket(
        ticketId = this.ticketId,
        qrCodeLinkOrToken = this.qrCodeLinkOrToken,
        passenger = com.fraggeil.ticketator.domain.model.Passenger(
            name = this.passengerName,
            seat = this.seat,
            phone = this.phone
        ),
        buyTime = this.buyTime,
        journey = com.fraggeil.ticketator.domain.model.Journey(
            id = "",
            timeStart = this.timeStart,
            timeArrival = this.timeArrival,
            from = Pair(fromRegion, fromDistrict),
            to = Pair(toRegion, toDistrict),
            price = this.price,
            seats = 1,
            seatsReserved = emptyList(),
            seatsAvailable = emptyList(),
            seatsUnavailable = emptyList(),
            stopAt = this.stopAt.takeIf { it.isNotBlank() }?.split("/") ?: emptyList()
        )
    )
}

fun Ticket.toTicketEntity(): TicketEntity {
    return TicketEntity(
        ticketId = this.ticketId,
        qrCodeLinkOrToken = this.qrCodeLinkOrToken,
        passengerName = this.passenger.name,
        seat = this.passenger.seat,
        phone = this.passenger.phone,
        buyTime = this.buyTime,
        timeStart = this.journey.timeStart,
        timeArrival = this.journey.timeArrival,
        fromRegion = this.journey.from.first.name,
        fromDistrict = this.journey.from.second.name,
        fromDistrictAbbr = this.journey.from.second.abbr,
        toRegion = this.journey.to.first.name,
        toDistrict = this.journey.to.second.name,
        toDistrictAbbr = this.journey.to.second.abbr,
        price = this.journey.price,
        stopAt = this.journey.stopAt.takeIf { it.isNotEmpty() }?.joinToString("/") ?: ""
    )
}
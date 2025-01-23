package com.fraggeil.ticketator.data.mappers

import com.fraggeil.ticketator.data.database.CardDataEntity
import com.fraggeil.ticketator.domain.model.CardData

fun CardDataEntity.toCardData(): CardData {
    return CardData(
        cardNumber = cardNumber,
        cardValidUntil = cardValidUntil
    )
}

fun CardData.toCardDataEntity(): CardDataEntity {
    return CardDataEntity(
        cardNumber = cardNumber,
        cardValidUntil = cardValidUntil
    )
}
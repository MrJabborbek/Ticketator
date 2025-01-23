package com.fraggeil.ticketator.domain.repository

import com.fraggeil.ticketator.core.domain.result.Error
import com.fraggeil.ticketator.core.domain.result.Result
import com.fraggeil.ticketator.domain.model.CardData
import com.fraggeil.ticketator.domain.model.Journey

interface SendDataToCheckRepository {
    suspend fun sendDataToCheck(
        journey: Journey,
        cardData: CardData,
        saveCardData: Boolean,
    ):Result<Pair<String, String>, Error>

    suspend fun getAllSavedCardData(): List<CardData>
}
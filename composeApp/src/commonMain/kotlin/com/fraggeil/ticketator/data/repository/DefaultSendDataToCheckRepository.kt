package com.fraggeil.ticketator.data.repository

import com.fraggeil.ticketator.core.domain.Constants
import com.fraggeil.ticketator.core.domain.result.Error
import com.fraggeil.ticketator.core.domain.result.Result
import com.fraggeil.ticketator.data.database.CardDataEntity
import com.fraggeil.ticketator.data.database.Dao
import com.fraggeil.ticketator.data.mappers.toCardData
import com.fraggeil.ticketator.data.mappers.toCardDataEntity
import com.fraggeil.ticketator.domain.model.CardData
import com.fraggeil.ticketator.domain.model.Journey
import com.fraggeil.ticketator.domain.repository.SendDataToCheckRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DefaultSendDataToCheckRepository(
    private val dao: Dao
): SendDataToCheckRepository {
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun sendDataToCheck(
        journey: Journey,
        cardData: CardData,
        saveCardData: Boolean,
    ): Result<Pair<String,String>, Error> {
        delay(Constants.FAKE_DELAY_TO_TEST)
        val token = Uuid.random().toString()
        _testSendDataToCheck.value += Triple(journey, cardData, token)

        if (saveCardData){ dao.insertCardData(cardData.toCardDataEntity()) }

        val phoneNumber = "+99890909741797"
        return Result.Success(Pair(token, phoneNumber)) //generating custom token
    }

    override suspend fun getAllSavedCardData(): List<CardData> {
        return dao.getAllCardData().firstOrNull()?.map { it.toCardData() }?: emptyList()
    }
}
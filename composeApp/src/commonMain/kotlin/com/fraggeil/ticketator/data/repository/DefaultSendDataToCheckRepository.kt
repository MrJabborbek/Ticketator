package com.fraggeil.ticketator.data.repository

import com.fraggeil.ticketator.core.domain.Constants
import com.fraggeil.ticketator.core.domain.result.Error
import com.fraggeil.ticketator.core.domain.result.Result
import com.fraggeil.ticketator.domain.model.Journey
import com.fraggeil.ticketator.domain.repository.SendDataToCheckRepository
import kotlinx.coroutines.delay
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DefaultSendDataToCheckRepository: SendDataToCheckRepository {
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun sendDataToCheck(
        journey: Journey,
        cardNumber: String,
        cardValidUntil: String,
    ): Result<Pair<String,String>, Error> {
        delay(Constants.FAKE_DELAY_TO_TEST)
        val token = Uuid.random().toString()
        val phoneNumber = "+99890909741797"
        return Result.Success(Pair(token, phoneNumber)) //generating custom token
    }
}
package com.fraggeil.ticketator.data.repository

import com.fraggeil.ticketator.core.domain.Constants
import com.fraggeil.ticketator.core.domain.DateTimeUtil
import com.fraggeil.ticketator.core.domain.result.DataError
import com.fraggeil.ticketator.core.domain.result.Error
import com.fraggeil.ticketator.core.domain.result.Result
import com.fraggeil.ticketator.data.database.Dao
import com.fraggeil.ticketator.data.mappers.toTicketEntity
import com.fraggeil.ticketator.domain.model.Ticket
import com.fraggeil.ticketator.domain.repository.OtpPaymentRepository
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DefaultOtpPaymentRepository(
    private val dao: Dao
): OtpPaymentRepository {
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun verifyOtp(token: String, otp: String): Result<List<Ticket>, Error> {
        delay(Constants.FAKE_DELAY_TO_TEST)
        if (otp != "12345"){
            return Result.Error(DataError.Remote.INVALID_OTP)
        }

        val index = _testSendDataToCheck.value.indexOfFirst { it.third == token }
        if (index == -1){
            return Result.Error(DataError.Remote.INVALID_TOKEN)
        }
        var tickets = emptyList<Ticket>()
        _testSendDataToCheck.value[index].let { triple ->
           tickets =  triple.first.passengers.map { passenger ->
               Ticket(
                   ticketId = Random.nextLong(),
                   journey = triple.first,
                   buyTime = DateTimeUtil.nowMilliseconds(),
                   passenger = passenger,
                   qrCodeLinkOrToken = Uuid.random().toString()
               )
            }
        }
        _testSendDataToCheck.value = _testSendDataToCheck.value.toMutableList().apply {
            this.removeAt(index)
        }

        tickets.forEach {
            dao.insertTicket(it.toTicketEntity())
        }
        return Result.Success(tickets)
    }

    override suspend fun resendOtp(token: String): Result<Boolean, Error> {
        delay(Constants.FAKE_DELAY_TO_TEST)
        return Result.Success(true)
    }
}
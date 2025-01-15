package com.fraggeil.ticketator.data.repository

import com.fraggeil.ticketator.core.domain.Constants
import com.fraggeil.ticketator.core.domain.result.DataError
import com.fraggeil.ticketator.core.domain.result.Error
import com.fraggeil.ticketator.core.domain.result.Result
import com.fraggeil.ticketator.domain.model.Ticket
import com.fraggeil.ticketator.domain.repository.OtpPaymentRepository
import kotlinx.coroutines.delay
import kotlin.random.Random

class DefaultOtpPaymentRepository: OtpPaymentRepository {
    override suspend fun verifyOtp(token: String, otp: String): Result<List<Ticket>, Error> {
        delay(Constants.FAKE_DELAY_TO_TEST)
        if (otp != "12345"){
            return Result.Error(DataError.Remote.INVALID_OTP)
        }
        return Result.Success(fakeTickets.shuffled().take(Random.nextInt(1, fakeTickets.size)))
    }

    override suspend fun resendOtp(token: String): Result<Boolean, Error> {
        delay(Constants.FAKE_DELAY_TO_TEST)
        return Result.Success(true)
    }
}
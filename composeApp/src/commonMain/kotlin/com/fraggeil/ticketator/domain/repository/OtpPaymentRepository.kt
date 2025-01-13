package com.fraggeil.ticketator.domain.repository

import com.fraggeil.ticketator.core.domain.result.Error
import com.fraggeil.ticketator.core.domain.result.Result
import com.fraggeil.ticketator.domain.model.Ticket

interface OtpPaymentRepository {
    suspend fun verifyOtp(token: String, otp: String): Result<List<Ticket>, Error>
    suspend fun resendOtp(token: String): Result<Boolean, Error>
}
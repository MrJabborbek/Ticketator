package com.fraggeil.ticketator.domain.repository

import com.fraggeil.ticketator.core.domain.result.Error
import com.fraggeil.ticketator.core.domain.result.Result

interface LoginRepository {
    suspend fun sendOtp(phoneNumber: String): Result<Boolean, Error>
    suspend fun verifyOtp(phoneNumber: String, otp: String): Result<String, Error>

}
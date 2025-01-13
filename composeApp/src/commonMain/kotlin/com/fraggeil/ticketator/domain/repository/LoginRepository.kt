package com.fraggeil.ticketator.domain.repository

import com.fraggeil.ticketator.core.domain.result.Error
import com.fraggeil.ticketator.core.domain.result.Result

interface LoginRepository {
    suspend fun sendOtp(phoneNumber: String): Result<String, Error>
    suspend fun verifyOtp(token: String, phoneNumber: String, otp: String): Result<String, Error>

}
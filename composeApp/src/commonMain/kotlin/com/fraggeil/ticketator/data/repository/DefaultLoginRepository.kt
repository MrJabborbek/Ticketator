package com.fraggeil.ticketator.data.repository

import com.fraggeil.ticketator.core.domain.Constants
import com.fraggeil.ticketator.core.domain.result.DataError
import com.fraggeil.ticketator.core.domain.result.Error
import com.fraggeil.ticketator.core.domain.result.Result
import com.fraggeil.ticketator.domain.model.Profile
import com.fraggeil.ticketator.domain.model.User
import com.fraggeil.ticketator.domain.repository.LoginRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update

class DefaultLoginRepository : LoginRepository {
    override suspend fun sendOtp(phoneNumber: String): Result<Boolean, Error> {
        delay(Constants.FAKE_DELAY_TO_TEST)
        return Result.Success(true)
    }

    override suspend fun verifyOtp(phoneNumber: String, otp: String): Result<String, Error> {
        delay(2000)
        if (otp == "12345") {
            _testCurrentProfile.update {
                Profile(
                    id = 1,
                    phoneNumber = phoneNumber,
                )
            }
            return Result.Success("some_token_will_be_here")
        }
        return Result.Error(DataError.Remote.INVALID_OTP)

    }

}
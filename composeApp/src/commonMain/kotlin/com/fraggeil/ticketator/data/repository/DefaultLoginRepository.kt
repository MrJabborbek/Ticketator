package com.fraggeil.ticketator.data.repository

import com.fraggeil.ticketator.core.domain.Constants
import com.fraggeil.ticketator.core.domain.result.DataError
import com.fraggeil.ticketator.core.domain.result.Error
import com.fraggeil.ticketator.core.domain.result.Result
import com.fraggeil.ticketator.data.database.Dao
import com.fraggeil.ticketator.data.mappers.toProfileEntity
import com.fraggeil.ticketator.domain.model.Profile
import com.fraggeil.ticketator.domain.model.User
import com.fraggeil.ticketator.domain.repository.LoginRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class DefaultLoginRepository(
    private val dao: Dao
) : LoginRepository {
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun sendOtp(phoneNumber: String): Result<String, Error> {
        delay(Constants.FAKE_DELAY_TO_TEST)
        return Result.Success(Uuid.random().toString())
    }

    override suspend fun verifyOtp(token: String, phoneNumber: String, otp: String): Result<String, Error> {
        delay(2000)
        if (otp == "12345") {
            dao.insertUser(
                Profile(
                    id = 1,
                    phoneNumber = phoneNumber,
                ).toProfileEntity()
            )
            return Result.Success("some_token_will_be_here")
        }
        return Result.Error(DataError.Remote.INVALID_OTP)

    }

}
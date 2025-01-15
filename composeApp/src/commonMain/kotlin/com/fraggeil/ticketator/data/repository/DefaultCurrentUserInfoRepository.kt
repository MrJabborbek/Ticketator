package com.fraggeil.ticketator.data.repository

import com.fraggeil.ticketator.core.domain.Constants
import com.fraggeil.ticketator.core.domain.result.DataError
import com.fraggeil.ticketator.core.domain.result.Error
import com.fraggeil.ticketator.core.domain.result.Result
import com.fraggeil.ticketator.domain.model.Profile
import com.fraggeil.ticketator.domain.model.User
import com.fraggeil.ticketator.domain.repository.CurrentUserInfoRepository
import kotlinx.coroutines.delay

class DefaultCurrentUserInfoRepository: CurrentUserInfoRepository {
    override suspend fun getUserInfo(): Result<User, Error> {
        delay(Constants.FAKE_DELAY_TO_TEST)
        _testCurrentProfile.value?.let {
            return Result.Success(User(
                id = it.id,
                name = it.firstName + " " + it.lastName,
                phoneNumber = it.phoneNumber
            ))
        } ?: return Result.Error(DataError.Local.USER_NOT_FOUND)
    }

    override suspend fun getOldTicketUsers(): Result<List<User>, Error> {
        delay(Constants.FAKE_DELAY_TO_TEST)
        return Result.Success(
            fakeTickets.map {
                User(
                    id = it.passenger.seat,
                    name = it.passenger.name,
                    phoneNumber = it.passenger.phone
                )
            }
        )
    }

}
package com.fraggeil.ticketator.data.repository

import com.fraggeil.ticketator.core.domain.Constants
import com.fraggeil.ticketator.core.domain.result.DataError
import com.fraggeil.ticketator.core.domain.result.Error
import com.fraggeil.ticketator.core.domain.result.Result
import com.fraggeil.ticketator.data.database.Dao
import com.fraggeil.ticketator.domain.model.Profile
import com.fraggeil.ticketator.domain.model.User
import com.fraggeil.ticketator.domain.repository.CurrentUserInfoRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull

class DefaultCurrentUserInfoRepository(
    private val dao: Dao
): CurrentUserInfoRepository {
    override suspend fun getUserInfo(): Result<User, Error> {
        delay(Constants.FAKE_DELAY_TO_TEST)
        dao.getUser().firstOrNull()?.let {
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
            dao.getTickets().firstOrNull()?.map {
                User(
                    id = it.seat,
                    name = it.passengerName,
                    phoneNumber = it.phone
                )
            } ?: emptyList()
        )
    }

}
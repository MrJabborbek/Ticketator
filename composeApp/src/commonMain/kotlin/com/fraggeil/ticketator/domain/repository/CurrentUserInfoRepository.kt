package com.fraggeil.ticketator.domain.repository

import com.fraggeil.ticketator.core.domain.result.Error
import com.fraggeil.ticketator.core.domain.result.Result
import com.fraggeil.ticketator.domain.model.Profile
import com.fraggeil.ticketator.domain.model.User

interface CurrentUserInfoRepository {
    suspend fun getUserInfo():Result<User, Error>
}
package com.fraggeil.ticketator.data.repository

import com.fraggeil.ticketator.core.domain.result.DataError
import com.fraggeil.ticketator.core.domain.result.Error
import com.fraggeil.ticketator.core.domain.result.Result
import com.fraggeil.ticketator.core.domain.Constants
import com.fraggeil.ticketator.core.presentation.Language
import com.fraggeil.ticketator.core.presentation.Strings
import com.fraggeil.ticketator.domain.model.Profile
import com.fraggeil.ticketator.domain.repository.ProfileRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class DefaultProfileRepository : ProfileRepository {
    init {
        _testCurrentProfile.value = Profile(id = 1, phoneNumber = "+998909741797", firstName = "Jabborbek", lastName = "Rahmonov", middleName = "Asliddin o'g'li")
    }

    override fun isNewTicketsEmpty(): Flow<Boolean> {
        return combine(_testNewTickets, _testCurrentProfile){ tickets, profile ->
            tickets.isNotEmpty() || profile == null
        }
    }

    override fun isLoggedIn(): Flow<Boolean> {
        return _testCurrentProfile.map { it != null }
    }

    override fun getCurrentProfileRealtime(): Flow<Profile?> {
        return _testCurrentProfile
    }

    override suspend fun getCurrentProfile(): Result<Profile, Error> {
        delay(Constants.FAKE_DELAY_TO_TEST)
        val user = _testCurrentProfile.value
        return if (user != null) {
            Result.Success(user)
        } else {
            Result.Error(DataError.Local.USER_NOT_FOUND)
        }
    }

    override suspend fun updateProfile(profile: Profile): Result<Boolean, Error> {
        delay(Constants.FAKE_DELAY_TO_TEST)
        _testCurrentProfile.value =  profile
        return Result.Success(true)
    }

    override suspend fun deleteProfile(): Result<Boolean, Error> {
        delay(Constants.FAKE_DELAY_TO_TEST)
        _testCurrentProfile.value = null
//        _testMyFavourites.value = emptyList()// TODO remove tickets
        return Result.Success(true)
    }

    override suspend fun logout(): Result<Boolean, Error> {
        delay(Constants.FAKE_DELAY_TO_TEST)
        _testCurrentProfile.value = null
//        _testMyFavourites.value = emptyList()// TODO remove tickets
        return Result.Success(true)
    }

    override suspend fun changeAppLanguage(language: Language) {
        Strings.selectedLanguage.value = language
    }

    override fun getCurrentAppLanguage(): Flow<Language> {
        return Strings.selectedLanguage
    }
}
package com.fraggeil.ticketator.data.repository

import com.fraggeil.ticketator.core.domain.result.DataError
import com.fraggeil.ticketator.core.domain.result.Error
import com.fraggeil.ticketator.core.domain.result.Result
import com.fraggeil.ticketator.core.domain.Constants
import com.fraggeil.ticketator.core.domain.DateTimeUtil
import com.fraggeil.ticketator.core.presentation.Language
import com.fraggeil.ticketator.core.presentation.Strings
import com.fraggeil.ticketator.data.database.AppSettingsEntity
import com.fraggeil.ticketator.data.database.Dao
import com.fraggeil.ticketator.data.mappers.toProfile
import com.fraggeil.ticketator.data.mappers.toProfileEntity
import com.fraggeil.ticketator.domain.model.Profile
import com.fraggeil.ticketator.domain.repository.ProfileRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class DefaultProfileRepository(
    private val dao: Dao
) : ProfileRepository {
//    init {
//        _testCurrentProfile.value = Profile(id = 1, phoneNumber = "+998909741797", firstName = "Jabborbek", lastName = "Rahmonov", middleName = "Asliddin o'g'li")
//    }

    override fun isNewTicketsEmpty(): Flow<Boolean> {
        return combine(dao.getTickets().map { it.filter { it.timeArrival > DateTimeUtil.nowMilliseconds() } }, dao.getUser()){ tickets, profile ->
            tickets.isNotEmpty() || profile == null
        }
    }

    override fun isLoggedIn(): Flow<Boolean> {
        return dao.getUser().map { it != null }
    }

    override fun getCurrentProfileRealtime(): Flow<Profile?> {
        return dao.getUser().map {
            it?.toProfile()
        }
    }

    override suspend fun getCurrentProfile(): Result<Profile, Error> {
        delay(Constants.FAKE_DELAY_TO_TEST)
        val profile = dao.getUser().firstOrNull()
        return if (profile != null) {
            Result.Success(profile.toProfile())
        } else {
            Result.Error(DataError.Local.USER_NOT_FOUND)
        }
    }

    override suspend fun updateProfile(profile: Profile): Result<Boolean, Error> {
        delay(Constants.FAKE_DELAY_TO_TEST)
        dao.insertUser(profile.toProfileEntity())
        return Result.Success(true)
    }

    override suspend fun deleteProfile(): Result<Boolean, Error> {
        delay(Constants.FAKE_DELAY_TO_TEST)
        dao.deleteUser()
        dao.deleteAllTickets()
        dao.deleteAllCardData()
        return Result.Success(true)
    }

    override suspend fun logout(): Result<Boolean, Error> {
        delay(Constants.FAKE_DELAY_TO_TEST)
        dao.deleteUser()
        dao.deleteAllTickets()
        dao.deleteAllCardData()
        return Result.Success(true)
    }

    override suspend fun changeAppLanguage(language: Language) {
        val appSettings = dao.getAppSettings().firstOrNull() ?: AppSettingsEntity()
        dao.insertAppSettings(appSettings.copy(languageCode = language.langCode))
//        Strings.selectedLanguage.value = language
    }

    override fun getCurrentAppLanguage(): Flow<Language> {
//        return Strings.selectedLanguage
        return dao.getAppSettings().map {e-> e?.let { Language.getLanguageByLangCode(it.languageCode)} ?: Language.Uzbek }
    }
}
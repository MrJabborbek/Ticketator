package com.fraggeil.ticketator.domain.repository

import com.fraggeil.ticketator.core.domain.result.Error
import com.fraggeil.ticketator.core.domain.result.Result
import com.fraggeil.ticketator.core.presentation.Language
import com.fraggeil.ticketator.domain.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun isNewTicketsEmpty(): Flow<Boolean>
    fun isLoggedIn(): Flow<Boolean>
    fun getCurrentProfileRealtime(): Flow<Profile?>
    suspend fun getCurrentProfile(): Result<Profile, Error>
    suspend fun updateProfile(profile: Profile): Result<Boolean, Error>
    suspend fun deleteProfile(): Result<Boolean, Error>
    suspend fun logout(): Result<Boolean, Error>
    suspend fun changeAppLanguage(language: Language)
    fun getCurrentAppLanguage(): Flow<Language>

}
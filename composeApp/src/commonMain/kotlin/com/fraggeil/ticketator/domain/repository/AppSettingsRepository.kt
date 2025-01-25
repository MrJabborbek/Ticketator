package com.fraggeil.ticketator.domain.repository

import com.fraggeil.ticketator.core.domain.result.Error
import com.fraggeil.ticketator.core.domain.result.Result
import com.fraggeil.ticketator.domain.model.AppSettings
import kotlinx.coroutines.flow.Flow

interface AppSettingsRepository {
    suspend fun setLaunched()
    suspend fun getIsLaunched(): Result<Boolean, Error>
    fun getAppSettings(): Flow<AppSettings>
}
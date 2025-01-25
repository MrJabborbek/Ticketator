package com.fraggeil.ticketator.data.repository

import com.fraggeil.ticketator.core.domain.result.Error
import com.fraggeil.ticketator.core.domain.result.Result
import com.fraggeil.ticketator.core.presentation.Language
import com.fraggeil.ticketator.core.presentation.Language.Companion.getLangCodeByLang
import com.fraggeil.ticketator.core.presentation.Language.Companion.getLanguageByLangCode
import com.fraggeil.ticketator.core.presentation.Strings
import com.fraggeil.ticketator.data.database.AppSettingsEntity
import com.fraggeil.ticketator.data.database.Dao
import com.fraggeil.ticketator.domain.model.AppSettings
import com.fraggeil.ticketator.domain.repository.AppSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class DefaultAppSettingsRepository(
    private val dao: Dao
): AppSettingsRepository {
    override suspend fun setLaunched() {
        val appSettings = dao.getAppSettings().firstOrNull() ?: AppSettingsEntity()
        dao.insertAppSettings(appSettings.copy(isLaunched = true))
    }

    override suspend fun getIsLaunched(): Result<Boolean, Error> {
         val isLaunched = dao.getAppSettings().firstOrNull()?.isLaunched ?: return Result.Success(false)
        return Result.Success(isLaunched)
    }

    override fun getAppSettings(): Flow<AppSettings> {
        return dao.getAppSettings().map { entity ->
            val language = entity?.languageCode?.let { getLanguageByLangCode(it) } ?: Language.Uzbek
            Strings.selectedLanguage.update { language }

            AppSettings(
                isLaunched = entity?.isLaunched ?: false,
                language = language
            )
        }

    }
}
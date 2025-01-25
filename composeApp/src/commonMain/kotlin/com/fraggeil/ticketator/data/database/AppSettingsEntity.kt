package com.fraggeil.ticketator.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fraggeil.ticketator.core.presentation.Language

@Entity
data class AppSettingsEntity(
    @PrimaryKey
    val isLaunched: Boolean = false,
    val languageCode: String = Language.Uzbek.langCode,
)
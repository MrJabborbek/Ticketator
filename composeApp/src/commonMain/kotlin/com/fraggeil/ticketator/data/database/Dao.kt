package com.fraggeil.ticketator.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Upsert
    suspend fun insertTicket(ticket: TicketEntity)

    @Query("SELECT * FROM ticketentity")
    fun getTickets(): Flow<List<TicketEntity>>

    @Query("DELETE  FROM ticketentity")
    suspend fun deleteAllTickets()

    @Upsert
    suspend fun insertUser(user: ProfileEntity)

    @Query("SELECT * FROM profileentity")
    fun getUser(): Flow<ProfileEntity?>

    @Query("DELETE FROM profileentity")
    suspend fun deleteUser()

    @Upsert
    suspend fun insertCardData(cardData: CardDataEntity)

    @Query("SELECT * FROM carddataentity")
    fun getAllCardData(): Flow<List<CardDataEntity>>

    @Query("DELETE FROM carddataentity")
    suspend fun deleteAllCardData()

    @Upsert
    suspend fun insertAppSettings(appSettingsEntity: AppSettingsEntity)

    @Query("SELECT * FROM appsettingsentity")
    fun getAppSettings(): Flow<AppSettingsEntity?>

}
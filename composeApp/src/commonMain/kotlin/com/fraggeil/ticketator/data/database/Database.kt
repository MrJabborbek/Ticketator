package com.fraggeil.ticketator.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TicketEntity::class, ProfileEntity::class, CardDataEntity::class, AppSettingsEntity::class],
    version = 1
)
abstract class Database: RoomDatabase() {
    abstract val dao: Dao
}

const val DB_NAME = "ticketator.db"
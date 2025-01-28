package com.fraggeil.ticketator.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor

@Database(
    entities = [TicketEntity::class, ProfileEntity::class, CardDataEntity::class, AppSettingsEntity::class],
    version = 1
)
@ConstructedBy(DatabaseConstructor::class) // IT is only for iOS required
abstract class Database: RoomDatabase() {
    abstract val dao: Dao
}

const val DB_NAME = "ticketator.db"


@Suppress("NO_ACTUAL_FOR_EXPECT") // IT is only for iOS required
expect object DatabaseConstructor: RoomDatabaseConstructor<com.fraggeil.ticketator.data.database.Database>{
    override fun initialize(): com.fraggeil.ticketator.data.database.Database
}
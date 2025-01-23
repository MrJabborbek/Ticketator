package com.fraggeil.ticketator.data.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create():RoomDatabase.Builder<Database>
}
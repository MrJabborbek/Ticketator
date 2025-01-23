package com.fraggeil.ticketator.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.fraggeil.ticketator.core.presentation.Strings
import com.fraggeil.ticketator.core.presentation.Strings.value
import java.io.File

actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<Database> {
        val os = System.getProperty("os.name").lowercase()
        val userHome = System.getProperty("user.home")
        val folder = Strings.AppName.value()
        val appDataDir = when {
            os.contains("win") -> File(System.getenv("APPDATA"), folder)
            os.contains("mac") -> File(userHome, "Library/Application Support/$folder")
            else -> File(userHome, ".local/share/$folder")
        }

        if(!appDataDir.exists()) {
            appDataDir.mkdirs()
        }

        val dbFile = File(appDataDir, DB_NAME)
        return Room.databaseBuilder(dbFile.absolutePath)
    }
}
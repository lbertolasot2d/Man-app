package com.example.man_app.data.webdb

import android.content.Context
import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.driver.AndroidSQLiteDriver

lateinit var appContext: Context

actual fun getWebDatabase(): RoomDatabase.Builder<AppDatabase> {
    val dbFile = appContext.getDatabasePath("manapp_kmp.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    ).setDriver(AndroidSQLiteDriver())
}

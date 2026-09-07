package com.example.man_app.data.webdb

import androidx.room3.RoomDatabase
import kotlinx.coroutines.Dispatchers

fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {
    return builder
        .setQueryCoroutineContext(Dispatchers.Default)
        .fallbackToDestructiveMigration(true) // Se lo schema non coincide, cancella e ricrea
        .build()
}

expect fun getWebDatabase(): RoomDatabase.Builder<AppDatabase>

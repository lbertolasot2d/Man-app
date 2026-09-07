package com.example.man_app.`data`.webdb

import androidx.room3.RoomDatabaseConstructor

public actual object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
  actual override fun initialize(): AppDatabase = com.example.man_app.`data`.webdb.AppDatabase_Impl()
}

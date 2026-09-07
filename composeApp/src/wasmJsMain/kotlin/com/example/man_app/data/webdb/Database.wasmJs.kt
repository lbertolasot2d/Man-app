package com.example.man_app.data.webdb

import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.SQLiteDriver
import androidx.sqlite.SQLiteStatement

actual fun getWebDatabase(): RoomDatabase.Builder<AppDatabase> {
    val driver = object : SQLiteDriver {
        override suspend fun open(fileName: String): SQLiteConnection = object : SQLiteConnection {
            override fun inTransaction(): Boolean = false
            override suspend fun prepare(sql: String): SQLiteStatement = object : SQLiteStatement {
                override fun bindBlob(index: Int, value: ByteArray) {}
                override fun bindDouble(index: Int, value: Double) {}
                override fun bindInt(index: Int, value: Int) {}
                override fun bindLong(index: Int, value: Long) {}
                override fun bindText(index: Int, value: String) {}
                override fun bindNull(index: Int) {}
                override fun clearBindings() {}
                override suspend fun step(): Boolean = false
                override fun reset() {}
                override fun getText(index: Int): String = ""
                override fun getInt(index: Int): Int = 0
                override fun getLong(index: Int): Long = 0
                override fun getDouble(index: Int): Double = 0.0
                override fun getBlob(index: Int): ByteArray = byteArrayOf()
                override fun isNull(index: Int): Boolean = true
                override fun getColumnCount(): Int = 0
                override fun getColumnName(index: Int): String = ""
                override fun getColumnType(index: Int): Int = 0
                override fun close() {}
            }
            override fun close() {}
        }
    }

    // Usiamo un database in memoria garantito per il Web
    return Room.inMemoryDatabaseBuilder<AppDatabase>()
        .setDriver(driver)
}

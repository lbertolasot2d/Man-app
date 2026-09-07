   package com.example.man_app.data.webdb

   import androidx.room3.Room
   import androidx.room3.RoomDatabase

   actual fun getWebDatabase(): RoomDatabase.Builder<AppDatabase> {
       // Per WASM, usiamo un database in memoria puro
       // Room gestisce automaticamente lo schema con fallbackToDestructiveMigration
       return Room.inMemoryDatabaseBuilder<AppDatabase>()
   }
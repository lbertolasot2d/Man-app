package com.example.man_app

import android.app.Application
import com.example.man_app.data.webdb.AppDatabase
import com.example.man_app.data.webdb.appContext
import com.example.man_app.data.webdb.getWebDatabase
import com.example.man_app.data.webdb.getRoomDatabase
import com.example.man_app.data.sync.SyncWorker
import com.example.man_app.SyncTrigger
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

import android.util.Log

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers

class ManApp : Application(), SyncTrigger {
    lateinit var database: AppDatabase
        private set
    
    var isDatabaseInitialized by mutableStateOf(false)
        private set
    
    var initializationError by mutableStateOf<String?>(null)
        private set

    override fun onCreate() {
        println("MANAPP_DEBUG: onCreate started")
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.e("ManApp", "CRITICAL CRASH in thread ${thread.name}", throwable)
            println("MANAPP_DEBUG: CRITICAL CRASH in thread ${thread.name}: ${throwable.message}")
        }
        super.onCreate()
        Log.d("ManApp", "onCreate started")
        appContext = this
        
        // Initialize database asynchronously to avoid blocking the main thread
        GlobalScope.launch(Dispatchers.Default) {
            println("MANAPP_DEBUG: DB init coroutine started")
            try {
                val db = getRoomDatabase(getDatabaseBuilder())
                database = db
                isDatabaseInitialized = true
                println("MANAPP_DEBUG: Database initialized successfully")
                Log.d("ManApp", "Database initialized in background")
            } catch (t: Throwable) {
                println("MANAPP_DEBUG: DB init failed: ${t.message}")
                Log.e("ManApp", "CRITICAL: Database initialization failed", t)
                initializationError = t.message ?: "Unknown database error"
            }
        }
    }

    override fun triggerSync() {
        Log.d("ManApp", "triggerSync() called")
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .addTag("manual_sync_tag")
            .build()

        WorkManager.getInstance(this).enqueueUniqueWork(
            "manual_sync",
            ExistingWorkPolicy.REPLACE,
            syncRequest
        )
        Log.d("ManApp", "Sync work enqueued")
    }
}

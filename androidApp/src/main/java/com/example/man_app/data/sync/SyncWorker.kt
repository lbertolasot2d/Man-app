package com.example.man_app.data.sync

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.man_app.ManApp
import com.example.man_app.data.model.SyncStatus
import com.example.man_app.data.remote.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.first

import com.example.man_app.data.sync.DataSyncManager

class SyncWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val app = applicationContext as ManApp
        Log.d("SyncWorker", "Starting global bidirectional sync...")
        
        while (!app.isDatabaseInitialized) {
            Log.d("SyncWorker", "Waiting for DB...")
            kotlinx.coroutines.delay(500)
        }
        
        val db = app.database
        val supabase = SupabaseClient
        
        return try {
            val syncManager = DataSyncManager(db, supabase)
            syncManager.syncAll()
            
            Log.d("SyncWorker", "Global bidirectional sync finished successfully")
            Result.success()
        } catch (e: Exception) {
            Log.e("SyncWorker", "Sync failed: ${e.message}", e)
            Result.retry()
        }
    }
}

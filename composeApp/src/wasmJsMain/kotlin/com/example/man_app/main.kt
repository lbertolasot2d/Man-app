package com.example.man_app

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.example.man_app.data.webdb.getWebDatabase
import com.example.man_app.data.webdb.getRoomDatabase
import com.example.man_app.data.sync.DataSyncManager
import com.example.man_app.data.remote.SupabaseClient
import com.example.man_app.SyncTrigger
import kotlinx.browser.document
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val body = document.body!!
    
    try {
        val database = getRoomDatabase(getWebDatabase())
        val scope = MainScope()
        
        val syncTrigger = object : SyncTrigger {
            override fun triggerSync() {
                scope.launch {
                    try {
                        val manager = DataSyncManager(database, SupabaseClient)
                        manager.syncAll()
                        println("Web Sync Completed")
                    } catch (e: Exception) {
                        println("Web Sync Error: ${e.message}")
                    }
                }
            }
        }

        // Trigger initial sync
        syncTrigger.triggerSync()

        ComposeViewport(body) {
            App(database, syncTrigger)
        }
    } catch (e: Exception) {
        // Se il DB fallisce, scriviamo l'errore direttamente nel corpo della pagina
        body.innerHTML = "<h1>Errore di Inizializzazione</h1><p>${e.message}</p><p>Prova a pulire la cache del browser (F12 -> Application -> Clear Storage)</p>"
        println("CRITICAL ERROR: ${e.message}")
    }
}

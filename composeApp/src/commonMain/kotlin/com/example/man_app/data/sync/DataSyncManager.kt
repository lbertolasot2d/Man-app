package com.example.man_app.data.sync

import com.example.man_app.data.model.SyncStatus
import com.example.man_app.data.remote.SupabaseClient
import com.example.man_app.data.webdb.AppDatabase
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json

class DataSyncManager(
    private val database: AppDatabase,
    private val supabase: SupabaseClient
) {
    private val client = supabase.client

    suspend fun syncAll() {
        syncPersonnel()
        syncProducts()
        syncShifts()
        syncMattoniera()
        syncImpilatrice()
        syncScaricatrice()
        syncStops()
        syncFaults()
        syncChecklists()
        syncProduction()
    }

    private suspend inline fun <reified T : Any> syncTable(
        tableName: String,
        getUnsynced: suspend () -> List<T>,
        updateLocal: suspend (T) -> Unit,
        insertLocal: suspend (T) -> Unit,
        markSynced: (T) -> T
    ) {
        // 1. Push
        try {
            val unsynced = getUnsynced()
            if (unsynced.isNotEmpty()) {
                client.postgrest[tableName].upsert(unsynced)
                unsynced.forEach { updateLocal(markSynced(it)) }
            }
        } catch (e: Exception) {
            println("Push Error for $tableName: ${e.message}")
        }

        // 2. Pull
        try {
            val remoteData = client.postgrest[tableName].select().decodeList<T>()
            remoteData.forEach { insertLocal(markSynced(it)) }
        } catch (e: Exception) {
            println("Pull Error for $tableName: ${e.message}")
        }
    }

    private suspend fun syncPersonnel() {
        val dao = database.personnelDao()
        syncTable(
            tableName = "personnel",
            getUnsynced = { dao.getUnsynced().first() },
            updateLocal = { dao.update(it) },
            insertLocal = { dao.insert(it) },
            markSynced = { it.copy(syncStatus = SyncStatus.SYNCED) }
        )
    }
    
    // I will implement them individually to handle different copy methods and DAO methods
    
    private suspend fun syncProducts() {
        val dao = database.productDao()
        dao.getUnsynced().first().let { list ->
            if (list.isNotEmpty()) {
                client.postgrest["products"].upsert(list)
                list.forEach { dao.insert(it.copy(syncStatus = SyncStatus.SYNCED)) }
            }
        }
        try {
            client.postgrest["products"].select().decodeList<com.example.man_app.data.model.Product>().forEach {
                dao.insert(it.copy(syncStatus = SyncStatus.SYNCED))
            }
        } catch (e: Exception) {}
    }

    private suspend fun syncShifts() {
        val dao = database.shiftDao()
        dao.getUnsynced().first().let { list ->
            if (list.isNotEmpty()) {
                client.postgrest["shift_configurations"].upsert(list)
                list.forEach { dao.update(it.copy(syncStatus = SyncStatus.SYNCED)) }
            }
        }
        try {
            client.postgrest["shift_configurations"].select().decodeList<com.example.man_app.data.model.ShiftConfiguration>().forEach {
                dao.insert(it.copy(syncStatus = SyncStatus.SYNCED))
            }
        } catch (e: Exception) {}
    }

    private suspend fun syncMattoniera() {
        val dao = database.mattonieraDao()
        
        // Reports
        dao.getUnsyncedReports().first().let { list ->
            if (list.isNotEmpty()) {
                client.postgrest["mattoniera_reports"].upsert(list)
                list.forEach { dao.updateReport(it.copy(syncStatus = SyncStatus.SYNCED)) }
            }
        }
        try {
            client.postgrest["mattoniera_reports"].select().decodeList<com.example.man_app.data.model.MattonieraReport>().forEach {
                dao.insertReport(it.copy(syncStatus = SyncStatus.SYNCED))
            }
        } catch (e: Exception) {}

        // Exit Checks
        dao.getUnsyncedExitChecks().first().let { list ->
            if (list.isNotEmpty()) {
                client.postgrest["exit_quality_checks"].upsert(list)
                list.forEach { dao.insertExitCheck(it.copy(syncStatus = SyncStatus.SYNCED)) }
            }
        }
        try {
            client.postgrest["exit_quality_checks"].select().decodeList<com.example.man_app.data.model.ExitQualityCheck>().forEach {
                dao.insertExitCheck(it.copy(syncStatus = SyncStatus.SYNCED))
            }
        } catch (e: Exception) {}

        // Measurements
        dao.getUnsyncedMeasurements().first().let { list ->
            if (list.isNotEmpty()) {
                client.postgrest["measurement_checks"].upsert(list)
                list.forEach { dao.insertMeasurement(it.copy(syncStatus = SyncStatus.SYNCED)) }
            }
        }
        try {
            client.postgrest["measurement_checks"].select().decodeList<com.example.man_app.data.model.MeasurementCheck>().forEach {
                dao.insertMeasurement(it.copy(syncStatus = SyncStatus.SYNCED))
            }
        } catch (e: Exception) {}
    }

    private suspend fun syncImpilatrice() {
        val dao = database.impilatriceDao()
        dao.getUnsyncedReports().first().let { list ->
            if (list.isNotEmpty()) {
                client.postgrest["impilatrice_reports"].upsert(list)
                list.forEach { dao.updateReport(it.copy(syncStatus = SyncStatus.SYNCED)) }
            }
        }
        try {
            client.postgrest["impilatrice_reports"].select().decodeList<com.example.man_app.data.model.ImpilatriceReport>().forEach {
                dao.insertReport(it.copy(syncStatus = SyncStatus.SYNCED))
            }
        } catch (e: Exception) {}

        dao.getUnsyncedKilnCars().first().let { list ->
            if (list.isNotEmpty()) {
                client.postgrest["impilatrice_kiln_cars"].upsert(list)
                list.forEach { dao.updateKilnCar(it.copy(syncStatus = SyncStatus.SYNCED)) }
            }
        }
        try {
            client.postgrest["impilatrice_kiln_cars"].select().decodeList<com.example.man_app.data.model.ImpilatriceKilnCar>().forEach {
                dao.insertKilnCar(it.copy(syncStatus = SyncStatus.SYNCED))
            }
        } catch (e: Exception) {}
    }

    private suspend fun syncScaricatrice() {
        val dao = database.scaricatriceDao()
        dao.getUnsyncedReports().first().let { list ->
            if (list.isNotEmpty()) {
                client.postgrest["scaricatrice_reports"].upsert(list)
                list.forEach { dao.updateReport(it.copy(syncStatus = SyncStatus.SYNCED)) }
            }
        }
        try {
            client.postgrest["scaricatrice_reports"].select().decodeList<com.example.man_app.data.model.ScaricatriceReport>().forEach {
                dao.insertReport(it.copy(syncStatus = SyncStatus.SYNCED))
            }
        } catch (e: Exception) {}

        dao.getUnsyncedKilnCars().first().let { list ->
            if (list.isNotEmpty()) {
                client.postgrest["scaricatrice_kiln_cars"].upsert(list)
                list.forEach { dao.updateKilnCar(it.copy(syncStatus = SyncStatus.SYNCED)) }
            }
        }
        try {
            client.postgrest["scaricatrice_kiln_cars"].select().decodeList<com.example.man_app.data.model.ScaricatriceKilnCar>().forEach {
                dao.insertKilnCar(it.copy(syncStatus = SyncStatus.SYNCED))
            }
        } catch (e: Exception) {}
    }

    private suspend fun syncStops() {
        val dao = database.stopDao()
        dao.getUnsyncedStops().first().let { list ->
            if (list.isNotEmpty()) {
                client.postgrest["stop_events"].upsert(list)
                list.forEach { dao.updateStop(it.copy(syncStatus = SyncStatus.SYNCED)) }
            }
        }
        try {
            client.postgrest["stop_events"].select().decodeList<com.example.man_app.data.model.StopEvent>().forEach {
                dao.insertStop(it.copy(syncStatus = SyncStatus.SYNCED))
            }
        } catch (e: Exception) {}
    }

    private suspend fun syncFaults() {
        val dao = database.faultDao()
        dao.getUnsynced().first().let { list ->
            if (list.isNotEmpty()) {
                client.postgrest["fault_reports"].upsert(list)
                list.forEach { dao.update(it.copy(syncStatus = SyncStatus.SYNCED)) }
            }
        }
        try {
            client.postgrest["fault_reports"].select().decodeList<com.example.man_app.data.model.FaultReport>().forEach {
                dao.insert(it.copy(syncStatus = SyncStatus.SYNCED))
            }
        } catch (e: Exception) {}
    }

    private suspend fun syncChecklists() {
        val dao = database.checklistDao()
        dao.getUnsyncedResponses().first().let { list ->
            if (list.isNotEmpty()) {
                client.postgrest["checklist_responses"].upsert(list)
                list.forEach { dao.updateResponse(it.copy(syncStatus = SyncStatus.SYNCED)) }
            }
        }
        try {
            client.postgrest["checklist_responses"].select().decodeList<com.example.man_app.data.model.ChecklistResponse>().forEach {
                dao.insertResponse(it.copy(syncStatus = SyncStatus.SYNCED))
            }
        } catch (e: Exception) {}
    }

    private suspend fun syncProduction() {
        val dao = database.productionDao()
        dao.getUnsynced().first().let { list ->
            if (list.isNotEmpty()) {
                client.postgrest["production_reports"].upsert(list)
                list.forEach { dao.update(it.copy(syncStatus = SyncStatus.SYNCED)) }
            }
        }
        try {
            client.postgrest["production_reports"].select().decodeList<com.example.man_app.data.model.ProductionReport>().forEach {
                dao.insert(it.copy(syncStatus = SyncStatus.SYNCED))
            }
        } catch (e: Exception) {}
    }
}

package com.example.man_app.data.webdb

import androidx.room3.*
import com.example.man_app.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonnelDao {
    @Query("SELECT * FROM personnel")
    fun getAll(): Flow<List<Personnel>>

    @Query("SELECT COUNT(*) FROM personnel")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(personnel: Personnel)

    @Update
    suspend fun update(personnel: Personnel)

    @Delete
    suspend fun delete(personnel: Personnel)

    @Query("SELECT * FROM personnel WHERE syncStatus != 'SYNCED'")
    fun getUnsynced(): Flow<List<Personnel>>
}

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAll(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getById(id: String): Product?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)

    @Query("SELECT * FROM products WHERE syncStatus != 'SYNCED'")
    fun getUnsynced(): Flow<List<Product>>
}

@Dao
interface MattonieraDao {
    @Query("SELECT * FROM mattoniera_reports WHERE isClosed = 0")
    fun getActiveReports(): Flow<List<MattonieraReport>>

    @Query("SELECT * FROM mattoniera_reports WHERE lineId = :lineId AND machineId = :machineId ORDER BY date DESC")
    fun getReportsByContext(lineId: String, machineId: String): Flow<List<MattonieraReport>>

    @Query("SELECT * FROM mattoniera_reports WHERE id = :reportId")
    fun getReportById(reportId: String): Flow<MattonieraReport?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: MattonieraReport)

    @Update
    suspend fun updateReport(report: MattonieraReport)

    @Query("SELECT * FROM exit_quality_checks WHERE reportId = :reportId ORDER BY timestamp ASC")
    fun getExitChecks(reportId: String): Flow<List<ExitQualityCheck>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExitCheck(check: ExitQualityCheck)

    @Query("SELECT * FROM measurement_checks WHERE reportId = :reportId ORDER BY timestamp ASC")
    fun getMeasurements(reportId: String): Flow<List<MeasurementCheck>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeasurement(check: MeasurementCheck)

    @Query("SELECT * FROM roller_cleanings WHERE reportId = :reportId")
    fun getRollerCleanings(reportId: String): Flow<List<RollerCleaning>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRollerCleaning(cleaning: RollerCleaning)

    @Query("SELECT * FROM clogging_removals WHERE reportId = :reportId")
    fun getCloggingRemovals(reportId: String): Flow<List<CloggingRemoval>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCloggingRemoval(removal: CloggingRemoval)

    @Update
    suspend fun updateCloggingRemoval(removal: CloggingRemoval)

    @Delete
    suspend fun deleteCloggingRemoval(removal: CloggingRemoval)

    @Query("SELECT * FROM clogging_reasons")
    fun getAllCloggingReasons(): Flow<List<CloggingReason>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCloggingReason(reason: CloggingReason)

    @Query("SELECT * FROM production_stops WHERE reportId = :reportId")
    fun getStopsForReport(reportId: String): Flow<List<ProductionStop>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStop(stop: ProductionStop)

    @Query("SELECT * FROM mattoniera_production_data WHERE reportId = :reportId ORDER BY timestamp ASC")
    fun getProductionData(reportId: String): Flow<List<MattonieraProductionData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductionData(data: MattonieraProductionData)

    @Query("SELECT * FROM scrap_records WHERE reportId = :reportId ORDER BY timestamp ASC")
    fun getScrapRecords(reportId: String): Flow<List<ScrapRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScrapRecord(record: ScrapRecord)

    @Query("SELECT * FROM cage_events WHERE reportId = :reportId ORDER BY timestamp ASC")
    fun getCageEvents(reportId: String): Flow<List<CageEvent>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCageEvent(event: CageEvent)

    @Update
    suspend fun updateCageEvent(event: CageEvent)

    @Delete
    suspend fun deleteCageEvent(event: CageEvent)

    @Query("SELECT DISTINCT productId FROM mattoniera_reports WHERE lineId = :lineId")
    fun getUniqueProductsForLine(lineId: String): Flow<List<String>>

    @Query("SELECT DISTINCT sigla FROM mattoniera_reports WHERE lineId = :lineId")
    fun getUniqueSigleForLine(lineId: String): Flow<List<String?>>

    @Query("SELECT * FROM mattoniera_reports WHERE syncStatus != 'SYNCED'")
    fun getUnsyncedReports(): Flow<List<MattonieraReport>>

    @Query("SELECT * FROM exit_quality_checks WHERE syncStatus != 'SYNCED'")
    fun getUnsyncedExitChecks(): Flow<List<ExitQualityCheck>>

    @Query("SELECT * FROM measurement_checks WHERE syncStatus != 'SYNCED'")
    fun getUnsyncedMeasurements(): Flow<List<MeasurementCheck>>

    @Query("SELECT * FROM mattoniera_production_data WHERE syncStatus != 'SYNCED'")
    fun getUnsyncedProductionData(): Flow<List<MattonieraProductionData>>

    @Query("SELECT * FROM scrap_records WHERE syncStatus != 'SYNCED'")
    fun getUnsyncedScrapRecords(): Flow<List<ScrapRecord>>

    @Query("SELECT * FROM cage_events WHERE syncStatus != 'SYNCED'")
    fun getUnsyncedCageEvents(): Flow<List<CageEvent>>

    @Query("SELECT * FROM roller_cleanings WHERE syncStatus != 'SYNCED'")
    fun getUnsyncedRollerCleanings(): Flow<List<RollerCleaning>>

    @Query("SELECT * FROM clogging_removals WHERE syncStatus != 'SYNCED'")
    fun getUnsyncedCloggingRemovals(): Flow<List<CloggingRemoval>>

    @Query("SELECT * FROM production_stops WHERE syncStatus != 'SYNCED'")
    fun getUnsyncedProductionStops(): Flow<List<ProductionStop>>
}

@Dao
interface ImpilatriceDao {
    @Query("SELECT * FROM impilatrice_reports WHERE lineId = :lineId AND machineId = :machineId ORDER BY date DESC")
    fun getReportsByContext(lineId: String, machineId: String): Flow<List<ImpilatriceReport>>

    @Query("SELECT * FROM impilatrice_reports WHERE id = :reportId")
    fun getReportById(reportId: String): Flow<ImpilatriceReport?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: ImpilatriceReport)

    @Update
    suspend fun updateReport(report: ImpilatriceReport)

    @Query("SELECT * FROM impilatrice_cage_events WHERE reportId = :reportId ORDER BY timestamp ASC")
    fun getCageEvents(reportId: String): Flow<List<ImpilatriceCageEvent>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCageEvent(event: ImpilatriceCageEvent)

    @Update
    suspend fun updateCageEvent(event: ImpilatriceCageEvent)

    @Delete
    suspend fun deleteCageEvent(event: ImpilatriceCageEvent)

    @Query("SELECT * FROM impilatrice_scrap_records WHERE reportId = :reportId ORDER BY timestamp ASC")
    fun getScrapRecords(reportId: String): Flow<List<ImpilatriceScrapRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScrapRecord(record: ImpilatriceScrapRecord)

    @Query("SELECT * FROM impilatrice_kiln_cars WHERE reportId = :reportId ORDER BY startTime ASC")
    fun getKilnCars(reportId: String): Flow<List<ImpilatriceKilnCar>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKilnCar(car: ImpilatriceKilnCar)

    @Update
    suspend fun updateKilnCar(car: ImpilatriceKilnCar)

    @Delete
    suspend fun deleteKilnCar(car: ImpilatriceKilnCar)

    @Query("SELECT * FROM impilatrice_reports WHERE syncStatus != 'SYNCED'")
    fun getUnsyncedReports(): Flow<List<ImpilatriceReport>>

    @Query("SELECT * FROM impilatrice_cage_events WHERE syncStatus != 'SYNCED'")
    fun getUnsyncedCageEvents(): Flow<List<ImpilatriceCageEvent>>

    @Query("SELECT * FROM impilatrice_scrap_records WHERE syncStatus != 'SYNCED'")
    fun getUnsyncedScrapRecords(): Flow<List<ImpilatriceScrapRecord>>

    @Query("SELECT * FROM impilatrice_kiln_cars WHERE syncStatus != 'SYNCED'")
    fun getUnsyncedKilnCars(): Flow<List<ImpilatriceKilnCar>>
}

@Dao
interface StopDao {
    @Query("SELECT * FROM stop_events WHERE isFinished = 0 LIMIT 1")
    fun getActiveStop(): Flow<StopEvent?>

    @Query("SELECT * FROM stop_events ORDER BY startTime DESC")
    fun getAllStops(): Flow<List<StopEvent>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStop(stop: StopEvent): Long

    @Update
    suspend fun updateStop(stop: StopEvent)

    @Delete
    suspend fun deleteStop(stop: StopEvent)

    @Query("SELECT * FROM stop_suggestions WHERE category = :category")
    fun getSuggestions(category: String): Flow<List<StopSuggestion>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSuggestion(suggestion: StopSuggestion)

    @Query("SELECT * FROM stop_events WHERE syncStatus != 'SYNCED'")
    fun getUnsyncedStops(): Flow<List<StopEvent>>
}

@Dao
interface ShiftDao {
    @Query("SELECT * FROM shift_configurations WHERE lineId = :lineId")
    fun getShiftsForLine(lineId: String): Flow<List<ShiftConfiguration>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(shift: ShiftConfiguration)

    @Update
    suspend fun update(shift: ShiftConfiguration)

    @Delete
    suspend fun delete(shift: ShiftConfiguration)

    @Query("SELECT * FROM shift_configurations WHERE syncStatus != 'SYNCED'")
    fun getUnsynced(): Flow<List<ShiftConfiguration>>
}

@Dao
interface ScaricatriceDao {
    @Query("SELECT * FROM scaricatrice_reports WHERE lineId = :lineId AND machineId = :machineId ORDER BY date DESC")
    fun getReportsByContext(lineId: String, machineId: String): Flow<List<ScaricatriceReport>>

    @Query("SELECT * FROM scaricatrice_reports WHERE id = :reportId")
    fun getReportById(reportId: String): Flow<ScaricatriceReport?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: ScaricatriceReport)

    @Update
    suspend fun updateReport(report: ScaricatriceReport)

    @Query("SELECT * FROM scaricatrice_scrap_records WHERE reportId = :reportId ORDER BY timestamp ASC")
    fun getScrapRecords(reportId: String): Flow<List<ScaricatriceScrapRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScrapRecord(record: ScaricatriceScrapRecord)

    @Query("SELECT * FROM scaricatrice_kiln_cars WHERE reportId = :reportId ORDER BY startTime ASC")
    fun getKilnCars(reportId: String): Flow<List<ScaricatriceKilnCar>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKilnCar(car: ScaricatriceKilnCar)

    @Update
    suspend fun updateKilnCar(car: ScaricatriceKilnCar)

    @Delete
    suspend fun deleteKilnCar(car: ScaricatriceKilnCar)

    @Query("SELECT * FROM scaricatrice_reports WHERE syncStatus != 'SYNCED'")
    fun getUnsyncedReports(): Flow<List<ScaricatriceReport>>

    @Query("SELECT * FROM scaricatrice_scrap_records WHERE syncStatus != 'SYNCED'")
    fun getUnsyncedScrapRecords(): Flow<List<ScaricatriceScrapRecord>>

    @Query("SELECT * FROM scaricatrice_kiln_cars WHERE syncStatus != 'SYNCED'")
    fun getUnsyncedKilnCars(): Flow<List<ScaricatriceKilnCar>>
}

@Dao
interface FaultDao {
    @Query("SELECT * FROM fault_reports WHERE syncStatus != 'SYNCED'")
    fun getUnsynced(): Flow<List<FaultReport>>

    @Update
    suspend fun update(report: FaultReport)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(report: FaultReport)
}

@Dao
interface ChecklistDao {
    @Query("SELECT * FROM checklist_responses WHERE syncStatus != 'SYNCED'")
    fun getUnsyncedResponses(): Flow<List<ChecklistResponse>>

    @Update
    suspend fun updateResponse(response: ChecklistResponse)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResponse(response: ChecklistResponse)
}

@Dao
interface ProductionDao {
    @Query("SELECT * FROM production_reports WHERE syncStatus != 'SYNCED'")
    fun getUnsynced(): Flow<List<ProductionReport>>

    @Update
    suspend fun update(report: ProductionReport)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(report: ProductionReport)
}

package com.example.man_app.data.webdb


import androidx.room3.Database
import androidx.room3.RoomDatabase

import com.example.man_app.data.model.*

@Database(
    entities = [
        Personnel::class,
        Product::class,
        MattonieraReport::class,
        ExitQualityCheck::class,
        MeasurementCheck::class,
        RollerCleaning::class,
        CloggingRemoval::class,
        CloggingReason::class,
        MattonieraProductionData::class,
        ScrapRecord::class,
        CageEvent::class,
        ProductionStop::class,
        StopEvent::class,
        StopSuggestion::class,
        FaultReport::class,
        ChecklistTemplate::class,
        ChecklistItem::class,
        ChecklistResponse::class,
        ImpilatriceReport::class,
        ImpilatriceCageEvent::class,
        ImpilatriceScrapRecord::class,
        ImpilatriceKilnCar::class,
        ShiftConfiguration::class,
        ScaricatriceReport::class,
        ScaricatriceScrapRecord::class,
        ScaricatriceKilnCar::class,
        ProductionReport::class
    ],
    version = 23,
    exportSchema = false
)
 abstract class AppDatabase : RoomDatabase() {
    abstract fun personnelDao(): PersonnelDao
    abstract fun productDao(): ProductDao
    abstract fun mattonieraDao(): MattonieraDao
    abstract fun impilatriceDao(): ImpilatriceDao
    abstract fun stopDao(): StopDao
    abstract fun shiftDao(): ShiftDao
    abstract fun scaricatriceDao(): ScaricatriceDao
    abstract fun faultDao(): FaultDao
    abstract fun checklistDao(): ChecklistDao
    abstract fun productionDao(): ProductionDao
}

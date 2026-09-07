package com.example.man_app.data.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.example.man_app.util.randomUUID

@Serializable
@Entity(tableName = "mattoniera_reports")
data class MattonieraReport(
    @PrimaryKey @SerialName("id") val id: String = randomUUID(),
    @SerialName("date") val date: Long,
    @SerialName("shift") val shift: String,
    @SerialName("productid") val productId: String,
    @SerialName("operatorid") val operatorId: String,
    @SerialName("dieid") val dieId: String,
    @SerialName("sigla") val sigla: String? = null,
    @SerialName("clayrecipecode") val clayRecipeCode: String? = null,
    @SerialName("lineid") val lineId: String,
    @SerialName("machineid") val machineId: String,
    
    @SerialName("dimacheckstart") val dimaCheckStart: Boolean = false,
    @SerialName("dimacheckmid") val dimaCheckMid: Boolean = false,
    
    @SerialName("starttime") val startTime: Long = 0,
    @SerialName("endtime") val endTime: Long = 0,
    @SerialName("scrapminutes") val scrapMinutes: Int = 0,
    @SerialName("cagesproduced") val cagesProduced: Int = 0,
    @SerialName("doserspeedhz") val doserSpeedHz: Double = 0.0,
    @SerialName("absorptionampere") val absorptionAmpere: Double = 0.0,
    @SerialName("productionnotes") val productionNotes: String? = null,
    @SerialName("qualitynotes") val qualityNotes: String? = null,
    
    @SerialName("isclosed") val isClosed: Boolean = false,
    
    @SerialName("lastupdated") val lastUpdated: Long = com.example.man_app.util.getNowMillis(),
    @SerialName("syncstatus") val syncStatus: String = SyncStatus.PENDING
)

@Serializable
@Entity(tableName = "exit_quality_checks")
data class ExitQualityCheck(
    @PrimaryKey @SerialName("id") val id: String = randomUUID(),
    @SerialName("reportid") val reportId: String,
    @SerialName("timestamp") val timestamp: Long,
    @SerialName("isok") val isOk: Boolean,
    @SerialName("temperature") val temperature: Int,
    @SerialName("pressure") val pressure: Int,
    
    @SerialName("lastupdated") val lastUpdated: Long = com.example.man_app.util.getNowMillis(),
    @SerialName("syncstatus") val syncStatus: String = SyncStatus.PENDING
)

@Serializable
@Entity(tableName = "measurement_checks")
data class MeasurementCheck(
    @PrimaryKey @SerialName("id") val id: String = randomUUID(),
    @SerialName("reportid") val reportId: String,
    @SerialName("timestamp") val timestamp: Long,
    @SerialName("height") val height: Double,
    @SerialName("width") val width: Double,
    @SerialName("thickness") val thickness: Double, // The 'T' in your drawing
    @SerialName("diagonal1") val diagonal1: Double,
    @SerialName("diagonal2") val diagonal2: Double,
    @SerialName("weight") val weight: Double,
    @SerialName("isdiagonalok") val isDiagonalOk: Boolean = true,
    
    @SerialName("lastupdated") val lastUpdated: Long = com.example.man_app.util.getNowMillis(),
    @SerialName("syncstatus") val syncStatus: String = SyncStatus.PENDING
)

@Serializable
@Entity(tableName = "roller_cleanings")
data class RollerCleaning(
    @PrimaryKey @SerialName("id") val id: String = randomUUID(),
    @SerialName("reportid") val reportId: String,
    @SerialName("timestamp") val timestamp: Long,
    
    @SerialName("lastupdated") val lastUpdated: Long = com.example.man_app.util.getNowMillis(),
    @SerialName("syncstatus") val syncStatus: String = SyncStatus.PENDING
)

@Serializable
@Entity(tableName = "clogging_removals")
data class CloggingRemoval(
    @PrimaryKey @SerialName("id") val id: String = randomUUID(),
    @SerialName("reportid") val reportId: String,
    @SerialName("timestamp") val timestamp: Long,
    @SerialName("reason") val reason: String,
    
    @SerialName("lastupdated") val lastUpdated: Long = com.example.man_app.util.getNowMillis(),
    @SerialName("syncstatus") val syncStatus: String = SyncStatus.PENDING
)

@Serializable
@Entity(tableName = "clogging_reasons")
data class CloggingReason(
    @PrimaryKey @SerialName("reasontext") val reasonText: String,
    @SerialName("lastupdated") val lastUpdated: Long = com.example.man_app.util.getNowMillis(),
    @SerialName("syncstatus") val syncStatus: String = SyncStatus.PENDING
)

@Serializable
@Entity(tableName = "mattoniera_production_data")
data class MattonieraProductionData(
    @PrimaryKey @SerialName("id") val id: String = randomUUID(),
    @SerialName("reportid") val reportId: String,
    @SerialName("timestamp") val timestamp: Long,
    @SerialName("dosergiri") val doserGiri: Int,
    @SerialName("isdoserok") val isDoserOk: Boolean,
    @SerialName("absorptionampere") val absorptionAmpere: Int,
    @SerialName("isabsorptionok") val isAbsorptionOk: Boolean,
    
    @SerialName("lastupdated") val lastUpdated: Long = com.example.man_app.util.getNowMillis(),
    @SerialName("syncstatus") val syncStatus: String = SyncStatus.PENDING
)

@Serializable
@Entity(tableName = "scrap_records")
data class ScrapRecord(
    @PrimaryKey @SerialName("id") val id: String = randomUUID(),
    @SerialName("reportid") val reportId: String,
    @SerialName("timestamp") val timestamp: Long,
    @SerialName("plcvalue") val plcValue: Double,
    
    @SerialName("lastupdated") val lastUpdated: Long = com.example.man_app.util.getNowMillis(),
    @SerialName("syncstatus") val syncStatus: String = SyncStatus.PENDING
)

@Serializable
@Entity(tableName = "cage_events")
data class CageEvent(
    @PrimaryKey @SerialName("id") val id: String = randomUUID(),
    @SerialName("reportid") val reportId: String,
    @SerialName("timestamp") val timestamp: Long,
    @SerialName("change") val change: Int, // +1 or -1
    
    @SerialName("lastupdated") val lastUpdated: Long = com.example.man_app.util.getNowMillis(),
    @SerialName("syncstatus") val syncStatus: String = SyncStatus.PENDING
)

@Serializable
@Entity(tableName = "production_stops")
data class ProductionStop(
    @PrimaryKey @SerialName("id") val id: String = randomUUID(),
    @SerialName("reportid") val reportId: String,
    @SerialName("workstation") val workstation: String,
    @SerialName("starttime") val startTime: Long,
    @SerialName("endtime") val endTime: Long? = null,
    @SerialName("reason") val reason: String,
    @SerialName("operators") val operators: String,
    @SerialName("activities") val activities: String? = null,
    
    @SerialName("lastupdated") val lastUpdated: Long = com.example.man_app.util.getNowMillis(),
    @SerialName("syncstatus") val syncStatus: String = SyncStatus.PENDING
)

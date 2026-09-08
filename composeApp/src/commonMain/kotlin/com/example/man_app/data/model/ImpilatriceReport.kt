package com.example.man_app.data.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames
import com.example.man_app.util.randomUUID

@Serializable
@Entity(tableName = "impilatrice_reports")
data class ImpilatriceReport(
    @PrimaryKey @SerialName("id") val id: String = randomUUID(),
    @SerialName("date") val date: Long,
    @SerialName("starttime") val startTime: Long,
    @SerialName("endtime") val endTime: Long = 0,
    @SerialName("operatorid") @JsonNames("operator_id") val operatorId: String,
    @SerialName("productid") @JsonNames("product_id") val productId: String,
    @SerialName("shift") val shift: String, // Label like "1", "2", etc.
    @SerialName("sigla") val sigla: String? = null,
    @SerialName("lineid") @JsonNames("line_id") val lineId: String,
    @SerialName("machineid") @JsonNames("machine_id") val machineId: String,
    
    @SerialName("isclosed") val isClosed: Boolean = false,
    
    // Production data
    @SerialName("cagesproduced") val cagesProduced: Int = 0,
    @SerialName("scrapminutes") val scrapMinutes: Int = 0,
    
    // Global Quality (Analisi Gabbia)
    @SerialName("cagematerialdistribution") val cageMaterialDistribution: String = "Bene", // Bene / Male
    @SerialName("cagenotes") val cageNotes: String = "",
    
    @SerialName("lastupdated") @JsonNames("last_updated") val lastUpdated: Long = com.example.man_app.util.getNowMillis(),
    @SerialName("syncstatus") @JsonNames("sync_status") val syncStatus: String = SyncStatus.PENDING
)

@Serializable
@Entity(tableName = "impilatrice_cage_events")
data class ImpilatriceCageEvent(
    @PrimaryKey @SerialName("id") val id: String = randomUUID(),
    @SerialName("reportid") @JsonNames("report_id") val reportId: String,
    @SerialName("timestamp") val timestamp: Long,
    @SerialName("change") val change: Int, // +1 or -1
    
    @SerialName("lastupdated") @JsonNames("last_updated") val lastUpdated: Long = com.example.man_app.util.getNowMillis(),
    @SerialName("syncstatus") @JsonNames("sync_status") val syncStatus: String = SyncStatus.PENDING
)

@Serializable
@Entity(tableName = "impilatrice_scrap_records")
data class ImpilatriceScrapRecord(
    @PrimaryKey @SerialName("id") val id: String = randomUUID(),
    @SerialName("reportid") @JsonNames("report_id") val reportId: String,
    @SerialName("timestamp") val timestamp: Long,
    @SerialName("plcvalue") @JsonNames("plc_value") val plcValue: Double,
    
    @SerialName("lastupdated") @JsonNames("last_updated") val lastUpdated: Long = com.example.man_app.util.getNowMillis(),
    @SerialName("syncstatus") @JsonNames("sync_status") val syncStatus: String = SyncStatus.PENDING
)

@Serializable
@Entity(tableName = "impilatrice_kiln_cars")
data class ImpilatriceKilnCar(
    @PrimaryKey @SerialName("id") val id: String = randomUUID(),
    @SerialName("reportid") @JsonNames("report_id") val reportId: String,
    @SerialName("starttime") @JsonNames("start_time") val startTime: Long,
    @SerialName("endtime") @JsonNames("end_time") val endTime: Long? = null,
    @SerialName("sigla") val sigla: String? = null,
    @SerialName("iscleaned") @JsonNames("is_cleaned") val isCleaned: Boolean = false,
    @SerialName("loadpercentage") @JsonNames("load_percentage") val loadPercentage: Int = 100,
    @SerialName("refractoriesok") @JsonNames("refractories_ok") val refractoriesOk: Boolean = true,
    
    // Analisi Pezzo
    @SerialName("height") val height: Double = 0.0,
    @SerialName("width") val width: Double = 0.0,
    @SerialName("thickness") val thickness: Double = 0.0,
    @SerialName("weight") val weight: Double = 0.0,
    @SerialName("isdiagonalok") @JsonNames("is_diagonal_ok") val isDiagonalOk: Boolean = true,
    
    @SerialName("dryingrating") @JsonNames("drying_rating") val dryingRating: String = "Essiccato", // Essiccato / Umido
    @SerialName("colorrating") @JsonNames("color_rating") val colorRating: String = "Normale", // Normale / Anomalo
    
    @SerialName("notes") val notes: String = "",
    @SerialName("lastupdated") @JsonNames("last_updated") val lastUpdated: Long = com.example.man_app.util.getNowMillis(),
    @SerialName("syncstatus") @JsonNames("sync_status") val syncStatus: String = SyncStatus.PENDING
)
    @SerialName("chipsrating") val chipsRating: String = "Nessuna", // Nessuna / Poche / Molte
    @SerialName("cracksrating") val cracksRating: String = "Nessuna",
    @SerialName("hairlinesrating") val hairlinesRating: String = "Nessuna",
    @SerialName("breaksrating") val breaksRating: String = "Nessuna",
    @SerialName("qualitynotes") val qualityNotes: String = "",
    
    @SerialName("lastupdated") val lastUpdated: Long = com.example.man_app.util.getNowMillis(),
    @SerialName("syncstatus") val syncStatus: String = SyncStatus.PENDING
)

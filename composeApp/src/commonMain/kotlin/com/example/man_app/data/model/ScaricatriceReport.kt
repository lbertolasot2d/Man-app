package com.example.man_app.data.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.example.man_app.util.randomUUID

@Serializable
@Entity(tableName = "scaricatrice_reports")
data class ScaricatriceReport(
    @PrimaryKey @SerialName("id") val id: String = randomUUID(),
    @SerialName("date") val date: Long,
    @SerialName("starttime") val startTime: Long,
    @SerialName("endtime") val endTime: Long = 0,
    @SerialName("operatorid") val operatorId: String,
    @SerialName("productid") val productId: String,
    @SerialName("shift") val shift: String,
    @SerialName("sigla") val sigla: String? = null,
    @SerialName("lineid") val lineId: String,
    @SerialName("machineid") val machineId: String,
    @SerialName("isclosed") val isClosed: Boolean = false,

    // Production data - Pacchi
    @SerialName("pacchi1ascelta") val pacchi1aScelta: Int = 0,
    @SerialName("pacchi2ascelta") val pacchi2aScelta: Int = 0,
    @SerialName("bobinenylon") val bobineNylon: Int = 0,
    @SerialName("bobinereggia") val bobineReggia: Int = 0,
    
    // Global Quality - Pacchi
    @SerialName("pacchisquadratura") val pacchiSquadratura: String = "Buona", // Buona / Non buona
    @SerialName("pacchiimballo") val pacchiImballo: String = "Buono", // Buono / Non buono
    @SerialName("pacchicentraturapallet") val pacchiCentraturaPallet: Boolean = true,
    @SerialName("pacchietichettaok") val pacchiEtichettaOk: Boolean = true,
    
    // Other production data
    @SerialName("scrapminutes") val scrapMinutes: Int = 0,
    
    @SerialName("lastupdated") val lastUpdated: Long = com.example.man_app.util.getNowMillis(),
    @SerialName("syncstatus") val syncStatus: String = SyncStatus.PENDING
)

@Serializable
@Entity(tableName = "scaricatrice_scrap_records")
data class ScaricatriceScrapRecord(
    @PrimaryKey @SerialName("id") val id: String = randomUUID(),
    @SerialName("reportid") val reportId: String,
    @SerialName("timestamp") val timestamp: Long,
    @SerialName("plcvalue") val plcValue: Double,
    
    @SerialName("lastupdated") val lastUpdated: Long = com.example.man_app.util.getNowMillis(),
    @SerialName("syncstatus") val syncStatus: String = SyncStatus.PENDING
)

@Serializable
@Entity(tableName = "scaricatrice_kiln_cars")
data class ScaricatriceKilnCar(
    @PrimaryKey @SerialName("id") val id: String = randomUUID(),
    @SerialName("reportid") val reportId: String,
    @SerialName("starttime") val startTime: Long,
    @SerialName("endtime") val endTime: Long? = null,
    @SerialName("sigla") val sigla: String? = null,
    @SerialName("iscleaned") val isCleaned: Boolean = false,
    @SerialName("refractoriesok") val refractoriesOk: Boolean = true,
    @SerialName("scelta") val scelta: String? = null, // null (1a), "2a", "Scarto"
    
    // Qualità Pezzo
    @SerialName("soundaltook") val soundAltoOk: Boolean = true,
    @SerialName("soundcentrook") val soundCentroOk: Boolean = true,
    @SerialName("soundbassook") val soundBassoOk: Boolean = true,
    
    @SerialName("height") val height: Double = 0.0,
    @SerialName("width") val width: Double = 0.0,
    @SerialName("thickness") val thickness: Double = 0.0,
    @SerialName("weight") val weight: Double = 0.0,
    
    @SerialName("firingrating") val firingRating: String = "Cotto", // Cotto / Poco Cotto
    
    @SerialName("chipsok") val chipsOk: Boolean = true, // true = No, false = Si
    @SerialName("chipsnotes") val chipsNotes: String = "",
    
    @SerialName("efflorescencerating") val efflorescenceRating: String = "Nulla", // Nulla / Media / Forte
    @SerialName("efflorescencenotes") val efflorescenceNotes: String = "",
    
    @SerialName("stainsrating") val stainsRating: String = "Nulla", // Nulla / Media / Forte
    @SerialName("stainscolor") val stainsColor: String = "Verdi", // Verdi / Nere
    @SerialName("stainsnotes") val stainsNotes: String = "",
    
    @SerialName("cracksrating") val cracksRating: String = "Nulla",
    @SerialName("cracksnotes") val cracksNotes: String = "",
    
    @SerialName("hairlinesrating") val hairlinesRating: String = "Nulla",
    @SerialName("hairlinesnotes") val hairlinesNotes: String = "",
    
    @SerialName("breaksrating") val breaksRating: String = "Nulla",
    @SerialName("breaksnotes") val breaksNotes: String = "",
    
    @SerialName("qualitynotes") val qualityNotes: String = "",
    
    @SerialName("lastupdated") val lastUpdated: Long = com.example.man_app.util.getNowMillis(),
    @SerialName("syncstatus") val syncStatus: String = SyncStatus.PENDING
)

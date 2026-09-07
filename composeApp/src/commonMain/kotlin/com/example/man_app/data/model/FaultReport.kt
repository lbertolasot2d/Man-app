package com.example.man_app.data.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "fault_reports")
data class FaultReport(
    @PrimaryKey(autoGenerate = true) @SerialName("id") val id: Long = 0,
    @SerialName("timestamp") val timestamp: Long,
    @SerialName("machineid") val machineId: String,
    @SerialName("description") val description: String,
    @SerialName("reporterid") val reporterId: String,
    @SerialName("severity") val severity: String, // e.g., "Low", "Medium", "High", "Critical"
    @SerialName("isresolved") val isResolved: Boolean = false,
    @SerialName("resolutionnotes") val resolutionNotes: String? = null,
    
    @SerialName("lastupdated") val lastUpdated: Long = com.example.man_app.util.getNowMillis(),
    @SerialName("syncstatus") val syncStatus: String = SyncStatus.PENDING
)

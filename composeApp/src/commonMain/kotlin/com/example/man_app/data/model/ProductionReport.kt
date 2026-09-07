package com.example.man_app.data.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "production_reports")
data class ProductionReport(
    @PrimaryKey(autoGenerate = true) @SerialName("id") val id: Long = 0,
    @SerialName("date") val date: Long, // Timestamp
    @SerialName("shift") val shift: String, // e.g., "Morning", "Afternoon", "Night"
    @SerialName("operatorid") val operatorId: String,
    @SerialName("machineid") val machineId: String,
    @SerialName("unitsproduced") val unitsProduced: Int,
    @SerialName("unitsdefective") val unitsDefective: Int,
    @SerialName("notes") val notes: String? = null,
    
    @SerialName("lastupdated") val lastUpdated: Long = com.example.man_app.util.getNowMillis(),
    @SerialName("syncstatus") val syncStatus: String = SyncStatus.PENDING
)

package com.example.man_app.data.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.example.man_app.util.randomUUID

@Serializable
@Entity(tableName = "stop_events")
data class StopEvent(
    @PrimaryKey @SerialName("id") val id: String = randomUUID(),
    @SerialName("starttime") val startTime: Long,
    @SerialName("endtime") val endTime: Long? = null,
    @SerialName("stoptype") val stopType: String = "",
    @SerialName("activities") val activities: String = "",
    @SerialName("machineid") val machineId: String,
    @SerialName("lineid") val lineId: String,
    @SerialName("isfinished") val isFinished: Boolean = false,
    
    // Metadata from the shift
    @SerialName("operatorid") val operatorId: String? = null,
    @SerialName("productid") val productId: String? = null,
    @SerialName("sigla") val sigla: String? = null,
    @SerialName("shiftname") val shiftName: String? = null,
    
    @SerialName("lastupdated") val lastUpdated: Long = com.example.man_app.util.getNowMillis(),
    @SerialName("syncstatus") val syncStatus: String = SyncStatus.PENDING
)

@Serializable
@Entity(tableName = "stop_suggestions")
data class StopSuggestion(
    @PrimaryKey @SerialName("text") val text: String,
    @SerialName("category") val category: String, // "TYPE" or "ACTIVITY"
    @SerialName("lastupdated") val lastUpdated: Long = com.example.man_app.util.getNowMillis(),
    @SerialName("syncstatus") val syncStatus: String = SyncStatus.PENDING
)

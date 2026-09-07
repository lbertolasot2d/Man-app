package com.example.man_app.data.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "personnel")
data class Personnel(
    @PrimaryKey @SerialName("id") val id: String,
    @SerialName("firstname") val firstName: String,
    @SerialName("lastname") val lastName: String,
    @SerialName("role") val role: String,
    @SerialName("line") val line: String = "",
    @SerialName("department") val department: String,
    @SerialName("isactive") val isActive: Boolean = true,
    
    @SerialName("lastupdated") val lastUpdated: Long = com.example.man_app.util.getNowMillis(),
    @SerialName("syncstatus") val syncStatus: String = SyncStatus.PENDING
)

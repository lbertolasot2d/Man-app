package com.example.man_app.data.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "products")
data class Product(
    @PrimaryKey @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("targetweight") val targetWeight: Double,
    @SerialName("targetlength") val targetLength: Double,
    @SerialName("targetwidth") val targetWidth: Double,
    @SerialName("targetheight") val targetHeight: Double,
    @SerialName("targetdiagonal") val targetDiagonal: Double,
    @SerialName("theoreticalcagesperhour") val theoreticalCagesPerHour: Double,
    
    @SerialName("lastupdated") val lastUpdated: Long = com.example.man_app.util.getNowMillis(),
    @SerialName("syncstatus") val syncStatus: String = SyncStatus.PENDING
)

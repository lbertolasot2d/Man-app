package com.example.man_app.data.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.example.man_app.util.randomUUID

@Serializable
@Entity(tableName = "shift_configurations")
data class ShiftConfiguration(
    @PrimaryKey @SerialName("id") val id: String = randomUUID(),
    @SerialName("name") val name: String, // "1", "2", "3", "0", etc.
    @SerialName("lineid") val lineId: String,
    
    // Times stored as minutes from midnight (0 to 1439)
    @SerialName("mondaystart") val mondayStart: Int? = null,
    @SerialName("mondayend") val mondayEnd: Int? = null,
    @SerialName("tuesdaystart") val tuesdayStart: Int? = null,
    @SerialName("tuesdayend") val tuesdayEnd: Int? = null,
    @SerialName("wednesdaystart") val wednesdayStart: Int? = null,
    @SerialName("wednesdayend") val wednesdayEnd: Int? = null,
    @SerialName("thursdaystart") val thursdayStart: Int? = null,
    @SerialName("thursdayend") val thursdayEnd: Int? = null,
    @SerialName("fridaystart") val fridayStart: Int? = null,
    @SerialName("fridayend") val fridayEnd: Int? = null,
    @SerialName("saturdaystart") val saturdayStart: Int? = null,
    @SerialName("saturdayend") val saturdayEnd: Int? = null,
    @SerialName("sundaystart") val sundayStart: Int? = null,
    @SerialName("sundayend") val sundayEnd: Int? = null,
    
    @SerialName("lastupdated") val lastUpdated: Long = com.example.man_app.util.getNowMillis(),
    @SerialName("syncstatus") val syncStatus: String = SyncStatus.PENDING
)

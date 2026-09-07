package com.example.man_app.data.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "checklist_templates")
data class ChecklistTemplate(
    @PrimaryKey @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String? = null
)

@Serializable
@Entity(tableName = "checklist_items")
data class ChecklistItem(
    @PrimaryKey @SerialName("id") val id: String,
    @SerialName("templateid") val templateId: String,
    @SerialName("text") val text: String,
    @SerialName("order") val order: Int
)

@Serializable
@Entity(tableName = "checklist_responses")
data class ChecklistResponse(
    @PrimaryKey(autoGenerate = true) @SerialName("id") val id: Long = 0,
    @SerialName("checklistid") val checklistId: String,
    @SerialName("itemid") val itemId: String,
    @SerialName("operatorid") val operatorId: String,
    @SerialName("timestamp") val timestamp: Long,
    @SerialName("ischecked") val isChecked: Boolean,
    @SerialName("note") val note: String? = null,
    
    @SerialName("lastupdated") val lastUpdated: Long = com.example.man_app.util.getNowMillis(),
    @SerialName("syncstatus") val syncStatus: String = SyncStatus.PENDING
)

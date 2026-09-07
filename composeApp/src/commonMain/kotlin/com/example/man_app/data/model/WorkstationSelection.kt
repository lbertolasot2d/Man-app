package com.example.man_app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorkstationContext(
    @SerialName("line") val line: String,
    @SerialName("machine") val machine: String
)

object SelectionData {
    val lines = listOf("MV", "T3", "GABBRO", "CAMBIANO", "MASSERANO")
    val machines = listOf("MATTONIERA", "IMPILATRICE", "SCARICATRICE", "FORNO")
}

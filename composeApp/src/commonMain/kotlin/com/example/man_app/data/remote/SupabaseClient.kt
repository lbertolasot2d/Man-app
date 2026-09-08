package com.example.man_app.data.remote

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.SupabaseClient as SupabaseClientType
import kotlinx.serialization.json.Json

object SupabaseClient {
    private const val SUPABASE_URL = "https://uwaqxonujzqilxwvmuaw.supabase.co"
    private const val SUPABASE_KEY = "sb_publishable_2mt2pA5S8Qmr5K7cGgcCGg_gbr-Du4G"

    // Configurazione JSON che RISPETTA SOLO i @SerialName
    // NON usa naming strategy automatica camelCase->snake_case
    private val jsonConfig = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        // Disabilita la naming strategy automatica
        // Usa SOLO i @SerialName definiti nelle data class
    }

    val client: SupabaseClientType = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        // Installa Postgrest senza personalizzazioni (usa i default di supabase-kt)
        install(Postgrest)
        install(Auth)
    }
}

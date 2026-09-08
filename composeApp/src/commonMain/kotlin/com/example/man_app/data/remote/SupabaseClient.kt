package com.example.man_app.data.remote

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.auth.Auth
import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import kotlinx.serialization.json.Json

object SupabaseClient {
    private const val SUPABASE_URL = "https://uwaqxonujzqilxwvmuaw.supabase.co"
    private const val SUPABASE_KEY = "sb_publishable_2mt2pA5S8Qmr5K7cGgcCGg_gbr-Du4G"

    // Json serializer che RISPETTA @SerialName e NON fa naming strategy
    private val customJson = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        // Disabilita qualsiasi conversione - usa SOLO i @SerialName definiti
        isLenient = false
    }

    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY,
        httpEngine = { 
            // Usa il JS engine con una configurazione custom
            HttpClient(Js)
        }
    ) {
        install(Postgrest) {
            // Imposta il serializer personalizzato
            json = customJson
        }
        install(Auth)
    }
}

package com.example.man_app.data.remote

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.auth.Auth
import kotlinx.serialization.json.Json

object SupabaseClient {
    private const val SUPABASE_URL = "https://uwaqxonujzqilxwvmuaw.supabase.co"
    private const val SUPABASE_KEY = "sb_publishable_2mt2pA5S8Qmr5K7cGgcCGg_gbr-Du4G"

    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        install(Postgrest)
        install(Auth)
    }
}

package com.example.cookitup.data.remote.supabase

import com.example.cookitup.data.remote.SUPABASE_API_KEY
import com.example.cookitup.data.remote.SUPABASE_URL
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

object Supabase {
    val client = createSupabaseClient(
        SUPABASE_URL,
        SUPABASE_API_KEY
    ) {
        install(Postgrest)
        install(Storage)
        install(Auth) {
            host = "login-callback"
            scheme = "cook-it-up"
        }
    }
}

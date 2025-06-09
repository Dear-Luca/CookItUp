package com.example.cookitup.data.remote.supabase

import com.example.cookitup.data.remote.SUPABASE_API_KEY
import com.example.cookitup.data.remote.SUPABASE_URL
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object Supabase {
    val client = createSupabaseClient(
        SUPABASE_URL,
        SUPABASE_API_KEY
    ) {
        install(Postgrest)
        install(Auth) {
            host = "login-callback"
            scheme = "cook-it-up"
        }
    }
}

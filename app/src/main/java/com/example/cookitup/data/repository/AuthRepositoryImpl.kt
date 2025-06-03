package com.example.cookitup.data.repository

import com.example.cookitup.data.remote.supabase.Supabase
import com.example.cookitup.domain.repository.AuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class AuthRepositoryImpl(
    private val client: SupabaseClient = Supabase.client
) : AuthRepository {
    override suspend fun signUp(email: String, password: String, username: String) {
        client.auth.signUpWith(Email) {
            this.email = email
            this.password = password
            data = buildJsonObject {
                put("username", username)
            }
        }
    }

    override suspend fun signIn(email: String, password: String) {
        client.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
    }

    override suspend fun signOut() {
        TODO("Not yet implemented")
    }
}

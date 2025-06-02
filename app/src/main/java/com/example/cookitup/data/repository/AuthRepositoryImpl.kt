package com.example.cookitup.data.repository

import com.example.cookitup.data.remote.supabase.SupabaseClient
import com.example.cookitup.domain.repository.AuthRepository
import com.example.cookitup.domain.repository.AuthResult
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth

class AuthRepositoryImpl(
    private val client: Auth = SupabaseClient.client.auth
) : AuthRepository {
    override suspend fun signUp(email: String, password: String): AuthResult {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(email: String, password: String): AuthResult {
        TODO("Not yet implemented")
    }

    override suspend fun signOut(): Boolean {
        TODO("Not yet implemented")
    }
}

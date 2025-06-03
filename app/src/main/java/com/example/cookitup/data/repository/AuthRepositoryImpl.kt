package com.example.cookitup.data.repository

import com.example.cookitup.data.remote.supabase.Supabase
import com.example.cookitup.domain.model.User
import com.example.cookitup.domain.repository.AuthRepository
import com.example.cookitup.domain.repository.AuthResult
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from

class AuthRepositoryImpl(
    private val client: SupabaseClient = Supabase.client
) : AuthRepository {
    override suspend fun signUp(email: String, password: String, username: String): AuthResult {
        return try {
            val userInfo = client.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }

            if (userInfo?.email == null) {
                return AuthResult.Error("Error: Check your email")
            }

            val user = User(
                id = userInfo.id,
                email = userInfo.email!!,
                username = username,
                image = null
            )
            client.from("users").insert(user)
            AuthResult.Success(user)
        } catch (e: Exception) {
            AuthResult.Error(e.localizedMessage ?: "Error")
        }
    }

    override suspend fun signIn(email: String, password: String): AuthResult {
        TODO("Not yet implemented")
    }

    override suspend fun signOut(): Boolean {
        TODO("Not yet implemented")
    }
}

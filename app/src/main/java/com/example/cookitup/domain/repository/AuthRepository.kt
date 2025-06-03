package com.example.cookitup.domain.repository

interface AuthRepository {
    suspend fun signUp(email: String, password: String, username: String)
    suspend fun signIn(email: String, password: String)
    suspend fun signOut()
}

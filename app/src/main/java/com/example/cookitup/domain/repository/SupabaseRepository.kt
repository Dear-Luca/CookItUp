package com.example.cookitup.domain.repository

import com.example.cookitup.domain.model.User

interface SupabaseRepository {
    // login
    suspend fun signUp(email: String, password: String, username: String)
    suspend fun signIn(email: String, password: String)
    suspend fun signOut()
    suspend fun getCurrentUser(): User
    suspend fun checkEmail(email: String): Boolean

    // update user
    suspend fun checkUsername(username: String): Boolean
    suspend fun updateUsername(newUsername: String)
    suspend fun updatePassword(newPassword: String)
}

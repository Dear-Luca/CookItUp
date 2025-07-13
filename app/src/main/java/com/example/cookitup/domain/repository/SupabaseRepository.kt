package com.example.cookitup.domain.repository

import com.example.cookitup.domain.model.User

interface SupabaseRepository {
    // sign/log in
    suspend fun signUp(email: String, password: String, username: String)
    suspend fun signIn(email: String, password: String)
    suspend fun getCurrentUser(): User
    suspend fun checkEmail(email: String): Boolean

    // sign/log out
    suspend fun signOut()
    suspend fun deleteCurrentUser()

    // update user
    suspend fun checkUsername(username: String): Boolean
    suspend fun updateUsername(newUsername: String)
    suspend fun updatePassword(newPassword: String)
    suspend fun updateImage(fileName: String, imageBytes: ByteArray)
    suspend fun getProfileImage(image: String): ByteArray
}

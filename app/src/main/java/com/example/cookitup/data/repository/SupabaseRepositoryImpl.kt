package com.example.cookitup.data.repository

import com.example.cookitup.data.remote.SUPABASE_SERVICE_ROLE_KEY
import com.example.cookitup.data.remote.dto.MapperDto
import com.example.cookitup.data.remote.dto.UserDto
import com.example.cookitup.data.remote.supabase.Supabase
import com.example.cookitup.domain.model.User
import com.example.cookitup.domain.repository.SupabaseRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.storage.storage
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class SupabaseRepositoryImpl(
    private val client: SupabaseClient = Supabase.client
) : SupabaseRepository {
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
        client.auth.signOut()
    }

    override suspend fun deleteCurrentUser() {
        val uuid = client.auth.retrieveUserForCurrentSession().id
        client.auth.importAuthToken(SUPABASE_SERVICE_ROLE_KEY)
        client.auth.admin.deleteUser(uuid)
    }

    override suspend fun getCurrentUser(): User {
        val currentUser = client.auth.retrieveUserForCurrentSession()
        val userDto = client.from("users").select() {
            filter {
                eq("id", currentUser.id)
            }
        }.decodeSingle<UserDto>()
        return MapperDto.mapToDomain(userDto, currentUser.email)
    }

    override suspend fun checkUsername(username: String): Boolean {
        val response = client.from("users")
            .select(columns = Columns.list("username")) {
                filter { eq("username", username) }
            }

        return response.data == "[]"
    }

    override suspend fun updateUsername(newUsername: String) {
        val currentUser = client.auth.currentUserOrNull()
        client.from("users")
            .update(mapOf("username" to newUsername)) {
                filter { eq("id", currentUser!!.id) }
            }
    }

    override suspend fun updatePassword(newPassword: String) {
        client.auth.updateUser {
            password = newPassword
        }
    }

    override suspend fun updateImage(fileName: String, imageBytes: ByteArray) {
        Supabase.client.storage.from("avatars").upload(fileName, imageBytes) {
            upsert = true
        }
    }

    override suspend fun checkEmail(email: String): Boolean {
        val response = client.from("users")
            .select(columns = Columns.list("email")) {
                filter { eq("email", email) }
            }
        return response.data == "[]"
    }
}

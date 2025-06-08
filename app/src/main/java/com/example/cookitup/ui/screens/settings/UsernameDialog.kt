package com.example.cookitup.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UsernameDialog(
    currentUsername: String,
    isLoading: Boolean = false,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var username by remember { mutableStateOf(currentUsername) }
    var isError by remember { mutableStateOf(false) }

    // Validazione username
    val isUsernameValid = username.isNotBlank() &&
        username.length >= 3 &&
        username.length <= 20 &&
        username != currentUsername

    AlertDialog(
        onDismissRequest = if (!isLoading) onDismiss else { {} },
        title = { Text("Change Username") },
        text = {
            Column {
                Text(
                    text = "Enter your new username (3-20 characters)",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                OutlinedTextField(
                    value = username,
                    onValueChange = {
                        username = it.trim()
                        isError = !isUsernameValid
                    },
                    label = { Text("Username") },
                    isError = username.isNotBlank() && !isUsernameValid,
                    supportingText = when {
                        username.isBlank() -> { { Text("Username cannot be empty") } }
                        username.length < 3 -> { { Text("Username must be at least 3 characters") } }
                        username.length > 20 -> { { Text("Username must be less than 20 characters") } }
                        username == currentUsername -> { { Text("Enter a different username") } }
                        else -> null
                    },
                    singleLine = true,
                    enabled = !isLoading
                )

                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(username) },
                enabled = isUsernameValid && !isLoading
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                enabled = !isLoading
            ) {
                Text("Cancel")
            }
        }
    )
}

package com.example.cookitup.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun EmailDialog(
    currentEmail: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var email by remember { mutableStateOf(currentEmail) }
    var isError by remember { mutableStateOf(false) }

    val emailPattern = android.util.Patterns.EMAIL_ADDRESS

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Change Email") },
        text = {
            Column {
                Text(
                    text = "Enter your new email address",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        isError = !emailPattern.matcher(it).matches()
                    },
                    label = { Text("Email") },
                    isError = isError,
                    supportingText = if (isError) {
                        { Text("Please enter a valid email address") }
                    } else {
                        null
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(email) },
                enabled = !isError && email.isNotBlank()
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

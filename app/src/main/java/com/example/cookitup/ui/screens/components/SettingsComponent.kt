package com.example.cookitup.ui.screens.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.example.cookitup.ui.screens.profile.ProfileActions
import com.example.cookitup.ui.screens.profile.ProfileState

@Composable
fun SettingsComponent(state: ProfileState, actions: ProfileActions) {
    IconButton(
        onClick = {}
    ) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Settings"
        )
    }
}

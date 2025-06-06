package com.example.cookitup.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cookitup.R
import com.example.cookitup.domain.model.User
import com.example.cookitup.ui.screens.components.BottomBar
import com.example.cookitup.ui.screens.components.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(
    navController: NavHostController,
    user: User?,
    currentTheme: Theme,
    actions: SettingsActions
) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    // dialog states
    var showUsernameDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(navController, stringResource(R.string.title_settings), scrollBehavior)
        },
        bottomBar = { BottomBar(navController) },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Account Section
                item {
                    SettingsSection(title = "ACCOUNT") {
                        SettingsItem(
                            icon = Icons.Default.Person,
                            title = "Change Username",
                            subtitle = user?.username ?: "",
                            onClick = { showUsernameDialog = true }
                        )
                        SettingsItem(
                            icon = Icons.Default.Email,
                            title = "Change Email",
                            subtitle = user?.email ?: "",
                            onClick = { /* Handle change email */ }
                        )
                        SettingsItem(
                            icon = Icons.Default.Lock,
                            title = "Change Password",
                            subtitle = "Update your password",
                            onClick = { /* Handle change password */ },
                            isLast = true
                        )
                    }
                }

                // App Preferences Section
                item {
                    SettingsSection(title = "APP PREFERENCES") {
                        SettingsItem(
                            icon = Icons.Default.Palette,
                            title = "Theme",
                            subtitle = "Choose your preferred theme"
                        ) {
                            ThemeSelector(
                                selectedTheme = currentTheme,
                                onThemeSelected = { actions.changeTheme(it) }
                            )
                        }
                        SettingsItem(
                            icon = Icons.Default.Notifications,
                            title = "Push Notifications",
                            subtitle = "Get notified about new recipes and updates"
                        ) {
                            Switch(
                                checked = notificationsEnabled,
                                onCheckedChange = { notificationsEnabled = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                                    checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                                )
                            )
                        }
                        SettingsItem(
                            icon = Icons.Default.Scale,
                            title = "Units of Measurement",
                            subtitle = "Metric",
                            onClick = { /* Handle units change */ },
                            isLast = true
                        )
                    }
                }

                // Account Actions Section
                item {
                    SettingsSection(title = "ACCOUNT ACTIONS") {
                        SettingsItem(
                            icon = Icons.Default.Delete,
                            title = "Delete Account",
                            subtitle = "Permanently delete your account and data",
                            onClick = { /* Handle delete account */ },
                            isDangerous = true
                        )
                        SettingsItem(
                            icon = Icons.AutoMirrored.Filled.Logout,
                            title = "Logout",
                            subtitle = "Sign out of your account",
                            onClick = { /* Handle logout */ },
                            isDangerous = true,
                            isLast = true
                        )
                    }
                }
            }
        }
    }
    // Dialog to change username
    if (showUsernameDialog) {
        UsernameDialog(
            currentUsername = user?.username ?: "",
            onDismiss = { showUsernameDialog = false },
            onConfirm = {
                showUsernameDialog = false
                // TODO
            }
        )
    }
}

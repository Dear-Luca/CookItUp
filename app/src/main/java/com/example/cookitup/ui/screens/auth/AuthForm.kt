package com.example.cookitup.ui.screens.auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookitup.utils.NetworkUtils
import kotlinx.coroutines.launch

@Composable
fun AuthForm(
    state: AuthState,
    actions: AuthActions
) {
    var isLoginMode by rememberSaveable { mutableStateOf(true) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var usernameError by remember { mutableStateOf<String?>(null) }
    var generalError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val emailPattern = android.util.Patterns.EMAIL_ADDRESS

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    // Handle errors
    LaunchedEffect(state) {
        when (state) {
            is AuthState.Error -> {
                generalError = state.message
                isLoading = false
            }
            AuthState.Loading -> {
                isLoading = true
                generalError = null
                emailError = null
                passwordError = null
                usernameError = null
            }
            else -> {
                isLoading = false
                generalError = null
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
    ) {
        // Add SnackbarHost at the bottom of the Box
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
        when (state) {
            AuthState.Initializing -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(48.dp),
                            strokeWidth = 4.dp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Loading...",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(bottom = 32.dp)
                    ) {
                        Text(
                            text = "🍳",
                            fontSize = 64.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "CookItUp",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Find the best recipes",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center
                        )
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp)),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            // Toggle Header
                            AnimatedContent(
                                targetState = isLoginMode,
                                transitionSpec = {
                                    fadeIn(animationSpec = tween(300)) togetherWith
                                        fadeOut(animationSpec = tween(300))
                                },
                                label = "auth_header"
                            ) { loginMode ->
                                Column(
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(
                                        text = if (loginMode) "Welcome back!" else "Create an account",
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = if (loginMode) {
                                            "Log in"
                                        } else {
                                            "Sign up"
                                        },
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }

                            // Form Fields
                            Column(
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                // Email Field
                                OutlinedTextField(
                                    value = email,
                                    onValueChange = {
                                        email = it
                                        emailError = null
                                    },
                                    label = { Text("Email") },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.Email,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    supportingText = {
                                        emailError?.let {
                                            Text(
                                                text = it,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }
                                    },
                                    isError = emailError != null,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                                        errorBorderColor = MaterialTheme.colorScheme.error,
                                        errorLabelColor = MaterialTheme.colorScheme.error
                                    ),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                // Password Field
                                OutlinedTextField(
                                    value = password,
                                    onValueChange = {
                                        password = it
                                        passwordError = null
                                    },
                                    label = { Text("Password") },
                                    singleLine = true,
                                    visualTransformation = if (passwordVisible) {
                                        VisualTransformation.None
                                    } else {
                                        PasswordVisualTransformation()
                                    },
                                    supportingText = {
                                        passwordError?.let {
                                            Text(
                                                text = it,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }
                                    },
                                    isError = passwordError != null,
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.Lock,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    trailingIcon = {
                                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                            Icon(
                                                if (passwordVisible) {
                                                    Icons.Default.VisibilityOff
                                                } else {
                                                    Icons.Default.Visibility
                                                },
                                                contentDescription = if (passwordVisible) {
                                                    "Hide password"
                                                } else {
                                                    "Show password"
                                                }
                                            )
                                        }
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                                        errorBorderColor = MaterialTheme.colorScheme.error,
                                        errorLabelColor = MaterialTheme.colorScheme.error
                                    ),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                // Username Field (only for signup)
                                AnimatedVisibility(
                                    visible = !isLoginMode,
                                    enter = slideInVertically(animationSpec = tween(300)) + fadeIn(),
                                    exit = slideOutVertically(animationSpec = tween(300)) + fadeOut()
                                ) {
                                    OutlinedTextField(
                                        value = username,
                                        onValueChange = {
                                            username = it
                                            usernameError = null
                                        },
                                        label = { Text("Username") },
                                        singleLine = true,
                                        leadingIcon = {
                                            Icon(
                                                Icons.Default.Person,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                        },
                                        supportingText = {
                                            usernameError?.let {
                                                Text(
                                                    text = it,
                                                    color = MaterialTheme.colorScheme.error
                                                )
                                            }
                                        },
                                        isError = usernameError != null,
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                                            errorBorderColor = MaterialTheme.colorScheme.error,
                                            errorLabelColor = MaterialTheme.colorScheme.error
                                        ),
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }

                            // General Error Message
                            AnimatedVisibility(
                                visible = generalError != null,
                                enter = slideInVertically(animationSpec = tween(300)) + fadeIn(),
                                exit = slideOutVertically(animationSpec = tween(300)) + fadeOut()
                            ) {
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.errorContainer
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = generalError.orEmpty(),
                                        color = MaterialTheme.colorScheme.onErrorContainer,
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(12.dp)
                                    )
                                }
                            }

                            // Action Button
                            Button(
                                onClick = {
                                    keyboardController?.hide()
                                    // Clear all errors before validation
                                    emailError = null
                                    passwordError = null
                                    usernameError = null
                                    generalError = null

                                    var hasErrors = false

                                    when (isLoginMode) {
                                        true -> {
                                            // Validate email
                                            if (email.isBlank()) {
                                                emailError = "Email is required"
                                                hasErrors = true
                                            } else if (!emailPattern.matcher(email).matches()) {
                                                emailError = "Please enter a valid email"
                                                hasErrors = true
                                            }

                                            // Validate password
                                            if (password.isBlank()) {
                                                passwordError = "Password is required"
                                                hasErrors = true
                                            }

                                            if (!hasErrors) {
                                                scope.launch {
                                                    NetworkUtils.checkConnectivity(
                                                        context,
                                                        snackbarHostState
                                                    ) {
                                                        actions.signInUser(
                                                            email.trim(),
                                                            password.trim()
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                        false -> {
                                            // Validate email
                                            if (email.isBlank()) {
                                                emailError = "Email is required"
                                                hasErrors = true
                                            } else if (!emailPattern.matcher(email).matches()) {
                                                emailError = "Please enter a valid email"
                                                hasErrors = true
                                            }

                                            // Validate password
                                            when {
                                                password.length < 6 -> {
                                                    passwordError = "Password must be at least 6 characters"
                                                    hasErrors = true
                                                }
                                                !password.any { it.isDigit() } -> {
                                                    passwordError = "Password must contain at least one number"
                                                    hasErrors = true
                                                }
                                                !password.any { it.isLetter() } -> {
                                                    passwordError = "Password must contain at least one letter"
                                                    hasErrors = true
                                                }
                                                !password.any { it.isUpperCase() } -> {
                                                    passwordError = "Password must contain" +
                                                        " at least one uppercase letter"
                                                    hasErrors = true
                                                }
                                            }

                                            // Validate username
                                            when {
                                                username.isEmpty() -> {
                                                    usernameError = "Username is required"
                                                    hasErrors = true
                                                }
                                                username.length > 20 -> {
                                                    usernameError = "Username must be less than 20 characters"
                                                    hasErrors = true
                                                }
                                            }

                                            if (!hasErrors) {
                                                scope.launch {
                                                    NetworkUtils.checkConnectivity(
                                                        context,
                                                        snackbarHostState
                                                    ) {
                                                        actions.signUpUser(
                                                            email.trim(),
                                                            password.trim(),
                                                            username.trim()
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                },
                                enabled = !isLoading,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                            ) {
                                if (isLoading) {
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(20.dp),
                                            strokeWidth = 2.dp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = if (isLoginMode) "Access..." else "Verify your email...",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                } else {
                                    Text(
                                        text = if (isLoginMode) "Log in" else "Sign up",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }

                            // Toggle Button
                            TextButton(
                                onClick = {
                                    isLoginMode = !isLoginMode
                                    // Reset all errors when changing mode
                                    emailError = null
                                    passwordError = null
                                    usernameError = null
                                    generalError = null
                                    // Reset form fields
                                    email = ""
                                    password = ""
                                    username = ""
                                },
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            ) {
                                Text(
                                    text = if (isLoginMode) {
                                        "Don't you have an account? Sign up"
                                    } else {
                                        "Already have an account? Log in"
                                    },
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

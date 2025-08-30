import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.0.21"
    id("org.jlleitschuh.gradle.ktlint") version "11.6.1"
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.cookitup"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.cookitup"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // load spoonacular api key
        val keystoreFile = project.rootProject.file("local.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())

        // return empty key in case something goes wrong
        val spooncularApiKey = properties.getProperty("SPOONACULAR_API_KEY") ?: ""
        buildConfigField(
            type = "String",
            name = "SPOONACULAR_API_KEY",
            value = "\"$spooncularApiKey\""
        )

        val supabaseApiKey = properties.getProperty("SUPABASE_API_KEY") ?: ""
        buildConfigField(
            type = "String",
            name = "SUPABASE_API_KEY",
            value = "\"$supabaseApiKey\""
        )

        val supabaseServiceRoleKey = properties.getProperty("SUPABASE_SERVICE_ROLE_KEY") ?: ""
        buildConfigField(
            type = "String",
            name = "SUPABASE_SERVICE_ROLE_KEY",
            value = "\"$supabaseServiceRoleKey\""
        )
    }
    buildTypes {
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // Keep debugging info for release builds to help with crash reports
            isDebuggable = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        android.buildFeatures.buildConfig = true
    }
}

configurations.all {
    resolutionStrategy {
        force(libs.jetbrains.annotations)
        exclude(group = "com.intellij", module = "annotations")
    }
}

dependencies {
    implementation(libs.androidx.core.ktx) {
        exclude(group = "org.jetbrains", module = "annotations")
    }
    implementation(libs.androidx.lifecycle.runtime.ktx) {
        exclude(group = "org.jetbrains", module = "annotations")
    }
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.coil.compose)
    implementation(libs.koin.androidx.compose)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.compiler)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.jetbrains.annotations)
    implementation(platform(libs.supabase.bom))
    implementation(libs.supabase.auth)
    implementation(libs.supabase.postgrest)
    implementation(libs.supabase.storage)
    implementation(libs.ktor.client.android)
    implementation(libs.androidx.datastore.preferences)
    ksp(libs.androidx.room.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep attributes needed for reflection
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses
-keepattributes EnclosingMethod

# ===== ROOM DATABASE =====
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-keep @androidx.room.Dao class *
-keep class androidx.room.** { *; }
-dontwarn androidx.room.**

# Keep all Room entities and DAOs
-keep class com.example.cookitup.data.local.entity.** { *; }
-keep class com.example.cookitup.data.local.dao.** { *; }
-keep class com.example.cookitup.data.local.db.AppDatabase { *; }

# ===== KOTLIN COROUTINES =====
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.coroutines.**
-keep class kotlin.coroutines.** { *; }

# ===== KOIN DEPENDENCY INJECTION =====
-keep class org.koin.** { *; }
-dontwarn org.koin.**
-keep class * extends org.koin.core.module.Module
-keep class com.example.cookitup.AppModule** { *; }
-keep class com.example.cookitup.CookItUpApplication { *; }

# ===== SUPABASE =====
-keep class io.github.jan.supabase.** { *; }
-dontwarn io.github.jan.supabase.**
-keep class com.example.cookitup.data.remote.supabase.** { *; }

# ===== KTOR CLIENT =====
-keep class io.ktor.** { *; }
-dontwarn io.ktor.**

# ===== RETROFIT & GSON =====
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**

# Keep API related classes
-keep class com.example.cookitup.data.remote.api.** { *; }
-keep class com.example.cookitup.data.remote.dto.** { *; }

# ===== KOTLINX SERIALIZATION =====
-keep class kotlinx.serialization.** { *; }
-dontwarn kotlinx.serialization.**
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

# Keep serializable classes
-keep @kotlinx.serialization.Serializable class * {
    static **Companion** companion;
}

# ===== JETPACK COMPOSE =====
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# ===== DOMAIN MODELS =====
-keep class com.example.cookitup.domain.model.** { *; }

# ===== VIEWMODELS =====
-keep class com.example.cookitup.ui.screens.**.ViewModel { *; }
-keep class * extends androidx.lifecycle.ViewModel { *; }

# ===== REPOSITORIES =====
-keep class com.example.cookitup.data.repository.** { *; }
-keep class com.example.cookitup.domain.repository.** { *; }

# ===== COIL IMAGE LOADING =====
-keep class coil.** { *; }
-dontwarn coil.**

# ===== DATASTORE =====
-keep class androidx.datastore.** { *; }
-dontwarn androidx.datastore.**

# ===== GENERAL ANDROID =====
-keep class androidx.lifecycle.** { *; }
-keep class androidx.navigation.** { *; }

# Keep BuildConfig
-keep class com.example.cookitup.BuildConfig { *; }

# Keep enum classes
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep Parcelable implementations
-keep class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
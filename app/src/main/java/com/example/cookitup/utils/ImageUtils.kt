package com.example.cookitup.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.SystemClock
import android.provider.MediaStore
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun uriToBitmap(imageUri: Uri, contentResolver: ContentResolver): Bitmap {
    val bitmap = when {
        Build.VERSION.SDK_INT < 28 -> {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
        }
        else -> {
            val source = ImageDecoder.createSource(contentResolver, imageUri)
            ImageDecoder.decodeBitmap(source)
        }
    }
    return bitmap
}

fun saveImageToStorage(
    imageUri: Uri,
    contentResolver: ContentResolver,
    name: String = "IMG_${SystemClock.uptimeMillis()}"
): Uri {
    val bitmap = uriToBitmap(imageUri, contentResolver)

    val values = ContentValues()
    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    values.put(MediaStore.Images.Media.DISPLAY_NAME, name)

    val savedImageUri =
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    val outputStream = savedImageUri?.let { contentResolver.openOutputStream(it) }
        ?: throw FileNotFoundException()

    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    outputStream.close()

    return savedImageUri
}

fun saveProfileImageToDB(
    imageUri: Uri,
    contentResolver: ContentResolver,
    userId: String,
    updateProfileImage: (String, ByteArray) -> Unit
) {
    val bitmap = uriToBitmap(imageUri, contentResolver)
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val imageBytes = baos.toByteArray()
    val filePath = "$userId/profile.jpg"
    updateProfileImage(filePath, imageBytes)
}

@OptIn(ExperimentalUuidApi::class)
fun saveRecipeImageToDB(
    imageUri: Uri,
    contentResolver: ContentResolver,
    recipeId: String,
    insertRecipeImage: (String, ByteArray, String) -> Unit
) {
    val bitmap = uriToBitmap(imageUri, contentResolver)
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val imageBytes = baos.toByteArray()
    val uuid = Uuid.random()

    insertRecipeImage(uuid.toString(), imageBytes, recipeId)
}

package com.fraggeil.ticketator.core.domain

import androidx.compose.runtime.Composable

expect class ShareManager {
    fun shareText(text: String)
    suspend fun shareFile(file: ShareFileModel): Result<Unit>
}

@Composable
expect fun rememberShareManager(): ShareManager

enum class MimeType {
    PDF,
    TEXT,
    IMAGE,
}

class ShareFileModel(
    val mime: MimeType = MimeType.PDF,
    val fileName: String,
    val bytes: ByteArray
)


// https://medium.com/@mohaberabi98/sharing-data-and-files-in-compose-multiplatform-602105eaa3e2
//Implementing ShareManager on Android
//In Android, sharing data requires handling file permissions and using Intent for user interactions. The ShareManagerimplementation for Android handles both text and file sharing.
//
//Key Points to Note:
//
//File Handling: Files need to be stored in a directory accessible to other apps, such as the app’s cache directory.
//FileProvider: Necessary for securely sharing files between apps. This requires an entry in AndroidManifest.xmland an XML configuration file.
//create a new directory in androidMain/res/xml/file_path.xml
//
//<?xml version="1.0" encoding="utf-8"?>
//<paths xmlns:android="http://schemas.android.com/apk/res/android">
//<cache-path
//name="cache_files"
//path="." />
//</paths>
//Using FileProvider improves security by controlling which files your app shares with other apps. The cache-path tag specifies that files in the app's cache directory can be shared, ensuring temporary files are handled safely.
//
//now we need to declare the androidx content provider for file provider
//
//go to AndroidManifest.xml
//
//declare the provider
//
//<provider
//android:name="androidx.core.content.FileProvider"
//android:authorities="${applicationId}.provider"
//android:exported="false"
//android:grantUriPermissions="true">
//<meta-data
//android:name="android.support.FILE_PROVIDER_PATHS"
//android:resource="@xml/file_paths" />
//</provider>
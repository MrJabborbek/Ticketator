/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.fraggeil.ticketator.core.domain.moko_permission.permissions.ios

import com.fraggeil.ticketator.core.domain.moko_permission.permissions.DeniedAlwaysException
import com.fraggeil.ticketator.core.domain.moko_permission.permissions.Permission
import com.fraggeil.ticketator.core.domain.moko_permission.permissions.PermissionState
import com.fraggeil.ticketator.core.domain.moko_permission.permissions.mainContinuation
import platform.Photos.PHAuthorizationStatus
import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHAuthorizationStatusDenied
import platform.Photos.PHAuthorizationStatusNotDetermined
import platform.Photos.PHPhotoLibrary
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class GalleryPermissionDelegate : PermissionDelegate {
    override suspend fun providePermission() {
        providePermission(PHPhotoLibrary.authorizationStatus())
    }

    private suspend fun providePermission(status: PHAuthorizationStatus) {
        return when (status) {
            PHAuthorizationStatusAuthorized -> return
            PHAuthorizationStatusNotDetermined -> {
                val newStatus = suspendCoroutine<PHAuthorizationStatus> { continuation ->
                    requestGalleryAccess { continuation.resume(it) }
                }
                providePermission(newStatus)
            }

            PHAuthorizationStatusDenied -> throw DeniedAlwaysException(Permission.GALLERY)
            else -> error("unknown gallery authorization status $status")
        }
    }

    override suspend fun getPermissionState(): PermissionState {
        val status: PHAuthorizationStatus = PHPhotoLibrary.authorizationStatus()
        return when (status) {
            PHAuthorizationStatusAuthorized -> PermissionState.Granted
            PHAuthorizationStatusNotDetermined -> PermissionState.NotDetermined
            PHAuthorizationStatusDenied -> PermissionState.DeniedAlways
            else -> error("unknown gallery authorization status $status")
        }
    }
}

private fun requestGalleryAccess(callback: (PHAuthorizationStatus) -> Unit) {
    PHPhotoLibrary.requestAuthorization(mainContinuation { status: PHAuthorizationStatus ->
        callback(status)
    })
}

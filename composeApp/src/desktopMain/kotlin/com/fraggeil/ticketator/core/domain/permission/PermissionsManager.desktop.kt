package com.fraggeil.ticketator.core.domain.permission

import androidx.compose.runtime.Composable

actual class PermissionsManager actual constructor(callback: PermissionCallback) :
    PermissionHandler {
        @Composable
    override fun askPermission(permission: PermissionType) {

    }
    @Composable

    override fun isPermissionGranted(permission: PermissionType): Boolean {
        return false
    }

    @Composable
    override fun launchSettings() {

    }

}

@Composable
actual fun createPermissionsManager(callback: PermissionCallback): PermissionsManager {
    return PermissionsManager(callback)
}
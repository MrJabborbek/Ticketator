package com.fraggeil.ticketator.core.domain.moko_permission.permissions

@Suppress("TooManyFunctions")
class PermissionsControllerImpl(
) : PermissionsController {
    override suspend fun providePermission(permission: Permission) {

    }

    override suspend fun isPermissionGranted(permission: Permission): Boolean {
        return true
    }

    override suspend fun getPermissionState(permission: Permission): PermissionState {
        return PermissionState.Granted
    }

    override fun openAppSettings() {

    }
}
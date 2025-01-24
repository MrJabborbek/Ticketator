package com.fraggeil.ticketator.core.domain.moko_permission.permissions

actual interface PermissionsController {
    actual suspend fun providePermission(permission: Permission)
    actual suspend fun isPermissionGranted(permission: Permission): Boolean
    actual suspend fun getPermissionState(permission: Permission): PermissionState
    actual fun openAppSettings()



    companion object {
        operator fun invoke(
        ): PermissionsController { return PermissionsControllerImpl()
        }
    }
}
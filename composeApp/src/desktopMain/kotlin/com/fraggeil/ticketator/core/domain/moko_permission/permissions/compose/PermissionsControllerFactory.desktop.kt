package com.fraggeil.ticketator.core.domain.moko_permission.permissions.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.fraggeil.ticketator.core.domain.moko_permission.permissions.PermissionsController

@Composable
actual fun rememberPermissionsControllerFactory(): PermissionsControllerFactory {
    return remember {
        PermissionsControllerFactory { PermissionsController() }
    }
}

actual class PermissionsControllerFactoryClass {
    actual fun create(): PermissionsController {
        return PermissionsController()
    }
}

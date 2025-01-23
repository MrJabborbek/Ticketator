/*
 * Copyright 2023 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.fraggeil.ticketator.core.domain.moko_permission.permissions.compose

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.fraggeil.ticketator.core.domain.moko_permission.permissions.PermissionsController
import com.fraggeil.ticketator.core.domain.moko_permission.permissions.compose.PermissionsControllerFactory

@Composable
actual fun rememberPermissionsControllerFactory(): PermissionsControllerFactory {
    val context: Context = LocalContext.current
    return remember(context) {
        PermissionsControllerFactory {
            PermissionsController(applicationContext = context.applicationContext)
        }
    }
}

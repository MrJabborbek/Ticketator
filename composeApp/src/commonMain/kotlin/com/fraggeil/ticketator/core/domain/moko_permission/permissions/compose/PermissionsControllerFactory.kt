/*
 * Copyright 2023 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.fraggeil.ticketator.core.domain.moko_permission.permissions.compose

import androidx.compose.runtime.Composable
import androidx.room.RoomDatabase
import com.fraggeil.ticketator.core.domain.moko_permission.permissions.PermissionsController
import com.fraggeil.ticketator.data.database.Database

fun interface PermissionsControllerFactory {
    fun createPermissionsController(): PermissionsController
}

@Composable
expect fun rememberPermissionsControllerFactory(): PermissionsControllerFactory

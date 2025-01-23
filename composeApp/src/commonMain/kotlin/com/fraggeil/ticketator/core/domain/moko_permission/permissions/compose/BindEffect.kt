/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.fraggeil.ticketator.core.domain.moko_permission.permissions.compose

import androidx.compose.runtime.Composable
import com.fraggeil.ticketator.core.domain.moko_permission.permissions.PermissionsController

@Suppress("FunctionNaming")
@Composable
expect fun BindEffect(permissionsController: PermissionsController)

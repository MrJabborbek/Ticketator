/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.fraggeil.ticketator.core.domain.moko_permission.permissions

import com.fraggeil.ticketator.core.domain.moko_permission.permissions.Permission

class RequestCanceledException(
    val permission: Permission,
    message: String? = null
) : Exception(message)

/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.fraggeil.ticketator.core.domain.moko_permission.permissions.ios

import com.fraggeil.ticketator.core.domain.moko_permission.permissions.DeniedAlwaysException
import com.fraggeil.ticketator.core.domain.moko_permission.permissions.Permission
import com.fraggeil.ticketator.core.domain.moko_permission.permissions.PermissionState
import platform.Contacts.CNAuthorizationStatus
import platform.Contacts.CNAuthorizationStatusAuthorized
import platform.Contacts.CNAuthorizationStatusDenied
import platform.Contacts.CNAuthorizationStatusNotDetermined
import platform.Contacts.CNAuthorizationStatusRestricted
import platform.Contacts.CNContactStore
import platform.Contacts.CNEntityType
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class ContactsPermissionDelegate(
    private val permission: Permission,
    private val contactStore: CNContactStore
) : PermissionDelegate {
    override suspend fun providePermission() {
        return provideLocationPermission(
            CNContactStore.authorizationStatusForEntityType(
                CNEntityType.CNEntityTypeContacts
            )
        )
    }

    override suspend fun getPermissionState(): PermissionState {
        val status: CNAuthorizationStatus =
            CNContactStore.authorizationStatusForEntityType(CNEntityType.CNEntityTypeContacts)
        return when (status) {
            CNAuthorizationStatusAuthorized -> PermissionState.Granted

            CNAuthorizationStatusNotDetermined -> PermissionState.NotDetermined
            CNAuthorizationStatusDenied, CNAuthorizationStatusRestricted -> PermissionState.DeniedAlways
            else -> error("unknown contacts $status")
        }
    }

    private suspend fun provideLocationPermission(
        status: CNAuthorizationStatus
    ) {
        when (status) {
            CNAuthorizationStatusAuthorized,
            CNAuthorizationStatusRestricted -> return

            CNAuthorizationStatusNotDetermined -> {
                //  用户未选择权限，发起权限申请
                val newStatus = suspendCoroutine<CNAuthorizationStatus> { continuation ->
                    contactStore.requestAccessForEntityType(CNEntityType.CNEntityTypeContacts) { flag, error ->
                        continuation.resume(
                            CNContactStore.authorizationStatusForEntityType(
                                CNEntityType.CNEntityTypeContacts
                            )
                        )
                    }
                }
                provideLocationPermission(newStatus)
            }

            CNAuthorizationStatusDenied -> throw DeniedAlwaysException(permission)
            else -> error("unknown location authorization status $status")
        }
    }
}

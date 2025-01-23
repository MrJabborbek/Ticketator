package com.fraggeil.ticketator.core.domain.permission


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.AVFoundation.AVAuthorizationStatus
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVAuthorizationStatusDenied
import platform.AVFoundation.AVAuthorizationStatusNotDetermined
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.authorizationStatusForMediaType
import platform.AVFoundation.requestAccessForMediaType
import platform.Foundation.NSURL
import platform.Photos.PHAuthorizationStatus
import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHAuthorizationStatusDenied
import platform.Photos.PHAuthorizationStatusNotDetermined
import platform.Photos.PHPhotoLibrary
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusDenied
import platform.CoreLocation.kCLAuthorizationStatusNotDetermined
import platform.UserNotifications.UNAuthorizationStatusAuthorized
import platform.UserNotifications.UNAuthorizationStatusDenied
import platform.UserNotifications.UNAuthorizationStatusNotDetermined
import platform.CoreLocation.CLLocationManager
import platform.UserNotifications.UNUserNotificationCenter


@Composable
actual fun createPermissionsManager(callback: PermissionCallback): PermissionsManager {
    return PermissionsManager(callback)
}

actual class PermissionsManager actual constructor(private val callback: PermissionCallback) :
    PermissionHandler {
    @Composable
    override fun askPermission(permission: PermissionType) {
        when (permission) {
            PermissionType.CAMERA -> {
                val status: AVAuthorizationStatus =
                    remember { AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo) }
                askCameraPermission(status, permission, callback)
            }

            PermissionType.GALLERY -> {
                val status: PHAuthorizationStatus =
                    remember { PHPhotoLibrary.authorizationStatus() }
                askGalleryPermission(status, permission, callback)
            }

            PermissionType.LOCATION -> {
                askLocationPermission(callback, permission)
            }
            PermissionType.NOTIFICATION -> {
                askNotificationPermission(callback, permission)
            }
        }
    }

    private fun askCameraPermission(
        status: AVAuthorizationStatus, permission: PermissionType, callback: PermissionCallback
    ) {
        when (status) {
            AVAuthorizationStatusAuthorized -> {
                callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
            }

            AVAuthorizationStatusNotDetermined -> {
                return AVCaptureDevice.Companion.requestAccessForMediaType(AVMediaTypeVideo) { isGranted ->
                    if (isGranted) {
                        callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
                    } else {
                        callback.onPermissionStatus(permission, PermissionStatus.DENIED)
                    }
                }
            }

            AVAuthorizationStatusDenied -> {
                callback.onPermissionStatus(permission, PermissionStatus.DENIED)
            }

            else -> error("unknown camera status $status")
        }
    }

    private fun askGalleryPermission(
        status: PHAuthorizationStatus, permission: PermissionType, callback: PermissionCallback
    ) {
        when (status) {
            PHAuthorizationStatusAuthorized -> {
                callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
            }

            PHAuthorizationStatusNotDetermined -> {
                PHPhotoLibrary.Companion.requestAuthorization { newStatus ->
                    askGalleryPermission(newStatus, permission, callback)
                }
            }

            PHAuthorizationStatusDenied -> {
                callback.onPermissionStatus(
                    permission, PermissionStatus.DENIED
                )
            }

            else -> error("unknown gallery status $status")
        }
    }

    private fun askLocationPermission(
        callback: PermissionCallback, permission: PermissionType
    ) {
        val locationManager = CLLocationManager()
        val status = CLLocationManager.authorizationStatus()

        when (status) {
            kCLAuthorizationStatusAuthorizedAlways, kCLAuthorizationStatusAuthorizedWhenInUse -> {
                callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
            }
            kCLAuthorizationStatusNotDetermined -> {
                locationManager.requestWhenInUseAuthorization()
                // You'll need to handle the callback for this in your app delegate or via a listener
            }
            kCLAuthorizationStatusDenied -> {
                callback.onPermissionStatus(permission, PermissionStatus.DENIED)
            }
            else -> error("unknown location status $status")
        }
    }

    private fun askNotificationPermission(
        callback: PermissionCallback, permission: PermissionType
    ) {
        val center = UNUserNotificationCenter.currentNotificationCenter()
        center.getNotificationSettingsWithCompletionHandler { settings ->
            when (settings?.authorizationStatus) {
                UNAuthorizationStatusAuthorized -> {
                    callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
                }
                UNAuthorizationStatusNotDetermined -> {
//                    center.requestAuthorizationWithOptions(
//                        UNAuthorizationOptions(
//                            (UNAuthorizationOptionAlert or
//                                    UNAuthorizationOptionSound or
//                                    UNAuthorizationOptionBadge).toLong()
//                        )
//                    ) { granted, _ ->
//                        if (granted) {
//                            callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
//                        } else {
//                            callback.onPermissionStatus(permission, PermissionStatus.DENIED)
//                        }
//                    }
                }
                UNAuthorizationStatusDenied -> {
                    callback.onPermissionStatus(permission, PermissionStatus.DENIED)
                }
                else -> error("unknown notification status ${settings?.authorizationStatus}")
            }
        }
    }

    @Composable
    override fun isPermissionGranted(permission: PermissionType): Boolean {
        return when (permission) {
            PermissionType.CAMERA -> {
                val status: AVAuthorizationStatus =
                    remember { AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo) }
                status == AVAuthorizationStatusAuthorized
            }

            PermissionType.GALLERY -> {
                val status: PHAuthorizationStatus =
                    remember { PHPhotoLibrary.authorizationStatus() }
                status == PHAuthorizationStatusAuthorized
            }

            PermissionType.LOCATION -> {
                val status = remember { CLLocationManager.authorizationStatus() }
                status == kCLAuthorizationStatusAuthorizedAlways || status == kCLAuthorizationStatusAuthorizedWhenInUse
            }
            PermissionType.NOTIFICATION -> {
                val center = remember { UNUserNotificationCenter.currentNotificationCenter() }
                var isGranted = false
                center.getNotificationSettingsWithCompletionHandler { settings ->
                    isGranted = settings?.authorizationStatus == UNAuthorizationStatusAuthorized
                }
                isGranted
            }
        }
    }

    @Composable
    override fun launchSettings() {
        NSURL.URLWithString(UIApplicationOpenSettingsURLString)?.let {
            UIApplication.sharedApplication.openURL(it)
        }
    }
}
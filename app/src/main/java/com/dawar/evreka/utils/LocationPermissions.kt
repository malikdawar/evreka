package com.dawar.evreka.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dawar.evreka.App
/**
 * @author Malik Dawar, malikdawar332@gmail.com
 */
object LocationPermissions {

    fun checkLocationPermissions(
        activity: Activity,
        locationPermission: LocationPermissionCallBack
    ) {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (ContextCompat.checkSelfPermission(
                App.getAppContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                App.getAppContext(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermission.onLocationPermissionChangeListener(true)

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                ActivityCompat.requestPermissions(
                    activity,
                    permissions,
                    Const.REQUEST_CODE_LOCATION
                )
            } else {
                ActivityCompat.requestPermissions(
                    activity,
                    permissions,
                    Const.REQUEST_CODE_LOCATION
                )
            }
        }
    }

    interface LocationPermissionCallBack {
        fun onLocationPermissionChangeListener(permission: Boolean)
    }
}
package com.dawar.evreka.base

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import com.dawar.evreka.R
import com.dawar.evreka.activities.MainActivity
import com.dawar.evreka.extensions.showToastMsg
import com.dawar.evreka.preference.PrefManager
import com.dawar.evreka.utils.Const
import com.dawar.evreka.utils.DialogUtils
import com.dawar.evreka.utils.LocationPermissions
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.kaopiz.kprogresshud.KProgressHUD
import org.koin.java.KoinJavaComponent
/**
 * @author Malik Dawar, malikdawar332@gmail.com
 */
abstract class BaseFragment : Fragment(), LocationPermissions.LocationPermissionCallBack {

    protected val prefManager: PrefManager by KoinJavaComponent.inject(
        PrefManager::class.java
    )
    protected lateinit var mainActivity: MainActivity
    protected lateinit var progressDialog: KProgressHUD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        LocationPermissions.checkLocationPermissions(mainActivity, this)
        progressDialog = DialogUtils.showProgressDialog(mainActivity, cancelable = false)
    }

    protected val latestLatLng: LatLng by lazy {
        LatLng(
            prefManager.getDouble(Const.TAG_USER_LAT, 0.0)!!,
            prefManager.getDouble(Const.TAG_USER_LANG, 0.0)!!
        )
    }

    var locationCallback: LocationCallback? = null
    private var locationRequest: LocationRequest? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var hasLocationPermissions: Boolean = false
    private var currentLocation: LatLng? = null

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == Const.REQUEST_CODE_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                mainActivity.showToastMsg(getString(R.string.location_permission_required))
            } else {
                hasLocationPermissions = true
                buildLocationCallBack()
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private fun createLocationRequest() {
        locationRequest?.run { } ?: run {
            locationRequest = LocationRequest()
            locationRequest?.interval = Const.UPDATE_INTERVAL_IN_MILLISECONDS
            locationRequest?.fastestInterval = Const.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
            locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest?.smallestDisplacement = 10f
        }
        buildLocationCallBack()
    }

    override fun onLocationPermissionChangeListener(permission: Boolean) {
        if (permission) {
            hasLocationPermissions = true
            fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(mainActivity)
            createLocationRequest()

            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    protected open fun buildLocationCallBack() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    currentLocation = LatLng(location.latitude, location.longitude)
                }
            }
        }
    }

    private fun removeLocationUpdate() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

}
package com.dawar.evreka.fragments

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dawar.evreka.R
import com.dawar.evreka.base.BaseFragment
import com.dawar.evreka.extensions.drawMarker
import com.dawar.evreka.extensions.moveMapsCamera
import com.dawar.evreka.extensions.showToastMsg
import com.dawar.evreka.models.ContainerDao
import com.dawar.evreka.utils.Const.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
import com.dawar.evreka.utils.Const.LANDING_LAT_LNG
import com.dawar.evreka.utils.Const.REQUEST_CODE_LOCATION
import com.dawar.evreka.utils.Const.TAG_USER_LANG
import com.dawar.evreka.utils.Const.TAG_USER_LAT
import com.dawar.evreka.utils.Const.UPDATE_INTERVAL_IN_MILLISECONDS
import com.dawar.evreka.viewmodels.OperationViewModel
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

class OperationFragment : BaseFragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    OperationViewModel.View {

    private var googleMap: GoogleMap? = null
    private var locationCallback: LocationCallback? = null
    private var locationRequest: LocationRequest? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var hasLocationPermissions: Boolean = false
    private val operationViewModel: OperationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_operations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        operationViewModel.let {
            it.addObserver(this)
            it.attachView(this)
        }

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(mMap: GoogleMap?) {
        googleMap = mMap
        googleMap?.run {
            val landingLocation = if (latestLatLng != LatLng(0.0, 0.0))
                latestLatLng else LANDING_LAT_LNG
            moveMapsCamera(animate = true, latLng = landingLocation, zoom = 14.5f)
            setOnMarkerClickListener(this@OperationFragment)
        }

        mainActivity.invalidateOptionsMenu()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_LOCATION) {
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
            locationRequest?.interval = UPDATE_INTERVAL_IN_MILLISECONDS
            locationRequest?.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
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

    private fun buildLocationCallBack() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {

                for (location in locationResult.locations) {
                    val latLng = LatLng(location.latitude, location.longitude)
                    prefManager.saveDouble(TAG_USER_LAT, latLng.latitude)
                    prefManager.saveDouble(TAG_USER_LANG, latLng.longitude)

                    googleMap?.run {
                        clear()
                        drawMarker(
                            latLng,
                            R.drawable.ic_current_location
                        )
                    }
                }
            }
        }
    }

    private fun removeLocationUpdate() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    override fun onMarkerClick(p0: Marker?): Boolean {

        return true
    }

    override fun onDetailsUpdateError(error: String) {
        mainActivity.showToastMsg(error)
    }

    override fun onContainers(containers: MutableList<ContainerDao>) {
        googleMap?.run {
            containers.forEach {
                run {
                    drawMarker(
                        location = LatLng(it.latitude, it.longitude),
                        resDrawable = R.drawable.ic_map_pin,
                        tagObject = it
                    )
                }
            }
        }
    }

    override fun onProgress() {

    }

    override fun onProgressDismiss() {

    }

    override fun onResume() {
        super.onResume()
        operationViewModel.getContainers()
    }
}

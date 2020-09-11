package com.dawar.evreka.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dawar.evreka.R
import com.dawar.evreka.base.BaseFragment
import com.dawar.evreka.extensions.drawMarker
import com.dawar.evreka.extensions.moveMapsCamera
import com.dawar.evreka.extensions.replaceFragment
import com.dawar.evreka.extensions.showToastMsg
import com.dawar.evreka.models.ContainerDao
import com.dawar.evreka.viewmodels.RelocateViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_relocation.*
/**
 * @author Malik Dawar, malikdawar332@gmail.com
 */

class RelocateFragment(var containerDao: ContainerDao) : BaseFragment(), OnMapReadyCallback,
    RelocateViewModel.View {

    private var googleMap: GoogleMap? = null
    private val relocateViewModel: RelocateViewModel by viewModels()
    private var markerPosition: LatLng = LatLng(containerDao.latitude, containerDao.longitude)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_relocation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        relocateViewModel.let { viewModel ->
            viewModel.addObserver(this)
            viewModel.attachView(this)
        }

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)

        btnSave.setOnClickListener {
            relocateViewModel.updateContainer(containerDao, markerPosition)
        }
    }

    override fun onMapReady(mMap: GoogleMap?) {
        googleMap = mMap
        googleMap?.run {
            moveMapsCamera(animate = true, latLng = markerPosition, zoom = 14.5f)
            drawMarker(
                location = markerPosition,
                resDrawable = R.drawable.ic_map_pin_yellow
            )

            setOnMapClickListener {
                clear()
                markerPosition = it
                moveMapsCamera(animate = true, latLng = it, zoom = 14.5f)
                drawMarker(
                    location = it,
                    resDrawable = R.drawable.ic_map_pin_yellow
                )
            }
        }
        mainActivity.invalidateOptionsMenu()
    }

    override fun onUpdateSuccess() {
        replaceFragment(OperationFragment(), addToBackStack = false)
    }

    override fun onProgress() {
        progressDialog.show()
    }

    override fun onProgressDismiss() {
        progressDialog.dismiss()
    }

    override fun onDetailsUpdateError(error: String) {
        mainActivity.showToastMsg(error)
    }

    override fun buildLocationCallBack() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    val currentLocation = LatLng(location.latitude, location.longitude)
                    relocateViewModel.userLocationUpdater(googleMap, currentLocation)
                }
            }
        }
    }
}

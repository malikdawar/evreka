package com.dawar.evreka.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dawar.evreka.R
import com.dawar.evreka.base.BaseFragment
import com.dawar.evreka.extensions.*
import com.dawar.evreka.models.ContainerDao
import com.dawar.evreka.utils.Const.LANDING_LAT_LNG
import com.dawar.evreka.viewmodels.OperationViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.fragment_operations.*

class OperationFragment : BaseFragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    OperationViewModel.View, View.OnClickListener {

    private var googleMap: GoogleMap? = null
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

        operationViewModel.let { viewModel ->
            viewModel.addObserver(this)
            viewModel.attachView(this)
        }

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)

        btnNavigate.setOnClickListener(this)
        btnRelocate.setOnClickListener(this)
    }

    override fun onMapReady(mMap: GoogleMap?) {
        googleMap = mMap

        googleMap?.run {
            val landingLocation = if (latestLatLng != LatLng(0.0, 0.0))
                latestLatLng else LANDING_LAT_LNG
            moveMapsCamera(animate = true, latLng = landingLocation, zoom = 14.5f)
            setOnMarkerClickListener(this@OperationFragment)
        }

        operationViewModel.getContainers()

        mainActivity.invalidateOptionsMenu()
    }

    override fun buildLocationCallBack() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    val latLng = LatLng(location.latitude, location.longitude)
                    operationViewModel.userLocationUpdater(googleMap, latLng)
                }
            }
        }
    }

    override fun onContainers(containers: MutableList<ContainerDao>) {
        operationViewModel.populateMap(googleMap, containers)
    }

    override fun onProgress() {
        progressDialog.show()
    }

    override fun onProgressDismiss() {
        progressDialog.dismiss()
    }

    var selectedContainerDao: ContainerDao? = null
    @SuppressLint("SetTextI18n")
    override fun onMarkerClick(p0: Marker?): Boolean {
        selectedContainerDao = p0?.tag as ContainerDao

        if (cardDetails.visibility == View.VISIBLE) {
            updateMarkerColor(p0)
            cardDetails.gone()
        } else {
            updateMarkerColor(p0, R.drawable.ic_map_pin_yellow)
            cardDetails.visible()
        }
        selectedContainerDao?.apply {
            tvContainerId.text = "Container ${id}(H3)"
            tvNextCollection.text = "Next Collection(${collection})"
            tvCollectionDate.text = collectionDate
            tvFullnessRate.text = "%${rate}"
        }
        return true
    }

    private fun updateMarkerColor(marker: Marker?, drawable: Int = R.drawable.ic_map_pin) {
        marker?.remove()
        googleMap?.drawMarker(
            location = LatLng(selectedContainerDao?.latitude!!, selectedContainerDao?.longitude!!),
            resDrawable = drawable,
            tagObject = selectedContainerDao
        )
    }

    override fun onDetailsUpdateError(error: String) {
        mainActivity.showToastMsg(error)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnNavigate -> {
                val navigationIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        "google.navigation:q=${selectedContainerDao?.latitude}," +
                                "${selectedContainerDao?.longitude}"
                    )
                )
                startActivity(navigationIntent)
            }

            R.id.btnRelocate -> {
                replaceFragment(
                    fragment = RelocateFragment(selectedContainerDao!!),
                    addToBackStack = false
                )
            }
        }
    }
}

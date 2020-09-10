package com.dawar.evreka.extensions

import androidx.core.content.ContextCompat
import com.bilty.driver.utils.UtilityFunctions.getMarkerIconFromDrawable
import com.dawar.evreka.App
import com.dawar.evreka.models.ContainerDao
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*

fun GoogleMap.drawMarker(
    location: LatLng?,
    resDrawable: Int,
    title: String? = null,
    tagObject : ContainerDao? = null
): Marker {
    val circleDrawable = ContextCompat.getDrawable(App.getAppContext(), resDrawable)
    val markerIcon = getMarkerIconFromDrawable(circleDrawable!!)

    val marker = addMarker(
        MarkerOptions()
            .position(location!!)
            .title(title)
            .icon(markerIcon)
    )
    tagObject?.run {
        marker.tag = this
    }
    return marker
}

fun GoogleMap.moveMapsCamera(
    zoom: Float = 15.5f,
    animate: Boolean = true,
    latLng: LatLng
) {
    if (animate) {
        this.animateCamera(
            CameraUpdateFactory.newCameraPosition(
                CameraPosition.Builder().target(LatLng(latLng.latitude, latLng.longitude)).zoom(
                    zoom
                ).build()
            )
        )
    } else {
        this.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
        this.animateCamera(
            CameraUpdateFactory.zoomTo(zoom)
        )
    }
}

fun GoogleMap.boundMarkersOnMap(
    latLng: ArrayList<LatLng>
) {
    val builder = LatLngBounds.Builder()
    for (marker in latLng) {
        builder.include(marker)
    }
    val bounds = builder.build()
    val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 425)
    this.moveCamera(cameraUpdate)
}

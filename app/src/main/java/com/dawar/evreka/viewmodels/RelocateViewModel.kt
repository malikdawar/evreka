package com.dawar.evreka.viewmodels

import androidx.lifecycle.LifecycleOwner
import com.dawar.evreka.R
import com.dawar.evreka.base.BaseViewModel
import com.dawar.evreka.extensions.drawMarker
import com.dawar.evreka.extensions.getString
import com.dawar.evreka.models.ContainerDao
import com.dawar.evreka.repository.Repository
import com.dawar.evreka.utils.Const
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import org.koin.java.KoinJavaComponent

class RelocateViewModel : BaseViewModel<RelocateViewModel.View>() {

    private var lifecycleOwner: LifecycleOwner? = null
    private val repository: Repository by KoinJavaComponent.inject(
        Repository::class.java
    )

    fun addObserver(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
    }

    private var userLocationMarkerHashMap: HashMap<String, Marker> = HashMap()
    fun userLocationUpdater(googleMap: GoogleMap?, latLng: LatLng) {
        userLocationMarkerHashMap[Const.LOCATION_MARKER]?.run {
            remove()
            userLocationMarkerHashMap.clear()
        }

        googleMap?.run {
            userLocationMarkerHashMap.put(
                Const.LOCATION_MARKER, drawMarker(
                    latLng,
                    R.drawable.ic_current_location
                )
            )
        }
    }

    fun updateContainer(containerDao: ContainerDao, updateLocation: LatLng) {
        /*getView().onProgress()*/
        containerDao.apply {
            val updatedContainerDao = ContainerDao(
                id = id,
                rate = rate,
                collection = collection,
                collectionDate = collectionDate,
                latitude = updateLocation.latitude,
                longitude = updateLocation.longitude
            )

            repository.saveContainerInFireBase(updatedContainerDao) {
                /*getView().onProgressDismiss()*/
                if (it)
                    getView().onUpdateSuccess()
                else
                    getView().onDetailsUpdateError(getString(R.string.someThingWentWrong))
            }
        }
    }

    interface View {
        fun onDetailsUpdateError(error: String)
        fun onUpdateSuccess()
        /*fun onProgress()
        fun onProgressDismiss()*/
    }
}
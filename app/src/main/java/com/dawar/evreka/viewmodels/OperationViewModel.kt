package com.dawar.evreka.viewmodels

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.dawar.evreka.R
import com.dawar.evreka.base.BaseViewModel
import com.dawar.evreka.extensions.drawMarker
import com.dawar.evreka.extensions.getString
import com.dawar.evreka.extensions.moveMapsCamera
import com.dawar.evreka.models.ContainerDao
import com.dawar.evreka.repository.Repository
import com.dawar.evreka.utils.Const
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import org.koin.java.KoinJavaComponent
import kotlin.random.Random
/**
 * @author Malik Dawar, malikdawar332@gmail.com
 */
class OperationViewModel : BaseViewModel<OperationViewModel.View>() {

    private var lifecycleOwner: LifecycleOwner? = null
    private val TAG = OperationViewModel::class.java.simpleName

    private val repository: Repository by KoinJavaComponent.inject(
        Repository::class.java
    )

    fun addObserver(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
    }

    fun getContainers() {
        getView().onProgress()
        repository.getContainersFromFireBase {
            it?.run {
                getView().onContainers(this)
            } ?: run {
                getView().onDetailsUpdateError(getString(R.string.someThingWentWrong))
            }
            getView().onProgressDismiss()
        }
    }

    fun saveInFireBase(iteration: Int) {
        //used this dynamic method to populate FireBase, called in a repeat method
        val containerDao = ContainerDao(
            id = 1000 + iteration,
            rate = Random.nextInt(0, 100),
            collectionDate = "${Random.nextInt(0, 30)}.12.2020(T1)",
            collection = "H${Random.nextInt(0, 100)}",
            latitude = 39.6883 + Random.nextDouble(0.0, 0.2),
            longitude = 32.624803 + Random.nextDouble(0.0, 0.2)
        )

        repository.saveContainerInFireBase(containerDao) {
            Log.d(TAG, "DataSaved: $it")
        }
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

    private var containerList: MutableList<ContainerDao> = ArrayList()
    private var latLngList: ArrayList<LatLng> = ArrayList()

    fun populateMap(googleMap: GoogleMap?, containers: MutableList<ContainerDao>) {
        this.containerList = containers
        containerList.forEachIndexed { index, it ->
            val latLng = LatLng(it.latitude, it.longitude)
            latLngList.add(latLng)
            googleMap!!.drawMarker(
                location = latLng,
                resDrawable = R.drawable.ic_map_pin,
                tagObject = it
            )

            if (index == containerList.size - 1) {
                googleMap.run {
                    moveMapsCamera(animate = true, latLng = latLng, zoom = 14.6f)
                    //boundMarkersOnMap(latLngList) // uncomment if map bound is required
                }
            }
        }
    }

    interface View {
        fun onDetailsUpdateError(error: String)
        fun onContainers(containers: MutableList<ContainerDao>)
        fun onProgress()
        fun onProgressDismiss()
    }
}
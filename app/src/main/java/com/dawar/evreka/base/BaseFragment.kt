package com.dawar.evreka.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dawar.evreka.activities.MainActivity
import com.dawar.evreka.preference.PrefManager
import com.dawar.evreka.utils.Const
import com.dawar.evreka.utils.LocationPermissions
import com.google.android.gms.maps.model.LatLng
import org.koin.java.KoinJavaComponent

abstract class BaseFragment : Fragment(), LocationPermissions.LocationPermissionCallBack {

    protected val prefManager: PrefManager by KoinJavaComponent.inject(
        PrefManager::class.java
    )
    protected lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        LocationPermissions.checkLocationPermissions(mainActivity, this)
    }

    protected val latestLatLng: LatLng by lazy {
        LatLng(
            prefManager.getDouble(Const.TAG_USER_LAT, 0.0)!!,
            prefManager.getDouble(Const.TAG_USER_LANG, 0.0)!!
        )
    }

    override fun onLocationPermissionChangeListener(permission: Boolean){

    }
}
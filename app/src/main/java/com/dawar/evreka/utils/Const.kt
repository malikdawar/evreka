package com.dawar.evreka.utils

import com.google.android.gms.maps.model.LatLng
import java.util.concurrent.TimeUnit


object Const {
    const val USER_SESSION = "userSession"
    const val REQUEST_CODE_LOCATION = 1010
    val LANDING_LAT_LNG = LatLng(31.5019032, 74.3000874)
    val UPDATE_INTERVAL_IN_MILLISECONDS: Long = TimeUnit.SECONDS.toMillis(8)
    val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2

    const val TAG_USER_LAT = "user_lat"
    const val TAG_USER_LANG = "user_lng"
}
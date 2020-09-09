package com.dawar.evreka.models

import com.google.gson.annotations.SerializedName

data class FireBasePayload(
    @SerializedName("latitude")
    var g: Double = 0.0,
    @SerializedName("longitude")
    var t: Double = 0.0,
    @SerializedName("isAvailable")
    var a: Boolean = false
)


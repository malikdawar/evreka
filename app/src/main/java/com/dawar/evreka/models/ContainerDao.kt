package com.dawar.evreka.models

import com.google.gson.annotations.SerializedName

data class ContainerDao(
    @SerializedName("latitude")
    var g: Double = 0.0,
    @SerializedName("longitude")
    var t: Double = 0.0,
    @SerializedName("isAvailable")
    var a: Boolean = false
)


package com.dawar.evreka.models

import com.google.gson.annotations.SerializedName

data class ContainerDao(
    @SerializedName("id")
    var id: String,
    @SerializedName("fullness_rate")
    var rate: String,
    @SerializedName("next_collection")
    var collection: String,
    @SerializedName("latitude")
    var latitude: Double = 0.0,
    @SerializedName("longitude")
    var longitude: Double = 0.0
)

package com.dawar.evreka.models

import com.google.gson.annotations.SerializedName

data class ContainerDao(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("fullness_rate")
    var rate: Int = 0,
    @SerializedName("next_collection")
    var collection: String? = null,
    @SerializedName("collectionDate")
    var collectionDate: String? = null,
    @SerializedName("latitude")
    var latitude: Double = 0.0,
    @SerializedName("longitude")
    var longitude: Double = 0.0
)

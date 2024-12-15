package com.example.noorointerview.model.response

import com.google.gson.annotations.SerializedName

data class LocationSearchResponse(
    @SerializedName("location")
    val location: Location? = null,
    @SerializedName("current")
    val current: Current? = null,
)

data class Location(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("region")
    val region: String? = null,
    @SerializedName("country")
    val country: String? = null,
    @SerializedName("localtime_epoch")
    val localTimeEpoch: Int? = null,
)

data class Current(
    @SerializedName("last_updated_epoch")
    val lastUpdatedEpoch: Int? = null,
    @SerializedName("condition")
    val condition: Condition? = null,
    @SerializedName("temp_c")
    val tempC: Double? = null,
    @SerializedName("temp_f")
    val tempF: Double? = null,
    @SerializedName("humidity")
    val humidity: Int? = null,
    @SerializedName("uv")
    val uv: Double? = null,
    @SerializedName("feelslike_c")
    val feelsLikeC: Double? = null,
    @SerializedName("feelslike_f")
    val feelsLikeF: Double? = null,
)

data class Condition(
    @SerializedName("text")
    val text: String? = null,
    @SerializedName("icon")
    val icon: String? = null,
) {
    fun getImageUrl(): String = "$icon".replace("//", "https://")
}
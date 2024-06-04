package com.abora.perfectobase.data.models

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    var errors: Any? = null,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<AddressDataModel>
)

data class AddAddressResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    var errors: Any? = null,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: AddressDataModel
)

data class AddressDataModel(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("lat") val lat: String? = null,
    @SerializedName("lng") var lng: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("city_id") var city_id: Int? = null,
    @SerializedName("active") var active: Int? = null,
    @SerializedName("region_id") var region_id: Int? = null,
    @SerializedName("district_id") var district_id: Int? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("address") val address: String? = null,
    @SerializedName("distance") val distance: String? = null,
    @SerializedName("user_distance") val user_distance: String? = null,
    @SerializedName("work_hours") val work_hours: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("code") val code: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("city") val city: CityDataModel? = null,
    @SerializedName("district") val district: CityDataModel? = null,
    @SerializedName("region") val region: CityDataModel? = null,
    var selected: Boolean? = false
)
data class MapData (val latLng: LatLng, val id:String, val status:String)
package com.abora.perfectobase.data.models

import com.google.gson.annotations.SerializedName
import com.olar.data.models.MediaDataModel

data class SearchResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    var errors: Any? = null,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: SearchDataModel
)

data class SearchMapResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    var errors: Any? = null,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<PropertyDataModel>
)

data class SearchDataModel(

    @SerializedName("search") val search: SearchData,
    @SerializedName("search_result") val search_result: List<PropertyDataModel>,
    @SerializedName("others") val others: List<PropertyDataModel>
)

data class SearchData(
    @SerializedName("destination") val destination: String,
    @SerializedName("check_in") val checkIn: String,
    @SerializedName("check_out") val checkOut: String
)

data class PropertyDataModel(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val name: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double,
    @SerializedName("price") val price: String,
    @SerializedName("media") val media: List<MediaDataModel>,
    @SerializedName("is_support_self_check_in") val is_support_self_check_in: Int,
    @SerializedName("price_after_discount") var priceAfterDiscount: Double,
    @SerializedName("city") val city: String,
    @SerializedName("rate") val reviews: Double,
    @SerializedName("reviews_count") val reviews_count: String,
    @SerializedName("image") val image: String,
    @SerializedName("per") val per: String,
    @SerializedName("is_favourite") var is_favourite: Int,
    var isLoading: Boolean = false,
    var position: Int = 0
)
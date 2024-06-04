package com.olar.data.models

import com.abora.perfectobase.data.models.CategoryDataModel
import com.abora.perfectobase.data.models.LoginDataModel
import com.google.gson.annotations.SerializedName

data class PropertyDetailsResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    var errors: Any? = null,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: PropertyDetailsDataModel
)


data class PropertyDetailsDataModel(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val name: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double,
    @SerializedName("price") val price: Double,
    @SerializedName("price_after_discount") val priceAfterDiscount: Double,
    @SerializedName("city") val city: String,
    @SerializedName("city_ar") val city_ar: String,
    @SerializedName("city_en") val city_en: String,
    @SerializedName("share_link") val share_link: String? = null,
    @SerializedName("reservation_type") val reservationType: String,
    @SerializedName("rate") val rate: String,
    @SerializedName("reviews_count") val reviews_count: Int,
    @SerializedName("image") val image: String,
    @SerializedName("per") val per: String,
    @SerializedName("is_favourite") var is_favourite: Int,
    @SerializedName("is_support_self_check_in") var is_support_self_check_in: Int,
    @SerializedName("media") val media: List<MediaDataModel>,
    @SerializedName("services_grouped") val services_grouped: FilterServiceData,
    @SerializedName("services") val services: List<FilterServiceDataModel>,
    @SerializedName("services_count") val services_count: Int,
    @SerializedName("user") val user: LoginDataModel,
    @SerializedName("reservation_types") val reservationTypes: List<ReservationTypeDataModel>,
    @SerializedName("reviews") val reviews: List<ReviewDataModel>,
    @SerializedName("categories") val categories: CategoryDataModel,
    @SerializedName("place_type") val place_type: FilterServiceDataModel,
    @SerializedName("rooms_count") val rooms_count: Int,
    @SerializedName("total_guests_count") val total_guests_count: Int,
    @SerializedName("total_children_count") val total_children_count: Int,
    @SerializedName("total_beds_count") val total_beds_count: Int,
    @SerializedName("total_bathrooms_count") val total_bathrooms_count: Int,
    @SerializedName("total_units_count") val total_units_count: Int,
    @SerializedName("show_phone") val show_phone: Int,
    @SerializedName("show_property_location") val show_property_location: Int,
    @SerializedName("default_price_rate") val default_price_rate: String,
    @SerializedName("check_in") val check_in: String,
    @SerializedName("description") val description : String,
    @SerializedName("check_out") val check_out: String
)

data class MediaDataModel(
    @SerializedName("id") val id: Int,
    @SerializedName("image") val image: String
)

data class ReservationTypeDataModel(
    @SerializedName("id") val id: Int,
    @SerializedName("type") val type: String,
    var selected:Boolean? = false
)

data class ReviewDataModel(
    @SerializedName("id") val id : Int,
    @SerializedName("rate") val rate : String,
    @SerializedName("description") val description : String,
    @SerializedName("created_at") val createdAt : String,
    @SerializedName("user") val user : LoginDataModel,
    @SerializedName("media") val media : List<MediaDataModel>
)

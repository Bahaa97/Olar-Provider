package com.olar.data.models

import com.google.gson.annotations.SerializedName


data class PropertyReservationDatesResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    var errors: Any? = null,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: PropertyReservationDateModel
)
data class ReservationPricesResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    var errors: Any? = null,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: ReservationPricesDataModel
)


data class PropertyReservationDateModel(
    @SerializedName("fully_reserved") val fullyReserved: List<PropertyReservationDateDataModel>,
    @SerializedName("not_fully_reserved") val notFullyReserved: List<PropertyReservationDateDataModel>,
)

data class PropertyReservationDateDataModel(
    @SerializedName("units_left") val unitsLeft: Int,
    @SerializedName("date") val date: String,
)


data class PropertyReservationHoursResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    var errors: Any? = null,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<PropertyReservationHoursDataModel>
)

data class PropertyReservationHoursDataModel(
    @SerializedName("reserved_units") var reservedUnits: Int,
    @SerializedName("hour") val hour: String,
    var time: String,
    var selected: Boolean? = null,
    var active: Boolean? = null
)

data class ReservationPricesDataModel (
    @SerializedName("price") val price : Double?,
    @SerializedName("price_after_discount") val price_after_discount : Double?,
    @SerializedName("coupon_msg") val coupon_msg : String?,
    @SerializedName("discount_value") val discount_value : Int?,
    @SerializedName("discount_price") val discount_price : Double?,
    @SerializedName("coupon_id") val coupon_id : String? = null,
    @SerializedName("service_fee") val service_fee : Double?,
    @SerializedName("total") val total : String?,
    @SerializedName("per") val per : String?,
    @SerializedName("range_duration") val range_duration : String?
)

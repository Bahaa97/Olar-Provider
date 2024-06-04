package com.abora.perfectobase.data.models

import com.google.gson.annotations.SerializedName

data class PaymentMethodResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    var errors: Any? = null,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<PaymentMethodDataModel>
)

data class PaymentMethodDataModel (
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("is_active") val is_active : Int,
    @SerializedName("image") val image : String,
    @SerializedName("type") val type : String,
    @SerializedName("selected") var selected : Boolean? = false
)


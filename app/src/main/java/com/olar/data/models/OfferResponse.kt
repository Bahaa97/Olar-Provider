package com.abora.perfectobase.data.models

import com.google.gson.annotations.SerializedName

data class OfferResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("errors") val errors: String,
    @SerializedName("message") val message: String,
)
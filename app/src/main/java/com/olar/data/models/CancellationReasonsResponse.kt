package com.abora.perfectobase.data.models

import com.google.gson.annotations.SerializedName

data class CancellationReasonsResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    var errors: Any? = null,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<CancellationReasonsDataModel>
)
data class CancellationReasonsDataModel (
    @SerializedName("id") val id : Int,
    @SerializedName("type") val type : String,
    @SerializedName("is_active") val is_active : Int,
    @SerializedName("title") val title : String,
    var selected : Boolean? = false
)

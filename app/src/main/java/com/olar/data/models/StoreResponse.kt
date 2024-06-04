package com.abora.perfectobase.data.models

import com.google.gson.annotations.SerializedName

data class StoreResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("errors") val errors: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: StoreData
)

data class StoreData(
    @SerializedName("stores") val stores: StorePagination
)

data class StorePagination(
    @SerializedName("total") val total: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("per_page") val per_page: Int,
    @SerializedName("current_page") val current_page: Int,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("last_page") val last_page: Int
)

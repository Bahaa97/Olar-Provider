package com.olar.data.models

import com.google.gson.annotations.SerializedName


data class DatesResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    var errors: Any? = null,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: DatesModel
)
data class DatesModel(
    @SerializedName("days") val days: List<DaysDataModel>,
    @SerializedName("times") val times: List<TimesDataModel>,
)


data class DaysDataModel(
    @SerializedName("day") val day: String,
    @SerializedName("busy") val busy: Int
)

data class TimesDataModel(
    @SerializedName("from") val from: String,
    @SerializedName("to") val to: String,
    @SerializedName("available") val available: Int,
    var selected: Boolean = false
)

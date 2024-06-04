package com.abora.perfectobase.data.models


import com.google.gson.annotations.SerializedName

data class DefaultDataModel(
    var code: Int,
    var status: Int,
    var data: DefaultData,
    var message: String
)

data class DefaultData(
    @SerializedName("user_id")
    var userId: Int,
    @SerializedName("verification_code")
    var verificationCode: Int,
    @SerializedName("count")
    var count: Int,
    var token: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("path")
    var path: String? = "",
    @SerializedName("is_success")
    var is_success: Any,
    @SerializedName("send_push")
    var pushStatus: Int,
    @SerializedName("used_times")
    var usedTimes: Int? = 0,
    @SerializedName("is_verified")
    var is_verified: Boolean,
    @SerializedName("is_favourite")
    var is_favourite: Int,
    @SerializedName("message")
    var message: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("registration_id")
    var registrationId: String,
    @SerializedName("body")
    var body: String,
    var favId: Int,
    var total_pending: Int,
    var total_approved: Int,
    var total_confirmed: Int,
    var reservation_id: Int,
    var payment_type: String
)



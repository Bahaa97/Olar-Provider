package com.abora.perfectobase.data.models

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import com.google.gson.annotations.SerializedName

data class SettingResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    var errors: Any? = null,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: SettingModel
)

data class SettingModel(
    @SerializedName("support") val support: SettingDataModel
)

data class SettingDataModel(
    @SerializedName("email") val email: String? = null,
    @SerializedName("phone_code") var phone_code: String? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("whatsapp") var whatsapp: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null
)
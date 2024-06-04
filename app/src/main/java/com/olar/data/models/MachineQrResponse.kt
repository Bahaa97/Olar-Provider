package com.abora.perfectobase.data.models

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import com.google.gson.annotations.SerializedName

data class MachineQrResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    var errors: Any? = null,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: MachineQrModel
)

data class MachineQrModel(
    @SerializedName("qr_code_image") val qr_code_image: String,
    @SerializedName("qr_code_id") val qr_code_id: String,
)

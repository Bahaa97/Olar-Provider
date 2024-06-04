package com.abora.perfectobase.data.models

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import com.google.gson.annotations.SerializedName

data class CurrencyDataModel(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    var errors: Any? = null,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<CurrencyData>
)

data class CurrencyData(
    @SerializedName("id") val id : Int,
    @SerializedName("currency_code_en") val currency_code_en : String,
    @SerializedName("currency_code_ar") val currency_code_ar : String,
    @SerializedName("currency_name") val currency_name : String,
    @SerializedName("symbol") val symbol : String,
    @SerializedName("image") val image : String,
    @SerializedName("decimal_places") val decimal_places : Int,
    @SerializedName("exchange_rate") val exchange_rate : Double,
    @SerializedName("order") val order : Int,
    @SerializedName("is_active") val is_active : Int,
    @SerializedName("is_default") val is_default : Int,
    @SerializedName("deleted_at") val deleted_at : String,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String
)

package com.abora.perfectobase.data.models

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import com.google.gson.annotations.SerializedName

data class TransactionResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    var errors: Any? = null,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: TransactionData
)

data class TransactionData(
    @SerializedName("transaction_history") val transaction_history: List<TransactionDataModel>? = null,
    @SerializedName("old_fine") val old_fine: TransactionDataModel? = null,
    @SerializedName("wallet") val wallet: String? = null
)
data class TransactionDataModel(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("reservation_id") val reservation_id: Int,
    @SerializedName("property_id") val property_id: Int,
    @SerializedName("transaction_id") val transaction_id: Int,
    @SerializedName("fine_id") val fine_id: Int,
    @SerializedName("type") val type: String,
    @SerializedName("custom_type") val custom_type: String,
    @SerializedName("status") val status: String? = "",
    @SerializedName("amount") val amount: Double,
    @SerializedName("deleted_at") val deleted_at: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("image") val image: String
)
package com.olar.data.models

import com.abora.perfectobase.data.models.CategoryDataModel
import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    var errors: Any? = null,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<ProductDataModel>
)
data class CompleteDataModel(
    @SerializedName("title") val title: String? = null,
    @SerializedName("data") val data: List<ProductDataModel>
)
data class CompleteOrderDataModel(
    @SerializedName("title") val title: String? = null,
    @SerializedName("data") val data: List<OrderProductsDataModel>
)
data class ProductDataModel(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("category_id") var category_id: Int? = null,
    @SerializedName("points") var points: Int? = null,
    @SerializedName("has_note") var has_note: Int? = null,
    @SerializedName("note") var note: String? = null,
    @SerializedName("unit_type") var unitType: Int? = null,
    var count: Int = 0,
    @SerializedName("minimum_qty") var minimum_qty: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("name_ar") val name_ar: String? = null,
    @SerializedName("name_en") val name_en: String? = null,
    @SerializedName("category") val category: CategoryDataModel? = null
)
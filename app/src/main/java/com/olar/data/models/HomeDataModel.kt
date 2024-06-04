package com.abora.perfectobase.data.models

import com.google.gson.annotations.SerializedName

data class HomeDataResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    var errors: Any? = null,
    @SerializedName("message") val message: String,
    @SerializedName("data") val homeData: HomeDataModel
)

data class CategoriesResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    var errors: Any? = null,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<CategoryDataModel>
)


data class VouchersResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    var errors: Any? = null,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<VoucherDataModel>
)
data class VoucherDetailsResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    var errors: Any? = null,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: VoucherDataModel
)


data class CitiesResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    var errors: Any? = null,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<CityDataModel>
)

data class HomeDataModel(
    @SerializedName("drop_off_title") val drop_off_title: String,
    @SerializedName("drop_off_description") val drop_off_description: String,
    @SerializedName("pick_up_title") val pick_up_title: String,
    @SerializedName("pick_up_description") val pick_up_description: String,
    @SerializedName("order_terms_text") val order_terms_text: String,
    @SerializedName("notifications_count") val notifications_count: Int,
    @SerializedName("points") val points: Int,
    @SerializedName("used_vouchers_count") val used_vouchers_count: Int,
    @SerializedName("addresses_count") val addresses_count: Int,
    @SerializedName("orders_count") val orders_count: Int,
    @SerializedName("categories") val categories: List<CategoryDataModel>,
    @SerializedName("sliders") val sliders: List<SliderDataModel>,
    @SerializedName("vouchers") val vouchers: List<VoucherDataModel>
)

data class SliderDataModel(
    @SerializedName("id") val id: Int,
    @SerializedName("order") val order: Int,
    @SerializedName("image") val image: String,
    @SerializedName("action") val action: String,
    @SerializedName("action_id") val action_id: Int,
    @SerializedName("active") val active: Int,
    @SerializedName("value") val value: String,
    @SerializedName("deleted_at") val deleted_at: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String
)

data class CategoryDataModel(
    @SerializedName("id") val id: Int,
    @SerializedName("image") val image: String,
    @SerializedName("name_ar") val name_ar: String,
    @SerializedName("name_en") val name_en: String,
    @SerializedName("active") val active: Int,
    @SerializedName("minimum_qty") val minimum_qty: Int,
    @SerializedName("order") val order: Int,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("name") val name: String,
    @SerializedName("unit_type") val unit_type: String,
    @SerializedName("unit_type_text") val unit_type_text: String,
    var selected: Boolean = false,
)


data class VoucherDataModel(
    @SerializedName("id") val id: Int,
    @SerializedName("image") val image: String,
    @SerializedName("cover") val cover: String,
    @SerializedName("name_ar") val name_ar: String,
    @SerializedName("name_en") val name_en: String,
    @SerializedName("qr_code_image") val qr_code_image: String,
    @SerializedName("description_en") val description_en: String,
    @SerializedName("description_ar") val description_ar: String,
    @SerializedName("points") val points: Int,
    @SerializedName("active") val active: Int,
    @SerializedName("users_count") val users_count: Int,
    @SerializedName("description") val description: String,
    @SerializedName("name") val name: String,
    @SerializedName("use_before") val use_before: Int,
    @SerializedName("branches") val branches: List<BranchesDataModel>

)

data class BranchesDataModel (

    @SerializedName("id") val id : Int,
    @SerializedName("voucher_id") val voucher_id : Int,
    @SerializedName("name_en") val name_en : String,
    @SerializedName("name_ar") val name_ar : String,
    @SerializedName("address_ar") val address_ar : String,
    @SerializedName("address_en") val address_en : String,
    @SerializedName("lat") val lat : Double,
    @SerializedName("lng") val lng : Double,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String,
    @SerializedName("name") val name : String,
    @SerializedName("address") val address : String
)

data class CityDataModel(
    @SerializedName("id") val id: Int,
    @SerializedName("lat") val lat: String,
    @SerializedName("lng") val lng: String,
    @SerializedName("name") val name: String,
    @SerializedName("country_id") val countryId: String
)
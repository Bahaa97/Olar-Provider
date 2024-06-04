package com.olar.data.models

import com.abora.perfectobase.data.models.CategoryDataModel
import com.abora.perfectobase.data.models.LoginDataModel
import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: OrderDataResponse
)
data class OrderDetailsResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: OrderDataModel
)
data class OrderDataResponse(
    @SerializedName("data") val data: List<OrderDataModel>
)
data class OrderDataModel(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("code") val code: String,
    @SerializedName("email") val email: String,
    @SerializedName("country_code") val country_code: String,
    @SerializedName("lat") val lat: String,
    @SerializedName("lng") val lng: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("status") val status: String,
    @SerializedName("address_id") val address_id: Int,
    @SerializedName("driver_id") val driver_id: Int,
    @SerializedName("canceled_user_id") val canceled_user_id: Int,
    @SerializedName("points") val points: Int,
    @SerializedName("cancellation_reason") val cancellation_reason: String?,
    @SerializedName("order_cancellation_reason") val orderCancellation: OrderCancellationReasonDataModel?,
    @SerializedName("feedback") val feedback: String,
    @SerializedName("date") val date: String,
    @SerializedName("from") val from: String,
    @SerializedName("to") val to: String,
    var waste_type: String,
    @SerializedName("time") val time: String,
    @SerializedName("user_comment") val user_comment: String,
    @SerializedName("photos") val photos: List<String>,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("qr_code_image") val qr_code_image: String,
    @SerializedName("status_data") val status_data: StatusDataModel,
    @SerializedName("order_products") val order_products: List<OrderProductsDataModel>,
    @SerializedName("user") val user: LoginDataModel,
    @SerializedName("driver") val driver: DriverDataModel,
    @SerializedName("address") val address: AddressDataClass
)

data class AddressDataClass(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("city_id") val city_id: Int,
    @SerializedName("district_id") val district_id: Int,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double,
    @SerializedName("title") val title: String,
    @SerializedName("address") val address: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("district") val district: CityDataModel,
    @SerializedName("city") val city: CityDataModel
)

data class OrderProductsDataModel (
    @SerializedName("id") val id : Int,
    @SerializedName("order_id") val order_id : Int,
    @SerializedName("product_id") val product_id : Int,
    @SerializedName("qty") val qty : Int,
    @SerializedName("points") val points : Int,
    @SerializedName("note") val note : String,
    @SerializedName("total_item_points") val total_item_points : Int,
    @SerializedName("product") val product : ProductsDataModel?
)

data class ProductsDataModel (
    @SerializedName("id") val id : Int,
    @SerializedName("name_en") val name_en : String,
    @SerializedName("name_ar") val name_ar : String,
    @SerializedName("image") val image : String,
    @SerializedName("points") val points : Int,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("category_id") val category_id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("category") val category : CategoryDataModel
)

data class CityDataModel (
    @SerializedName("id") val id : Int,
    @SerializedName("name_en") val name_en : String,
    @SerializedName("name_ar") val name_ar : String,
    @SerializedName("name") val name : String
)

data class OrderCancellationReasonDataModel (
    @SerializedName("id") val id : Int,
    @SerializedName("name_en") val name_en : String,
    @SerializedName("name_ar") val name_ar : String,
    @SerializedName("title") val title : String
)

data class DriverDataModel (

    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("image") val image : String,
    @SerializedName("country_code") val country_code : String,
    @SerializedName("phone") val phone : String,
    @SerializedName("active") val active : Int,
    @SerializedName("email") val email : String,
    @SerializedName("type") val type : String
)

data class StatusDataModel (
    @SerializedName("key") val key : String,
    @SerializedName("name") val name : String,
    @SerializedName("color") val color : String
)
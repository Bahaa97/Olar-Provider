package com.olar.data.models

import com.google.gson.annotations.SerializedName

data class FilterServiceResponse(
    @SerializedName("code") val code : Int,
    @SerializedName("status") val status : Int,
    @SerializedName("errors") val errors : List<String>,
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : FilterServiceData
)
data class FilterServiceData(
    @SerializedName("general") var general : List<FilterServiceDataModel>,
    @SerializedName("special") var special : List<FilterServiceDataModel>,
    @SerializedName("security") var security : List<FilterServiceDataModel>,
    @SerializedName("house_rules") var house_rules : List<FilterServiceDataModel>,
    @SerializedName("food_and_drink") var food_and_drink : List<FilterServiceDataModel>,
    @SerializedName("house_services") var house_services : List<FilterServiceDataModel>
)
data class FilterServiceDataModel(
    @SerializedName("id") val id : Int,
    @SerializedName("name_ar") val name_ar : String,
    @SerializedName("name_en") val name_en : String,
    @SerializedName("is_active") val is_active : Int,
    @SerializedName("order") val order : Int,
    @SerializedName("image") val image : String,
    @SerializedName("type") val type : String,
    @SerializedName("deleted_at") val deleted_at : String,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String,
    @SerializedName("name") val name : String,
    var selected : Boolean = false
)
package com.abora.perfectobase.data.models

import com.google.gson.annotations.SerializedName

data class RoomsResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<RoomsDataModel>
)
data class CreateRoomsResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: RoomsDataModel
)

data class RoomsDataModel(

    @SerializedName("_id") val _id: String,
    @SerializedName("name") val name: String,
    @SerializedName("property_name") val propertyName: String,
    @SerializedName("city_ar") val city_ar: String,
    @SerializedName("city_en") val city_en: String,
    @SerializedName("host_phone") val hostPhone: String,
    @SerializedName("property_image") val property_image: String,
    @SerializedName("property_id") val property_id: Int,
    @SerializedName("participants") val participants: List<Int>,
    @SerializedName("lastViewed") val lastViewed: String,
    @SerializedName("unreadCount") val unreadCount: List<UnreadCountDataModel>,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("lastChatMessage") val lastChatMessage: LastChatMessageDataModel
)
data class LastChatMessageDataModel (

    @SerializedName("_id") val _id : String,
    @SerializedName("room_id") val room_id : String,
    @SerializedName("message") val message : String,
    @SerializedName("user_id") val user_id : Int,
    @SerializedName("receiver_id") val receiver_id : Int,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("__v") val __v : Int
)
data class UnreadCountDataModel (
    @SerializedName("id") val id : Int,
    @SerializedName("unread_count") val unread_count : Int,
    @SerializedName("name") val name : String,
    @SerializedName("_id") val _id : String
)
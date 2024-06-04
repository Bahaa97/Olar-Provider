package com.olar.data.models

import com.google.gson.annotations.SerializedName

data class CreateRoomChatModel(
    @SerializedName("participants") val participants: List<Int>,
    @SerializedName("property_id") val property_id: Int,
    @SerializedName("property_name") val property_name: String,
    @SerializedName("property_image") val property_image: String,
    @SerializedName("city_ar") val city_ar: String,
    @SerializedName("city_en") val city_en: String,
    @SerializedName("host_phone") val host_phone: String,
    @SerializedName("users") val users: List<RoomUsers>,
    @SerializedName("client_phone") val clientPhone: String?,
    @SerializedName("host_email") val hostEmail: String?,
    @SerializedName("client_email") val clientEmail: String?
)

data class RoomUsers(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)
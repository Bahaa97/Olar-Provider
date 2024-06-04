package com.abora.perfectobase.data.models

import com.google.gson.annotations.SerializedName

data class ChatMessagesResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<ChatMessagesDataModel>
)

data class ChatMessagesDataModel(
    @SerializedName("_id") val _id: String,
    @SerializedName("room_id") val room_id: String,
    @SerializedName("message") val message: String,
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("receiver_id") val receiver_id: Int,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("text_type") val text_type: String
)
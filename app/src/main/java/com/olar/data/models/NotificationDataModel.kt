package com.olar.data.models

import com.google.gson.annotations.SerializedName

data class NotificationDataModel(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data:  List<NotificationData>
)

data class NotificationResponse (
    @SerializedName("notifications") val notificationData : List<NotificationData>
)

data class NotificationData (

    @SerializedName("id") val id : String,
    @SerializedName("notifiable_id") val notifiable_id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("body") val body : String,
    @SerializedName("action_type") val action_type : String,
    @SerializedName("action_id") val action_id : String,
    @SerializedName("image") val image : String,
    @SerializedName("read_at") var read_at : String,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("created_at_human_format") val created_at_human_format : String
)
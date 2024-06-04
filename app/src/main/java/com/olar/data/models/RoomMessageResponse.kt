package com.olar.data.models

data class RoomMessageResponse(
    val code: Int,
    val `data`: RoomData,
    val errors: Any,
    val message: String,
    val status: Int
)

data class RoomData(
    val current_page: Int,
    val `data`: List<RoomDataModel>,
    val first_page_url: String,
    val from: Int,
    val last_page: Int,
    val last_page_url: String,
    val links: List<RoomDataLink>,
    val next_page_url: Any,
    val path: String,
    val per_page: Int,
    val prev_page_url: Any,
    val to: Int,
    val total: Int
)

data class RoomDataModel(
    val created_at: String,
    val id: Int,
    val message: String,
    val order_id: Int,
    val user_id: Int
)

data class RoomDataLink(
    val active: Boolean,
    val label: String,
    val url: String
)
package com.olar.data.models

data class SupportResponse(
    val code: Int,
    val `data`: SupportDataModel,
    val errors: Any,
    val message: String,
    val status: Int
)

data class SupportDataModel(
    val created_at: String,
    val id: Int,
    val message: String,
    val support_topic_id: String,
    val type: String,
    val updated_at: String,
    val user_id: Int
)
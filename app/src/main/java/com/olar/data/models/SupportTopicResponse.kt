package com.olar.data.models

data class SupportTopicResponse(
    val code: Int,
    val `data`: List<SupportTopicDataModel>,
    val errors: Any,
    val message: String,
    val status: Int
)

data class SupportTopicDataModel(
    val id: Int,
    val name: String,
    val name_ar: String,
    val name_en: String
)
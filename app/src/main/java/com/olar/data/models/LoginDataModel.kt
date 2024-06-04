package com.abora.perfectobase.data.models


import com.google.gson.annotations.SerializedName

data class LoginDataResponse(
    var code: Int? = 0,
    var `data`: LoginDataModel? = null,
    var errors: Any? = "",
    var message: String? = "",
    var status: Int? = 0
)

data class LoginDataModel(
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("push_token")
    var push_token: Int? = 0,
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("image")
    var image: String? = "",
    @SerializedName("username")
    var username: String? = "",
    @SerializedName("gender")
    var gender: String? = "",
    @SerializedName("first_name")
    var firstName: String? = "",
    @SerializedName("last_name")
    var lastName: String? = "",
    @SerializedName("email")
    var email: String? = "",
    @SerializedName("unit_house_type")
    var unit_house_type: String? = "",
    @SerializedName("family_members_num")
    var family_members_num: String? = "",
    @SerializedName("phone")
    var phone: String? = "",
    @SerializedName("points")
    var points: String? = "",
    @SerializedName("active")
    var active: String? = "",
    @SerializedName("type")
    var type: String? = "",
    @SerializedName("birthdate")
    var birthdate: String? = "",
    @SerializedName("token")
    var token: String? = "",
    @SerializedName("country")
    var country: String? = "",
    @SerializedName("country_code")
    var countryCode: String? = "",
    @SerializedName("phone_code")
    var phoneCode: String? = "",
    @SerializedName("referral_code")
    var referralCode: String? = "",
    @SerializedName("referral_points")
    var referral_points: String? = "",
    @SerializedName("country_id")
    var countryId: String? = "",
    @SerializedName("created_at")
    var createdAt: String? = "",
    @SerializedName("wallet")
    var wallet: Double? = 0.0,
    @SerializedName("description")
    val description: String,
    @SerializedName("available_referral_points")
    val available_referral_points: String,
    @SerializedName("total_referral_points")
    val total_referral_points: String,
    @SerializedName("total_referred_users")
    val total_referred_users: String,
    @SerializedName("vouchers")
    val vouchers: List<VoucherDataModel>,
)

data class Country(
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("phone_code")
    var phone_code: String? = "",
    @SerializedName("country_code")
    var country_code: String? = "",
)



package com.olar.data.models


enum class CategoryType(val type:String){
    GENERAL("general_categories"),
    PLACES("place_type"),
    ALL("")
}
enum class ServicesType(val type:String){
    PROPERTY_TYPE("property_type"),
    GENERAL("general"),
    SPECIAL("special"),
    SECURITY("security"),
    HOUSE_RULES("house_rules"),
    FOOD_AND_DRINK("food_and_drink"),
    HOUSE_SERVICES("house_services")
}
data class AddGuestDataModel(
    var position:Int,
    var adultNum:Int,
    var childrenNum:Int
)
data class RatingDataModel(
    var rate:String,
    var value:String? = "",
    var selected: Boolean = false
)

data class RecentSearchDataModel(
    var categories:String,
    var country_id:String? = "",
    var city_id:String? = "",
    var booking_status:String? = "",
    var guests_count:String? = "",
    var children_count:String? = "",
    var rooms_count:String? = "",
    var from_time:String? = "",
    var to_time:String? = "",
    var from_date:String? = "",
    var to_date:String? = "",
    var sort_by:String? = "",
    var country:String? = ""
)

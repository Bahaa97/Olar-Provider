package com.olar.Utils

import android.content.Context
import android.content.SharedPreferences
import com.abora.perfectobase.data.models.AddressDataModel
import com.abora.perfectobase.data.models.CurrencyData
import com.abora.perfectobase.data.models.DefaultData
import com.abora.perfectobase.data.models.LoginDataModel
import com.google.gson.Gson
import com.olar.R
import com.olar.data.models.ProductDataModel
import com.olar.data.models.RatingDataModel
import com.olar.data.models.RecentSearchDataModel
import com.olar.data.models.TimesDataModel

class AppManger constructor(var sharedPreferences: SharedPreferences) {

    fun isLogin(): Boolean {
        return sharedPreferences.contains("token")
    }

    fun setCountry(countryCode: String) {
        sharedPreferences.edit().putString("country", countryCode).apply()
    }

    fun getCountry(): String {
        return sharedPreferences.getString("country", "").toString()
    }

    fun setCurrencies(currency: List<CurrencyData>) {
        sharedPreferences.edit().putString("currencies", Gson().toJson(currency)).apply()
        currency.forEach {
            if (it.symbol == "SAR")
                sharedPreferences.edit().putString("saudi", Gson().toJson(it)).apply()
        }
    }

    fun getSARCurrency(): CurrencyData {
        return Gson().fromJson(
            sharedPreferences.getString("saudi", "").toString(),
            CurrencyData::class.java
        )
    }

    fun getCurrencies(): List<CurrencyData> {
        return Gson().fromJson(
            sharedPreferences.getString("currencies", "").toString(),
            Array<CurrencyData>::class.java
        ).toList()
    }

    fun setCart(cart: List<ProductDataModel>) {
        sharedPreferences.edit().remove("user_cart").apply()
        sharedPreferences.edit().putString("user_cart", Gson().toJson(cart)).apply()
    }

    fun emptyCart() {
        sharedPreferences.edit().remove("user_cart").apply()
    }

    fun getCart(): List<ProductDataModel> {
        return if (sharedPreferences.contains("user_cart"))
            Gson().fromJson(
                sharedPreferences.getString("user_cart", "").toString(),
                Array<ProductDataModel>::class.java
            ).toList()
        else
            arrayListOf()
    }

    fun setCartId(id: Int) {
        sharedPreferences.edit().putInt("cart_id", id).apply()
    }

    fun getCartId(): Int {
        return sharedPreferences.getInt("cart_id", 0)
    }

    fun setUserCurrency(currency: CurrencyData) {
        sharedPreferences.edit().putString("currency", Gson().toJson(currency)).apply()
    }

    fun getUserCurrency(): CurrencyData? {
        return if (sharedPreferences.contains("currency"))
            Gson().fromJson(
                sharedPreferences.getString("currency", "").toString(),
                CurrencyData::class.java
            )
        else
            null
    }

    fun haveCurrency(): Boolean {
        return sharedPreferences.contains("currency")
    }

    fun setPhoneCode(countryCode: String) {
        sharedPreferences.edit().putString("phoneCode", countryCode).apply()
    }

    fun getPhoneCode(): String {
        return sharedPreferences.getString("phoneCode", "EG").toString()
    }

    fun setTerms(countryCode: String) {
        sharedPreferences.edit().putString("terms", countryCode).apply()
    }

    fun getTerms(): String {
        return sharedPreferences.getString("terms", "").toString()
    }

    fun haveCountry(): Boolean {
        return sharedPreferences.contains("country")
    }

    fun isVendorLogin(): Boolean {
        return sharedPreferences.getString("type", "").equals("vendor")
    }

    fun logout() {
        emptyCart()
        sharedPreferences.edit().remove("user").apply()
        sharedPreferences.edit().remove("phone").apply()
        sharedPreferences.edit().remove("email").apply()
        sharedPreferences.edit().remove("token").apply()
        sharedPreferences.edit().remove("id").apply()
        sharedPreferences.edit().remove("name").apply()
        sharedPreferences.edit().remove("type").apply()
        sharedPreferences.edit().remove("provider").apply()
        sharedPreferences.edit().remove("isFirstTime").apply()
        sharedPreferences.edit().remove("isFirstHome").apply()
        sharedPreferences.edit().remove("address").apply()
    }

    fun saveUserData(loginData: LoginDataModel?) {
        if (loginData?.token.isNullOrEmpty().not()) {
            sharedPreferences.edit().putString("token", loginData?.token).apply()
        }
        sharedPreferences.edit().putString("id", loginData?.id.toString()).apply()
        sharedPreferences.edit().putString("name", loginData?.name).apply()
        sharedPreferences.edit().putString("phone", loginData?.phone).apply()
        sharedPreferences.edit().putString("email", loginData?.email).apply()
        sharedPreferences.edit().putString("user", Gson().toJson(loginData)).apply()
    }


    fun saveUserAddress(loginData: AddressDataModel?) {
        loginData?.selected = true
        sharedPreferences.edit().putString("address", Gson().toJson(loginData)).apply()

    }

    fun getUserAddress(): AddressDataModel? {
        val userData = if (Gson().fromJson(
                sharedPreferences.getString("address", ""),
                AddressDataModel::class.java
            ) != null
        ) {
            Gson().fromJson(
                sharedPreferences.getString("address", ""),
                AddressDataModel::class.java
            )
        } else {
            null
        }
        return userData

    }

    fun getUserData(): LoginDataModel? {
        val userData = if (Gson().fromJson(
                sharedPreferences.getString("user", ""),
                LoginDataModel::class.java
            ) != null
        ) {
            Gson().fromJson(sharedPreferences.getString("user", ""), LoginDataModel::class.java)
        } else {
            null
        }
        return userData

    }

    fun setReservationStatusCount(data: DefaultData?) {
        sharedPreferences.edit().putString("reservation_status", Gson().toJson(data)).apply()
    }

    fun getReservationStatus(): DefaultData? {
        val userData = if (Gson().fromJson(
                sharedPreferences.getString("reservation_status", ""),
                DefaultData::class.java
            ) != null
        ) {
            Gson().fromJson(
                sharedPreferences.getString("reservation_status", ""),
                DefaultData::class.java
            )
        } else {
            null
        }
        return userData
    }

    fun deleteSearch() {
        sharedPreferences.edit().remove("search").apply()
    }

    fun getRecentSearch(): List<RecentSearchDataModel> {
        val list: MutableList<RecentSearchDataModel> = arrayListOf()
        if (sharedPreferences.contains("search")) {
            list.addAll(
                Gson().fromJson(
                    sharedPreferences.getString("search", ""),
                    Array<RecentSearchDataModel>::class.java
                ).toList()
            )
        }
        return list
    }

    fun addRecentSearch(search: RecentSearchDataModel) {
        val list: MutableList<RecentSearchDataModel> = arrayListOf()
        list.add(search)
        val searchList = getRecentSearch().toMutableList()
        if (searchList.isNotEmpty()) {
            searchList.forEach { item ->
                if (item.country_id != search.country_id)
                    list.add(item)
            }
        }
        sharedPreferences.edit().putString("search", Gson().toJson(list)).apply()
    }

    fun getUserToken(): String {
        return sharedPreferences.getString("token", "").toString()
    }

    fun setUserLocation(lat: String, lng: String) {
        sharedPreferences.edit().putString("lat", lat).apply()
        sharedPreferences.edit().putString("lng", lng).apply()
    }

    fun getUserLat(): String {
        return sharedPreferences.getString("lat", "0.0").toString()
    }

    fun setNotificationCount(count: Int) {
        sharedPreferences.edit().putInt("notification", count).apply()
    }

    fun getNotificationCount(): Int {
        return sharedPreferences.getInt("notification", 0)
    }

    fun getUserLng(): String {
        return sharedPreferences.getString("lng", "0.0").toString()
    }

    fun isFirstTime(): Boolean {
        return if (sharedPreferences.contains("isFirstTime")) {
            false
        } else {
            sharedPreferences.edit().putBoolean("isFirstTime", true).apply()
            true
        }

    }

    fun isFirstHome(): Boolean {
        return if (sharedPreferences.contains("isFirstHome")) {
            false
        } else {
            sharedPreferences.edit().putBoolean("isFirstHome", true).apply()
            true
        }

    }

    fun setPushToken(task: String) {
        sharedPreferences.edit().putString("pushToken", task).apply()
    }

    fun getPushToken(): String {
        return sharedPreferences.getString("pushToken", "").toString()
    }

    fun getRating(context: Context): List<RatingDataModel> {
        val data: MutableList<RatingDataModel> = arrayListOf()
        data.add(RatingDataModel(context.resources.getString(R.string.rate_5), "5"))
        data.add(RatingDataModel(context.resources.getString(R.string.rate_4), "4"))
        data.add(RatingDataModel(context.resources.getString(R.string.rate_3), "3"))
        data.add(RatingDataModel(context.resources.getString(R.string.rate_2), "2"))
        data.add(RatingDataModel(context.resources.getString(R.string.rate_1), "1"))
        return data
    }

    fun getSortBy(context: Context): List<RatingDataModel> {
        val data: MutableList<RatingDataModel> = arrayListOf()
        data.add(
            RatingDataModel(
                value = "id",
                rate = context.resources.getString(R.string.default_value),
                selected = true
            )
        )
        data.add(
            RatingDataModel(
                value = "best_seller",
                rate = context.resources.getString(R.string.best_seller)
            )
        )
        data.add(
            RatingDataModel(
                value = "center",
                rate = context.resources.getString(R.string.distance_center)
            )
        )
        data.add(
            RatingDataModel(
                value = "distance",
                rate = context.resources.getString(R.string.nearby_search)
            )
        )
        data.add(
            RatingDataModel(
                value = "most_viewed",
                rate = context.resources.getString(R.string.most_viewed)
            )
        )
        data.add(
            RatingDataModel(
                value = "rating",
                rate = context.resources.getString(R.string.rating)
            )
        )
        data.add(
            RatingDataModel(
                value = "lowest_price",
                rate = context.resources.getString(R.string.lowest_price)
            )
        )
        data.add(
            RatingDataModel(
                value = "highest_price",
                rate = context.resources.getString(R.string.highest_price)
            )
        )
        return data
    }

    fun getFilterBy(context: Context): List<RatingDataModel> {
        val data: MutableList<RatingDataModel> = arrayListOf()
        data.add(
            RatingDataModel(
                value = "",
                rate = context.resources.getString(R.string.default_value),
                selected = true
            )
        )
        data.add(
            RatingDataModel(
                value = "hourly",
                rate = context.resources.getString(R.string.hourly)
            )
        )
        data.add(
            RatingDataModel(
                value = "daily",
                rate = context.resources.getString(R.string.daily)
            )
        )
        data.add(
            RatingDataModel(
                value = "weekly",
                rate = context.resources.getString(R.string.weekly)
            )
        )
        data.add(
            RatingDataModel(
                value = "monthly",
                rate = context.resources.getString(R.string.monthly)
            )
        )
        return data
    }

    fun getPropertyTime(context: Context): List<RatingDataModel> {
        val data: MutableList<RatingDataModel> = arrayListOf()
        data.add(
            RatingDataModel(
                value = "hourly",
                rate = context.resources.getString(R.string.hourly)
            )
        )
        data.add(
            RatingDataModel(
                value = "daily",
                rate = context.resources.getString(R.string.daily)
            )
        )
        data.add(
            RatingDataModel(
                value = "weekly",
                rate = context.resources.getString(R.string.weekly)
            )
        )
        data.add(
            RatingDataModel(
                value = "monthly",
                rate = context.resources.getString(R.string.monthly)
            )
        )
//        data.add(
//            RatingDataModel(
//                value = "annually",
//                rate = context.resources.getString(R.string.annual)
//            )
//        )
//        data.add(RatingDataModel(value = "annual",rate = context.resources.getString(R.string.annual)))
        return data
    }

    fun addPaymentId(id: String) {
        val ids: MutableList<String> = arrayListOf()
        ids.add(id)
        if (sharedPreferences.contains("payment_id"))
            sharedPreferences.getStringSet("payment_id", null)?.toList()?.let { ids.addAll(it) }
        sharedPreferences.edit().putStringSet("payment_id", ids.toSet()).apply()
    }

    fun getPaymentIds(): List<String> {
        return if (sharedPreferences.contains("payment_id"))
            sharedPreferences.getStringSet("payment_id", null)!!.toList()
        else
            arrayListOf()
    }

    fun setSelectedTime(time: TimesDataModel) {
        sharedPreferences.edit().putString("TimesDataModel", Gson().toJson(time)).apply()

    }

    fun removeSelectedTime() {
        sharedPreferences.edit().remove("TimesDataModel").apply()

    }

    fun getSelectedTime(): TimesDataModel? {
        return Gson().fromJson(
            sharedPreferences.getString("TimesDataModel", "").toString(),
            TimesDataModel::class.java
        )
    }


    fun setSelectedDay(task: String) {
        sharedPreferences.edit().putString("selectedDay", task).apply()
    }

    fun getSelectedDay(): String? {
        return sharedPreferences.getString("selectedDay", "").toString()
    }

    fun removeSelectedDay() {
        sharedPreferences.edit().remove("selectedDay").apply()

    }


}
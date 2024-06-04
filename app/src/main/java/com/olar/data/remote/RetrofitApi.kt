package com.abora.perfectobase.data.remote


import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

import retrofit2.http.POST

import com.abora.perfectobase.data.models.*
import com.olar.data.models.DatesResponse
import com.olar.data.models.FilterServiceResponse
import com.olar.data.models.NotificationDataModel
import com.olar.data.models.OrderDetailsResponse
import com.olar.data.models.OrderResponse
import com.olar.data.models.ProductResponse
import com.olar.data.models.PropertyDetailsResponse
import com.olar.data.models.RoomMessageResponse
import com.olar.data.models.SupportResponse
import com.olar.data.models.SupportTopicResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody


interface RetrofitApi {

    @GET("regions?return_all=1")
    fun getRegion(): Deferred<Response<CitiesResponse>>

    @GET("cities?return_all=1")
    fun getCities(@Query("region_id") id: Int): Deferred<Response<CitiesResponse>>

    @GET("districts?return_all=1")
    fun getDistricts(@Query("city_id") id: Int): Deferred<Response<CitiesResponse>>

    @POST("auth/login")
    fun login(@Body loginData: JsonObject): Deferred<Response<LoginDataResponse>>

    @POST("auth/phone-login")
    fun loginWithPhone(@Body loginData: JsonObject): Deferred<Response<DefaultDataModel>>

    @POST("auth/verifications/confirm-phone-login")
    fun verifyLoginWithPhone(@Body loginData: JsonObject): Deferred<Response<LoginDataResponse>>

    @POST("verifications/confirm")
    fun verifyPhoneRegister(@Body loginData: JsonObject): Deferred<Response<LoginDataResponse>>

    @POST("verifications/resend")
    fun registerSendCode(@Body loginData: JsonObject): Deferred<Response<DefaultDataModel>>

    @POST("auth/register")
    fun userRegister(@Body responseData: JsonObject): Deferred<Response<LoginDataResponse>>

    @POST("password/reset")
    fun resetVerifyCodePassword(@Body responseData: JsonObject): Deferred<Response<LoginDataResponse>>

    @POST("password/forget")
    fun forgetPassword(@Body responseData: JsonObject): Deferred<Response<DefaultDataModel>>

    @GET("pages?return_all=1&socials=1")
    fun getPages(): Deferred<Response<MorePagesDataModel>>

    @GET("settings")
    fun getSetting(): Deferred<Response<SettingResponse>>

    @GET("pages/{id}")
    fun getPage(@Path("id") id: Int): Deferred<Response<PageDataModel>>

    @GET("home/mobile")
    fun getHome(): Deferred<Response<HomeDataResponse>>

    @GET("categories?return_all=1")
    fun getCategories(): Deferred<Response<CategoriesResponse>>

    @GET("products?return_all=1")
    fun getProducts(): Deferred<Response<ProductResponse>>
    @GET("products?return_all=1")
    fun getProducts(@Query("category_id") id: Int): Deferred<Response<ProductResponse>>

    @GET("vouchers?return_all=1")
    fun getVouchers(): Deferred<Response<VouchersResponse>>

    @GET("vouchers/{id}")
    fun getVoucherDetails(@Path("id") id: Int): Deferred<Response<VoucherDetailsResponse>>

    @POST("vouchers/{id}/scan-and-confirm")
    fun scanVoucher(
        @Path("id") id: Int,
        @Body loginData: JsonObject
    ): Deferred<Response<DefaultDataModel>>

    @POST("orders/{id}/scan-and-deliver-order")
    fun scanOrder(
        @Path("id") id: Int,
        @Body loginData: JsonObject
    ): Deferred<Response<DefaultDataModel>>
    @POST("orders/{id}/user-cancellation")
    fun cancelOrder(
        @Path("id") id: Int
    ): Deferred<Response<DefaultDataModel>>

    @GET("support-topics?return_all=1")
    fun getSupportTopic(
    ): Deferred<Response<SupportTopicResponse>>

    @POST("supports")
    fun sendComplain(
        @Body responseData: JsonObject
    ):Deferred<Response<SupportResponse>>

    @GET("orders")
    fun getOrders(@Query("page") page: Int): Deferred<Response<OrderResponse>>

    @GET("orders/{id}")
    fun getOrderDetails(@Path("id") page: Int): Deferred<Response<OrderDetailsResponse>>

    @Multipart
    @POST("orders")
    fun createOrder(
        @Part("email") email: RequestBody,
        @Part("country_code") country_code: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("address_id") address_id: RequestBody,
        @Part("date") date: RequestBody,
        @Part("from") from: RequestBody,
        @Part("to") to: RequestBody,
        @Part("user_comment") user_comment: RequestBody,
        @Part images: List<MultipartBody.Part>? = null,
        @PartMap items: HashMap<String, Int>,
        @PartMap itemsNote: HashMap<String, String>,
    ): Deferred<Response<DefaultDataModel>>

    @POST("properties/search")
    fun search(
        @Body data: JsonObject
    ): Deferred<Response<SearchResponse>>

    @POST("properties/map")
    fun searchMap(
        @Body data: JsonObject
    ): Deferred<Response<SearchMapResponse>>
    @GET("settings/services/group-by-type")
    fun getFilterOptions(): Deferred<Response<FilterServiceResponse>>


    @GET("properties/{id}")
    fun getPropertyDetails(
        @Path("id") id: Int,
        @Query("booking_status") bookingStatus: String? = null,
        @Query("from_time") fromTime: String? = null,
        @Query("to_time") toTime: String? = null,
        @Query("from_date") fromDate: String? = null,
        @Query("to_date") toDate: String? = null,
    ): Deferred<Response<PropertyDetailsResponse>>


    @GET("busy-days")
    fun getDates(
        @Query("month") month: String,
        @Query("year") year: String
    ): Deferred<Response<DatesResponse>>


    @POST("addresses")
    fun addAddress(
        @Body data: JsonObject
    ): Deferred<Response<AddAddressResponse>>

    @POST("addresses/{id}")
    fun updateAddress(
        @Path("id") id: Int,
        @Body data: JsonObject
    ): Deferred<Response<AddAddressResponse>>

    @DELETE("addresses/{id}")
    fun deleteAddress(
        @Path("id") id: Int
    ): Deferred<Response<DefaultDataModel>>

    @GET("addresses?return_all=1")
    fun getAddress(
    ): Deferred<Response<AddressResponse>>

    @GET("machines")
    fun getMachinesLocation(
    ): Deferred<Response<AddressResponse>>

    @POST("orders/generate-qr-for-machine")
    fun getMachineQr(
        @Query("machine_id") id:Int
    ): Deferred<Response<MachineQrResponse>>

    @GET("orders/{roomId}/messages")
    fun getRoomMessages(
        @Path("roomId") roomId: Int
    ): Deferred<Response<RoomMessageResponse>>


    @GET("notifications?return_all=1")
    fun getNotification(
    ): Deferred<Response<NotificationDataModel>>

    @GET("notifications/total-unread-notifications")
    fun getNotificationCount(
    ): Deferred<Response<DefaultDataModel>>

    @POST("notifications")
    fun makeNotificationAsRead(): Deferred<Response<DefaultDataModel>>

    @POST("notifications/mark-as-read")
    fun makeNotificationAsRead(
        @Query("id") id: String
    ): Deferred<Response<DefaultDataModel>>
    @GET("users/profile")
    fun getProfile(): Deferred<Response<LoginDataResponse>>


    @Multipart
    @POST("users/update-profile")
    fun updateUserData(
        @Part("_method") method: RequestBody? = null,
        @Part("name") name: RequestBody? = null,
        @Part("email") email: RequestBody? = null,
        @Part("family_members_num") family_members_num: RequestBody? = null,
        @Part("unit_house_type") unit_house_type: RequestBody? = null,
        @Part image: MultipartBody.Part? = null,
    ): Deferred<Response<LoginDataResponse>>

    //    @FormUrlEncoded
    @POST("settings/delete-my-account")
    fun deleteAccount(): Deferred<Response<DefaultDataModel>>

    @POST("users/change-phone")
    fun changePhone(
        @Body responseData: JsonObject,
    ): Deferred<Response<DefaultDataModel>>

    @POST("users/verify-phone")
    fun verifyPhone(
        @Body responseData: JsonObject
    ): Deferred<Response<DefaultDataModel>>

    @POST("users/change-email")
    fun changeEmail(
        @Body responseData: JsonObject
    ): Deferred<Response<DefaultDataModel>>

    @POST("users/change-password")
    fun changePasswordRequest(
        @Body responseData: JsonObject
    ): Deferred<Response<DefaultDataModel>>
    @POST("orders/{orderId}/feedback")
    fun sendFeedback(
        @Path("orderId") orderId: Int,
        @Body responseData: JsonObject
    ): Deferred<Response<DefaultDataModel>>

}
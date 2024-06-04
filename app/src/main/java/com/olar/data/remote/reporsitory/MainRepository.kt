package com.olar.data.remote.reporsitory

import android.content.Context
import com.abora.perfectobase.data.models.LoginDataModel
import com.abora.perfectobase.data.remote.RetrofitApi
import com.abora.perfectobase.data.remote.networkHandling.NetworkResult
import com.abora.perfectobase.data.remote.networkHandling.NetworkStatus
import com.google.gson.JsonObject
import com.olar.Utils.PathUtil.getPath
import com.olar.data.models.ProductDataModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class MainRepository constructor(var apiService: RetrofitApi) : NetworkResult() {

    suspend fun getRegion(networkStatus: NetworkStatus) = getResult({
        apiService.getRegion().await()
    }, networkStatus)
    suspend fun getCities(networkStatus: NetworkStatus,id:Int) = getResult({
        apiService.getCities(id).await()
    }, networkStatus)

    suspend fun getDistricts(networkStatus: NetworkStatus, id: Int) = getResult({
        apiService.getDistricts(id).await()
    }, networkStatus)

    suspend fun getCategories(networkStatus: NetworkStatus) = getResult({
        apiService.getCategories().await()
    }, networkStatus)

    suspend fun getProducts(networkStatus: NetworkStatus) = getResult({
        apiService.getProducts().await()
    }, networkStatus)

    suspend fun getProducts(networkStatus: NetworkStatus, id: Int) = getResult({
        apiService.getProducts(id).await()
    }, networkStatus)

    suspend fun getVouchers(networkStatus: NetworkStatus) = getResult({
        apiService.getVouchers().await()
    }, networkStatus)

    suspend fun getVoucherDetails(networkStatus: NetworkStatus, id: Int) = getResult({
        apiService.getVoucherDetails(id).await()
    }, networkStatus)

    suspend fun getOrders(networkStatus: NetworkStatus, id: Int) = getResult({
        apiService.getOrders(id).await()
    }, networkStatus)

    suspend fun getOrderDetails(networkStatus: NetworkStatus, id: Int) = getResult({
        apiService.getOrderDetails(id).await()
    }, networkStatus)

    suspend fun scanVoucher(networkStatus: NetworkStatus, id: Int, data: JsonObject) = getResult({
        apiService.scanVoucher(id, data).await()
    }, networkStatus)

    suspend fun scanOrder(networkStatus: NetworkStatus, id: Int, data: JsonObject) = getResult({
        apiService.scanOrder(id, data).await()
    }, networkStatus)

    suspend fun getSupportTopic(networkStatus: NetworkStatus) =
        getResult({ apiService.getSupportTopic().await() }, networkStatus)

    suspend fun sendComplain(networkStatus: NetworkStatus, responseData: JsonObject) =
        getResult({ apiService.sendComplain(responseData).await() }, networkStatus)

    suspend fun cancelOrder(networkStatus: NetworkStatus, id: Int) = getResult({
        apiService.cancelOrder(id).await()
    }, networkStatus)

    suspend fun login(networkStatus: NetworkStatus, data: JsonObject) = getResult({
        apiService.login(data).await()
    }, networkStatus)

    suspend fun loginWithPhone(networkStatus: NetworkStatus, data: JsonObject) = getResult({
        apiService.loginWithPhone(data).await()
    }, networkStatus)

    suspend fun verifyLoginWithPhone(networkStatus: NetworkStatus, data: JsonObject) = getResult({
        apiService.verifyLoginWithPhone(data).await()
    }, networkStatus)

    suspend fun verifyPhoneRegister(networkStatus: NetworkStatus, data: JsonObject) = getResult({
        apiService.verifyPhoneRegister(data).await()
    }, networkStatus)

    suspend fun registerSendCode(networkStatus: NetworkStatus, data: JsonObject) = getResult({
        apiService.registerSendCode(data).await()
    }, networkStatus)

    suspend fun userRegister(networkStatus: NetworkStatus, data: JsonObject) = getResult({
        apiService.userRegister(data).await()
    }, networkStatus)


    suspend fun forgetPassword(networkStatus: NetworkStatus, data: JsonObject) = getResult({
        apiService.forgetPassword(data).await()
    }, networkStatus)

    suspend fun resetVerifyCodePassword(networkStatus: NetworkStatus, data: JsonObject) =
        getResult({
            apiService.resetVerifyCodePassword(data).await()
        }, networkStatus)

    suspend fun getPages(networkStatus: NetworkStatus) = getResult({
        apiService.getPages().await()
    }, networkStatus)

    suspend fun getSetting(networkStatus: NetworkStatus) = getResult({
        apiService.getSetting().await()
    }, networkStatus)

    suspend fun getPage(networkStatus: NetworkStatus, id: Int) = getResult({
        apiService.getPage(id).await()
    }, networkStatus)

    suspend fun getHome(networkStatus: NetworkStatus) = getResult({
        apiService.getHome().await()
    }, networkStatus)

    suspend fun search(
        networkStatus: NetworkStatus,
        data: JsonObject
    ) = getResult({
        apiService.search(data).await()
    }, networkStatus)

    suspend fun searchMap(
        networkStatus: NetworkStatus,
        data: JsonObject
    ) = getResult({
        apiService.searchMap(data).await()
    }, networkStatus)

    suspend fun getFilterOptions(
        networkStatus: NetworkStatus
    ) = getResult({
        apiService.getFilterOptions().await()
    }, networkStatus)

    suspend fun getPropertyDetails(
        networkStatus: NetworkStatus,
        id: Int,
        bookingStatus: String? = null,
        fromTime: String? = null,
        toTime: String? = null,
        fromDate: String? = null,
        toDate: String? = null,
    ) = getResult({
        apiService.getPropertyDetails(id, bookingStatus, fromTime, toTime, fromDate, toDate).await()
    }, networkStatus)


    suspend fun getDates(
        networkStatus: NetworkStatus,
        month: String,
        year: String
    ) = getResult({
        apiService.getDates(month, year).await()
    }, networkStatus)

    suspend fun addAddress(
        networkStatus: NetworkStatus,
        data: JsonObject
    ) = getResult({
        apiService.addAddress(data).await()
    }, networkStatus)

    suspend fun updateAddress(
        networkStatus: NetworkStatus,
        id: Int,
        data: JsonObject
    ) = getResult({
        apiService.updateAddress(id, data).await()
    }, networkStatus)

    suspend fun deleteAddress(
        networkStatus: NetworkStatus,
        id: Int
    ) = getResult({
        apiService.deleteAddress(id).await()
    }, networkStatus)

    suspend fun getAddress(
        networkStatus: NetworkStatus
    ) = getResult({
        apiService.getAddress().await()
    }, networkStatus)

    suspend fun getMachinesLocation(
        networkStatus: NetworkStatus
    ) = getResult({
        apiService.getMachinesLocation().await()
    }, networkStatus)

    suspend fun getMachineQr(
        networkStatus: NetworkStatus,
        id: Int
    ) = getResult({
        apiService.getMachineQr(id).await()
    }, networkStatus)

    suspend fun getRoomMessages(
        networkStatus: NetworkStatus,
        roomId: Int,
    ) = getResult({
        apiService.getRoomMessages(
            roomId
        )
            .await()
    }, networkStatus)

    suspend fun getNotification(
        networkStatus: NetworkStatus
    ) = getResult({
        apiService.getNotification().await()
    }, networkStatus)


    suspend fun getNotificationCount(
        networkStatus: NetworkStatus,
    ) = getResult({
        apiService.getNotificationCount().await()
    }, networkStatus)


    suspend fun makeNotificationAsRead(
        networkStatus: NetworkStatus,
    ) = getResult({
        apiService.makeNotificationAsRead().await()
    }, networkStatus)

    suspend fun makeNotificationAsRead(
        networkStatus: NetworkStatus,
        id: String
    ) = getResult({
        apiService.makeNotificationAsRead(id).await()
    }, networkStatus)

    suspend fun getProfile(networkStatus: NetworkStatus) = getResult({
        apiService.getProfile().await()
    }, networkStatus)


    suspend fun editUserData(
        networkStatus: NetworkStatus,
        name: String? = null,
        email: String? = null,
        familyNumber: String? = null,
        houseType: String? = null,
        image: File? = null,
    ) = getResult({
        var attachReqBody: MultipartBody.Part? = null
        if (image != null) {
            attachReqBody = MultipartBody.Part.createFormData(
                "image",
                image.name,
                image.asRequestBody("image/*".toMediaTypeOrNull())
            )
        }
        apiService.updateUserData(
            "PUT".toRequestBody("text/plain".toMediaTypeOrNull()),
            name?.toRequestBody("text/plain".toMediaTypeOrNull()),
            email?.toRequestBody("text/plain".toMediaTypeOrNull()),
            familyNumber?.toRequestBody("text/plain".toMediaTypeOrNull()),
            houseType?.toRequestBody("text/plain".toMediaTypeOrNull()),
            attachReqBody
        ).await()
    }, networkStatus)

    suspend fun deleteAccount(networkStatus: NetworkStatus) = getResult({
        apiService.deleteAccount().await()
    }, networkStatus)

    suspend fun changePhone(networkStatus: NetworkStatus, responseData: JsonObject) = getResult({
        apiService.changePhone(responseData).await()
    }, networkStatus)

    suspend fun verifyPhone(networkStatus: NetworkStatus, responseData: JsonObject) = getResult({
        apiService.verifyPhone(responseData).await()
    }, networkStatus)

    suspend fun changeEmail(networkStatus: NetworkStatus, responseData: JsonObject) = getResult({
        apiService.changeEmail(responseData).await()
    }, networkStatus)

    suspend fun changePassword(
        networkStatus: NetworkStatus,
        oldPassword: String,
        newPassword: String
    ) = getResult({
        val data = JsonObject()
        data.addProperty("password", newPassword)
        data.addProperty("password_confirmation", newPassword)
        data.addProperty("old_password", oldPassword)
        apiService.changePasswordRequest(data).await()
    }, networkStatus)

    suspend fun sendFeedback(networkStatus: NetworkStatus, orderId: Int, responseData: JsonObject) =
        getResult({ apiService.sendFeedback(orderId, responseData).await() }, networkStatus)

}
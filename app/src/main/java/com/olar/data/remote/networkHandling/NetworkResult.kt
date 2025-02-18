package com.abora.perfectobase.data.remote.networkHandling

import android.util.Log
import retrofit2.Response

abstract class NetworkResult {

    protected suspend fun <T> getResult(
        call: suspend () -> Response<T>,
        status: NetworkStatus
    ): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            } else if (response.code() == 500) {
                status.onServerSideError(response.errorBody()?.source()?.buffer?.readUtf8())
            } else if (response.code() == 406) {
                status.onNotVerifyRequest(response.errorBody()?.source()?.buffer?.readUtf8())
            } else if (response.code() == 405) {
                status.onNotAllowed()
            } else if (response.code() == 401) {
                status.onNotAuthorized(response.errorBody()?.source()?.buffer?.readUtf8())
            } else if (response.code() == 400) {
                status.onMakeAction(response.errorBody()?.source()?.buffer?.readUtf8())
            } else if (response.code() == 422) {
                status.onBadRequest(response.errorBody()?.source()?.buffer?.readUtf8())
            }else if (response.code() == 409) {
                status.onBadRequest(response.errorBody()?.source()?.buffer?.readUtf8())
            }
//            else if (response.code() == 404) {
//                status.onBadRequest(response.errorBody()?.source()?.buffer?.readUtf8())
//            }
            else {
                status.onDynamicCode(response)
            }
        } catch (e: Exception) {
            Log.e("ERROR", e.message!!)
            status.onConnectionFail(e.message.toString())
        }
        return Resource.error("")
    }



}
package com.olar.di.module

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.provider.Settings
import com.abora.perfectobase.data.remote.RetrofitApi
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.olar.Utils.AppManger
import com.olar.app.Application
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit


const val BASE_URL = "http://api.olar.world/api/"

const val BASE_URL_SOCKET = "http://api.olar.world:4353/"

val networkModule = module {
    single { getLoggingInterceptor() }
    single { getOkHttp(get(), get(), get()) }
    single { getRetrofit(get()) }
    single { getRetrofitApi(get()) }

}

fun getLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    return httpLoggingInterceptor.apply {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    }
}

fun getOkHttp(
    httpLoggingInterceptor: HttpLoggingInterceptor,
    context: Context,
    appManger: AppManger
): OkHttpClient {
    return OkHttpClient().newBuilder()
        .connectTimeout(2, TimeUnit.MINUTES)
        .readTimeout(2, TimeUnit.MINUTES)
        .writeTimeout(2, TimeUnit.MINUTES)
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor { chain ->
            val request = chain.request()
            val builder = request.newBuilder()
                .addHeader("Accept-Language", Application.language)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Device-Type", "android")
                .addHeader("Device-Name", android.os.Build.MODEL)

                .addHeader("Device-OS-Version", android.os.Build.VERSION.RELEASE)
                .addHeader("app-env", "test")
                .addHeader(
                    "Device-UDID",
                    Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
                )
                .addHeader(
                    "Device-Push-Token",
                    appManger.getPushToken()
                )
                .addHeader("mobile_version", android.os.Build.VERSION.SDK_INT.toString())

            try {
                val pInfo: PackageInfo =
                    context.packageManager.getPackageInfo(context.packageName, 0)
                val version = pInfo.versionName
                builder.addHeader("App-Version", version)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                builder.addHeader("App-Version", "1")
            }

            if (appManger.isLogin()) {
                builder.addHeader(
                    "Authorization",
                    "Bearer ${appManger.getUserToken()}"
                )
            }

            if (appManger.getUserAddress() != null) {
                builder.addHeader(
                    "lng",
                    appManger.getUserAddress()?.lng.toString()
                )
                builder.addHeader(
                    "lat",
                    appManger.getUserAddress()?.lat.toString()
                )
            }

            if (appManger.haveCountry()) {
                builder.addHeader(
                    "country-code",
                    appManger.getCountry()
                )
            }


            val response = chain.proceed(builder.build())



            response
        }
        .build()
}


fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
    val gson = GsonBuilder()
        .registerTypeAdapter(HttpUrl::class.java, UrlDeserializer())
        .create()
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}

fun getRetrofitApi(retrofit: Retrofit): RetrofitApi {
    return retrofit.create(RetrofitApi::class.java)
}

class UrlDeserializer : JsonDeserializer<HttpUrl> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): HttpUrl =
        json.asString.toHttpUrl()

}
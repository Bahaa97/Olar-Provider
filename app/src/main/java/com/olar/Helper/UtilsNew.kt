package com.bountybunch.helper

import android.app.DownloadManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Environment
import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun isOnline(context: Context): Boolean {
    return try {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mobile_info = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val wifi_info = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (mobile_info != null) {
            mobile_info.isConnectedOrConnecting || wifi_info!!.isConnectedOrConnecting
        } else {
            wifi_info!!.isConnectedOrConnecting
        }
    } catch (e: Exception) {
        e.printStackTrace()
        println("" + e)
        false
    }
}

fun startDownload(url: String, con: Context, fileName: String, tittle: String) {
    val request = DownloadManager.Request(Uri.parse(url))
    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
    request.setTitle(tittle)
    request.setMimeType("*/*")
    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
    /*enque manager*/
    val manager = con.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    manager.enqueue(request)
}

fun getAge(date: String?): Int {
    var age = 0
    try {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date1 = formatter.parse(date)
        val now = Calendar.getInstance()
        val dob = Calendar.getInstance()
        dob.time = date1
        require(!dob.after(now)) { "Can't be born in the future" }
        val year1 = now[Calendar.YEAR]
        val year2 = dob[Calendar.YEAR]
        age = year1 - year2
        val month1 = now[Calendar.MONTH]
        val month2 = dob[Calendar.MONTH]
        if (month2 > month1) {
            age--
        } else if (month1 == month2) {
            val day1 = now[Calendar.DAY_OF_MONTH]
            val day2 = dob[Calendar.DAY_OF_MONTH]
            if (day2 > day1) {
                age--
            }
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    Log.d("TAG", "getAge: AGE=> $age")
    return age
}

//fun setRoundImage(
//    context: Context?,
//    imageView: ImageView?,
//    lottie: LottieAnimationView?,
//    url: String?
//) {
//
//    if (lottie != null) {
//        lottie.visibility = View.VISIBLE
//    }
//    Glide.with(context!!).load(checkUrl(url)).listener(object : RequestListener<Drawable?> {
//        override fun onLoadFailed(
//            e: GlideException?,
//            model: Any,
//            target: Target<Drawable?>,
//            isFirstResource: Boolean
//        ): Boolean {
//            if (lottie != null) lottie.visibility = View.GONE
//            return false
//        }
//
//        override fun onResourceReady(
//            resource: Drawable?,
//            model: Any,
//            target: Target<Drawable?>,
//            dataSource: DataSource,
//            isFirstResource: Boolean
//        ): Boolean {
//            if (lottie != null) lottie.visibility = View.GONE
//            return false
//        }
//    }).apply(RequestOptions.bitmapTransform(RoundedCorners(14))).into(imageView!!)
//}

//private fun checkUrl(url: String?): Any {
//    if (url.equals("" + ApiClient.getApiClient(BASE_URL) + "honey_app/public/img/imagenotfound.jpg")) return "https://i.ibb.co/y6sCcvm/user-thumbnail.jpg"
//    else if (url.equals("")) return "https://i.ibb.co/y6sCcvm/user-thumbnail.jpg"
//    else if (url == null) return "https://i.ibb.co/y6sCcvm/user-thumbnail.jpg"
//    else if (url.equals(
//            "http://54.152.130.226/honey_app/public",
//            ignoreCase = true
//        )
//    ) return "https://i.ibb.co/y6sCcvm/user-thumbnail.jpg"
//    else return url
//}

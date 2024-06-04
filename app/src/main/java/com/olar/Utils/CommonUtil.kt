package com.olar.Utils

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.NoInternetConnectionDailog
import com.olar.ui.home.HomeActivity
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.location.LocationManager
import android.os.Build
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.olar.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object CommonUtil {
    const val DELAY_MS: Long = 500
    const val PERIOD_MS: Long = 3000
    var noInternetDailog: NoInternetConnectionDailog? = null

    //    fun themeSet(context: Context, window: Window) {
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//            )
//        }
//    }
    fun themeSet(context: Context, window: Window) {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(
                context as Activity,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                true
            )
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(
                context as Activity,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                false
            )
            window.statusBarColor = Color.TRANSPARENT
        }


    }

    fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
        val win: Window = activity.window
        val winParams: WindowManager.LayoutParams = win.getAttributes()
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.setAttributes(winParams)
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

    fun checkGPS(context: Context?): Boolean {
        return try {
            if (context != null) {
                val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
                val network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                return !(!gps_enabled && !network_enabled)
            } else {
                return false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}

//    fun showNoInternetDialog(activity: Activity?) {
//        if (noInternetDailog == null) noInternetDailog = NoInternetConnectionDailog.show(
//            activity!!,
//            true
//        )
//        try {
//            noInternetDailog!!.setCancelable(false)
//            noInternetDailog!!.show()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//    fun networkConnectionCheck(context: Context): Boolean {
//        val isConnected = isOnline(context)
//        if (!isConnected) {
//            showNoInternetDialog(context as Activity)
//        }
//        return isConnected
//    }
//
//    fun isOnline(context: Context): Boolean {
//        return try {
//            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//            val mobile_info = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
//            val wifi_info = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
//            if (mobile_info != null) {
//                if (mobile_info.isConnectedOrConnecting || wifi_info!!.isConnectedOrConnecting) true
//                else false
//            } else {
//                if (wifi_info!!.isConnectedOrConnecting) true
//                else false
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            println("" + e)
//            false
//        }
//    }


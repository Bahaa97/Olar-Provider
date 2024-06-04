package com.olar.ui.auth.Splash

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.olar.R
import com.olar.Utils.MyUtils.openActivity
import com.olar.base.BaseActivity
import com.olar.databinding.ActivitySplashBinding
import com.olar.ui.auth.AuthViewModel
import com.olar.ui.auth.walkThrough.TourActivity
import com.olar.ui.home.HomeActivity
import kotlin.reflect.KClass


class SplashActivity : BaseActivity<ActivitySplashBinding, AuthViewModel>() {
    private var handler: Handler? = null

    override fun resourceId(): Int = R.layout.activity_splash
    override fun viewModelClass(): KClass<AuthViewModel> = AuthViewModel::class
    var action = false
    var id = 0

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun setUI(savedInstanceState: Bundle?) {

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        FirebaseMessaging.getInstance().token.addOnSuccessListener { task ->
            if (task != null) {
                appManger.setPushToken(task)
                Log.d("pushToken", task)
            }
        }
        if (Build.VERSION.SDK_INT > 32) {
            if (!shouldShowRequestPermissionRationale("112")) {
                getNotificationPermission();
            } else {
                openApp()
            }
        } else {
            openApp()
        }

    }

    fun openApp() {
        handler = Handler(Looper.getMainLooper())
        handler?.postDelayed({
            if (appManger.isLogin()) {
                openActivity(HomeActivity::class.java)
            } else {
                if (appManger.isFirstTime())
                    openActivity(TourActivity::class.java)
                else
                    openActivity(TourActivity::class.java)
            }
            finishAffinity()
        }, 1500)

    }

    override fun observer() {

    }

    fun getNotificationPermission() {
        try {
            if (Build.VERSION.SDK_INT > 32) {
                ActivityCompat.requestPermissions(
                    this, arrayOf<String>(Manifest.permission.POST_NOTIFICATIONS),
                    112
                )
            }
        } catch (e: Exception) {
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            112 -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // allow
                    openApp()
                } else {
                    //deny
                    openApp()
                }
                return
            }
        }
    }

    override fun clicks() {

    }

    override fun callApis() {

    }
}
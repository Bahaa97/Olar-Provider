package com.olar.ui.setting

import android.content.pm.PackageInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.olar.R
import com.olar.Utils.MyUtils.myToastAction2Button
import com.olar.Utils.MyUtils.openActivity
import com.olar.Utils.MyUtils.shareContent
import com.olar.app.Application
import com.olar.base.BaseActivity
import com.olar.databinding.ActivitySettingBinding
import com.olar.ui.home.HomeActivity
import com.olar.ui.home.HomeViewModel
import kotlin.reflect.KClass

class SettingActivity : BaseActivity<ActivitySettingBinding, HomeViewModel>() {

    var notificationStatus: Int = 0

    override fun resourceId(): Int = R.layout.activity_setting

    override fun viewModelClass(): KClass<HomeViewModel> = HomeViewModel::class

    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.apply {

            if (Application.appTheme == "dark") {
                switchDarkTheme.isChecked = true
                tvThemeStatus.text = "ON"
                tvThemeStatus.setTextColor(
                    ContextCompat.getColor(
                        this@SettingActivity,
                        R.color.green_on
                    )
                )
            } else {
                switchDarkTheme.isChecked = false
                tvThemeStatus.text = "off"
                tvThemeStatus.setTextColor(
                    ContextCompat.getColor(
                        this@SettingActivity,
                        R.color.gray_off
                    )
                )
            }
        }


        val pInfo: PackageInfo = packageManager.getPackageInfo(this.packageName, 0)
        val version = pInfo.versionName
        dataBinding.tvAppVersion.text = version

        dataBinding.toolbar.tvTittle.text = resources.getString(R.string.setting)

    }

    override fun observer() {
        viewModel.changeSendPushResponse.observe(this) {
            notificationStatus = it.pushStatus

            dataBinding.apply {
                if (it.pushStatus == 1) {
                    switchNotification.isChecked = true
                    tvNotificationStatus.text = "ON"
                    tvNotificationStatus.setTextColor(
                        ContextCompat.getColor(
                            this@SettingActivity,
                            R.color.green_on
                        )
                    )
                } else {
                    switchNotification.isChecked = false
                    tvNotificationStatus.text = "off"
                    tvNotificationStatus.setTextColor(
                        ContextCompat.getColor(
                            this@SettingActivity,
                            R.color.gray_off
                        )
                    )
                }
            }
        }
    }

    override fun clicks() {
        dataBinding.apply {
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }

            switchNotification.setOnClickListener {
                notificationStatus = 1
                callApis()
            }

            switchDarkTheme.setOnClickListener {
                if (switchDarkTheme.isChecked) {
                    Application.appTheme = "dark"
                } else {
                    Application.appTheme = "light"
                }
                changeAppTheme()
                openActivity(HomeActivity::class.java)
                finishAffinity()
            }

            layChangeLang.setOnClickListener {
                myToastAction2Button(resources.getString(R.string.are_yo_sure_change_app_laguage)) {
                    it.dismiss()
                    changeAppLang()
                }
            }

            layPushToken.setOnClickListener {
                layPushToken.isEnabled = false
                shareContent(sharedPreferences.getString("pushToken", "").toString())
                Handler(Looper.getMainLooper()).postDelayed({
                    layPushToken.isEnabled = true
                }, 400)
            }

            layUdid.setOnClickListener {
                layUdid.isEnabled = false
                shareContent(
                    Settings.Secure.getString(
                        this@SettingActivity.contentResolver,
                        Settings.Secure.ANDROID_ID
                    )
                )
                Handler(Looper.getMainLooper()).postDelayed({
                    layUdid.isEnabled = true
                }, 400)
            }
        }

    }

    override fun callApis() {
//        viewModel.updateSendPush(
//            sendAction = notificationStatus,
//            udid = Settings.Secure.getString(
//                this.contentResolver,
//                Settings.Secure.ANDROID_ID
//            )
//        )
    }

    override fun onResume() {
        super.onResume()

    }
}
package com.olar.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.olar.R
import com.olar.Utils.MyUtils.openActivity
import com.olar.base.BaseActivity
import com.olar.databinding.ActivityHomeBinding
import com.olar.ui.recycleing.RecyclingActivity
import kotlin.reflect.KClass

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {
    override fun resourceId(): Int = R.layout.activity_home

    override fun viewModelClass(): KClass<HomeViewModel> = HomeViewModel::class

    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.circleCount.setCountdownListener(object:CircleCountdownView.CountdownListener{
            override fun onCountdownFinished() {
               openActivity(RecyclerActivity::class.java)
            }

        })
        dataBinding.circleCount.startCountdown(3000)
    }

    override fun observer() {

    }

    override fun clicks() {

    }

    override fun callApis() {

    }
}
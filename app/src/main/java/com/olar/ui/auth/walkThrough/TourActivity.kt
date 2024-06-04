package com.olar.ui.auth.walkThrough

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.viewpager.widget.ViewPager
import com.olar.ui.auth.walkThrough.adapter.TourAdapter
import com.olar.Model.TourModal
import com.olar.R
import com.olar.Utils.MyUtils.openActivity
import com.olar.app.Application
import com.olar.base.BaseActivity
import com.olar.databinding.ActivityTourBinding
import com.olar.ui.auth.AuthViewModel
import com.olar.ui.auth.Login.LoginActivity
import com.olar.ui.home.HomeActivity
import kotlin.reflect.KClass

class TourActivity : BaseActivity<ActivityTourBinding, AuthViewModel>(),
    ViewPager.OnPageChangeListener {
    private val walkthroughmodel: ArrayList<TourModal> = ArrayList()
    companion object {
        fun getIntent(context: Context?): Intent {
            return Intent(context, TourActivity::class.java)
        }
    }

    override fun resourceId(): Int = R.layout.activity_tour

    override fun viewModelClass(): KClass<AuthViewModel> = AuthViewModel::class


    override fun setUI(savedInstanceState: Bundle?) {
        initView()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    override fun observer() {

    }

    override fun clicks() {
        dataBinding.btnSkip.setOnClickListener {
            openActivity(LoginActivity::class.java)
            finish()
        }
        dataBinding.btnStart.setOnClickListener {
            openActivity(HomeActivity::class.java)
            finish()
        }
    }

    override fun callApis() {

    }

    private fun initView() {
        if (Application.language == "ar"){
            dataBinding.viewPager.layoutDirection = View.LAYOUT_DIRECTION_RTL
        }else{
            dataBinding.viewPager.layoutDirection = View.LAYOUT_DIRECTION_LTR

        }
        walkthroughmodel.add(
            TourModal(
                R.drawable.intro_bg_1,
                resources.getString(R.string.intro_title_1),
                resources.getString(R.string.intro_description_1)
            )
        )
        walkthroughmodel.add(
            TourModal(
                R.drawable.intro_bg_2,
                resources.getString(R.string.intro_title_2),
                resources.getString(R.string.intro_description_2)
            )
        )
        walkthroughmodel.add(
            TourModal(
                R.drawable.intro_bg_3,
                resources.getString(R.string.intro_title_3),
                resources.getString(R.string.intro_description_3)
            )
        )
        dataBinding.viewPager.adapter = TourAdapter(this, walkthroughmodel)
        dataBinding.tabLayout.setupWithViewPager(dataBinding.viewPager, true)
        dataBinding.viewPager.addOnPageChangeListener(this)

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {

        // position == dataBinding.tabLayout.tabCount - 1
    }

    override fun onPageScrollStateChanged(state: Int) {

    }
}
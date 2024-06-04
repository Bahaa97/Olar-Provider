package com.olar.ui.auth.ResetPassword.Successfully

import android.content.Intent
import android.os.Bundle
import com.olar.R
import com.olar.base.BaseActivity
import com.olar.databinding.ActivitySuccessfullyBinding
import com.olar.ui.auth.AuthViewModel
import com.olar.ui.auth.Login.LoginActivity
import kotlin.reflect.KClass

class SuccessfullyActivity : BaseActivity<ActivitySuccessfullyBinding, AuthViewModel>() {
    override fun resourceId(): Int = R.layout.activity_successfully

    override fun viewModelClass(): KClass<AuthViewModel> = AuthViewModel::class


    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.commonBtn.tvCommonTitle.text = getString(R.string.login_again)
    }

    override fun observer() {
    }

    override fun clicks() {
        dataBinding.commonBtn.tvCommonTitle.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
            finishAffinity()
        }
    }

    override fun callApis() {

    }
}

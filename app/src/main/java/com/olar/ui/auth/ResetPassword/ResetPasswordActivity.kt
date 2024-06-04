package com.olar.ui.auth.ResetPassword

import android.os.Bundle
import com.olar.R
import com.olar.Utils.MyUtils.openActivity
import com.olar.base.BaseActivity
import com.olar.databinding.ActivityResetPasswordBinding
import com.olar.ui.auth.AuthViewModel
import com.olar.ui.auth.ResetPassword.Successfully.SuccessfullyActivity
import kotlin.reflect.KClass

class ResetPasswordActivity : BaseActivity<ActivityResetPasswordBinding, AuthViewModel>() {
    override fun resourceId(): Int = R.layout.activity_reset_password

    override fun viewModelClass(): KClass<AuthViewModel> = AuthViewModel::class
    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel
        dataBinding.toolbar.tvTittle.text = getString(R.string.reset_password)
        dataBinding.btnResetPassword.tvCommonTitle.text = getString(R.string.reset)
        viewModel.viewPhoneLiveData.value = intent.getStringExtra("phone")
    }

    override fun observer() {
        viewModel.loginResponse.observe(this) {
            openActivity(SuccessfullyActivity::class.java)
            finishAffinity()
        }
    }

    override fun clicks() {
        dataBinding.btnResetPassword.tvCommonTitle.setOnClickListener {

            viewModel.resetPassword(this, intent.getStringExtra("countryCode").toString())

        }
        dataBinding.toolbar.ivBack.setOnClickListener {
            onBackPressed()
        }

    }

    override fun callApis() {

    }
}
package com.olar.ui.auth.Login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.olar.R
import com.olar.Utils.MyUtils.myToastAction2Button
import com.olar.Utils.MyUtils.openActivity
import com.olar.app.Application
import com.olar.base.BaseActivity
import com.olar.databinding.ActivityLoginBinding
import com.olar.databinding.BottomSheetForgetPasswordBinding
import com.olar.ui.home.HomeActivity
import com.olar.ui.auth.AuthViewModel
import com.olar.ui.auth.ConfrimYourMobile.ConfirmYourMobileActivity
import com.olar.ui.auth.ResetPassword.ResetPasswordActivity
import com.olar.ui.auth.Signup.SignupActivity
import kotlin.reflect.KClass

class LoginActivity : BaseActivity<ActivityLoginBinding, AuthViewModel>() {


    lateinit var bottomSheetDialog: BottomSheetDialog
    lateinit var bottomSheetForgetBinding: BottomSheetForgetPasswordBinding
    override fun resourceId(): Int = R.layout.activity_login

    override fun viewModelClass(): KClass<AuthViewModel> = AuthViewModel::class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.countryCodePicker.registerCarrierNumberEditText(dataBinding.edMobile)

    }

    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel
        initView()
    }

    override fun observer() {
        viewModel.loginResponse.observe(this) {
            openActivity(HomeActivity::class.java)
            finishAffinity()
        }
        viewModel.forgetPasswordResponse.observe(this){
            openActivity(
                ResetPasswordActivity::class.java,
                Intent().putExtra("type", "phone")
                    .putExtra("phone", viewModel.viewPhoneForgetLiveData.value)
                    .putExtra("countryCode",  bottomSheetForgetBinding.countryCodePicker.selectedCountryNameCode)
            )
        }
    }

    override fun clicks() {

        dataBinding.btnRegister.setOnClickListener {
            openActivity(SignupActivity::class.java)
        }
        dataBinding.tvforgot.setOnClickListener {
            bottomSheetForget()
        }
        dataBinding.btnLanguage.setOnClickListener {
            var massage = resources.getString(R.string.changeToArabic)
            if (Application.language == "ar"){
                massage = resources.getString(R.string.changeToEnglish)
            }else{
                massage = resources.getString(R.string.changeToArabic)
            }
            myToastAction2Button(massage){
                changeAppLang()
            }

        }
        dataBinding.edPassword.setOnEditorActionListener { v, actionId, event ->
            if (actionId === EditorInfo.IME_ACTION_DONE) {
                if (dataBinding.countryCodePicker.isValidFullNumber) {
                    appManger.setCountry(dataBinding.countryCodePicker.selectedCountryNameCode)
                    appManger.setPhoneCode(dataBinding.countryCodePicker.selectedCountryCode)
                    viewModel.login(this, dataBinding.countryCodePicker.selectedCountryNameCode)
                } else {
                    viewModel._viewPhoneLiveDataError.value =
                        resources.getString(R.string.enter_valid_phone)
                }
            }
            true
        }

        dataBinding.btnLogin.tvCommonTitle.setOnClickListener {
            if (dataBinding.countryCodePicker.isValidFullNumber) {
                appManger.setCountry(dataBinding.countryCodePicker.selectedCountryNameCode)
                appManger.setPhoneCode(dataBinding.countryCodePicker.selectedCountryCode)
                viewModel.login(this, dataBinding.countryCodePicker.selectedCountryNameCode)
            } else {
                viewModel._viewPhoneLiveDataError.value =
                    resources.getString(R.string.enter_valid_phone)
            }
        }
        dataBinding.edPassword.setOnEditorActionListener { v, actionId, event ->
            if (actionId === EditorInfo.IME_ACTION_DONE) {
                dataBinding.btnLogin.tvCommonTitle.performClick()
            }
            true
        }
    }

    override fun callApis() {

    }

    private fun initView() {
        dataBinding.btnLogin.tvCommonTitle.text = getString(R.string.login)

    }

    private fun bottomSheetForget() {
        bottomSheetForgetBinding =
            BottomSheetForgetPasswordBinding.inflate(LayoutInflater.from(this))

        bottomSheetDialog =
            BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme).apply {
                setContentView(bottomSheetForgetBinding.root)
            }
        bottomSheetForgetBinding.lifecycleOwner = this
        bottomSheetForgetBinding.viewModel = viewModel

        bottomSheetForgetBinding.countryCodePicker.registerCarrierNumberEditText(bottomSheetForgetBinding.edMobile)

        bottomSheetForgetBinding.btnConfirm.setOnClickListener {
            if (bottomSheetForgetBinding.countryCodePicker.isValidFullNumber) {
                viewModel._viewPhoneForgetLiveDataError.value = ""
                appManger.setCountry(bottomSheetForgetBinding.countryCodePicker.selectedCountryNameCode)
                appManger.setPhoneCode(bottomSheetForgetBinding.countryCodePicker.selectedCountryCode)
                viewModel.forgetPassword(
                    bottomSheetForgetBinding.countryCodePicker.selectedCountryNameCode
                )
                bottomSheetDialog.dismiss()
            } else {
                viewModel._viewPhoneForgetLiveDataError.value =
                    resources.getString(R.string.enter_valid_phone)
            }
        }
        bottomSheetDialog.setOnShowListener { dialog ->
            val layout: FrameLayout? =
                (dialog as BottomSheetDialog).findViewById(com.google.android.material.R.id.design_bottom_sheet)
            layout?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.skipCollapsed = true
            }
        }
        bottomSheetDialog.show()
    }


    override fun onNotVerifyRequest(exception: String?) {
        openActivity(
            ConfirmYourMobileActivity::class.java,
            Intent()
                .putExtra("phone", viewModel.viewPhoneLiveData.value)
                .putExtra("code", dataBinding.countryCodePicker.selectedCountryNameCode)
                .putExtra("country", dataBinding.countryCodePicker.selectedCountryName)
                .putExtra(
                    "phone_code",
                    dataBinding.countryCodePicker.selectedCountryCodeWithPlus
                )
                .putExtra(
                    "phone_with_code",
                    viewModel.viewPhoneLiveData.value.plus(" ")
                        .plus(dataBinding.countryCodePicker.selectedCountryCode)
                )
                .putExtra("country_code", dataBinding.countryCodePicker.selectedCountryNameCode)

        )
    }
}
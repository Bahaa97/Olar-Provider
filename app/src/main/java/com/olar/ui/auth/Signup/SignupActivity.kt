package com.olar.ui.auth.Signup

import android.content.Intent
import android.os.Bundle
import com.olar.R
import com.olar.Utils.MyUtils.openActivity
import com.olar.base.BaseActivity
import com.olar.databinding.ActivitySignupBinding
import com.olar.ui.auth.AuthViewModel
import com.olar.ui.auth.ConfrimYourMobile.ConfirmYourMobileActivity
import com.olar.ui.appUtils.HtmlActivity
import kotlin.reflect.KClass

class SignupActivity : BaseActivity<ActivitySignupBinding, AuthViewModel>() {
    override fun resourceId(): Int = R.layout.activity_signup

    override fun viewModelClass(): KClass<AuthViewModel> = AuthViewModel::class


    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel
        dataBinding.btnNext.tvCommonTitle.text = getString(R.string.sign_up)
        viewModel.viewCountryLiveData.value =
            dataBinding.countryCodePicker.selectedCountryName.toString()
        dataBinding.countryCodePicker.registerCarrierNumberEditText(dataBinding.edMobile)
    }

    override fun observer() {
        viewModel.registerCodeSendResponse.observe(this) {
            openActivity(
                ConfirmYourMobileActivity::class.java,
                Intent()
                    .putExtra("phone", viewModel.viewPhoneLiveData.value)
                    .putExtra("firstName", viewModel.viewFirstNameLiveData.value)
                    .putExtra("lastName", viewModel.viewLastNameLiveData.value)
                    .putExtra("referCode", viewModel.viewReferCodeLiveData.value)
                    .putExtra("country_code", dataBinding.countryCodePicker.selectedCountryNameCode)
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
            )
        }
    }

    override fun clicks() {

        dataBinding.btnNext.tvCommonTitle.setOnClickListener {

            if (dataBinding.countryCodePicker.isValidFullNumber) {
                appManger.setCountry(dataBinding.countryCodePicker.selectedCountryNameCode)
                appManger.setPhoneCode(dataBinding.countryCodePicker.selectedCountryCode)
                viewModel.register(this,dataBinding.checkbox.isChecked, true,dataBinding.countryCodePicker.selectedCountryNameCode)
            } else {
                viewModel.register(this,dataBinding.checkbox.isChecked, false,"")

            }
        }
        dataBinding.countryCodePicker.setOnCountryChangeListener {
            viewModel.viewCountryLiveData.value =
                dataBinding.countryCodePicker.selectedCountryName.toString()
        }
        dataBinding.btnTerms.setOnClickListener {
            openActivity(
                HtmlActivity::class.java,
                Intent().putExtra("title", viewModel.pageResponse.value?.data?.name).putExtra("body", viewModel.pageResponse.value?.data?.text)
            )
        }
    }

    override fun callApis() {
        viewModel.getPage(1)
    }

    override fun onNotVerifyRequest(exception: String?) {
        openActivity(
            ConfirmYourMobileActivity::class.java,
            Intent()
                .putExtra("phone", viewModel.viewPhoneLiveData.value)
                .putExtra("firstName", viewModel.viewFirstNameLiveData.value)
                .putExtra("lastName", viewModel.viewLastNameLiveData.value)
                .putExtra("referCode", viewModel.viewReferCodeLiveData.value)
                .putExtra("country_code", dataBinding.countryCodePicker.selectedCountryNameCode)
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
        )
    }
}
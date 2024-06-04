package com.olar.ui.auth.ConfrimYourMobile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.olar.R
import com.olar.Utils.MyUtils.openActivity
import com.olar.base.BaseActivity
import com.olar.databinding.ActivityConfirmYourMobileBinding
import com.olar.ui.home.HomeActivity
import com.olar.ui.auth.AuthViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

class ConfirmYourMobileActivity : BaseActivity<ActivityConfirmYourMobileBinding, AuthViewModel>() {

    private var timerDisposable: Disposable? = null

    override fun resourceId(): Int = R.layout.activity_confirm_your_mobile

    override fun viewModelClass(): KClass<AuthViewModel> = AuthViewModel::class


    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel
        initView()
        startTime()
    }

    override fun observer() {
        viewModel.loginResponse.observe(this) {
//            openActivity(AddAddressActivity::class.java,intent.putExtra("type","register"))
            finishAffinity()
        }
        viewModel.loginWithPhoneResponse.observe(this) {
            Toast.makeText(
                this,
                resources.getString(R.string.code_send_seccussfuly),
                Toast.LENGTH_LONG
            ).show()
            startTime()
        }
        viewModel.registerCodeSendResponse.observe(this) {
            Toast.makeText(
                this,
                resources.getString(R.string.code_send_seccussfuly),
                Toast.LENGTH_LONG
            ).show()
            startTime()
        }
        viewModel.changePhoneVerifyResponse.observe(this) {
            Toast.makeText(this, resources.getString(R.string.phone_changed), Toast.LENGTH_SHORT)
                .show()
            appManger.setCountry(intent.getStringExtra("country_code").toString())
            appManger.setPhoneCode(intent.getStringExtra("phone_code").toString())
            finishAffinity()
            openActivity(HomeActivity::class.java)
        }
        viewModel.changeEmailVerifyResponse.observe(this) {
            Toast.makeText(this, resources.getString(R.string.email_changed), Toast.LENGTH_SHORT)
                .show()
            finishAffinity()
            openActivity(HomeActivity::class.java)
        }
    }

    override fun clicks() {
        dataBinding.ivBack.setOnClickListener {
            onBackPressed()
        }
        dataBinding.txtPhone.setOnClickListener {
            onBackPressed()
        }
        dataBinding.continerResend.setOnClickListener {
            if (intent.getStringExtra("type") == "login") {
                viewModel.loginWithPhone(this, appManger.getCountry())
            } else if (intent.getStringExtra("type") == "change") {
                viewModel.viewPhoneForgetLiveData.value = intent.getStringExtra("phone").toString()
                viewModel.editUserPhone(intent.getStringExtra("country_code").toString())
            }else {
                viewModel.registerSendCode(intent.getStringExtra("country_code").toString())
            }
        }
        dataBinding.btnConfirm.tvCommonTitle.setOnClickListener {
            if (intent.getStringExtra("type") == "login") {
                viewModel.verifyPhoneLogin(this,intent.getStringExtra("country_code").toString())
            } else if (intent.getStringExtra("type") == "change") {
                viewModel.verifyPhone(this,intent.getStringExtra("phone").toString(),intent.getStringExtra("country_code").toString())
            } else {
                viewModel.verifyPhoneRegister(this,intent.getStringExtra("country_code").toString())
            }
        }


    }

    override fun callApis() {

    }

    private fun initView() {
        dataBinding.btnConfirm.tvCommonTitle.text = getString(R.string.verify_now)


        if (intent.getStringExtra("type") == "email") {
            dataBinding.txtPhone.text = intent.getStringExtra("email").toString()
            dataBinding.textView8.text = resources.getString(R.string.confirm_your_email)
            viewModel.viewEmailLiveData.value = intent.getStringExtra("email")
        } else {
            dataBinding.textView8.text = resources.getString(R.string.confirm_your_mobile)
            dataBinding.txtPhone.text = intent.getStringExtra("phone")

            viewModel.viewPhoneLiveData.value = intent.getStringExtra("phone")

            dataBinding.txtPhoneCode.text =  intent.getStringExtra("phone_code").toString()
        }
    }

    fun startTime() {
        timerDisposable?.dispose()
        timerDisposable = Observable.interval(1, TimeUnit.SECONDS)
            .take(60)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.compareTo(59) == 0) {
                    dataBinding.continerResend.visibility = View.VISIBLE
                    dataBinding.layResendCode.isVisible = false
                    dataBinding.tvVerifyTimerTitle.isVisible = false

                } else {
                    dataBinding.continerResend.visibility = View.INVISIBLE
                    dataBinding.layResendCode.isVisible = true
                    dataBinding.tvVerifyTimerTitle.isVisible = true
                    dataBinding.tvVerifyTimerTitle.text =
                        resources.getString(R.string.code_expire_at)
                    dataBinding.tvVerifyTimerAction.text = String()
                        .plus(String.format(Locale.US, "%02d", 60 - it))
                        .plus(" ")
                }
            }, {

            })
    }

    override fun onDestroy() {
        super.onDestroy()
        timerDisposable?.dispose()
    }

    override fun onNotVerifyRequest(exception: String?) {
        Toast.makeText(this,resources.getString(R.string.code_resended),Toast.LENGTH_SHORT).show()
    }

}
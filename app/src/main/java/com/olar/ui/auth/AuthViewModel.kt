package com.olar.ui.auth

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.olar.base.BaseViewModel
import com.abora.perfectobase.data.models.*
import com.google.gson.JsonObject
import com.olar.R
import com.olar.Utils.AppManger
import com.olar.Utils.MyUtils
import com.olar.Utils.MyUtils.checkMatchedPassword
import com.olar.Utils.MyUtils.checkPassword
import com.olar.Utils.MyUtils.checkTextIsEmptyOrNot
import com.olar.data.remote.reporsitory.MainRepository
import kotlinx.coroutines.launch
import java.io.File

class AuthViewModel constructor(
    override var mainRepository: MainRepository,
    override val appManger: AppManger
) : BaseViewModel(mainRepository, appManger) {

    //Responses
    val loginResponse = MutableLiveData<LoginDataModel?>()
    val loginWithPhoneResponse = MutableLiveData<DefaultData?>()
    val registerFirstStepResponse = MutableLiveData<Boolean>()
    val registerCodeSendResponse = MutableLiveData<DefaultData>()
    val registerVerifyPhoneResponse = MutableLiveData<DefaultData>()
    val forgetPasswordResponse = MutableLiveData<DefaultData>()
    val changePasswordResponse = MutableLiveData<DefaultData>()
    val deleteAddressResponse = MutableLiveData<DefaultData>()
    val addAddressResponse = MutableLiveData<AddressDataModel?>()


    //Fields
    var viewPasswordLiveData = MutableLiveData<String>()
    var viewPhoneForgetLiveData = MutableLiveData<String>()
    var viewOldPasswordLiveData = MutableLiveData<String>()
    var viewEmailLiveData = MutableLiveData<String>()
    var viewUserNameLiveData = MutableLiveData<String>()
    var viewUserDateLiveData = MutableLiveData<String>()
    var viewImageLiveData = MutableLiveData<File?>()
    var viewFirstNameLiveData = MutableLiveData<String>()
    var viewLastNameLiveData = MutableLiveData<String>()
    var viewCountryLiveData = MutableLiveData<String>()
    var viewConfirmNewPasswordLiveData = MutableLiveData<String>()
    var viewReferCodeLiveData = MutableLiveData<String>()
    var viewUserGenderLiveData = MutableLiveData<String>()
    var userGenderLiveData = MutableLiveData<String>()
    var placeLiveData = MutableLiveData<String>()
    var regionLiveData = MutableLiveData<String>()
    var cityLiveData = MutableLiveData<String>()
    var areaLiveData = MutableLiveData<String>()
    var addressLiveData = MutableLiveData<String>()
    var viewDateLiveData = MutableLiveData<String>()
    var viewTimeLiveData = MutableLiveData<String>()
    var viewHouseLiveData = MutableLiveData<String>()
    var viewFamilyLiveData = MutableLiveData<String>()

    var regionIdLiveData = MutableLiveData<Int>()
    var cityIdLiveData = MutableLiveData<Int>()
    var areaIdLiveData = MutableLiveData<Int>()
    var latLiveData = MutableLiveData<String>()
    var lngLiveData = MutableLiveData<String>()


    //Error Fields
    val _viewPhoneLiveDataError = MutableLiveData<String?>()
    val _viewPhoneForgetLiveDataError = MutableLiveData<String?>()
    val _viewPasswordLiveDataError = MutableLiveData<String?>()
    val _viewOldPasswordLiveDataError = MutableLiveData<String?>()
    val _viewUserNameLiveDataError = MutableLiveData<String?>()
    val _viewUserDateLiveDataError = MutableLiveData<String?>()
    val _viewFirstNameLiveDataError = MutableLiveData<String?>()
    val _viewUserLastNameLiveDataError = MutableLiveData<String?>()
    val _viewCountryLiveDataError = MutableLiveData<String?>()
    val _viewCodeLiveDataError = MutableLiveData<String?>()
    val _viewEmailLiveDataError = MutableLiveData<String?>()
    val _viewHouseLiveDataError = MutableLiveData<String?>()
    val _viewFamilyNumberLiveDataError = MutableLiveData<String?>()
    val _viewConfirmPasswordLiveDataError = MutableLiveData<String?>()
    val _viewUserGenderLiveDataError = MutableLiveData<String?>()
    var _placeLiveDataError = MutableLiveData<String?>()
    var _cityLiveDataError = MutableLiveData<String?>()
    var _regionLiveDataError = MutableLiveData<String?>()
    var _areaLiveDataError = MutableLiveData<String?>()
    var _addressLiveDataError = MutableLiveData<String?>()
    var _viewDateLiveDataError = MutableLiveData<String?>()
    var _viewTimeLiveDataError = MutableLiveData<String?>()


    fun login(context: Context, countryCode: String) {
        _viewPhoneLiveDataError.value = ""
        _viewPasswordLiveDataError.value = ""
        if (checkPassword(viewPasswordLiveData.value)) {
            _viewPasswordLiveDataError.value = context.getString(R.string.enter_valid_password)
            return
        }

        val requestData = JsonObject()
        requestData.addProperty("country_code", countryCode)
        requestData.addProperty("type", "user")
        requestData.addProperty("phone", viewPhoneLiveData.value)
        requestData.addProperty("password", viewPasswordLiveData.value)

        loading.value = true
        viewModelScope.launch {
            mainRepository.login(networkStatus, requestData).let {
                loading.value = false
                if (it.data?.data != null) {
                    val loginData = it.data.data

                    appManger.saveUserData(loginData)

                    loginResponse.postValue(loginData)
                }

            }
        }
    }

    fun loginWithPhone(context: Context, countryCode: String) {
        _viewPhoneLiveDataError.value = null
        _viewUserNameLiveDataError.value = null
        _viewPasswordLiveDataError.value = null

        val requestData = JsonObject()
        requestData.addProperty("type", "user")
        requestData.addProperty("country_code", countryCode)
        requestData.addProperty("phone", viewPhoneLiveData.value)

        loading.value = true
        viewModelScope.launch {
            mainRepository.loginWithPhone(networkStatus, requestData).let {
                loading.value = false
                if (it.data?.data != null) {
                    val loginData = it.data.data

                    loginWithPhoneResponse.postValue(loginData)
                }

            }
        }
    }

    fun verifyPhoneLogin(context: Context,countryCode:String) {
        if (checkTextIsEmptyOrNot(viewCodeLiveData.value) || viewCodeLiveData.value!!.length < 5) {
            showMassage.postValue(context.getString(R.string.please_enter_verify_code))
            return
        }
        val requestData = JsonObject()
        requestData.addProperty("phone", viewPhoneLiveData.value)
        requestData.addProperty("code", viewCodeLiveData.value)
        requestData.addProperty("country_code", countryCode)
        requestData.addProperty("type", "user")

        loading.value = true
        viewModelScope.launch {
            mainRepository.verifyLoginWithPhone(networkStatus, requestData).let {
                loading.value = false
                if (it.data?.data != null) {
                    val loginData = it.data.data

                    appManger.saveUserData(loginData)

                    loginResponse.postValue(loginData)
                }

            }
        }
    }

    fun forgetPassword(countryCode: String? = "") {
        _viewPhoneForgetLiveDataError.value = null

        val requestData = JsonObject()

        requestData.addProperty("phone", viewPhoneForgetLiveData.value)
        requestData.addProperty("type", "user")
        requestData.addProperty("country_code", countryCode)

        loading.value = true
        viewModelScope.launch {
            mainRepository.forgetPassword(networkStatus, requestData).let {
                loading.value = false
                if (it.data?.data != null) {
                    forgetPasswordResponse.postValue(it.data.data)
                }
            }
        }
    }


    fun resetPassword(context: Context, countryCode: String) {
        _viewPasswordLiveDataError.value = null
        _viewConfirmPasswordLiveDataError.value = null


        if (checkTextIsEmptyOrNot(viewCodeLiveData.value) || viewCodeLiveData.value!!.length < 5) {
            _viewCodeLiveDataError.value = context.getString(R.string.please_enter_verify_code)
            return
        }
        if (checkPassword(viewPasswordLiveData.value)) {
            _viewPasswordLiveDataError.value = context.getString(R.string.enter_valid_password)
            return
        }

        if (checkPassword(viewConfirmNewPasswordLiveData.value)) {
            _viewConfirmPasswordLiveDataError.value =
                context.getString(R.string.enter_valid_password)
            return
        }

        if (checkMatchedPassword(
                viewPasswordLiveData.value,
                viewConfirmNewPasswordLiveData.value
            )
        ) {
            _viewConfirmPasswordLiveDataError.value =
                context.getString(R.string.enter_match_password)
            return
        }

        val requestData = JsonObject()
        requestData.addProperty("type", "user")
        requestData.addProperty("phone", viewPhoneLiveData.value)
        requestData.addProperty("code", viewCodeLiveData.value)
        requestData.addProperty("country_code", countryCode)
        requestData.addProperty("password", viewPasswordLiveData.value)
        requestData.addProperty("password_confirmation", viewConfirmNewPasswordLiveData.value)

        loading.value = true
        viewModelScope.launch {
            mainRepository.resetVerifyCodePassword(networkStatus, requestData).let {
                loading.value = false
                if (it.data?.data != null) {
                    val loginData = it.data.data

                    appManger.saveUserData(loginData)

                    loginResponse.postValue(loginData)
                }
            }
        }
    }


    fun register(context: Context,isTermsChecked:Boolean, isValidPhone: Boolean, countryCode: String) {
        _viewUserNameLiveDataError.value = null
        _viewPasswordLiveDataError.value = null
        _viewUserLastNameLiveDataError.value = null
        _viewEmailLiveDataError.value = null
        _viewPhoneLiveDataError.value = null

        if (checkTextIsEmptyOrNot(viewUserNameLiveData.value)) {
            _viewUserNameLiveDataError.value = context.getString(R.string.enter_valid_name)
            return
        }
        if (!isValidPhone) {
            _viewPhoneLiveDataError.value =
                context.resources.getString(R.string.enter_valid_phone)
            return
        }
        if (checkTextIsEmptyOrNot(viewEmailLiveData.value)) {
            _viewEmailLiveDataError.value = context.getString(R.string.enter_valid_email)
            return
        }

        if (MyUtils.emailNotValid(viewEmailLiveData.value!!.replace(" ", ""))) {
            _viewEmailLiveDataError.value = context.getString(R.string.enter_valid_email)
            return
        }

        if (checkPassword(viewPasswordLiveData.value)) {
            _viewPasswordLiveDataError.value = context.getString(R.string.enter_valid_password)
            return
        }
        if (isTermsChecked.not()) {
            Toast.makeText(context,context.resources.getString(R.string.accept_terms), Toast.LENGTH_SHORT).show()
            return
        }
        registerFirstStepResponse.value = true
        val requestData = JsonObject()
        requestData.addProperty("type", "user")
        requestData.addProperty("name", viewUserNameLiveData.value)
        requestData.addProperty("country_code", countryCode)
        requestData.addProperty("phone", viewPhoneLiveData.value)
        requestData.addProperty("email", viewEmailLiveData.value)
        requestData.addProperty("password", viewPasswordLiveData.value)
        requestData.addProperty("password_confirmation", viewPasswordLiveData.value)

        loading.value = true
        viewModelScope.launch {
            mainRepository.userRegister(networkStatus, requestData).let {
                loading.value = false
                if (it.data?.data != null) {
                    loading.value = false
                    if (it.data?.data != null) {
                        loginResponse.postValue(it.data.data)
                        appManger.saveUserData(it.data.data)
                    }
                }

            }
        }
    }


    fun registerSendCode(country_code: String) {
        val requestData = JsonObject()
        requestData.addProperty("phone", viewPhoneLiveData.value)
        requestData.addProperty("country_code", country_code)
        requestData.addProperty("type", "user")

        loading.value = true
        viewModelScope.launch {
            mainRepository.registerSendCode(networkStatus, requestData).let {
                loading.value = false
                if (it.data?.data != null) {
                    val loginData = it.data.data

                    registerCodeSendResponse.postValue(loginData)
                }

            }
        }
    }

    fun verifyPhoneRegister(context: Context, countryCode: String) {
        if (checkTextIsEmptyOrNot(viewCodeLiveData.value) || viewCodeLiveData.value!!.length < 5) {
            showMassage.postValue(context.getString(R.string.please_enter_verify_code))
            return
        }
        val requestData = JsonObject()
        requestData.addProperty("phone", viewPhoneLiveData.value)
        requestData.addProperty("code", viewCodeLiveData.value)
        requestData.addProperty("country_code", countryCode)
        requestData.addProperty("type", "user")

        loading.value = true
        viewModelScope.launch {
            mainRepository.verifyPhoneRegister(networkStatus, requestData).let {
                loading.value = false
                if (it.data?.data != null) {
                    val loginData = it.data.data
                    appManger.saveUserData(it.data.data)
                    loginResponse.postValue(loginData)
                }

            }
        }
    }


    fun changePassword(context: Context) {

        _viewOldPasswordLiveDataError.value = null
        _viewPasswordLiveDataError.value = null
        _viewConfirmPasswordLiveDataError.value = null

        if (checkPassword(viewOldPasswordLiveData.value)) {
            _viewOldPasswordLiveDataError.value = context.getString(R.string.enter_valid_password)
            return
        }
        if (checkPassword(viewPasswordLiveData.value)) {
            _viewPasswordLiveDataError.value = context.getString(R.string.enter_valid_password)
            return
        }
        if (checkPassword(viewConfirmNewPasswordLiveData.value)) {
            _viewConfirmPasswordLiveDataError.value =
                context.getString(R.string.enter_valid_password)
            return
        }


        if (checkMatchedPassword(
                viewPasswordLiveData.value,
                viewConfirmNewPasswordLiveData.value
            )
        ) {
            _viewConfirmPasswordLiveDataError.value =
                context.getString(R.string.enter_match_password)
            return
        }

        loading.value = true
        viewModelScope.launch {
            mainRepository.changePassword(
                networkStatus,
                viewOldPasswordLiveData.value!!,
                viewPasswordLiveData.value!!
            ).let {
                loading.value = false
                if (it.data?.data != null) {
                    val loginData = it.data.data
                    changePasswordResponse.postValue(loginData)
                }

            }
        }
    }


    fun register(context: Context) {
        _viewEmailLiveDataError.value = null
        _viewUserNameLiveDataError.value = null
        _viewUserDateLiveDataError.value = null
        _viewUserGenderLiveDataError.value = null
        _viewPasswordLiveDataError.value = null
        _viewConfirmPasswordLiveDataError.value = null


        if (checkTextIsEmptyOrNot(viewEmailLiveData.value)) {
            _viewEmailLiveDataError.value = context.getString(R.string.enter_valid_email)
            return
        }

        if (MyUtils.emailNotValid(viewEmailLiveData.value!!.replace(" ", ""))) {
            _viewEmailLiveDataError.value = context.getString(R.string.enter_valid_email)
            return
        }
        if (checkTextIsEmptyOrNot(viewUserNameLiveData.value)) {
            _viewUserNameLiveDataError.value = context.getString(R.string.enter_valid_name)
            return
        }
        if (checkTextIsEmptyOrNot(viewUserDateLiveData.value)) {
            _viewUserDateLiveDataError.value = context.getString(R.string.enter_date)
            return
        }
        if (checkPassword(viewPasswordLiveData.value)) {
            _viewPasswordLiveDataError.value = context.getString(R.string.enter_valid_password)
            return
        }
        if (checkPassword(viewConfirmNewPasswordLiveData.value)) {
            _viewConfirmPasswordLiveDataError.value =
                context.getString(R.string.enter_valid_password)
            return
        }


        if (checkMatchedPassword(
                viewPasswordLiveData.value,
                viewConfirmNewPasswordLiveData.value
            )
        ) {
            _viewConfirmPasswordLiveDataError.value =
                context.getString(R.string.enter_match_password)
            return
        }

        val requestData = JsonObject()
        requestData.addProperty("first_name", viewFirstNameLiveData.value)
        requestData.addProperty("last_name", viewLastNameLiveData.value)
        requestData.addProperty("country", viewCountryLiveData.value)
        requestData.addProperty("country_code", appManger.getCountry())
        requestData.addProperty("phone", viewPhoneLiveData.value)
        requestData.addProperty("phone_code", appManger.getPhoneCode())
        requestData.addProperty("referral_code", viewReferCodeLiveData.value)
        requestData.addProperty("email", viewEmailLiveData.value!!.replace(" ", ""))
        requestData.addProperty("username", viewUserNameLiveData.value)
        requestData.addProperty("birthdate", viewUserDateLiveData.value)
        requestData.addProperty("type", "user")
        requestData.addProperty("password", viewPasswordLiveData.value)
        requestData.addProperty("password_confirmation", viewPasswordLiveData.value)
        requestData.addProperty("gender", userGenderLiveData.value)

        loading.value = true
        viewModelScope.launch {
            2
            mainRepository.userRegister(networkStatus, requestData).let {
                loading.value = false
                if (it.data?.data != null) {
                    loginResponse.postValue(it.data.data)
                    appManger.saveUserData(it.data.data)
                }

            }
        }

    }


    fun editUserData(context: Context) {

        _viewUserNameLiveDataError.value = null
        _viewEmailLiveDataError.value = null

        if (checkTextIsEmptyOrNot(viewUserNameLiveData.value)) {
            _viewFirstNameLiveDataError.value = context.getString(R.string.enter_valid_first_name)
            return
        }
        if (viewEmailLiveData.value == null || MyUtils.emailNotValid(
                viewEmailLiveData.value!!.replace(
                    " ",
                    ""
                )
            )
        ) {
            _viewEmailLiveDataError.value = context.getString(R.string.enter_valid_email)
            return
        }
        if (viewFamilyLiveData.value.isNullOrEmpty()){
            viewFamilyLiveData.value = "1"
        }

        loading.value = true
        viewModelScope.launch {
            mainRepository.editUserData(
                networkStatus,
                viewUserNameLiveData.value,
                viewEmailLiveData.value,
                viewFamilyLiveData.value,
                viewHouseLiveData.value,
                viewImageLiveData.value,
            ).let {
                loading.value = false
                if (it.data != null) {
                    profileDataResponse.postValue(it.data.data)
                }
            }
        }
    }


    fun editUserPhone(countryCode: String) {

        val requestData = JsonObject()
        requestData.addProperty("phone", viewPhoneForgetLiveData.value)
        requestData.addProperty("phone_code", viewCodeLiveData.value)
        requestData.addProperty("country_code", countryCode)
        requestData.addProperty("type", "user")

        loading.value = true
        viewModelScope.launch {
            mainRepository.changePhone(
                networkStatus,
                requestData
            ).let {
                loading.value = false
                if (it.data != null) {

                    changePhoneResponse.postValue(it.data.data)
                }
            }
        }
    }

    fun editAddress(context: Context, id: Int) {
        _placeLiveDataError.value = null
        _cityLiveDataError.value = null
        _areaLiveDataError.value = null

        if (checkTextIsEmptyOrNot(placeLiveData.value)) {
            _placeLiveDataError.value = context.getString(R.string.enter_valid_place_name)
            return
        }
        if (cityIdLiveData.value == null) {
            _cityLiveDataError.value = context.getString(R.string.select_city)
            return
        }
        if (areaIdLiveData.value == null) {
            _areaLiveDataError.value = context.getString(R.string.select_area)
            return
        }

        if (latLiveData.value == null) {
            showMassage.value = context.getString(R.string.select_location)
            return
        }
        val requestData = JsonObject()
        requestData.addProperty("_method", "PUT")
        requestData.addProperty("title", placeLiveData.value)
        requestData.addProperty("lat", latLiveData.value)
        requestData.addProperty("lng", lngLiveData.value)
        requestData.addProperty("region_id", regionIdLiveData.value)
        requestData.addProperty("city_id", cityIdLiveData.value)
        requestData.addProperty("district_id", areaIdLiveData.value)
        requestData.addProperty("type", addressType.value)

        loading.value = true
        viewModelScope.launch {
            mainRepository.updateAddress(
                networkStatus, id, requestData
            ).let {
                loading.value = false
                appManger.saveUserAddress(it.data?.data)
                addAddressResponse.postValue(it.data!!.data)
            }
        }
    }

    fun addAddress(context: Context) {
        _placeLiveDataError.value = null
        _addressLiveDataError.value = null
        _cityLiveDataError.value = null
        _areaLiveDataError.value = null

        if (checkTextIsEmptyOrNot(placeLiveData.value)) {
            _placeLiveDataError.value = context.getString(R.string.enter_valid_place_name)
            return
        }
        if (regionIdLiveData.value == null) {
            _regionLiveDataError.value = context.getString(R.string.select_region)
            return
        }
        if (cityIdLiveData.value == null) {
            _cityLiveDataError.value = context.getString(R.string.select_city)
            return
        }
        if (areaIdLiveData.value == null) {
            _areaLiveDataError.value = context.getString(R.string.select_area)
            return
        }

        if (latLiveData.value == null) {
            showMassage.value = context.getString(R.string.select_location)
            return
        }
        val requestData = JsonObject()
        requestData.addProperty("title", placeLiveData.value)
        requestData.addProperty("lat", latLiveData.value)
        requestData.addProperty("lng", lngLiveData.value)
        requestData.addProperty("city_id", cityIdLiveData.value)
        requestData.addProperty("region_id", regionIdLiveData.value)
        requestData.addProperty("district_id", areaIdLiveData.value)
        requestData.addProperty("type", addressType.value)

        loading.value = true
        viewModelScope.launch {
            mainRepository.addAddress(
                networkStatus, requestData
            ).let {
                loading.value = false
                appManger.saveUserAddress(it.data?.data!!)
                addAddressResponse.postValue(it.data.data)
            }
        }
    }


    fun deleteAddress(id: Int) {
        loading.value = true
        viewModelScope.launch {
            mainRepository.deleteAddress(
                networkStatus, id
            ).let {
                loading.value = false
                deleteAddressResponse.postValue(it.data?.data)
            }
        }
    }

    fun deleteAccount() {
        loading.value = true
        viewModelScope.launch {
            mainRepository.deleteAccount(
                networkStatus
            ).let {
                loading.value = false
                deleteAccountResponse.postValue(it.data?.data)
            }
        }
    }

    fun changeEmail(context: Context) {
        _viewEmailLiveDataError.value = null
        if (viewEmailLiveData.value == null || MyUtils.emailNotValid(
                viewEmailLiveData.value!!.replace(
                    " ",
                    ""
                )
            )
        ) {
            _viewEmailLiveDataError.value = context.getString(R.string.enter_valid_email)
            return
        }
        val requestData = JsonObject()
        requestData.addProperty("email", viewEmailLiveData.value)

        loading.value = true
        viewModelScope.launch {
            mainRepository.changeEmail(
                networkStatus,
                requestData
            ).let {
                loading.value = false
                if (it.data != null) {
                    changeEmailResponse.postValue(it.data.data)
                }
            }
        }
    }


}
package com.olar.base

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abora.perfectobase.data.models.*
import com.abora.perfectobase.data.remote.networkHandling.NetworkStatus
import com.olar.data.remote.reporsitory.MainRepository
import com.google.gson.JsonObject
import com.olar.R
import com.olar.Utils.AppManger
import com.olar.Utils.MyUtils
import com.olar.data.models.OrderDataModel
import com.olar.data.models.ProductDataModel
import kotlinx.coroutines.launch


open class BaseViewModel(
    open var mainRepository: MainRepository,
    open val appManger: AppManger
) : ViewModel() {
    lateinit var networkStatus: NetworkStatus
    val loading = MutableLiveData<Boolean>()
    val showMassage = MutableLiveData<String>()
    val addressType = MutableLiveData<String?>()

    var viewPhoneLiveData = MutableLiveData<String>()
    var viewCodeLiveData = MutableLiveData<String>()
    var notificationCountResponse = MutableLiveData<DefaultData>()

    val changePhoneResponse = MutableLiveData<DefaultData>()
    val deleteAccountResponse = MutableLiveData<DefaultData>()
    val addressResponse = MutableLiveData<List<AddressDataModel>>()
    val machinesAddressResponse = MutableLiveData<List<AddressDataModel>>()
    val machinesQrResponse = MutableLiveData<MachineQrModel>()
    val changeEmailResponse = MutableLiveData<DefaultData>()
    val regionsDataResponse = MutableLiveData<List<CityDataModel>?>()
    val citiesDataResponse = MutableLiveData<List<CityDataModel>?>()
    val distractsDataResponse = MutableLiveData<List<CityDataModel>?>()
    val categoriessDataResponse = MutableLiveData<List<CategoryDataModel>?>()
    val productResponse = MutableLiveData<List<ProductDataModel>?>()
    val vouchersDataResponse = MutableLiveData<List<VoucherDataModel>?>()
    val voucherDetailsResponse = MutableLiveData<VoucherDataModel?>()
    val createOrderResponse = MutableLiveData<DefaultDataModel?>()
    val ordersResponse = MutableLiveData<List<OrderDataModel>?>()
    val orderDetailsResponse = MutableLiveData<OrderDataModel?>()
    val changePhoneVerifyResponse = MutableLiveData<DefaultData>()
    val changeEmailVerifyResponse = MutableLiveData<DefaultData?>()
    val pageResponse = MutableLiveData<PageDataModel?>()
    val currenciesResponse = MutableLiveData<List<CurrencyData>>()
    val profileDataResponse = MutableLiveData<LoginDataModel?>()
    val pagesDataResponse = MutableLiveData<MoreDataModel>()
    val settingResponse = MutableLiveData<SettingModel>()
    val feedbackResponse = MutableLiveData<DefaultData>()

    fun getRegion() {
        loading.value = true
        viewModelScope.launch {
            mainRepository.getRegion(networkStatus).let {
                loading.value = false
                if (it.data != null)
                    regionsDataResponse.postValue(it.data.data)
            }
        }
    }
    fun getCities(id:Int) {
        loading.value = true
        viewModelScope.launch {
            mainRepository.getCities(networkStatus,id).let {
                loading.value = false
                if (it.data != null)
                    citiesDataResponse.postValue(it.data.data)
            }
        }
    }

    fun getDistricts(id: Int) {
        loading.value = true
        viewModelScope.launch {
            mainRepository.getDistricts(networkStatus, id).let {
                loading.value = false
                if (it.data != null)
                    distractsDataResponse.postValue(it.data.data)
            }
        }
    }

    fun getAddress() {
        loading.value = true
        viewModelScope.launch {
            mainRepository.getAddress(
                networkStatus
            ).let {
                loading.value = false
                addressResponse.postValue(it.data?.data)
            }
        }
    }

    fun getMachinesLocation() {
        loading.value = true
        viewModelScope.launch {
            mainRepository.getMachinesLocation(
                networkStatus
            ).let {
                loading.value = false
                machinesAddressResponse.postValue(it.data?.data)
            }
        }
    }

    fun getMachineQr(id: Int) {
        loading.value = true
        viewModelScope.launch {
            mainRepository.getMachineQr(
                networkStatus,id
            ).let {
                loading.value = false
                machinesQrResponse.postValue(it.data?.data)
            }
        }
    }
    fun getCategories() {
        loading.value = true
        viewModelScope.launch {
            mainRepository.getCategories(networkStatus).let {
                loading.value = false
                if (it.data != null)
                    categoriessDataResponse.postValue(it.data.data)
            }
        }
    }

    fun getProducts() {
        loading.value = true
        viewModelScope.launch {
            mainRepository.getProducts(networkStatus).let {
                loading.value = false
                if (it.data != null)
                    productResponse.postValue(it.data.data)
            }
        }
    }


    fun getProducts(id: Int) {
        loading.value = true
        viewModelScope.launch {
            mainRepository.getProducts(networkStatus, id).let {
                loading.value = false
                if (it.data != null)
                    productResponse.postValue(it.data.data)
            }
        }
    }


    fun getVouchers() {
        loading.value = true
        viewModelScope.launch {
            mainRepository.getVouchers(networkStatus).let {
                loading.value = false
                if (it.data != null)
                    vouchersDataResponse.postValue(it.data.data)
            }
        }
    }

    fun getVoucherDetails(id: Int) {
        loading.value = true
        viewModelScope.launch {
            mainRepository.getVoucherDetails(networkStatus, id).let {
                loading.value = false
                if (it.data != null)
                    voucherDetailsResponse.postValue(it.data.data)
            }
        }
    }


    fun getOrders(page: Int) {
        loading.value = true
        viewModelScope.launch {
            mainRepository.getOrders(networkStatus, page).let {
                loading.value = false
                if (it.data != null)
                    ordersResponse.postValue(it.data.data.data)
            }
        }
    }

    fun getOrderDetails(id: Int) {
        loading.value = true
        viewModelScope.launch {
            mainRepository.getOrderDetails(networkStatus, id).let {
                loading.value = false
                if (it.data != null)
                    orderDetailsResponse.postValue(it.data.data)
            }
        }
    }


    fun verifyPhone(context: Context, phone: String, countryCode: String) {
        if (MyUtils.checkTextIsEmptyOrNot(viewCodeLiveData.value) || viewCodeLiveData.value!!.length < 5) {
            showMassage.postValue(context.getString(R.string.please_enter_verify_code))
            return
        }
        val requestData = JsonObject()
        requestData.addProperty("code", viewCodeLiveData.value)
        requestData.addProperty("phone", phone)
        requestData.addProperty("country_code", countryCode)

        loading.value = true
        viewModelScope.launch {
            mainRepository.verifyPhone(networkStatus, requestData).let {
                loading.value = false
                if (it.data != null)
                    changePhoneVerifyResponse.postValue(it.data.data)
            }
        }

    }

    fun getPage(id: Int = 1) {
//        loading.value = true
        viewModelScope.launch {
            mainRepository.getPage(networkStatus, id).let {
                loading.value = false
                if (it.data != null) {
                    pageResponse.postValue(it.data)
                }

            }
        }
    }


    fun getProfile() {
        loading.value = true
        viewModelScope.launch {
            mainRepository.getProfile(networkStatus).let {
                loading.value = false
                it.data?.data?.let {
                    profileDataResponse.postValue(it)
                    appManger.saveUserData(it)
                }
            }
        }
    }

    fun getPages() {
//        loading.value = true
        viewModelScope.launch {
            mainRepository.getPages(networkStatus).let {
                loading.value = false
                it.data?.let {
                    pagesDataResponse.postValue(it.data)

                }
            }
        }
    }

    fun getSetting(isLoading: Boolean = true) {
        loading.value = isLoading
        viewModelScope.launch {
            mainRepository.getSetting(networkStatus).let {
                loading.value = false
                it.data?.let {
                    settingResponse.postValue(it.data)
                }
            }
        }
    }

    fun getNotificationCount() {
//        loading.value = true
        viewModelScope.launch {
            mainRepository.getNotificationCount(networkStatus).let {
//                loading.value = false
                it.data?.let {
                    notificationCountResponse.value = it.data
                    appManger.setNotificationCount(it.data.count)
                }
            }
        }
    }
    fun sendFeedback(orderId: Int, feedback: String) {

        val data = JsonObject()
        data.addProperty("feedback", feedback)
        loading.value = true
        viewModelScope.launch {
            mainRepository.sendFeedback(networkStatus, orderId, data).let {
                if (it.data != null) {
                    feedbackResponse.postValue(it.data.data)
                }
            }
        }
    }
}
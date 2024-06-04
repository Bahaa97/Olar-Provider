package com.olar.ui.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abora.perfectobase.data.models.*
import com.google.gson.JsonObject
import com.olar.R
import com.olar.Utils.AppManger
import com.olar.base.BaseViewModel
import com.olar.data.models.DatesModel
import com.olar.data.models.NotificationData
import com.olar.data.models.RoomDataModel
import com.olar.data.models.SupportResponse
import com.olar.data.models.SupportTopicResponse
import com.olar.data.remote.reporsitory.MainRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    override var mainRepository: MainRepository,
    override val appManger: AppManger
) : BaseViewModel(mainRepository, appManger) {


    //Responses
    val homeResponse = MutableLiveData<HomeDataModel>()
    val searchResponse = MutableLiveData<SearchDataModel>()
    val changeSendPushResponse = MutableLiveData<DefaultData>()
    val scanVoucherResponse = MutableLiveData<DefaultData>()
    val scanOrder = MutableLiveData<DefaultData>()
    val notificationData = MutableLiveData<List<NotificationData>?>()
    val messagesResponse = MutableLiveData<List<RoomDataModel>?>()
    val makeNotificationRead = MutableLiveData<DefaultDataModel?>()
    val makeNotificationReadResponse = MutableLiveData<Int>()
    val sendImgChatResponse = MutableLiveData<DefaultData?>()
    val datesResponse = MutableLiveData<DatesModel?>()
    val supportTopicResponse = MutableLiveData<SupportTopicResponse?>()
    val complainResponse = MutableLiveData<SupportResponse?>()

    //Fields
    val viewEmailLiveData = MutableLiveData<String>()
    var mldSupportTitle = MutableLiveData<String>()
    var mldSupportMessage = MutableLiveData<String>()

    //Error Fields
    var _mldSupportTitleError = MutableLiveData<String?>()
    var _mldSupportMessageError = MutableLiveData<String?>()

    var isloading: Boolean = false
    var lastPage: Boolean = false
    var pageSize: Int = 15

    fun getHome() {
        loading.value = true
        viewModelScope.launch {
            mainRepository.getHome(networkStatus).let {
                loading.value = false
                it.data?.let {
                    homeResponse.value = it.homeData
                }
            }
        }
    }


    fun search(data: JsonObject) {
        loading.value = true
        viewModelScope.launch {
            mainRepository.search(
                networkStatus,
                data
            ).let {
                loading.value = false
                it.data?.let {
                    searchResponse.value = it.data
                }
            }
        }
    }
    fun getRoomMessages(id: Int) {
        loading.value = true
        viewModelScope.launch {
            mainRepository.getRoomMessages(networkStatus, id).let {
                loading.value = false
                isloading = false
                if (it.data != null) {
                    messagesResponse.postValue(it.data.data.data)
                } else {
                    messagesResponse.postValue(null)
                }
            }
        }
    }

    fun getDates(month:String,
                 year:String) {
//        loading.value = true
        viewModelScope.launch {
            mainRepository.getDates(networkStatus,month, year).let {
                loading.value = false
                isloading = false
                if (it.data != null) {
                    datesResponse.value = it.data.data
                } else {
                    notificationData.postValue(null)
                }
            }
        }
    }

    fun getNotification() {
        loading.value = true
        viewModelScope.launch {
            mainRepository.getNotification(networkStatus).let {
                loading.value = false
                isloading = false
                if (it.data != null) {
                    notificationData.postValue(it.data.data)
                } else {
                    notificationData.postValue(null)
                }
            }
        }
    }


    fun makeNotificationAsRead() {
        loading.value = true
        viewModelScope.launch {
            mainRepository.makeNotificationAsRead(networkStatus).let {
                loading.value = false
                if (it.data != null) {
                    makeNotificationRead.postValue(it.data)
                }
            }
        }
    }


    fun makeNotificationAsRead(id: String, position: Int) {
        loading.value = true
        viewModelScope.launch {
            mainRepository.makeNotificationAsRead(networkStatus, id).let {
                loading.value = false
                makeNotificationReadResponse.value = position
//                if (it.data != null)
            }
        }
    }


    fun scanVoucher(id: Int, qrCode: String) {
        val requestData = JsonObject()
        requestData.addProperty("code", qrCode)

        loading.value = true
        viewModelScope.launch {
            mainRepository.scanVoucher(networkStatus, id, requestData).let {
                loading.value = false
                if (it.data != null)
                    scanVoucherResponse.postValue(it.data.data)
            }
        }
    }
    fun scanOrder(id: Int, qrCode: String) {
        val requestData = JsonObject()
        requestData.addProperty("code", qrCode)

        loading.value = true
        viewModelScope.launch {
            mainRepository.scanOrder(networkStatus, id, requestData).let {
                loading.value = false
                if (it.data != null)
                    scanOrder.postValue(it.data.data)
            }
        }
    }
    fun cancelOrder(id: Int) {

        loading.value = true
        viewModelScope.launch {
            mainRepository.cancelOrder(networkStatus, id).let {
                loading.value = false
                if (it.data != null)
                    scanOrder.postValue(it.data.data)
            }
        }
    }
    fun getSupportTopic() {
        loading.value = true
        viewModelScope.launch {
            mainRepository.getSupportTopic(networkStatus).let {
                loading.value = false
                if (it.data != null) {
                    supportTopicResponse.postValue(it.data)
                }
            }
        }
    }

    fun sendComplain(context:Context,supportTopicId: Int?) {
        _mldSupportTitleError.value = null
        _mldSupportMessageError.value = null

        if (mldSupportTitle.value.isNullOrEmpty()){
            _mldSupportTitleError.value= context.getString(R.string.enter_title)
            return
        }
        if (mldSupportMessage.value.isNullOrEmpty()){
            _mldSupportMessageError.value= context.getString(R.string.enter_problem)
            return
        }

        val data = JsonObject()
        data.addProperty("support_topic_id", supportTopicId)
        data.addProperty("message", mldSupportMessage.value.toString())
        data.addProperty("type", "complain")

        loading.value = true
        viewModelScope.launch {
            mainRepository.sendComplain(networkStatus, data).let {
                loading.value = false
                if (it.data != null) {
                    complainResponse.postValue(it.data)
                }
            }
        }
    }

}
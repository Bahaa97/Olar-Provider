package com.olar.ui.recycleing

import android.os.Bundle
import android.os.SystemClock
import android.text.TextUtils
import android.widget.ScrollView
import com.google.gson.Gson
import com.leesche.logger.Logger
import com.leesche.yyyiotlib.entity.CmdResultEntity
import com.leesche.yyyiotlib.entity.UnitEntity
import com.leesche.yyyiotlib.serial.callback.ControlCallBack
import com.leesche.yyyiotlib.serial.manager.Cmd2Constants
import com.leesche.yyyiotlib.serial.manager.helper.RvmHelper
import com.olar.R
import com.olar.base.BaseActivity
import com.olar.databinding.ActivityRecyclingBinding
import com.olar.ui.home.HomeViewModel
import com.olar.ui.recycleing.helper.BottleChecker
import com.olar.ui.recycleing.helper.DevStatusAdapter
import com.olar.ui.recycleing.helper.DevStatusHandler
import com.olar.ui.recycleing.helper.ThreadManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.reflect.KClass

class RecyclingActivity : BaseActivity<ActivityRecyclingBinding, HomeViewModel>() {
    var isInitSuccess = false
    var fwVer = ""
    var devId = ""
    var gson: Gson? = null
    var curTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
    var sbInfo = StringBuilder()
    var devAList: List<UnitEntity> = ArrayList()
    var devBList: List<UnitEntity> = ArrayList()
    var unitEntities: List<UnitEntity> = ArrayList()
    var devStatusAdapterA: DevStatusAdapter? = null
    var devStatusAdapterB: DevStatusAdapter? = null
    override fun resourceId(): Int = R.layout.activity_recycling

    override fun viewModelClass(): KClass<HomeViewModel> = HomeViewModel::class


    override fun setUI(savedInstanceState: Bundle?) {
        initRvm()
    }

    private fun initRvm() {
        ThreadManager.getThreadPollProxy()?.execute {
            do {
                isInitSuccess = RvmHelper.getInstance()
                    .initDev(this@RecyclingActivity, 0) //0-->single entrance  1-->double entrance
            } while (!isInitSuccess)
            RvmHelper.getInstance().addControlCallBack(controlCallBack)
            while (TextUtils.isEmpty(devId)) {
                RvmHelper.getInstance().getDevIdAndEnableEntrance(true, true)
                SystemClock.sleep(6000)
            }
        }
    }


    override fun observer() {

    }

    override fun clicks() {

    }

    override fun callApis() {

    }

    var controlCallBack: ControlCallBack = object : ControlCallBack {
        override fun onResult(cmdBackResult: String) {
            if (gson == null) gson = Gson()
            val cmdResultEntity: CmdResultEntity = gson!!.fromJson<CmdResultEntity>(
                cmdBackResult,
                CmdResultEntity::class.java
            )
            if (cmdResultEntity.func_code != Cmd2Constants.CMD_StmToAndroid.CMD_OStar_StatusErr.toInt() && cmdResultEntity.func_code != Cmd2Constants.CMD_StmToAndroid.CMD_OStar_OutNum.toInt() && cmdResultEntity.func_code != Cmd2Constants.CMD_StmToAndroid.CMD2STM32_BackCtOtherMotor.toInt()) {
                sbInfo.append("[").append(curTime).append("] ")
            }
            when (cmdResultEntity.func_code) {
                Cmd2Constants.CMD_StmToAndroid.CMD_OStar_BackMacInfo.toInt() -> {
                    //dev id and FW version form main board
                    val values =
                        cmdResultEntity.value.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }
                            .toTypedArray()
                    devId = values[1]
                    fwVer = values[0]
                    dataBinding.tvDevId.text = "Dev Id: $devId"
                    sbInfo.append("Dev Id: ").append(devId).append(" FW: V").append(fwVer)
                        .append("\n")
                }

                Cmd2Constants.CMD_SCAN_StmToAndroid.CMD_OStar_BackMacInfo.toInt() -> {
                    if (cmdResultEntity.status == 1 || cmdResultEntity.status == 2) {
                        sbInfo.append(
                            if (cmdResultEntity.box_code == 1) getString(R.string.scannerA_02) else getString(
                                R.string.scannerB_02
                            )
                        )
                            .append(getString(R.string.power_on)).append("\n")
                    }
                    if (cmdResultEntity.status == 3 || cmdResultEntity.status == 4) {
                        sbInfo.append(
                            if (cmdResultEntity.box_code == 1) getString(R.string.scannerA_02) else getString(
                                R.string.scannerB_02
                            )
                        )
                            .append(getString(R.string.power_off)).append("\n")
                    }
                }

                Cmd2Constants.CMD_StmToAndroid.CMD_OStar_BackOpenCloseDoor.toInt() -> {
                    if (cmdResultEntity.status == 2) {
                        sbInfo.append(getString(R.string.entrance_a_open_success))
                    }
                    if (cmdResultEntity.status == 4) {
                        sbInfo.append(getString(R.string.entrance_a_close_success))
                    }
                    if (cmdResultEntity.status == 3) {
                        sbInfo.append(getString(R.string.entrance_b_open_success))
                    }
                    if (cmdResultEntity.status == 5) {
                        sbInfo.append(getString(R.string.entrance_b_close_success))
                    }
                    if (cmdResultEntity.status == 12) {
                        sbInfo.append(getString(R.string.recycle_door_open_success))
                    }
                    if (cmdResultEntity.status == 13) {
                        sbInfo.append(getString(R.string.recycle_door_close_success))
                    } //open or close signal form door
                }

                Cmd2Constants.CMD_StmToAndroid.CMD_OStar_OutNum.toInt() -> {
                    val bottleCountStr =
                        cmdResultEntity.value.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }
                            .toTypedArray()
                    if (bottleCountStr[0].toInt() != 0) {
                        sbInfo.append(getString(R.string.entrance_a_collect))
                            .append(bottleCountStr[0]).append("\n")
                    }
                    if (bottleCountStr[1].toInt() != 0) {
                        sbInfo.append(getString(R.string.entrance_b_collect))
                            .append(bottleCountStr[1]).append("\n")
                    } //bottle count
                }

                Cmd2Constants.CMD_StmToAndroid.CMD_OStar_WeightCheckRes.toInt() -> {
                    val weight = cmdResultEntity.value.toInt()
                    BottleChecker().getInstance()
                        .setBottleWeightStatus(cmdResultEntity.box_code, weight)
                    val hintMsg: String = BottleChecker().getInstance().checkToSendCmdToStm(
                        this@RecyclingActivity,
                        cmdResultEntity
                    )!! //check the weight of bottle
                    sbInfo.append(getString(R.string.weight_hint)).append(
                        if (cmdResultEntity.box_code == 1) getString(R.string.entrance_a) else getString(
                            R.string.entrance_b
                        )
                    ).append(cmdResultEntity.value + "g. ").append(hintMsg).append("\n")
                }

                Cmd2Constants.CMD_SCAN_StmToAndroid.CMD_OStar_BarCode.toInt() -> {
                    BottleChecker().getInstance().setBottleCodeStatus(cmdResultEntity)
                    val hint2Msg: String = BottleChecker().getInstance().checkToSendCmdToStm(
                        this@RecyclingActivity,
                        cmdResultEntity
                    )!! //check the barcode of bottle
                    sbInfo.append(hint2Msg).append(
                        if (cmdResultEntity.box_code == 1) getString(R.string.scannerA) else getString(
                            R.string.scannerB
                        )
                    )
                        .append(" [").append(cmdResultEntity.value.trim { it <= ' ' }).append("]\n")
                }

                Cmd2Constants.CMD_StmToAndroid.CMD_OStar_BackEnMT220V.toInt() -> sbInfo.append(
                    if (cmdResultEntity.status == 6) getString(
                        R.string.shredder_on
                    ) else getString(R.string.shredder_off)
                ) //Shredder
                Cmd2Constants.CMD_StmToAndroid.CMD_OStar_StatusErr.toInt() -> {
                    DevStatusHandler().getInstance()
                        ?.updateEntranceAStatus(cmdResultEntity.value, devAList.toMutableList(), devBList.toMutableList())
                    devStatusAdapterA?.setNewData(devAList)
                    devStatusAdapterB?.setNewData(devBList)
                }

                Cmd2Constants.CMD_SCAN_StmToAndroid.CMD_OStar_Distance.toInt() -> sbInfo.append(
                    getString(R.string.distance_show)
                )
                    .append(cmdResultEntity.value)
                    .append("mm \n") //distance form the bottom of container
                Cmd2Constants.CMD_SCAN_StmToAndroid.CMD_OStar_LoadCode.toInt(), Cmd2Constants.CMD_StmToAndroid.CMD_OStar_QrCode.toInt() -> sbInfo.append(
                    getString(R.string.login_code_show)
                ).append(cmdResultEntity.value).append("\n") //received code to login
                Cmd2Constants.OtherSysStatus.ENTRANCE_STATUS -> {
                    BottleChecker().getInstance().init(cmdResultEntity.box_code)
                    sbInfo.append(getString(if (cmdResultEntity.status == 11) R.string.bottle_in_hint else R.string.bottle_out_hint))
                }

                else -> Logger.i("【RVM Result】 $cmdBackResult")
            }
            runOnUiThread {
                dataBinding.tvInfo.text = sbInfo.toString()
                dataBinding.svInfo.fullScroll(ScrollView.FOCUS_DOWN)
            }
        }

        override fun onSaleResult(saleCmdBackResult: String) {}
        override fun onDeviceStatusResult(code: Int, otherResult: String) {}
    }


}
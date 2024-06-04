package com.olar.ui.recycleing.helper

import android.content.Context
import com.leesche.logger.Logger
import com.leesche.yyyiotlib.entity.CmdResultEntity
import com.leesche.yyyiotlib.serial.manager.helper.RvmHelper

class BottleChecker {
    var bottleWeightHaveChecked = false
    var bottleCodeHaveChecked = false
    var bottleWeightCheckedIsValid = false
    var bottleCodeCheckedIsValid = false

    var bottleBWeightHaveChecked = false
    var bottleBCodeHaveChecked = false
    var bottleBWeightCheckedIsValid = false
    var bottleBCodeCheckedIsValid = false
    var bottleChecker: BottleChecker? = null

    fun getInstance(): BottleChecker {
        synchronized(BottleChecker::class.java) {
            if (bottleChecker == null) {
                bottleChecker = BottleChecker()
            }
        }
        return bottleChecker!!
    }

    fun init(boxCode: Int) {
        if (boxCode == 1) {
            bottleCodeHaveChecked = false
            bottleWeightHaveChecked = false
            bottleCodeCheckedIsValid = false
            bottleWeightCheckedIsValid = false
        } else {
            bottleBCodeHaveChecked = false
            bottleBWeightHaveChecked = false
            bottleBCodeCheckedIsValid = false
            bottleBWeightCheckedIsValid = false
        }
    }

    fun setBottleWeightStatus(boxCode: Int, weight: Int) {
        Logger.i("【Bottle Checker】weight $boxCode")
        if (boxCode == 1) {
            bottleWeightHaveChecked = true
            bottleWeightCheckedIsValid = weight < 50
        } else {
            bottleBWeightHaveChecked = true
            bottleBWeightCheckedIsValid = weight < 50
        }
    }

    fun isCanSendCmdToStm(boxCode: Int): Boolean {
        Logger.i("【Bottle Checker】check $boxCode")
        return if (boxCode == 1) {
            if (bottleWeightHaveChecked && !bottleWeightCheckedIsValid) return true
            if (bottleCodeHaveChecked && !bottleCodeCheckedIsValid) true else bottleWeightHaveChecked && bottleCodeHaveChecked
        } else {
            if (bottleBWeightHaveChecked && !bottleBWeightCheckedIsValid) return true
            if (bottleBCodeHaveChecked && !bottleBCodeCheckedIsValid) true else bottleBWeightHaveChecked && bottleBCodeHaveChecked
        }
    }

    fun setBottleCodeStatus(cmdResultEntity: CmdResultEntity) {
        Logger.i("【Bottle Checker】code " + cmdResultEntity.value)
        if (cmdResultEntity.box_code == 1) {
            bottleCodeHaveChecked = true
            bottleCodeCheckedIsValid = cmdResultEntity.value.trim { it <= ' ' } != "123456"
        } else {
            bottleBCodeHaveChecked = true
            bottleBCodeCheckedIsValid = cmdResultEntity.value.trim { it <= ' ' } != "123456"
        }
    }

    fun setBottleCodeStatus(boxCode: Int, isValid: Boolean) {
        if (boxCode == 1) {
            bottleCodeHaveChecked = true
            bottleCodeCheckedIsValid = isValid
        } else {
            bottleBCodeHaveChecked = true
            bottleBCodeCheckedIsValid = isValid
        }
    }

    fun isBottleValid(boxCode: Int): Boolean {
        return if (boxCode == 1) {
            bottleWeightCheckedIsValid && bottleCodeCheckedIsValid
        } else {
            bottleBWeightCheckedIsValid && bottleBCodeCheckedIsValid
        }
    }

    fun checkIsPass(cmdResultEntity: CmdResultEntity): Int {
        return if (isCanSendCmdToStm(cmdResultEntity.box_code)) {
            val isPass: Boolean =
                BottleChecker().getInstance().isBottleValid(cmdResultEntity.box_code)
            RvmHelper.getInstance()
                .uploadCheckResultToBoard(cmdResultEntity.box_code, cmdResultEntity.status, isPass)
            var statusCode = 2
            if (!isPass) {
                if (cmdResultEntity.box_code == 1) {
                    if (bottleCodeHaveChecked && bottleWeightHaveChecked) {
                        if (!bottleCodeCheckedIsValid && !bottleWeightCheckedIsValid) statusCode = 3
                        if (bottleCodeCheckedIsValid && !bottleWeightCheckedIsValid) statusCode = 4
                        if (!bottleCodeCheckedIsValid && bottleWeightCheckedIsValid) statusCode = 5
                    }
                    if (bottleCodeHaveChecked && !bottleWeightHaveChecked) {
                        return 6
                    }
                    if (!bottleCodeHaveChecked && bottleWeightHaveChecked) {
                        return 7
                    }
                }
                if (cmdResultEntity.box_code == 2) {
                    if (bottleBCodeHaveChecked && bottleBWeightHaveChecked) {
                        if (!bottleBCodeCheckedIsValid && !bottleBWeightCheckedIsValid) statusCode =
                            3
                        if (bottleBCodeCheckedIsValid && !bottleBWeightCheckedIsValid) statusCode =
                            4
                        if (!bottleBCodeCheckedIsValid && bottleBWeightCheckedIsValid) statusCode =
                            5
                    }
                    if (bottleBCodeHaveChecked && !bottleBWeightHaveChecked) {
                        return 6
                    }
                    if (!bottleBCodeHaveChecked && bottleBWeightHaveChecked) {
                        return 7
                    }
                }
            }
            init(cmdResultEntity.box_code)
            statusCode
        } else {
            if (cmdResultEntity.box_code == 1) {
                if (bottleCodeHaveChecked) {
                    Logger.i("【Bottle Checker】 等待检查重量 (" + cmdResultEntity.box_code + ")")
                    return 1
                }
                if (bottleWeightHaveChecked) {
                    Logger.i("【Bottle Checker】 等待检查条码(" + cmdResultEntity.box_code + ")")
                    return 0
                }
            }
            if (cmdResultEntity.box_code == 2) {
                if (bottleBCodeHaveChecked) {
                    Logger.i("【Bottle Checker】 等待检查重量 (" + cmdResultEntity.box_code + ")")
                    return 1
                }
                if (bottleBWeightHaveChecked) {
                    Logger.i("【Bottle Checker】 等待检查条码(" + cmdResultEntity.box_code + ")")
                    return 0
                }
            }
            -1
        }
    }

    fun getMsgHintByStatus(boxCode: Int, status: Int): String? {
        var hintMsg = "  waiting for check..."
        when (status) {
            0 -> hintMsg = "  waiting for check barcode..."
            1 -> hintMsg = "waiting for check weight..."
            2 -> hintMsg = "barcode and weight are all match together."
            3 -> hintMsg = "  barcode and weight aren't match together."
            4, 7 -> hintMsg = "weight more than 50g!"
            5, 6 -> hintMsg = "  Invalid barcode!"
            -1 -> {
                if (boxCode == 1) {
                    hintMsg =
                        if (bottleCodeHaveChecked && bottleCodeCheckedIsValid && !bottleWeightHaveChecked || !bottleCodeHaveChecked && bottleWeightHaveChecked && bottleWeightCheckedIsValid) {
                            "checked overtime!\n"
                        } else {
                            "pls deposit standardize!\n"
                        }
                }
                if (boxCode == 2) {
                    hintMsg =
                        if (bottleBCodeHaveChecked && bottleBCodeCheckedIsValid && !bottleBWeightHaveChecked || !bottleBCodeHaveChecked && bottleBWeightHaveChecked && bottleBWeightCheckedIsValid) {
                            "checked overtime!\n"
                        } else {
                            "pls deposit standardize!\n"
                        }
                }
            }
        }
        return hintMsg
    }

    fun checkToSendCmdToStm(context: Context?, cmdResultEntity: CmdResultEntity): String? {
        val status = checkIsPass(cmdResultEntity)
        return getMsgHintByStatus(cmdResultEntity.box_code, status)
    }
}
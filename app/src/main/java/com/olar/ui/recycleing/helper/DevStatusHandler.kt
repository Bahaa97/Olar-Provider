package com.olar.ui.recycleing.helper

import com.leesche.yyyiotlib.entity.UnitEntity

class DevStatusHandler {

    var devConfigHandler: DevStatusHandler? = null

    fun getInstance(): DevStatusHandler? {
        synchronized(DevStatusHandler::class.java) {
            if (devConfigHandler == null) {
                devConfigHandler = DevStatusHandler()
            }
        }
        return devConfigHandler
    }

    //Status information will be use to analysis when the machine not work normal.
    fun updateEntranceAStatus(
        value: String,
        devAList: MutableList<UnitEntity?>,
        devBList: MutableList<UnitEntity?>
    ) {
        val devStatus = value.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val weightErr = devStatus[2].toInt()
        val rockErr = devStatus[3].toInt()
        val matStatus = devStatus[4].toInt()
        val eysStatus = devStatus[5].toInt()
        val mtFlag = devStatus[6].toInt()
        val mtErrReg = devStatus[7].toInt()
        val distanceStatus = devStatus[9].toInt()
        val A_WET = if (weightErr and 0x01 == 0) "NORM" else "ERR"
        val A_ROCK_H = if (rockErr and 0x01 == 0) "NORM" else "ERR"
        val A_ROCK_L = if (rockErr and 0x02 shr 1 == 0) "NORM" else "ERR"
        val A_MT_DOOR = getMatStatusHint(matStatus, 0)
        val A_MT_BELT = getMatStatusHint(matStatus, 1)
        val A_MT_ROLL = getMatStatusHint(matStatus, 4)
        val A_MT_ROCK = getMatStatusHint(matStatus, 5)
        val A_BackLock1 = getMatStatusHint(matStatus, 8)
        val A_SG1 = eysStatus and 0x01
        val A_SG2 = eysStatus and 0x02 shr 1
        val A_SG3 = eysStatus and 0x04 shr 2
        val A_SG4 = eysStatus and 0x08 shr 3
        val A_Clamp = eysStatus and 0x100 shr 8
        val A_DownPos = eysStatus and 0x400 shr 10
        val A_UpPos = eysStatus and 0x800 shr 11
        val SG_BackLock = eysStatus and 0x8000 shr 15 //回收锁
        val distanceA = distanceStatus and 0x01
        val MT220_FLAG = getMat220StatusHint(mtFlag)
        if (devAList.size > 0) devAList.clear()
        // status of entrance A
        devAList.add(UnitEntity(0, 0, "Entrance_S(A)" + devStatus[0]))
        // status of electronic scale in entrance A
        devAList.add(UnitEntity(2, 0, "A_WET $A_WET"))
        // status of the high point of swing motor in entrance A
        devAList.add(UnitEntity(2, 0, "A_ROCK_H $A_ROCK_H"))
        //status of the high point of swing motor in entrance A
        devAList.add(UnitEntity(2, 0, "A_ROCK_L $A_ROCK_L"))
        //status of the door in entrance A
        devAList.add(UnitEntity(0, 0, "A_MT_DOOR $A_MT_DOOR"))
        //status of the belt motor in entrance A
        devAList.add(UnitEntity(0, 0, "A_MT_BELT $A_MT_BELT"))
        //status of the drum motor in entrance A
        devAList.add(UnitEntity(0, 0, "A_MT_ROLL $A_MT_ROLL"))
        //status of the swing motor int entrance A
        devAList.add(UnitEntity(0, 0, "A_MT_ROCK $A_MT_ROCK"))
        //status of the 1th photoelectric sensor in entrance A
        devAList.add(UnitEntity(0, A_SG1, if (A_SG1 == 1) "A_SG1(1)" else "A_SG1(0)"))
        //status of the 2th photoelectric sensor in entrance A
        devAList.add(UnitEntity(0, A_SG2, if (A_SG2 == 1) "A_SG2(1)" else "A_SG2(0)"))
        //status of the 3th photoelectric sensor in entrance A
        devAList.add(UnitEntity(0, A_SG3, if (A_SG3 == 1) "A_SG3(1)" else "A_SG3(0)"))
        //status of the 4th photoelectric sensor in entrance A
        devAList.add(UnitEntity(0, A_SG4, if (A_SG4 == 1) "A_SG4(1)" else "A_SG4(0)"))
        //status of the Anti-pinch in entrance A
        devAList.add(UnitEntity(0, A_Clamp, if (A_Clamp == 1) "A_Clamp(1)" else "A_Clamp(0)"))
        //If A_UpPos equal to 1, the swing motor running in the lowest position
        devAList.add(
            UnitEntity(
                0,
                A_DownPos,
                if (A_DownPos == 1) "A_DownPos(1)" else "A_DownPos(0)"
            )
        )
        //If A_UpPos equal to 1, the swing motor running in the highest position
        devAList.add(UnitEntity(1, A_UpPos, if (A_UpPos == 1) "A_UpPos(1)" else "A_UpPos(0)"))
        //If distanceA equal to 1, the distance sensor have bean detect
        devAList.add(
            UnitEntity(
                0,
                distanceA,
                if (distanceA == 1) "DistanceA(1)" else "DistanceA(0)"
            )
        )
        devAList.add(UnitEntity(0, mtFlag, MT220_FLAG))
        devAList.add(
            UnitEntity(
                0,
                SG_BackLock,
                if (SG_BackLock == 1) "SG_BackLock(1)" else "SG_BackLock(0)"
            )
        )
        devAList.add(UnitEntity(0, 0, "SG_BackLock $A_BackLock1"))
        //        devAList.add(new UnitEntity(0, recycleDoorStatus, recycleDoorStatus == 1 ? "回收门(超时)" : "回收门(正常)"));
        updateEntranceBStatus(devStatus, devBList)
    }


    fun updateEntranceBStatus(devStatus: Array<String>, devBList: MutableList<UnitEntity?>) {
        val weightErr = devStatus[2].toInt()
        val rockErr = devStatus[3].toInt()
        val matStatus = devStatus[4].toInt()
        val eysStatus = devStatus[5].toInt()
        val distanceStatus = devStatus[9].toInt()
        val B_WET = if (weightErr and 0x10 shr 4 == 0) "NORM" else "ERR"
        val B_ROCK_H = if (rockErr and 0x10 shr 4 == 0) "NORM" else "ERR"
        val B_ROCK_L = if (rockErr and 0x20 shr 5 == 0) "NORM" else "ERR"
        val B_MT_DOOR = getMatStatusHint(matStatus, 2)
        val B_MT_BELT = getMatStatusHint(matStatus, 3)
        val B_MT_ROLL = getMatStatusHint(matStatus, 6)
        val B_MT_ROCK = getMatStatusHint(matStatus, 7)
        val B_SG1 = eysStatus and 0x10 shr 4
        val B_SG2 = eysStatus and 0x20 shr 5
        val B_SG3 = eysStatus and 0x40 shr 6
        val B_SG4 = eysStatus and 0x80 shr 7
        val B_Clamp = eysStatus and 0x200 shr 9
        val B_DownPos = eysStatus and 0x1000 shr 12
        val B_UpPos = eysStatus and 0x2000 shr 13
        val distanceB = distanceStatus and 0x02 shr 1
        if (devBList.size > 0) devBList.clear()
        devBList.add(UnitEntity(0, 0, "Entrance_S(B)" + devStatus[1])) //投口B状态
        devBList.add(UnitEntity(2, 0, "B_WET(B) $B_WET")) //电子秤
        devBList.add(UnitEntity(2, 0, "B_ROCK_H $B_ROCK_H"))
        devBList.add(UnitEntity(2, 0, "B_ROCK_L $B_ROCK_L"))
        devBList.add(UnitEntity(0, 0, "B_MT_DOOR $B_MT_DOOR"))
        devBList.add(UnitEntity(0, 0, "B_MT_BELT $B_MT_BELT"))
        devBList.add(UnitEntity(0, 0, "B_MT_ROLL $B_MT_ROLL"))
        devBList.add(UnitEntity(0, 0, "B_MT_ROCK $B_MT_ROCK"))
        devBList.add(UnitEntity(0, B_SG1, if (B_SG1 == 1) "B_SG1(1)" else "B_SG1(0)"))
        devBList.add(UnitEntity(0, B_SG2, if (B_SG2 == 1) "B_SG2(1)" else "B_SG2(0)"))
        devBList.add(UnitEntity(0, B_SG3, if (B_SG3 == 1) "B_SG3(1)" else "B_SG3(0)"))
        devBList.add(UnitEntity(0, B_SG4, if (B_SG4 == 1) "B_SG4(1)" else "B_SG4(0)"))
        devBList.add(UnitEntity(0, B_Clamp, if (B_Clamp == 1) "B_Clamp(1)" else "B_Clamp(0)"))
        devBList.add(
            UnitEntity(
                0,
                B_DownPos,
                if (B_DownPos == 1) "B_DownPos(1)" else "B_DownPos(0)"
            )
        )
        devBList.add(UnitEntity(1, B_UpPos, if (B_UpPos == 1) "B_UpPos(1)" else "B_UpPos(0)"))
        devBList.add(
            UnitEntity(
                0,
                distanceB,
                if (distanceB == 1) "Distance_B(1)" else "Distance_B(0)"
            )
        )
    }

    private fun getMat220StatusHint(mtFlag: Int): String {
        var hint = "Shredder(1)"
        if (mtFlag and 0x01 == 1) {
            //shredder not detected
            hint = "Shredder(0)"
        }
        if (mtFlag and 0x02 shr 1 == 1) {
            //shredder motor maybe have been block
            hint = "Shredder(-1)"
        }
        //        if (((mtFlag & 0x04) >> 2) == 1) {
//            hint = "Shredder(-1)";
//        }
        return hint
    }

    private fun getMatStatusHint(matStatus: Int, offset: Int): String {
        var status = 0
        when (offset) {
            0 -> status = matStatus and 0x03
            1 -> status = matStatus and 0x12 shr 2
            2 -> status = matStatus and 0x030 shr 4
            3 -> status = matStatus and 0x1200 shr 6
            4 -> status = matStatus and 0x0300 shr 8
            5 -> status = matStatus and 0x12000 shr 10
            6 -> status = matStatus and 0x03000 shr 12
            7 -> status = matStatus and 0x120000 shr 14
            8 -> status = matStatus and 0x030000 shr 16
        }
        if (status == 0) {
            //no action
            return " NA"
        }
        if (status == 1) {
            //forward rotation
            return " FWD"
        }
        if (status == 2) {
            //reverse rotation
            return " REV"
        }
        return if (status == 3) {
            //stop rotaiton
            " STOP"
        } else ""
    }
}
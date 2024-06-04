package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import com.olar.R

class NoInternetConnectionDailog(context: Context, theme: Int) : Dialog(context) {

    companion object {
        fun show(context: Context, cancelable: Boolean): NoInternetConnectionDailog? {
            var dialog = NoInternetConnectionDailog(context, android.R.style.Theme_Black)
            dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.layout_no_internet)
            dialog.setCancelable(cancelable)
            dialog.show()
            return dialog
        }
    }

}
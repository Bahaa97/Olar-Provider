package com.olar.ui.appUtils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.olar.R
import com.olar.base.BaseActivity
import com.olar.databinding.ActivityHtmlBinding
import com.olar.ui.auth.AuthViewModel
import kotlin.reflect.KClass


class HtmlActivity : BaseActivity<ActivityHtmlBinding, AuthViewModel>() {


    override fun resourceId(): Int = R.layout.activity_html

    override fun viewModelClass(): KClass<AuthViewModel> = AuthViewModel::class


    override fun setUI(savedInstanceState: Bundle?) {
        dataBinding.toolbar.tvTittle.text = intent.getStringExtra("title")
        dataBinding.htmlWebView.setWebViewClient(MyWebViewClient())
        dataBinding.htmlWebView.settings.javaScriptEnabled = true
        dataBinding.htmlWebView.loadData(
            intent.getStringExtra("body").toString(),
            "text/html;charset=utf-8",
            "UTF-8"
        )
    }

    override fun observer() {

    }

    override fun clicks() {
        dataBinding.toolbar.ivBack.setOnClickListener {
            onBackPressed()
        }
    }


    override fun callApis() {
    }


    private class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            openBrowserEngine(url,view.context)
            return true
        }

        private fun openBrowserEngine(targetUrl: String, activity: Context?) {
            var url = targetUrl
            if (!targetUrl.startsWith("http://") && !targetUrl.startsWith("https://")) {
                url = "http://$targetUrl"
            }

            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(url))
            activity?.startActivity(browserIntent)
        }
    }


}

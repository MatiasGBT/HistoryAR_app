package com.grupo3.historyar.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.grupo3.historyar.R
import com.grupo3.historyar.databinding.ActivitySubBinding

class SubActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initWebView()
    }

    private fun initWebView() {
        binding.wvSub.loadUrl("https://www.anitmals.com/suscribirse/")
        binding.wvSub.settings.javaScriptEnabled = true
        binding.wvSub.settings.setSupportZoom(true)
        binding.wvSub.webViewClient = WebViewClient()
    }
}
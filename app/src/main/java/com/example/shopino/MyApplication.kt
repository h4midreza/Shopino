package com.example.shopino

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags("fa-IR")
        AppCompatDelegate.setApplicationLocales(appLocale)
    }
}
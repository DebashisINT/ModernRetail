package com.example.modernretail.others

import android.app.Application
import com.marcinmoskala.kotlinpreferences.PreferenceHolder

class MRApp : Application() {
    override fun onCreate() {
        super.onCreate()
        PreferenceHolder.setContext(applicationContext)
    }
}
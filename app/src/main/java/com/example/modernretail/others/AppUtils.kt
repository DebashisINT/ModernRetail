package com.example.modernretail.others

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.inputmethod.InputMethodManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object AppUtils{

    val REQ_PERMISSION_CAMERA = 101
    val ACTION_CAMERA = 999

    fun hideKeyboardClearFocus(activity: Activity) {
        try {
            //hide keyboard
            val inMethod =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inMethod.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
            //clear focus
            val currentFocus = activity.currentFocus
            if (currentFocus != null) {
                currentFocus.clearFocus()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                return true
            }
        }
        return false
    }

    fun getAppVersion(context: Context): String {
        try {
            return context.packageManager.getPackageInfo(context.packageName, 0).versionName!!
        } catch (ex: PackageManager.NameNotFoundException) {
            ex.printStackTrace()
            return ""
        }
    }

    fun getCurrentDateTime(): String {
        val dt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        return dt.format(Date()).toString()
    }
}
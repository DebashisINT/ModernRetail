package com.example.modernretail.others

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest

class GpsUtils(private val context: Context) {

    val locationRequest = com.google.android.gms.location.LocationRequest.create().apply {
        interval = 1000 * 10
        fastestInterval = 1000 * 5
        priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private val locationSettingsRequest = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)
        .build()

    private val settingsClient = LocationServices.getSettingsClient(context)

    fun turnOnGps(activity: Activity, requestCode: Int) {
        settingsClient.checkLocationSettings(locationSettingsRequest)
            .addOnSuccessListener {
                // GPS is already enabled
            }
            .addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    try {
                        // Show the GPS settings dialog
                        exception.startResolutionForResult(activity, requestCode)
                    } catch (sendEx: IntentSender.SendIntentException) {
                        // Ignore the error
                    }
                }
            }
    }
}
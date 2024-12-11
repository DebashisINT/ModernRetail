package com.example.modernretail.others

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import java.io.IOException

object LocationUtils {
    suspend fun getCurrentLocation(context: Context,callback: (Location) -> Unit){
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
            if (location != null) {
                callback(location)
            }
        }
    }

    suspend fun getLocationName(context: Context, latitude: Double, longitude: Double): String? {
        val geocoder = Geocoder(context)
        var locationName: String? = null
        try {
            val addresses: MutableList<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses!!.isNotEmpty()) {
                val address: Address = addresses[0]
                locationName = address.getAddressLine(0) // Get the full address
                // or you can get specific parts like:
                // address.locality // City or town
                // address.subAdminArea // County or district
                // address.adminArea // State or province
                // address.countryName // Country
            }
        } catch (e: IOException) {
            // Handle the exception (e.g., network error, no geocoder service available)
            e.printStackTrace()
            return ""
        }
        return locationName
    }

    suspend fun getLocationPincode(context: Context, latitude: Double, longitude: Double): String? {
        val geocoder = Geocoder(context)
        var locationName: String? = null
        try {
            val addresses: MutableList<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses!!.isNotEmpty()) {
                val address: Address = addresses[0]
                // or you can get specific parts like:
                // address.locality // City or town
                // address.subAdminArea // County or district
                // address.adminArea // State or province
                // address.countryName // Country
                locationName = address.postalCode
            }
        } catch (e: IOException) {
            // Handle the exception (e.g., network error, no geocoder service available)
            e.printStackTrace()
            return ""
        }
        return locationName
    }

    fun isGpsEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}
package com.olar.locationPackage

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.olar.R
import com.olar.Utils.Constants.RUNTIME_LOCATION_PERMISSION
import com.olar.Utils.Constants.SETTINGS_DIALOG
import com.olar.Utils.MyUtils.myToastAction

class MyLocation(var context: Context) {
    var fusedLocationProviderClient: FusedLocationProviderClient? = null
    var mLocationRequest: LocationRequest? = null
    var myLocation = MutableLiveData<Location?>()
    var countForLocation = 0


    private fun takePermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) {

                if (ActivityCompat.shouldShowRequestPermissionRationale((context as Activity), Manifest.permission.ACCESS_FINE_LOCATION)) {
                    (context as AppCompatActivity). myToastAction(context.resources.getString(R.string.need_your_location)) { alertDialog ->
                        (context as AppCompatActivity).requestPermissions(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ), RUNTIME_LOCATION_PERMISSION
                        )
                        alertDialog.dismiss()
                    }


                } else {
                    (context as AppCompatActivity).requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), RUNTIME_LOCATION_PERMISSION)
                }
            }
        }
    }

    private fun getMyLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        takePermissions()
        (context as AppCompatActivity).let { context ->
            fusedLocationProviderClient?.lastLocation?.addOnSuccessListener { location ->
                if (location != null) {
                    Log.v("lastLocation ","${location}")
                    myLocation.postValue(location)
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && context.checkSelfPermission(
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            (context as AppCompatActivity).requestPermissions(
                                arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                ), RUNTIME_LOCATION_PERMISSION
                            )
                            return@addOnSuccessListener
                        }
                    }
                    LocationServices.getFusedLocationProviderClient(context)
                        .requestLocationUpdates(mLocationRequest!!, object : LocationCallback() {
                            override fun onLocationResult(locationResult: LocationResult) {
                                for (location1 in locationResult.locations) {
                                    if (location != null) {
                                        myLocation.postValue(location)
                                        break
                                    }
                                }
                            }

                            override fun onLocationAvailability(locationAvailability: LocationAvailability) {
                                super.onLocationAvailability(locationAvailability)
                            }
                        }, Looper.myLooper()!!)
                }
                if (location == null && countForLocation < 50) {
                    getMyLocation()
                    countForLocation++
                }
            }?.addOnFailureListener {
                it.printStackTrace()
            }
        }
    }

    private fun createLocationRequest() {
        if (mLocationRequest == null) {
            mLocationRequest = LocationRequest.create()
            mLocationRequest?.interval = 10000
            mLocationRequest?.fastestInterval = 5000
            mLocationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    fun checkLocationSettings(): MutableLiveData<Location?> {
        val builder = LocationSettingsRequest
            .Builder()
            .addLocationRequest(mLocationRequest!!)

        val client = LocationServices.getSettingsClient(context)

        val task = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener((context as AppCompatActivity)) { locationSettingsResponse: LocationSettingsResponse? ->
            getMyLocation()
        }.addOnFailureListener((context as AppCompatActivity)) { e: Exception? ->
            if (e is ResolvableApiException) {
                try {
                    e.startResolutionForResult(context as AppCompatActivity, SETTINGS_DIALOG)
                } catch (e1: SendIntentException) {
                    e1.printStackTrace()
                }
            }
        }
        return myLocation
    }



    init {
        createLocationRequest()
    }

}
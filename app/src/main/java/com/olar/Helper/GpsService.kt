package com.olar.Helper

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat

import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import java.util.*


class GPSService(private val context: Context, private val listener: OnLocationUpdate) :
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
    LocationListener {
    // Google client to interact with Google API
    private var mGoogleApiClient: GoogleApiClient? = null
    private var mLocationRequest: LocationRequest? = null
    private var isSavedCalled = false

    public interface OnLocationUpdate {
        fun onLocationUpdate(latitude: Double, longitude: Double)
    }

    /**
     * Method to display the location
     */
    private fun saveLocation() {
        if (Build.VERSION.SDK_INT < 23) {
            if (mLastLocation == null) {
                try {
                    mLastLocation =
                        mGoogleApiClient?.let { LocationServices.FusedLocationApi.getLastLocation(it) }

                } catch (ex: SecurityException) {
                    ex.printStackTrace()
                }
            }
        } else {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
            } else {
                if (mLastLocation == null) {
                    try {
                        mLastLocation =
                            mGoogleApiClient?.let {
                                LocationServices.FusedLocationApi.getLastLocation(
                                    it
                                )
                            }
                    } catch (ex: SecurityException) {
                        ex.printStackTrace()
                    }
                }
            }
        }
        if (mLastLocation != null) {
            Log.d(
                TAG,
                "getLastKnownLocation: LOCATION = ${mLastLocation!!.latitude} , ${mLastLocation!!.longitude}"
            )
            val geocoder = Geocoder(context, Locale.getDefault())
            val list: List<Address> =
                geocoder.getFromLocation(mLastLocation!!.latitude, mLastLocation!!.longitude, 1) as List<Address>
           /* context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit() {
                putString(kLATITUDE, mLastLocation!!.latitude.toString())
                putString(kLONGITUDE, mLastLocation!!.longitude.toString())
                putString(kAddress, list[0].subLocality)

                apply()
            }*/
//            println("-----kLATITUDE$kLATITUDE")
//            Toast.makeText(context, "${list.get(0).subLocality}", Toast.LENGTH_SHORT).show()

            listener.onLocationUpdate(mLastLocation!!.latitude, mLastLocation!!.longitude)
        }
    }

    /**
     * Creating google api client object
     */
    @Synchronized
    protected fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(context)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API).build()
    }

    /**
     * Creating location request object
     */
    protected fun createLocationRequest() {
        mLocationRequest = LocationRequest()
    }

    /**
     * Starting the location updates
     */
    protected fun startLocationUpdates() {
        if (Build.VERSION.SDK_INT < 23) {
            try {
                mGoogleApiClient?.let {
                    mLocationRequest?.let { it1 ->
                        LocationServices.FusedLocationApi.requestLocationUpdates(
                            it,
                            it1,
                            this
                        )
                    }
                }
            } catch (ex: SecurityException) {
                ex.printStackTrace()
            }
        } else {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
            } else {
                try {
                    mGoogleApiClient?.let {
                        mLocationRequest?.let { it1 ->
                            LocationServices.FusedLocationApi.requestLocationUpdates(
                                it,
                                it1,
                                this
                            )
                        }
                    }
                } catch (ex: SecurityException) {
                    ex.printStackTrace()
                }
            }
        }
    }

    /**
     * Stopping location updates
     */
    protected fun stopLocationUpdates() {
        mGoogleApiClient?.let { LocationServices.FusedLocationApi.removeLocationUpdates(it, this) }
    }

    /**
     * Google api callback methods
     */
    override fun onConnectionFailed(result: ConnectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.errorCode)
    }

    override fun onConnected(arg0: Bundle?) {
        //  saveLocation();
        startLocationUpdates()
    }

    override fun onConnectionSuspended(arg0: Int) {
        mGoogleApiClient!!.connect()
    }

    override fun onLocationChanged(location: Location) {
        mLastLocation = location
        if (!isSavedCalled) {
            isSavedCalled = true
            saveLocation()
        }
        stopLocationUpdates()
    }

    companion object {
        private val TAG = GPSService::class.java.simpleName
        var mLastLocation: Location? = null

        // Location updates intervals in sec
        private const val UPDATE_INTERVAL = 6000000
        private const val FATEST_INTERVAL = 6000000
        private const val DISPLACEMENT = 100000
    }

    init {
        buildGoogleApiClient()
        createLocationRequest()
        if (mGoogleApiClient != null) {
            mGoogleApiClient!!.connect()
        }
        if (mGoogleApiClient!!.isConnected) {
            startLocationUpdates()
        }
    }
}
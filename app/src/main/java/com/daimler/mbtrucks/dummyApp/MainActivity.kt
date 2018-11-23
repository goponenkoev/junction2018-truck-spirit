/*
 * Copyright (c) 2018. Daimler AG.
 */

package com.daimler.mbtrucks.dummyApp

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.daimler.mbtrucks.dummyApp.repository.vehicle.IVehicleDataSubscriber
import com.daimler.mbtrucks.dummyApp.repository.vehicle.VehicleDataRepository
import com.daimler.mbtrucks.dummyApp.services.DataSimulationService
import kotlinx.android.synthetic.main.activity_main.*

///
// Here we have our basic main activity. This is the entry point of connecting to the vehicle
///
class MainActivity : AppCompatActivity(), IVehicleDataSubscriber {

    companion object {
        private const val TAG = "MAIN"
    }

    // Define the variable for our vehicle repository, which will handle the vehicle connection
    // This will be done, when the activity is created
    lateinit var vehicleDataRepository: VehicleDataRepository

    // Define the variable for our data simulation service, which will give us defined simulated
    // values from the vehicle FMS interface or CAN bus
    lateinit var dataSimulationService: DataSimulationService

    // Define a helper variable for tracking if this activity is in foreground. So we are able to
    // handle some things that are not useful while in background
    private var isInForeground: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Here we inject everything defined within the injector like any depending services
        // or repositories
        Injector.inject(this)

        // After having all dependencies, we can now register this activity as a listener on
        // vehicle related messages
        vehicleDataRepository.register(this)

        // Finally just connect this application to the vehicle
        connectVehicle(applicationContext)
    }

    private fun connectVehicle(context: Context) {
        try {
            if (vehicleDataRepository.initializeSdk(context)) {
                vehicleDataRepository.connectVehicle(context)
                Toast.makeText(context, "Connection to vehicle established", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Couldn't connect to the vehicle due to $e")
            Toast.makeText(context, "Connection to vehicle couldn't be established", Toast.LENGTH_LONG).show()
        }
    }

    override fun onVehicleSpeed(speed: Float) {
        Log.i(TAG, "Current vehicle speed: $speed km/h")

        // Only do something with incoming values, if the app is in foreground
        if (isInForeground) {
            // Show the new incoming value on the ui
            txt_speed.text = speed.toInt().toString()
        }
    }

    override fun onTotalVehicleDistance(totalDistance: Long) {
        Log.i(TAG, "Current total vehicle distance: $totalDistance km")

        // Only do something with incoming values, if the app is in foreground
        if (isInForeground) {
            // Show the new incoming value on the ui
            // ...
        }
    }

    override fun onPause() {
        // This activity is not in foreground anymore
        isInForeground = false
        super.onPause()
    }

    override fun onDestroy() {
        // When the activity is going to be destroyed, we should also clear up everything
        // regarding the vehicle
        super.onDestroy()
        vehicleDataRepository.disconnectVehicle()
        vehicleDataRepository.deinitializeSdk()
        vehicleDataRepository.remove(this)
    }

    override fun onResume() {
        // We're back on... so also tell that our variable
        isInForeground = true
        super.onResume()
    }
}

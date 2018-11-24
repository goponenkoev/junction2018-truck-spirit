/*
 * Copyright (c) 2018. Daimler AG.
 */

package com.daimler.mbtrucks.dummyApp

import android.content.Context
import android.location.Criteria
import android.location.LocationManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fleetboard.sdk.lib.android.log.Log
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

    private var totalDistance: Long = 0L


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
        createLocationManager(applicationContext)
    }

    private fun createLocationManager(context: Context) {

        val CRITERIA = object : Criteria() {
            init {
                this.accuracy = Criteria.ACCURACY_FINE
                this.isAltitudeRequired = true
                this.bearingAccuracy = Criteria.ACCURACY_HIGH
                this.isBearingRequired = true
                this.horizontalAccuracy = Criteria.ACCURACY_HIGH
                this.speedAccuracy = Criteria.ACCURACY_HIGH
                this.isSpeedRequired = true
                this.verticalAccuracy = Criteria.ACCURACY_HIGH
            }
        }

        val enabled = true

        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val providerNames = lm.getProviders(CRITERIA, enabled)
        val bestProviderMatch = lm.getBestProvider(CRITERIA, enabled)

        Log.i(TAG, "TEST")
        Log.i(TAG, "LM: $providerNames")
        Log.i(TAG, "LM: $bestProviderMatch")


    }

    override fun onCruiseControl(cruiseControlState: Int) {
        // Only do something with incoming values, if the app is in foreground
        if (isInForeground) {
            // Show the new incoming value on the ui
            if (cruiseControlState == 0 ){ // OFF
                Log.i(TAG, "Current cruise control state is OFF ")
            }else if (cruiseControlState == 1){ // ON
                Log.i(TAG, "Current cruise control state id ON ")
            }
        }
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

    override fun onVehicleDistanceToObject(speed: Long) {
        Log.i(TAG, "Current distance to the object: $speed km")
        Log.i(TAG, "Current total distance: $totalDistance km")

        // Only do something with incoming values, if the app is in foreground
        if (isInForeground) {
            if (speed < totalDistance) {
                val rest = (totalDistance - speed).toDouble() / totalDistance.toDouble()
                progressBar.progress = (rest * 100).toInt()
            }
            // Show the new incoming value on the ui
        }
    }

    override fun onTotalEngineHours(totalEngineHours: Int) {
        Log.i(TAG, "Current total engine hours: $totalEngineHours km")

        // Only do something with incoming values, if the app is in foreground
        if (isInForeground) {
            // Show the new incoming value on the ui
        }
    }

    override fun onVehicleWeight(vehicleWeight: Int) {
        Log.i(TAG, "Current vehicle weight: $vehicleWeight kg")

        // Only do something with incoming values, if the app is in foreground
        if (isInForeground) {
            // Show the new incoming value on the ui
        }
    }

    override fun onFMSVersion(fmsVersion: Long) {
        Log.i(TAG, "Current FMS version: $fmsVersion")

        // Only do something with incoming values, if the app is in foreground
        if (isInForeground) {
            // Show the new incoming value on the ui
        }
    }

    override fun onEngineSpeed(engineSpeed: Long) {
        Log.i(TAG, "Current engine speed: $engineSpeed")

        // Only do something with incoming values, if the app is in foreground
        if (isInForeground) {
            // Show the new incoming value on the ui
        }
    }

    override fun onDistanceToForwardVehicle(distanceToForwardVehicle: Long) {
        Log.i(TAG, "Current distance to forward vehicle: $distanceToForwardVehicle km")

        // Only do something with incoming values, if the app is in foreground
        if (isInForeground) {
            // Show the new incoming value on the ui
        }
    }

    override fun onCoolantTemperature(coolantTemperature: Float) {
        Log.i(TAG, "Current coolant temperature: $coolantTemperature")

        // Only do something with incoming values, if the app is in foreground
        if (isInForeground) {
            // Show the new incoming value on the ui
        }
    }

    override fun onAccelPedalPos(accelPedalPos: Int) {
        Log.i(TAG, "Current accel pedal position: $accelPedalPos")

        // Only do something with incoming values, if the app is in foreground
        if (isInForeground) {
            // Show the new incoming value on the ui
        }
    }

    override fun onAmbientTemperature(temperature: Float) {
        Log.i(TAG, "Current ambient temperature: $temperature")

        // Only do something with incoming values, if the app is in foreground
        if (isInForeground) {
            // Show the new incoming value on the ui
        }
    }

    override fun onFuelLevel(fuelLevel: Int) {
        Log.i(TAG, "Current fuel level: $fuelLevel %")

        // Only do something with incoming values, if the app is in foreground
        if (isInForeground) {
            // Show the new incoming value on the ui
        }
    }

    override fun onTotalVehicleDistance(totalDistance: Long) {
        Log.i(TAG, "Current total vehicle distance: $totalDistance km")

        this.totalDistance = totalDistance
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

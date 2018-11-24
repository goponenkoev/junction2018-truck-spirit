/*
 * Copyright (c) 2018. Daimler AG.
 */

package com.daimler.mbtrucks.dummyApp.repository.vehicle

import android.content.Context
import com.fleetboard.sdk.lib.android.common.SDKInitializer
import com.fleetboard.sdk.lib.android.common.SDKInitializerException
import com.fleetboard.sdk.lib.android.vehicle.VehicleClient
import com.fleetboard.sdk.lib.android.vehicle.VehicleClientException
import com.fleetboard.sdk.lib.vehicle.IVehicleMessage
import com.fleetboard.sdk.lib.vehicle.ValidState
import com.fleetboard.sdk.lib.android.log.Log

///
// This repository handles the connection with the vehicle and the messages sent from the vehicle
// Here you have to define how to post the values of your topics of interest to all the subscribed
// activities or classes within this application
///
object VehicleDataRepository : IVehicleDataRepository, IVehicleDataPublisher {
    private const val TAG = "VEHICLE_DATA_REPO"

    override var subscribers: MutableList<IVehicleDataSubscriber> = mutableListOf()

    lateinit var vehicleClientCallback: VehicleClientCallback

    private const val MAX_SPEED = 162f

    override fun initializeSdk(context: Context): Boolean {

        return try {
            SDKInitializer.INSTANCE.init(context)
            true
        } catch (e: Exception) {
            Log.e(TAG, "Initialization of the SDK has failed: $e")
            false
        }
    }

    override fun deinitializeSdk() {
        try {
            SDKInitializer.INSTANCE.terminate()
        } catch (e: SDKInitializerException) {
            Log.e(TAG, "Termination of SDK failed: $e")
        }
    }

    override fun connectVehicle(context: Context): Boolean {
        try {
            if (!VehicleClient.INSTANCE.isConnected) {
                VehicleClient.INSTANCE.connect(vehicleClientCallback, context)

                vehicleClientCallback.onNewVehicleMessage = { handleMessage(it) }
            }
        } catch (e: VehicleClientException) {
            Log.e(TAG, "Connect VehicleClient failed: $e")
        }

        return VehicleClient.INSTANCE.isConnected
    }

    override fun disconnectVehicle() {
        try {
            if (VehicleClient.INSTANCE.isConnected) {
                VehicleClient.INSTANCE.disconnect()
            }
        } catch (e: VehicleClientException) {
            Log.e(TAG, "Disconnect VehicleClient failed: $e")
        }
    }

    ///
    // Here you need to define how to handle the incoming messages
    ///
    override fun handleMessage(message: IVehicleMessage) {
        // Handle every new incoming valid message and decide on how to post the data
        // within the application
        // You have to do this for each registered topic separately
        if (message.value != null) {

            Log.i(TAG, "TEST: topic ${message.topic}")
            when (message.topic) {

                VehicleClientData.topicList[0] -> {
                    val speed = message.valueAsFloat
                    if (speed in 0.0f..MAX_SPEED && message.validState == ValidState.VALID) {
                        postVehicleSpeed(speed)
                    }else{
                        android.util.Log.i(TAG, "Wrong value of vehicle speed: $speed km/h" +
                                " VALID_STATE: ${message.validState} ")
                    }
                }

                VehicleClientData.topicList[1] -> {
                    if (message.valueAsLong >= 0) {
                        postTotalVehicleDistance(message.valueAsLong)
                    }
                }

                VehicleClientData.topicList[2] -> {
                    if (message.valueAsLong >= 0) {
                        postDistanceToTheObject(message.valueAsLong)
                    }
                }

                VehicleClientData.topicList[3] -> {
                    val temperature = message.valueAsFloat
                    if (message.validState == ValidState.VALID) {
                        postAmbientTemperature(temperature)
                    }else{
                        android.util.Log.i(TAG, "Wrong value of AMBIENT_TEMPERATURE: $temperature *C" +
                                " VALID_STATE: ${message.validState} ")
                    }
                }

                VehicleClientData.topicList[4] -> {
                    val accelPedalPos = message.valueAsInteger
                    if (accelPedalPos in 0..360 && message.validState == ValidState.VALID) {
                        postAccelPedalPos(accelPedalPos)
                    }else{
                        android.util.Log.i(TAG, "Wrong value of ACCEL_PEDAL_POS: $accelPedalPos %" +
                                " VALID_STATE: ${message.validState} ")
                    }
                }

                VehicleClientData.topicList[5] -> {
                    val coolantTemperature = message.valueAsFloat
                    if (message.validState == ValidState.VALID) {
                        postCoolantTemperature(coolantTemperature)
                    }else{
                        android.util.Log.i(TAG, "Wrong value of COOLANT_TEMPERATURE: $coolantTemperature %" +
                                " VALID_STATE: ${message.validState} ")
                    }
                }

                VehicleClientData.topicList[6] -> {

                    val distanceToForwardVehicle = message.valueAsLong
                    if (distanceToForwardVehicle >= 0 && message.validState == ValidState.VALID) {
                        postDistanceToForwardVehivale(distanceToForwardVehicle)
                    }else{
                        android.util.Log.i(TAG, "Wrong value of DISTANCE_TO_FORWARD_VEHICLE: $distanceToForwardVehicle %" +
                                " VALID_STATE: ${message.validState} ")
                    }
                }

                VehicleClientData.topicList[7] -> {
                    val cruiseControlState = message.valueAsInteger
                    if (message.validState == ValidState.VALID) {
                        postCruiseControlState(cruiseControlState)
                    }else{
                        android.util.Log.i(TAG, "Wrong value of CRUISE_CONTROL_STATE: $cruiseControlState %" +
                                " VALID_STATE: ${message.validState} ")
                    }
                }

                VehicleClientData.topicList[8] -> {
                    val engineSpeed = message.valueAsLong
                    if (engineSpeed >= 0 && message.validState == ValidState.VALID) {
                        postEngineSpeed(engineSpeed)
                    }else{
                        android.util.Log.i(TAG, "Wrong value of ENGINE_SPEED: $engineSpeed %" +
                                " VALID_STATE: ${message.validState} ")
                    }
                }

                VehicleClientData.topicList[9] -> {
                    val fmsVersion = message.valueAsLong
                    Log.i("TEST", " $fmsVersion")
                    if (message.validState == ValidState.VALID) {
                        postFMSVersion(fmsVersion)
                    }else{
                        android.util.Log.i(TAG, "Wrong value of FMS_MODULE_SW_VERSION: $fmsVersion %" +
                                " VALID_STATE: ${message.validState} ")
                    }
                }

                VehicleClientData.topicList[10] -> {
                    val fuelLevel = message.valueAsInteger
                    if (fuelLevel in 0..100 && message.validState == ValidState.VALID) {
                        postFuelLevel(fuelLevel)
                    }else{
                        Log.i(TAG, "Wrong value of FUEL_LEVEL: $fuelLevel %" +
                                " VALID_STATE: ${message.validState} ")
                    }
                }

                VehicleClientData.topicList[11] -> {
                    val vehicleWeight = message.valueAsInteger
                    if (vehicleWeight > 0 && message.validState == ValidState.VALID) {
                        postVehicleWeight(vehicleWeight)
                    }else{
                        Log.i(TAG, "Wrong value of VEHICLE_WEIGHT: $vehicleWeight %" +
                                " VALID_STATE: ${message.validState} ")
                    }
                }

                VehicleClientData.topicList[12] -> {
                    val totalEngineHours = message.valueAsInteger
                    if (message.validState == ValidState.VALID) {
                        postTotalEngineHours(totalEngineHours)
                    }else{
                        Log.i(TAG, "Wrong value of TOTAL_ENGINE_HOURS: $totalEngineHours %" +
                                " VALID_STATE: ${message.validState} ")
                    }
                }

            }
        }
    }

    private fun postTotalEngineHours(totalEngineHours: Int) {
        subscribers.forEach { it.onTotalEngineHours(totalEngineHours) }
    }

    private fun postVehicleWeight(vehicleWeight: Int) {
        subscribers.forEach { it.onVehicleWeight(vehicleWeight) }
    }

    private fun postFuelLevel(fuelLevel: Int) {
        subscribers.forEach { it.onFuelLevel(fuelLevel) }
    }

    private fun postFMSVersion(fmsVersion: Long) {
        subscribers.forEach { it.onFMSVersion(fmsVersion) }
    }

    private fun postEngineSpeed(engineSpeed: Long) {
        subscribers.forEach { it.onEngineSpeed(engineSpeed) }
    }

    private fun postCruiseControlState(cruiseControlState: Int) {
        subscribers.forEach { it.onCruiseControl(cruiseControlState) }
    }

    private fun postDistanceToForwardVehivale(distanceToForwardVehicle: Long) {
        subscribers.forEach { it.onDistanceToForwardVehicle(distanceToForwardVehicle) }
    }

    private fun postCoolantTemperature(coolantTemperature: Float) {
        subscribers.forEach { it.onCoolantTemperature(coolantTemperature) }
    }

    private fun postAccelPedalPos(accelPedalPos: Int) {
        subscribers.forEach { it.onAccelPedalPos(accelPedalPos) }
    }

    private fun postAmbientTemperature(temperature: Float) {
        subscribers.forEach { it.onAmbientTemperature(temperature) }
    }

    private fun postVehicleSpeed(speed: Float) {
        subscribers.forEach { it.onVehicleSpeed(speed) }
    }

    private fun postTotalVehicleDistance(totalDistance: Long) {
        subscribers.forEach { it.onTotalVehicleDistance(totalDistance) }
    }

    private fun postDistanceToTheObject(distance: Long) {
        subscribers.forEach { it.onVehicleDistanceToObject(distance) }
    }

    override fun register(subscriber: IVehicleDataSubscriber) {
        subscribers.add(subscriber)
    }

    override fun remove(subscriber: IVehicleDataSubscriber) {
        subscribers.remove(subscriber)
    }
}

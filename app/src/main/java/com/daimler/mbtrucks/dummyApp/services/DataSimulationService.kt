/*
 * Copyright (c) 2018. Daimler AG.
 */

package com.daimler.mbtrucks.dummyApp.services

import android.os.Handler
import com.daimler.mbtrucks.dummyApp.repository.vehicle.VehicleDataRepository.handleMessage
import com.fleetboard.sdk.lib.vehicle.ValidState
import com.fleetboard.sdk.lib.vehicle.VehicleMessage
import com.fleetboard.sdk.lib.vehicle.VehicleTopicConsts

///
// Helper service for simulating incoming data from the FMS interface or CAN bus
///
object DataSimulationService {

    // Define a set of messages
    var messages: Array<VehicleMessage> = arrayOf(
            VehicleMessage(VehicleTopicConsts.VEHICLE_SPEED, 9.3f, ValidState.VALID),
            VehicleMessage(VehicleTopicConsts.VEHICLE_SPEED, 25.4f, ValidState.VALID),
            VehicleMessage(VehicleTopicConsts.VEHICLE_SPEED, 45.4f, ValidState.VALID),
            VehicleMessage(VehicleTopicConsts.VEHICLE_ID, 1, ValidState.VALID),
            VehicleMessage(VehicleTopicConsts.VEHICLE_ID, 1, ValidState.VALID),
            VehicleMessage(VehicleTopicConsts.ACCEL_PEDAL_POS, 1, ValidState.VALID),
            VehicleMessage(VehicleTopicConsts.VEHICLE_SPEED, -95.4f, ValidState.VALID),
            VehicleMessage(VehicleTopicConsts.VEHICLE_SPEED, 189.4f, ValidState.VALID),
            VehicleMessage(VehicleTopicConsts.VEHICLE_SPEED, 95.4f, ValidState.VALID),
            VehicleMessage(VehicleTopicConsts.VEHICLE_SPEED, 95.4f, ValidState.VALID),
            VehicleMessage(VehicleTopicConsts.VEHICLE_SPEED, 95.4f, ValidState.VALID),
            VehicleMessage(VehicleTopicConsts.TOTAL_ENGINE_HOURS, 9, ValidState.VALID),
            VehicleMessage(VehicleTopicConsts.VEHICLE_SPEED, 95.4f, ValidState.ERROR),
            VehicleMessage(VehicleTopicConsts.FUEL_LEVEL, 99, ValidState.VALID),
            VehicleMessage(VehicleTopicConsts.VEHICLE_SPEED, 95.4f, ValidState.VALID),
            VehicleMessage(VehicleTopicConsts.FMS_MODULE_SW_VERSION, 666L, ValidState.VALID),
            VehicleMessage(VehicleTopicConsts.VEHICLE_SPEED, 95.4f, ValidState.VALID),
            VehicleMessage(VehicleTopicConsts.AMBIENT_TEMPERATURE, 14.5f, ValidState.VALID),
            VehicleMessage(VehicleTopicConsts.DISTANCE_TO_FORWARD_VEHICLE, 9545648L, ValidState.VALID),
            VehicleMessage(VehicleTopicConsts.COOLANT_TEMPERATURE, 107.4f, ValidState.VALID),
            VehicleMessage(VehicleTopicConsts.CRUISE_CONTROL_STATE, 1, ValidState.VALID),
            VehicleMessage(VehicleTopicConsts.CRUISE_CONTROL_STATE, 0, ValidState.VALID),
            VehicleMessage(VehicleTopicConsts.VEHICLE_SPEED, 90.4f, ValidState.VALID))

    init {
        getDataFromSensor()
    }

    // Simulate data coming from the sensors every time interval [2sec]
    private fun getDataFromSensor() {
        val handler = Handler()
        var element = 0

        val runnable = object : Runnable {
            override fun run() {
                if (element < messages.size) handleMessage(messages[element])

                element++
                handler.postDelayed(this, 100)
            }
        }
        handler.postDelayed(runnable, 2000)
    }
}

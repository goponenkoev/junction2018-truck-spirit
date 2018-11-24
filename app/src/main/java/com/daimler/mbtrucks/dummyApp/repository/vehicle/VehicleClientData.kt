/*
 * Copyright (c) 2018. Daimler AG.
 */

package com.daimler.mbtrucks.dummyApp.repository.vehicle

import com.fleetboard.sdk.lib.vehicle.VehicleTopicConsts

///
// Register all your topics (data points) your application should interact with,
// within this object below
///
object VehicleClientData {

    val topicList = intArrayOf(
            VehicleTopicConsts.VEHICLE_SPEED, // 0
            VehicleTopicConsts.TOTAL_VEHICLE_DISTANCE, // 1
            VehicleTopicConsts.ABA_DISTANCE_TO_OBJECT, // 2
            VehicleTopicConsts.AMBIENT_TEMPERATURE, // 3
            VehicleTopicConsts.ACCEL_PEDAL_POS, // 4
            VehicleTopicConsts.COOLANT_TEMPERATURE, // 5
            VehicleTopicConsts.DISTANCE_TO_FORWARD_VEHICLE, // 6
            VehicleTopicConsts.CRUISE_CONTROL_STATE, // 7
            VehicleTopicConsts.ENGINE_SPEED, // 8
            VehicleTopicConsts.FMS_MODULE_SW_VERSION, // 9
            VehicleTopicConsts.FUEL_LEVEL,// 10
            VehicleTopicConsts.VEHICLE_WEIGHT, // 11
            VehicleTopicConsts.TOTAL_ENGINE_HOURS // 12
            // ...
    )
}

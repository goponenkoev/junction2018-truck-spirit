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
            VehicleTopicConsts.VEHICLE_SPEED,
            VehicleTopicConsts.TOTAL_VEHICLE_DISTANCE
            // ...
    )
}

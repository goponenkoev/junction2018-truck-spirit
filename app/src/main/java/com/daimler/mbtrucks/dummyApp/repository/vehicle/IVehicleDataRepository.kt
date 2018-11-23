/*
 * Copyright (c) 2018. Daimler AG.
 */

package com.daimler.mbtrucks.dummyApp.repository.vehicle

import android.content.Context
import com.fleetboard.sdk.lib.vehicle.IVehicleMessage

///
// Nothing needs to be changed here
///
interface IVehicleDataRepository {
    fun initializeSdk(context: Context): Boolean
    fun deinitializeSdk()
    fun connectVehicle(context: Context): Boolean
    fun disconnectVehicle()
    fun handleMessage(message: IVehicleMessage)
}

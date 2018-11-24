/*
 * Copyright (c) 2018. Daimler AG.
 */

package com.daimler.mbtrucks.dummyApp.repository.vehicle

///
// Declare all your methods in this interface so that interested classes
// could subscribe to new incoming data
///
interface IVehicleDataSubscriber {
    fun onVehicleSpeed(speed: Float)
    fun onTotalVehicleDistance(totalDistance: Long)
    fun onVehicleDistanceToObject(speed: Long)
    fun onCruiseControl(cruiseControlState: Int)
    fun onTotalEngineHours(totalEngineHours: Int)
    fun onVehicleWeight(vehicleWeight: Int)
    fun onFuelLevel(fuelLevel: Int)
    fun onFMSVersion(fmsVersion: Long)
    fun onEngineSpeed(engineSpeed: Long)
    fun onDistanceToForwardVehicle(distanceToForwardVehicle: Long)
    fun onCoolantTemperature(coolantTemperature: Float)
    fun onAccelPedalPos(accelPedalPos: Int)
    fun onAmbientTemperature(temperature: Float)
}

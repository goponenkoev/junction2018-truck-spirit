/*
 * Copyright (c) 2018. Daimler AG.
 */

package com.daimler.mbtrucks.dummyApp

import com.daimler.mbtrucks.dummyApp.repository.vehicle.VehicleClientCallback
import com.daimler.mbtrucks.dummyApp.repository.vehicle.VehicleDataRepository
import com.daimler.mbtrucks.dummyApp.services.DataSimulationService

///
// Bundle all your new repositories, services and activities together for
// easy dependency injection
///
object Injector {

    private fun vehicleDataRepository(): VehicleDataRepository {
        val fleetboardVehicleDataRepository = VehicleDataRepository
        fleetboardVehicleDataRepository.vehicleClientCallback = Injector.vehicleClientCallback()

        return fleetboardVehicleDataRepository
    }

    private fun vehicleClientCallback(): VehicleClientCallback {
        return VehicleClientCallback()
    }

    private fun dataSimulationService(): DataSimulationService {
        return DataSimulationService
    }

    fun inject(mainActivity: MainActivity) {
        mainActivity.vehicleDataRepository = Injector.vehicleDataRepository()
        mainActivity.dataSimulationService = Injector.dataSimulationService()
    }
}

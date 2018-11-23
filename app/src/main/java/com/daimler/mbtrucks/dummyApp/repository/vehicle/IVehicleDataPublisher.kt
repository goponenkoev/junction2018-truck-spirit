/*
 * Copyright (c) 2018. Daimler AG.
 */

package com.daimler.mbtrucks.dummyApp.repository.vehicle

///
// Nothing needs to be changed here
///
interface IVehicleDataPublisher {
    var subscribers: MutableList<IVehicleDataSubscriber>

    fun register(subscriber: IVehicleDataSubscriber)
    fun remove(subscriber: IVehicleDataSubscriber)
}

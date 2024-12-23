package io.pc7.ninu.di

import io.pc7.ninu.data.ble.BLECommunicationHandler
import io.pc7.ninu.data.ble.repository.PerfumeBleCommunication
import io.pc7.ninu.data.ble.repository.StatsCommunication
import io.pc7.ninu.data.ble.repository.SystemCommunication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val bluetoothDi = module {

    singleOf(::BLECommunicationHandler)
    singleOf(::PerfumeBleCommunication)
    singleOf(::StatsCommunication)
    singleOf(::SystemCommunication)

}
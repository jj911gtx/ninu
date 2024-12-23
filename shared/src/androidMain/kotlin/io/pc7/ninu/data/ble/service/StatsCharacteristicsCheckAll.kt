//package io.pc7.ninu.data.ble.service
//
//import android.bluetooth.BluetoothGatt
//import core.bluetooth.services.StatsService.Companion.CHARACTERISTIC_STATS_UUID
//import io.pc7.ninu.data.ble.service.StatsService.Companion.CHARACTERISTIC_STATS_UUID
//import java.util.UUID
//
//class StatsCharacteristicsCheckAll {
//    operator fun invoke(gatt: BluetoothGatt) {
//        gatt.getService(UUID.fromString(StatsService.SERVICE_UUID))?.let { service ->
//            try{
//                service.getCharacteristic(UUID.fromString(StatsService.CHARACTERISTIC_STATS_UUID))
//            }catch (e: Exception){
//                println("no characteristic $CHARACTERISTIC_STATS_UUID")
//            }
//        }?:{
//            throw IllegalStateException("No service ${StatsService.SERVICE_UUID}")
//        }
//
//
//    }
//
//
//
//}

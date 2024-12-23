//package io.pc7.ninu.data.ble.service
//
//import android.bluetooth.BluetoothGatt
//import core.bluetooth.services.SystemService
//import core.bluetooth.services.SystemService.Companion.CHARACTERISTIC_ACTIVATION_UUID
//import core.bluetooth.services.SystemService.Companion.CHARACTERISTIC_SYSTEM_LOCKED_UUID
//import core.bluetooth.services.SystemService.Companion.CHARACTERISTIC_TIME_SYNC_UUID
//import java.util.UUID
//
//class SystemCharacteristicsCheckAll {
//    operator fun invoke(gatt: BluetoothGatt) {
//        gatt.getService(UUID.fromString(SystemService.SERVICE_UUID))?.let { service ->
//
//            try{
//                service.getCharacteristic(UUID.fromString(
//                    SystemService.CHARACTERISTIC_SYSTEM_LOCKED_UUID
//                ))
//            }catch (e: Exception){
//                println("no characteristic $CHARACTERISTIC_SYSTEM_LOCKED_UUID")
//            }
//
//            try{
//                service.getCharacteristic(UUID.fromString(SystemService.CHARACTERISTIC_ACTIVATION_UUID))
//            }catch (e: Exception){
//                println("no characteristic $CHARACTERISTIC_ACTIVATION_UUID")
//            }
//
//
//            try{
//                service.getCharacteristic(UUID.fromString(SystemService.CHARACTERISTIC_TIME_SYNC_UUID))
//            }catch (e: Exception){
//                println("no characteristic $CHARACTERISTIC_TIME_SYNC_UUID")
//            }
//
//        }?:{
//            throw IllegalStateException("No service ${StatsService.SERVICE_UUID}")
//        }
//
//
//
//    }
//
//
//
//}

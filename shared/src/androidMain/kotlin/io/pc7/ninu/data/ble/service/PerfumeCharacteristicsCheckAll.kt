//package io.pc7.ninu.data.ble.service
//
//import android.bluetooth.BluetoothGatt
//import android.bluetooth.BluetoothGattCharacteristic
//import android.bluetooth.BluetoothGattService
//import java.util.UUID
//
//class PerfumeCharacteristicsCheckAll {
//    operator fun invoke(gatt: BluetoothGatt) {
//        gatt.getService(UUID.fromString(PerfumeService.SERVICE_UUID))?.let { service ->
//            fun BluetoothGattService.getPerfumeCharacteristic(
//                sku: String,
//                sn: String,
//                doseVolume: String,
//                remainingVolume: String,
//            ) {
//                fun String.getCHaracteristic(): BluetoothGattCharacteristic? {
//                    try {
//                        return this@getPerfumeCharacteristic.getCharacteristic(UUID.fromString(this))
//                    }catch (e: Exception){
//                        println("No characteristic: $this")
//                    }
//                    return null
//
//                }
//                sku.getCHaracteristic()
//                sn.getCHaracteristic()
//                doseVolume.getCHaracteristic()
//                remainingVolume.getCHaracteristic()
//            }
//            service.getPerfumeCharacteristic(CHARACTERISTIC_SKU_1_UUID, CHARACTERISTIC_SN_1_UUID, CHARACTERISTIC_DOSE_VOLUME_1_UUID, CHARACTERISTIC_REMAINING_VOLUME_1_UUID)
//            service.getPerfumeCharacteristic(CHARACTERISTIC_SKU_2_UUID, CHARACTERISTIC_SN_2_UUID, CHARACTERISTIC_DOSE_VOLUME_2_UUID, CHARACTERISTIC_REMAINING_VOLUME_2_UUID)
//            service.getPerfumeCharacteristic(CHARACTERISTIC_SKU_3_UUID, CHARACTERISTIC_SN_3_UUID, CHARACTERISTIC_DOSE_VOLUME_3_UUID, CHARACTERISTIC_REMAINING_VOLUME_3_UUID)
//            return
//        } ?: {
//            throw IllegalStateException("No service ${PerfumeService.SERVICE_UUID}")
//        }
//
//
//    }
//
//
//
//}
//
//
//

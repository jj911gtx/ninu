package io.pc7.ninu.data.ble


import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.bluetooth.BluetoothStatusCodes
import android.content.Context
import android.os.Build
import io.pc7.ninu.data.ble.model.BleConnectionStatus
import io.pc7.ninu.data.ble.model.BleError
import io.pc7.ninu.data.ble.model.BleException
import io.pc7.ninu.data.ble.model.BleResult
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid

actual class BLECommunicationHandler(
    private val context: Context
) {
    private val adapter: BluetoothAdapter = (context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
    var gatt: BluetoothGatt? = null



    private val _notifyFlow = MutableSharedFlow<ByteArray>()
    val notifyFlow = _notifyFlow.asSharedFlow()

    private val _serviceDiscoveredFlow = MutableStateFlow<BluetoothGatt?>(null)
    val serviceDiscoveredFlow = _serviceDiscoveredFlow.asSharedFlow()

    private val _bleConnection = MutableStateFlow<BleConnectionStatus>(BleConnectionStatus.Disconnected)
    actual val bleConnection = _bleConnection.asStateFlow()


    private val notifyCustomScope = CoroutineScope(Dispatchers.IO + Job())

    private val characteristicReadDeferreds = mutableMapOf<UUID, CompletableDeferred<ByteArray?>>()
    private val characteristicWriteDeferreds = mutableMapOf<UUID, CompletableDeferred<Unit>>()



    private var address: String? = null

    @SuppressLint("MissingPermission")
    fun connect(address: String = "00:12:32:17:62:31"){
        this.address = address
        val device = adapter.getRemoteDevice(address)
        device.connectGatt(context, false, gattCallback)
    }

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    @SuppressLint("MissingPermission")
    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            _bleConnection.value = newState.toBleConnectionStatus()
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                this@BLECommunicationHandler.gatt = gatt
                gatt.discoverServices()
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                disconnectGatt(gatt)
            }
        }


        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            super.onServicesDiscovered(gatt, status)
            if (status == BluetoothGatt.GATT_SUCCESS) {
                scope.launch {
                    _serviceDiscoveredFlow.emit(gatt)
                }
            }
        }


        override fun onCharacteristicRead(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic, value: ByteArray, status: Int) {
            super.onCharacteristicRead(gatt, characteristic, value, status)

            val characteristicUuid = characteristic.uuid
            val deferred = characteristicReadDeferreds.remove(characteristicUuid) // Remove entry to clean up

            if (status == BluetoothGatt.GATT_SUCCESS) {
                deferred?.complete(value) // Complete with the read value on success
            } else {
                deferred?.completeExceptionally(BleException(status)) // Complete with an exception on error
            }
        }


        override fun onCharacteristicWrite(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic, status: Int) {
            super.onCharacteristicWrite(gatt, characteristic, status)

            val characteristicUuid = characteristic.uuid
            val deferred = characteristicWriteDeferreds[characteristicUuid]

            if (status == BluetoothGatt.GATT_SUCCESS) {
                deferred?.complete(Unit)
            } else {
                deferred?.completeExceptionally(BleException(status))
            }

            characteristicWriteDeferreds.remove(characteristicUuid)
        }



        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray
        ) {
            super.onCharacteristicChanged(gatt, characteristic, value)

            notifyCustomScope.launch {
                _notifyFlow.emit(value)
            }
        }
    }

    private val operationMutex = Mutex()

    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalUuidApi::class)
    @SuppressLint("MissingPermission")
    actual suspend fun readCharacteristic(serviceUuid: Uuid, characteristicUUID: Uuid): BleResult<ByteArray> {
        return operationMutex.withLock {
            safeCall {
                suspendCancellableCoroutine { continuation ->
                    val deferred = CompletableDeferred<ByteArray?>()

                    characteristicReadDeferreds[characteristicUUID.toJavaUuid()] = deferred

                    val success = gatt?.readCharacteristic(gatt?.getService(serviceUuid.toJavaUuid())?.getCharacteristic(characteristicUUID.toJavaUuid())) ?: false

                    if (!success) {
                        characteristicReadDeferreds.remove(characteristicUUID.toJavaUuid())
                        continuation.resumeWithException(BleException(BluetoothGatt.GATT_FAILURE))
                        return@suspendCancellableCoroutine
                    }

                    deferred.invokeOnCompletion {
                        characteristicReadDeferreds.remove(characteristicUUID.toJavaUuid())
                        if (deferred.isCompleted) {
                            deferred.getCompleted()?.let { result ->
                                continuation.resume(result)
                            } ?: continuation.resumeWithException(BleException(BluetoothGatt.GATT_FAILURE))
                        } else {
                            continuation.resumeWithException(BleException(BluetoothGatt.GATT_FAILURE))
                        }
                    }
                }
            }
        }
    }




    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalUuidApi::class)
    @SuppressLint("MissingPermission")
    actual suspend fun writeCharacteristic(serviceUuid: Uuid, characteristicUUID: Uuid, value: ByteArray): BleResult<Unit> {
        return operationMutex.withLock {
            safeCall {
                suspendCancellableCoroutine { continuation ->
                    val characteristicUuid = characteristicUUID.toJavaUuid()

                    val deferred = CompletableDeferred<Unit>()
                    characteristicWriteDeferreds[characteristicUuid] = deferred

                    val characteristic = gatt?.getService(serviceUuid.toJavaUuid())?.getCharacteristic(characteristicUuid)
                    if(characteristic == null){
                        continuation.resumeWithException(BleException(BluetoothGatt.STATE_DISCONNECTED))
                        return@suspendCancellableCoroutine
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        val respond = gatt?.writeCharacteristic(
                            characteristic,
                            value,
                            BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT
                        )
                        when (respond) {
                            BluetoothStatusCodes.SUCCESS -> {
                                continuation.resume(Unit)
                                return@suspendCancellableCoroutine
                            }

                            else -> {}
                        }
                    } else {
                        characteristic.value = value
                        characteristic.writeType = BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT
                        val success = gatt?.writeCharacteristic(characteristic)
                        success?.let {
                            if (!success) {
                                continuation.resume(Unit)
                                return@suspendCancellableCoroutine
                            }
                        }
                    }
                    continuation.resumeWithException(BleException(BluetoothGatt.GATT_FAILURE))



                    deferred.invokeOnCompletion {
                        if (deferred.isCompleted) {
                            continuation.resume(deferred.getCompleted())
                        } else {
                            continuation.resumeWithException(BleException(BluetoothGatt.GATT_FAILURE))
                        }
                    }
                }
            }

        }
    }






    @SuppressLint("MissingPermission")
    private fun disconnectGatt(gatt: BluetoothGatt?) {
        gatt?.disconnect()
        gatt?.close()
        this.gatt = null
        notifyCustomScope.cancel()
    }


    private fun Int.toBleConnectionStatus(): BleConnectionStatus{
        return when(this){
            BluetoothProfile.STATE_CONNECTED -> BleConnectionStatus.Connected
            else -> BleConnectionStatus.Disconnected
        }
    }

    @SuppressLint("MissingPermission")
    actual suspend fun connectSuspending(): BleResult<Unit> {
        if(address == null){
            return BleResult.Error(BleError.NoAddressInitialized)
        }
        return suspendCancellableCoroutine { continuation ->
            if (_bleConnection.value == BleConnectionStatus.Connecting) {
                continuation.resume(BleResult.Error(BleError.Connecting))
                return@suspendCancellableCoroutine
            }

            _bleConnection.value = BleConnectionStatus.Connecting

            val timeoutJob = scope.launch {
                delay(CONNECTION_TIMEOUT)
                if (continuation.isActive) {
                    _bleConnection.value = BleConnectionStatus.Disconnected
                    continuation.resume(BleResult.Error(BleError.ConnectionTimeout))
                }
            }

            // Create connection state collector
            val stateJob = scope.launch {
                _bleConnection
                    .onSubscription {
                        val device = adapter.getRemoteDevice(address)
                        device.connectGatt(context, false, gattCallback)
                    }
                    .takeWhile { state ->
                        state == BleConnectionStatus.Connecting || continuation.isActive
                    }
                    .collect { state ->
                        when (state) {
                            BleConnectionStatus.Connected -> {
                                timeoutJob.cancel()
                                continuation.resume(BleResult.Success(Unit))
                            }
                            BleConnectionStatus.Failed -> {
                                timeoutJob.cancel()
                                continuation.resume(BleResult.Error(BleError.ConnectionFailed))
                            }
                            else -> {}
                        }
                    }
            }

            continuation.invokeOnCancellation {
                timeoutJob.cancel()
                stateJob.cancel()
                disconnectGatt(gatt)
            }
        }
    }

    companion object {
        private const val CONNECTION_TIMEOUT = 30_000L // 30 seconds
    }
}


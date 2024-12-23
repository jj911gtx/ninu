package io.pc7.ninu.presentation.home

sealed class HomeScreenEvent {
    data object BluetoothConnected: HomeScreenEvent()

}
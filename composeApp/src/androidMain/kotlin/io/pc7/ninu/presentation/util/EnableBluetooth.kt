package io.pc7.ninu.presentation.util

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable


@Composable
@SuppressLint("MissingPermission")
fun EnableBluetooth(onBluetoothResult: (Boolean) -> Unit): ActivityResultLauncher<Intent> {
    return rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            onBluetoothResult(true)
        } else {
            onBluetoothResult(false)
        }
    }

}

fun ActivityResultLauncher<Intent>.launchEnableBle() {
    val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
    launch(enableBtIntent)
}
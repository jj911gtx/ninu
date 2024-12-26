package io.pc7.ninu.presentation.util.permission

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import io.pc7.ninu.presentation.components.dialog.PermissionDialog


@Composable
fun managePermissionPermissionDisplay(
    onAllPermissionsGranted: () -> Unit,

): ActivityResultLauncher<Array<String>> {


    val permissions = remember { cameraPermissions() }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        var allGranted = true
        permissions.forEachIndexed { index, permission ->
            if (perms[permission.value.permission.permission] == false) {
                permissions[index].value = permission.value.copy(showRational = true)
                allGranted = false
            }
        }
        if (allGranted) {
            onAllPermissionsGranted()
        }
    }


    permissions.forEachIndexed {index, permission ->
        if (permission.value.showRational) {
            PermissionDialog(
                onDismiss = { permissions[index].value = permissions[index].value.copy(showRational = false) },
                permission = permission.value.permission
            )
        }
    }

    return permissionLauncher
}

fun ActivityResultLauncher<Array<String>>.requestCameraPermission() {

    val requiredPermissions = arrayOf(Manifest.permission.CAMERA)

    launch(requiredPermissions)

}

private fun bluetoothPermissions(): MutableList<MutableState<PermissionRationalState>> {
    val savedPermissions = mutableListOf<MutableState<PermissionRationalState>>()
    getBTPermissionsForBuildSDK().forEach {
        savedPermissions.add(mutableStateOf(PermissionRationalState(it, false)))
    }
    return savedPermissions
}

fun cameraPermissions(): MutableList<MutableState<PermissionRationalState>> {
    val savedPermissions = mutableListOf<MutableState<PermissionRationalState>>()
    getCameraPermissions().forEach {
        savedPermissions.add(mutableStateOf(PermissionRationalState(it, false)))
    }
    return savedPermissions
}

private fun ActivityResultLauncher<Array<String>>.requestPermissions() {

    val requiredPermissions = getBTPermissionsForBuildSDK().map { it.permission } + Manifest.permission.CAMERA


    if (requiredPermissions.isNotEmpty()) {
        launch(requiredPermissions.toTypedArray()/*requiredPermissions.toList().map { it.permission }.toTypedArray()*/)
    }
}
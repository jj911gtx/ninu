package io.pc7.ninu.presentation.util.permission

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog


@Composable
fun ManageBluetoothPermissionDisplay(
) {
    fun statePermissions(): MutableList<MutableState<PermissionRationalState>> {
        val savedPermissions = mutableListOf<MutableState<PermissionRationalState>>()
        getBTPermissionsForBuildSDK().forEach {
            savedPermissions.add(mutableStateOf(PermissionRationalState(it, false)))
        }
        return savedPermissions
    }

    val permissions = remember {
        statePermissions()
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        permissions.forEachIndexed { index, permission ->
            if (perms[permission.value.permission.permission] == false) {
                permissions[index].value = permission.value.copy(showRational = true)
            }
        }
//        if (allGranted) {
//            onAllPermissionsGranted()
//        }
    }
    LaunchedEffect(Unit) {
        permissionLauncher.requestPermissions()
    }


    permissions.forEachIndexed {index, permission ->
        if (permission.value.showRational) {
            Dialog(onDismissRequest = {
//                /*permissions[index] = */permission.value = permission
            }) {
                Column(
                    modifier = Modifier.background(Color.White)
                ) {
                    val xx = permission.value.permission.permission
                    Text(
                        text = xx,
                        color = Color.Black
                    )
                }
            }
        }
    }
}




private fun ActivityResultLauncher<Array<String>>.requestPermissions(

) {

    val requiredPermissions = getBTPermissionsForBuildSDK().map { it.permission } + Manifest.permission.CAMERA


    if (requiredPermissions.isNotEmpty()) {
        launch(requiredPermissions.toTypedArray()/*requiredPermissions.toList().map { it.permission }.toTypedArray()*/)
    }
}
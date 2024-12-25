package io.pc7.ninu.presentation.util.permission

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi


enum class BTPermissionsAPIOld(override val permission: String, override val description: String):
    Permissions {
    BLUETOOTH(Manifest.permission.BLUETOOTH, "To connect"),
    COARSE_LOCATION(Manifest.permission.ACCESS_COARSE_LOCATION, "To be able to connect with bluetooth to device"),
    FINE_LOCATION(Manifest.permission.ACCESS_FINE_LOCATION, "To be able to connect with bluetooth to device")
}

@RequiresApi(Build.VERSION_CODES.S)
enum class BTPermissionsAPINew(override val permission: String, override val description: String):
    Permissions {
    BLUETOOTH_CONNECT(Manifest.permission.BLUETOOTH_CONNECT, "To connect with bluetooth"),
}



fun getBTPermissionsForBuildSDK(): List<Permissions>{
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        BTPermissionsAPINew.entries
    } else {
        BTPermissionsAPIOld.entries
    }
}





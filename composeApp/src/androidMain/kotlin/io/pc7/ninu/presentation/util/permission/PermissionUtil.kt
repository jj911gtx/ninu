package io.pc7.ninu.presentation.util.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat



sealed interface Permissions{
    val permission: String
    val description: String
}

private fun Context.hasPermissions(permissions: List<String>): Boolean {
    permissions.forEach { permission ->
        if(ContextCompat.checkSelfPermission(
                this,
                permission
            ) != PackageManager.PERMISSION_GRANTED){
            return false
        }
    }
    return true
}


data class PermissionRationalState(
    val permission: Permissions,
    val showRational: Boolean
)

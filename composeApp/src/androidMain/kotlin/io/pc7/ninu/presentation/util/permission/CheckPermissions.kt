package io.pc7.ninu.presentation.util.permission

import android.content.Context
import android.content.pm.PackageManager


//data class PermissionGrantedState(
//    val permission: Permissions,
//    val isGranted: Boolean
//)
//
//class CheckPermissions(
//    private val context: Context
//){
//
//    operator fun invoke(
//        permissions: List<Permissions> = getBTPermissionsForBuildSDK()
//    ): List<PermissionGrantedState>{
//        val permissions = permissions.map { PermissionGrantedState(it, false) }.toMutableList()
//        permissions.forEachIndexed {index, permission ->
//            permissions[index] = permission.copy(isGranted = context.checkSelfPermission(permission.permission.permission) == PackageManager.PERMISSION_GRANTED)
//        }
//        return permissions
//    }
//
//
//
//    fun hasAllPermissions(): Boolean{
//        invoke().forEach {
//            if(!it.isGranted){
//                return false
//            }
//        }
//        return true
//    }
//}
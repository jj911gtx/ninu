package io.pc7.ninu.presentation.util.permission

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.pc7.ninu.presentation.components.main.ScrollableColumn


//@Composable
//fun NeedPermissions(
//    context: Context
//
//) {
//    val permissions = CheckPermissions(context).invoke(getBTPermissionsForBuildSDK())
//
//    var display by remember { mutableStateOf(true) }
//    Dialog(onDismissRequest = { display = false }) {
//        ScrollableColumn(
//            verticalArrangement = Arrangement.spacedBy(5.dp)
//        ) {
//            Text(text = "You denied permisions so enable them in settings and then you will be able to pair device:")
//            permissions.forEach {
//                if(!it.isGranted){
//                    Text(text = it.permission.permission)
//                }
//            }
//        }
//    }
//}
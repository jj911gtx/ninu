//package io.pc7.ninu.presentation.other
//
//import android.Manifest
//import android.content.Context
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.ActivityResultLauncher
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.animation.core.animateDpAsState
//import androidx.compose.animation.core.tween
//import androidx.compose.animation.fadeIn
//import androidx.compose.animation.fadeOut
//import androidx.compose.animation.slideInVertically
//import androidx.compose.animation.slideOutVertically
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.material3.Button
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavHostController
//import io.pc7.ninu.presentation.components.main.ScrollableColumn
//import io.pc7.ninu.presentation.components.util.ObserveAsEvents
//import org.koin.androidx.compose.koinViewModel
//import si.zpiz.mzpiz.R
//import si.zpiz.mzpiz.domain.language.localization.localized
//import si.zpiz.mzpiz.ui.components.scroll.ScrollableColumn
//import si.zpiz.mzpiz.ui.components.button.ZPIZButtonContainerColorEmpty
//import si.zpiz.mzpiz.ui.components.input.text.TextInputBracket
//import si.zpiz.mzpiz.ui.navigation.NavRoutes
//import si.zpiz.mzpiz.ui.other.BarcodeCameraScreen
//import si.zpiz.mzpiz.ui.util.ObserveAsEvents
//import si.zpiz.mzpiz.ui.util.hasCameraPermission
//import si.zpiz.mzpiz.ui.util.shouldShowCameraPermissionRationale
//
//
//@Composable
//fun BarcodeInputScreenRoot(
//    navController: NavHostController,
//    navToSetPin: () -> Unit,
//) {
//
//
//    val localContext = LocalContext.current
//    val permissionLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestPermission()
//    ) { perms ->
//        val activity = localContext as ComponentActivity
////        val showCameraRational = activity.shouldShowCameraPermissionRationale()
//
//        println("BarcodeInputAction.OnUpdatePermissions")
//    }
//
////    ObserveAsEvents(flow = viewModel.events) {event ->
////        when(event){
////            BarcodeInputEvent.Next -> {
////                navController.navigate(NavRoutes.Registration.SetPin)
////            }
////        }
////    }
//
//    BarcodeInputScreen(
//        navNext = navToSetPin,
//        displayBarcodeScanner = {
//
//
//        }
//    )
//
//
//}
//
//
//
//@Composable
//private fun BarcodeInputScreen(//TODO wait ui
//    navNext: () -> Unit,
//    displayBarcodeScanner: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//
//    Box(
//        Modifier.fillMaxSize()
//    ) {
//        ScrollableColumn(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalAlignment  = Alignment.CenterHorizontally
//        ) {
//
//
//
//
//            Spacer(modifier = Modifier.height(50.dp))
//
//            Text(text = "scan_or_enter_code",
//                style = MaterialTheme.typography.headlineSmall,
//            )
//            Spacer(modifier = Modifier.height(10.dp))
//
//
//
////        if(state.openBarcodeReader){
////            BarcodeCameraScreen(onDetected = {onAction(BarcodeInputAction.OnBarcodeDetected(it))})
////            Spacer(modifier = Modifier.height(50.dp))
////            Button(onClick = { onAction(BarcodeInputAction.OnBarcodeReaderToggle(false)) }
////            ) {
////                Text(text = "Close")
////            }
////        }else{
////            Button(onClick = displayBarcodeScanner
////            ) {
////                Text(text = "Open barcode reader")
////            }
////        }
////
////        if(state.openBarcodeReader){
////            Button(onClick = { onAction(BarcodeInputAction.OnBarcodeReaderToggle(false)) }
////            ) {
////                Text(text = "Close")
////            }
////        }else{
////            Button(onClick = displayBarcodeScanner
////            ) {
////                Text(text = "Open barcode reader")
////            }
////        }
//
//
//
//        }
//
//        Button(onClick = navNext/*{ onAction(BarcodeInputAction.OnNext) }*/,
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .fillMaxWidth()
//        ) {
//            Text("activate".localized)
//        }
//
//    }
//
//}
//
//
//private fun ActivityResultLauncher<String>.requestPermissions(
//    context: Context
//) {
//    if(!context.hasCameraPermission()){
//        launch(Manifest.permission.CAMERA)
//    }
//}
//@Preview
//@Composable
//fun BarcodeInputScreenPreview() {
//    BarcodeInputScreen(
//        BarcodeInputState(),
//        {},
//        {},
//        {}
//    )
//}
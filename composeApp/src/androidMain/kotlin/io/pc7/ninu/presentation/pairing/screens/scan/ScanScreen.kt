package io.pc7.ninu.presentation.pairing.screens.scan

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import io.pc7.ninu.R
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.presentation.components.main.input.text.NINUTextField
import io.pc7.ninu.presentation.components.other.GrayBracketWithText
import io.pc7.ninu.presentation.components.util.ObserveAsEvents
import io.pc7.ninu.presentation.components.util.rememberKeyboardVisibility
import io.pc7.ninu.presentation.pairing.scan.ScanAction
import io.pc7.ninu.presentation.pairing.scan.ScanEvent
import io.pc7.ninu.presentation.pairing.scan.ScanState
import io.pc7.ninu.presentation.pairing.scan.ScanViewModel
import io.pc7.ninu.presentation.pairing.screens.PairingDefaultScreen
import io.pc7.ninu.presentation.theme.NINUTheme
import io.pc7.ninu.presentation.util.BarcodeAnalyzer
import io.pc7.ninu.presentation.util.permission.managePermissionPermissionDisplay
import io.pc7.ninu.presentation.util.permission.requestCameraPermission


@Composable
fun ScanScreen(
    viewModel: ScanViewModel,
    navNext: () -> Unit,
    navBack: () -> Unit,
) {


    ObserveAsEvents(flow = viewModel.events) {event ->
        when(event){
            ScanEvent.NavNext -> navNext()
        }
    }

    ScanScreen(
        state = viewModel.state.collectAsState().value,
        action = {viewModel.action(it)},
        navBack,
    )

}

@Composable
private fun ScanScreen(
    state: ScanState,
    action: (ScanAction) -> Unit,
    navBack: () -> Unit
) {



    var scanEnabled by remember { mutableStateOf(false) }
    val permissionLauncher = managePermissionPermissionDisplay(
        onAllPermissionsGranted = {scanEnabled = true}
    )

    PairingDefaultScreen(
        backText = stringResource(R.string.device_info),
        navBack = navBack,
        bracketContent = {
            Box {
                val localContext = LocalContext.current
                val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
                val cameraProviderFuture = remember {
                    ProcessCameraProvider.getInstance(localContext)
                }
                AnimatedVisibility(
                    visible = scanEnabled,
                    enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
                    exit = fadeOut(animationSpec = tween(durationMillis = 1000))
                ) {

                    AndroidView(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .aspectRatio(1f)
                            .fillMaxWidth()
                            .clickable { scanEnabled = false }
                            .background(Color.Green),
                        factory = { context ->
                            val previewView = PreviewView(context)
                            val preview = androidx.camera.core.Preview.Builder().build()
                            val selector = CameraSelector.Builder()
                                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                                .build()

                            preview.surfaceProvider = previewView.surfaceProvider

                            val imageAnalysis = ImageAnalysis.Builder().build()
                            imageAnalysis.setAnalyzer(
                                ContextCompat.getMainExecutor(context),
                                BarcodeAnalyzer(context) { barcode ->
                                    action(ScanAction.OnBarcodeDetect(barcode))
                                    cameraProviderFuture.get().unbindAll()
                                }
                            )

                            runCatching {
                                cameraProviderFuture.get().bindToLifecycle(
                                    lifecycleOwner,
                                    selector,
                                    preview,
                                    imageAnalysis
                                )
                            }.onFailure {
                                Log.e("CAMERA", "Camera bind error ${it.localizedMessage}", it)
                            }

                            previewView
                        },
                        update = { view ->
                            // Handle any updates to the view here
                        },
                        onRelease = { view ->
                            // Clean up resources
                            cameraProviderFuture.get().unbindAll()
                        }
                    )
                }

                AnimatedVisibility(
                    visible = !scanEnabled,
                    enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
                    exit = fadeOut(animationSpec = tween(durationMillis = 1000))
                ) {
                    GrayBracketWithText(
                        content = { },
                        text = stringResource(R.string.scan_serial_number),
                        onClick = {
                            permissionLauncher.requestCameraPermission()
                        }
                    )
                }

            }


        },
//        bracketContentWithTxt = {
//            var openBarcodeReader by remember { mutableStateOf(false) }
//            val sizeAnimation = animateDpAsState(
//                targetValue = if (openBarcodeReader) 350.dp else 0.dp,
//                animationSpec = tween(durationMillis = 400)
//            )
//            AnimatedVisibility(
//                visible = openBarcodeReader,
//                enter = fadeIn() + slideInVertically(initialOffsetY = { -it }),
//                exit = fadeOut() + slideOutVertically(targetOffsetY = { -it })
//            ) {
//                Column(modifier = Modifier
//                    .fillMaxWidth()
//                    .height(sizeAnimation.value),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    BarcodeCameraScreen(onDetected = { println("onDetected") })
//                    Spacer(modifier = Modifier.weight(1f))
//                    ZPIZButtonContainerColorEmpty(
//                        onClick = { onAction(BarcodeInputAction.OnBarcodeReaderToggle(false)) }
//                    ) {
//                        Text(text = "close".localized)
//                    }
//                }
//            }
//            Icon(painter = painterResource(id = R.drawable.icon_scan), contentDescription = "Scan",
//                tint = colorScheme.white
//            )
//        },
        onClickHelp = { /*TODO*/ },
        buttonOnCLick = {
            action(ScanAction.OnProceed)
        },
        buttonText = stringResource(R.string.proceed),
        isButtonEnabled = state.serialNumber.value.isNotEmpty(),
    ){
        if(!rememberKeyboardVisibility().value){
            Text(
                text = stringResource(R.string.or),
                color = colorScheme.primaryLight,
                style = MaterialTheme.typography.bodyMedium

            )
        }



        NINUTextField(
            value = state.serialNumber ,
            onUpdate = {action(ScanAction.OnSerialNumberUpdate(it))},
            placeholderText = stringResource(R.string.enter_serial_number),
        )

    }



}


@Preview
@Composable
private fun ScanScreenPreview() {
    NINUTheme{
        ScanScreen(
            state = ScanState(),
            action = {},
            navBack = {}
        )
    }
}


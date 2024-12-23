//package io.pc7.ninu.presentation.other
//
//import androidx.camera.core.CameraSelector
//import androidx.camera.core.ImageAnalysis
//import androidx.camera.core.Preview
//import androidx.camera.lifecycle.ProcessCameraProvider
//import androidx.camera.view.PreviewView
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.core.content.ContextCompat
//import androidx.lifecycle.compose.LocalLifecycleOwner
//import io.pc7.ninu.presentation.util.BarcodeAnalyzer
//
//@Composable
//fun BarcodeCameraScreen(
//    onDetected: (String) -> Unit,
//
//) {
//    val localContext = LocalContext.current
//    val lifecycleOwner = LocalLifecycleOwner.current
//    val cameraProviderFuture = remember {
//        ProcessCameraProvider.getInstance(localContext)
//    }
//    Box(
//        modifier = Modifier/*.padding(20.dp)*/
//            .fillMaxWidth(0.7f)
//            .fillMaxHeight(0.7f),
////            .size(height = 300.dp, width = 250.dp),
//        contentAlignment = Alignment.Center
//    ) {
//        AndroidView(
//            modifier = Modifier
//                .clip(RoundedCornerShape(10.dp)),
//            factory = { context ->
//                val previewView = PreviewView(context)
//                val preview = Preview.Builder().build()
//                val selector = CameraSelector.Builder()
//                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
//                    .build()
//
//                preview.setSurfaceProvider(previewView.surfaceProvider)
//
//                val imageAnalysis = ImageAnalysis.Builder().build()
//                imageAnalysis.setAnalyzer(
//                    ContextCompat.getMainExecutor(context),
//                    BarcodeAnalyzer(context) { barcode ->
//                        onDetected(barcode)
//                        cameraProviderFuture.get().unbindAll()
//                    }
//                )
//
//                runCatching {
//                    cameraProviderFuture.get().bindToLifecycle(
//                        lifecycleOwner,
//                        selector,
//                        preview,
//                        imageAnalysis
//                    )
//                }.onFailure {
//                    Log.e("CAMERA", "Camera bind error ${it.localizedMessage}", it)
//                }
//
//                // Return the PreviewView
//                previewView
//            },
//            update = { view ->
//                // Handle any updates to the view here
//            },
//            onRelease = { view ->
//                // Clean up resources
//
//                cameraProviderFuture.get().unbindAll()
//            }
//        )
//    }
//
//
//}
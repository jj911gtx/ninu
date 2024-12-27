package io.pc7.ninu.presentation.components.other

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.FileProvider
import io.pc7.ninu.R
import io.pc7.ninu.presentation.util.permission.managePermissionPermissionDisplay
import io.pc7.ninu.presentation.util.permission.requestCameraPermission
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date


@Composable
fun TakeChosePhoto(
    photoSelectOptionBottomSheetDisplay: MutableState<Boolean>,
    onUpdate: (ByteArray) -> Unit,
) {
    fun createImageFile(context: Context): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )
    }



    val context = LocalContext.current
    var photoUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { selectedUri ->
            context.contentResolver.openInputStream(selectedUri)?.use { inputStream ->
                val bytes = inputStream.readBytes()
                onUpdate(bytes)
            }
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            photoUri?.let { uri ->
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    val bytes = inputStream.readBytes()
                    onUpdate(bytes)
                }
            }
        }
    }



    val permissionLauncher = managePermissionPermissionDisplay(
        onAllPermissionsGranted = {
            try {
                val photoFile = createImageFile(context)
                photoUri = FileProvider.getUriForFile(
                    context,
                    "io.pc7.ninu.fileprovider", // matches manifest
                    photoFile
                )
                cameraLauncher.launch(photoUri!!)
                photoSelectOptionBottomSheetDisplay.value = false
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
        }
    )

    if(photoSelectOptionBottomSheetDisplay.value){
        NINUModalSheet(
            onDismiss = { photoSelectOptionBottomSheetDisplay.value = false },
            onConfirm = null
        ) {
            NINUModalBottomSheetItem(
                text = stringResource(R.string.take_photo),
                onClick = {
                    permissionLauncher.requestCameraPermission()
                }
            )
            NINUModalBottomSheetItem(
                text = stringResource(R.string.chose_photo),
                onClick = {
                    galleryLauncher.launch("image/*")
                    photoSelectOptionBottomSheetDisplay.value = false
                }
            )
        }
    }
}
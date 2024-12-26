package io.pc7.ninu.presentation.register.userInfo

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import io.pc7.ninu.R
import io.pc7.ninu.data.mapper.toTextString
import io.pc7.ninu.domain.model.input.MyInput
import io.pc7.ninu.presentation.components.main.ScrollableColumn
import io.pc7.ninu.presentation.components.main.buttons.ButtonTopLeftBack
import io.pc7.ninu.presentation.components.main.buttons.DefaultButtonText
import io.pc7.ninu.presentation.components.main.input.datePicker.CalendarDatePicker
import io.pc7.ninu.presentation.components.main.input.text.NINUTextField
import io.pc7.ninu.presentation.components.main.input.text.NINUinputFieldNoText
import io.pc7.ninu.presentation.components.other.NINUModalBottomSheetItem
import io.pc7.ninu.presentation.components.other.NINUModalSheet
import io.pc7.ninu.presentation.pairing.purchaseInfo.PurchaseInfoAction
import io.pc7.ninu.presentation.theme.NINUTheme
import org.koin.androidx.compose.koinViewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date


@Composable
fun UserInfoInputScreen(
    next: () -> Unit,
    back: () -> Unit,
    viewModel: UserInfoInputViewModel = koinViewModel<UserInfoInputViewModelAndroid>().viewModel
) {

    UserInfoInputScreen(
        state = viewModel.state.collectAsState().value,
        action = {viewModel.action(it)},
        next = next,
        back = back
    )

}

private fun createImageFile(context: Context): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_${timeStamp}_"
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

    return File.createTempFile(
        imageFileName,  /* prefix */



        ".jpg",         /* suffix */
        storageDir      /* directory */
    )
}

@Composable
fun UserInfoInputScreen(
    state: UserInfoInputState,
    action: (UserInfoInputAction) -> Unit,
    next: () -> Unit,
    back: () -> Unit,
) {



    ScrollableColumn(
        modifier = Modifier

            .fillMaxSize()
            .imePadding()
        ,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        ButtonTopLeftBack(
            onClick = back,
            text = "Create your account",
            modifier = Modifier
                .align(Alignment.Start)
        )

        Spacer(modifier = Modifier.weight(1f))
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            NINUTextField(
                value = state.username,
                onUpdate = {action(UserInfoInputAction.OnUsernameChange(it))},
                placeholderText = "Username",
                prefix = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_verified_user),
                        contentDescription = null
                    )
                }
            )


            val showCalendar = remember { mutableStateOf(false) }
            CalendarDatePicker(
                onDateChanged = {action(UserInfoInputAction.OnDateOfBirthChange(it))},
                showDialog = showCalendar,
            )
            NINUinputFieldNoText(
                value = MyInput(state.dateOfBirth.value?.toTextString() ?: ""),
                placeholderText = "Date of birth",
                suffix = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_image_plus),
                        contentDescription = null
                    )
                },
                onClick = {
                    showCalendar.value = true
                }
            )
            AddProfileImage(
                profileImage = state.profileImage,
                onUpdate = {
                    action(UserInfoInputAction.OnUploadProfileImage(it))
                }

            )



        }

        Spacer(modifier = Modifier.weight(2f))

        DefaultButtonText(
            onClick = {
                if(inputsFilled(state)){
                    action(UserInfoInputAction.UploadData)
                }else{
                    next()
                }
            },
            text = if(inputsFilled(state)) "Done" else "Do this later",
            displayDisabled = !inputsFilled(state),
        )

    }



}

@Composable
fun AddProfileImage(
    profileImage: MyInput<Unit?>,
    onUpdate: (ByteArray) -> Unit,


) {
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

    var photoSelectOptionBottomSheetDisplay by remember { mutableStateOf(false) }

    NINUinputFieldNoText(
        value = MyInput(if(profileImage.value != null) "Uploaded" else ""),
        placeholderText = "Add profile image",
        suffix = {
            Icon(
                painter = painterResource(id = if(profileImage.value != null) R.drawable.icon_check else R.drawable.icon_image_plus),
                contentDescription = null
            )
        },
        onClick = { photoSelectOptionBottomSheetDisplay = true }

    )

    if(photoSelectOptionBottomSheetDisplay){
        NINUModalSheet(
            onDismiss = { photoSelectOptionBottomSheetDisplay = false },
            onConfirm = null
        ) {
            NINUModalBottomSheetItem(
                text = "Take photo",
                onClick = {
                    try {
                        val photoFile = createImageFile(context)
                        photoUri = FileProvider.getUriForFile(
                            context,
                            "io.pc7.ninu.fileprovider", // matches manifest
                            photoFile
                        )
                        cameraLauncher.launch(photoUri!!)
                        photoSelectOptionBottomSheetDisplay = false
                    } catch (ex: IOException) {
                        ex.printStackTrace()
                    }
                }
            )
            NINUModalBottomSheetItem(
                text = "Choose photo",
                onClick = {
                    galleryLauncher.launch("image/*")
                    photoSelectOptionBottomSheetDisplay = false
                }
            )
        }
    }

}

private fun inputsFilled(state: UserInfoInputState): Boolean{
    return state.username.value.isNotEmpty()
            && state.dateOfBirth.value != null
            && state.profileImage.value == Unit
}



@Preview
@Composable
private fun UserInfoInputScreenPreview() {
    NINUTheme {
        UserInfoInputScreen(
            state = UserInfoInputState(),
            action = {}, next = {}, back = {}

        )
    }
}



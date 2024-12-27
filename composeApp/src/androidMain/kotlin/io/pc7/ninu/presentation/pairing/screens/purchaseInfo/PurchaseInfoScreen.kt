package io.pc7.ninu.presentation.pairing.screens.purchaseInfo

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.R
import io.pc7.ninu.data.mapper.toStringSlash
import io.pc7.ninu.domain.model.input.MyInput
import io.pc7.ninu.presentation.components.main.input.datePicker.CalendarDatePicker
import io.pc7.ninu.presentation.components.main.input.text.NINUinputFieldNoText
import io.pc7.ninu.presentation.components.other.GrayBracket
import io.pc7.ninu.presentation.components.other.GrayBracketWithText
import io.pc7.ninu.presentation.components.other.NINUModalBottomSheetItem
import io.pc7.ninu.presentation.components.other.NINUModalSheet
import io.pc7.ninu.presentation.pairing.purchaseInfo.PurchaseInfoAction
import io.pc7.ninu.presentation.pairing.purchaseInfo.PurchaseInfoState
import io.pc7.ninu.presentation.pairing.purchaseInfo.PurchaseInfoViewModel
import io.pc7.ninu.presentation.pairing.screens.PairingDefaultScreen
import io.pc7.ninu.presentation.theme.NINUTheme
import java.io.File
import android.os.Environment
import io.pc7.ninu.presentation.components.other.TakeChosePhoto
import java.text.SimpleDateFormat
import java.util.Date


@Composable
fun PurchaseInfoScreen(
    viewModel: PurchaseInfoViewModel,
    navBack: () -> Unit,
) {

    PurchaseInfoScreen(
        state = viewModel.state.collectAsState().value,
        action = {viewModel.action(it)},
        navBack = navBack
    )
}


//fun saveByteArrayAsImage(context: Context, byteArray: ByteArray, filename: String) {
//    try {
//        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
//
//        val jpgFilename = if (!filename.endsWith(".jpg")) "$filename.jpg" else filename
//        val file = File(context.filesDir, jpgFilename)
//
//        FileOutputStream(file).use { fos ->
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
//        }
//        println(file.path)
//
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//}


private fun createImageFile(context: Context): File {
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


@Composable
private fun PurchaseInfoScreen(
    state: PurchaseInfoState,
    action: (PurchaseInfoAction) -> Unit,
    navBack: () -> Unit,
) {
    val context = LocalContext.current

    PairingDefaultScreen(
        backText = "Pairing",
        navBack = navBack,
//        bracketContentWithTxt = {
//            Image(painter = painterResource(id = R.drawable.device), contentDescription = "Device",
//                modifier = Modifier
//                    .size(200.dp)
//            )
//        },
        bracketContent = {
            when(state.deviceConnected){
                true -> {
                    @Composable
                    fun Item(
                        title: String,
                        text: String,
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(text = title,
                                style = MaterialTheme.typography.bodyMedium,
                                color = colorScheme.primaryLighter
                            )
                            Text(text = text,
                                style = MaterialTheme.typography.headlineSmall,
                                color = colorScheme.white
                            )
                        }
                    }
                    GrayBracket {
                        Column(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .fillMaxWidth()
                            ,
                            verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.Top)
                        ) {
                            Item(
                                title = "Device Serial Number",
                                text = "j70kh7ikbasf900asf84jsf"
                            )
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                Item(
                                    title = "Warranty",
                                    text = "2 years"
                                )
                                Item(
                                    title = "Firmware versions",
                                    text = "1.4.1"
                                )
                            }
                        }
                    }
                }
                false -> {
                    GrayBracketWithText(
                        content = {
                            Column {
                                Image(painter = painterResource(id = R.drawable.device), contentDescription = "Device",
                                    modifier = Modifier.size(200.dp))
                            }
                        },
                        text = "Press and hold power button on your NINU device.",

                    )

                }
            }
        } ,
        onClickHelp = { /*TODO*/ },
        buttonOnCLick = { /*TODO*/ },
        buttonText = "Register",
        isButtonEnabled = true,
    ) {

        var displayWhereBoughtOptions  by remember { mutableStateOf(false) }
        val whereBoughtState = state.whereBought
        val whereBought = MyInput<String>(value = whereBoughtState.value ?: "", errors = whereBoughtState.errors, displayErrors = whereBoughtState.displayErrors)
        NINUinputFieldNoText(
            value = whereBought,
            placeholderText = "Where did you buy it?",
            onClick = { displayWhereBoughtOptions = true },
        )
        if(displayWhereBoughtOptions){
            var selectedItem by remember { mutableStateOf<String?>(null) }
            NINUModalSheet(
                onDismiss = { displayWhereBoughtOptions = false },
                onConfirm = {
                    selectedItem?.let { action(PurchaseInfoAction.OnWhereBoughtUpdate(it)) }
                    displayWhereBoughtOptions = false
                }
            ) {
                NINUModalBottomSheetItem(
                    text = "Online",
                    selected = selectedItem == "Online",
                    onClick = { selectedItem = "Online" }
                )
                NINUModalBottomSheetItem(
                    text = "Gift",
                    selected =  selectedItem == "Gift",
                    onClick = { selectedItem = "Gift" }
                )
                NINUModalBottomSheetItem(
                    text = "Store",
                    selected =  selectedItem == "Store",
                    onClick = { selectedItem = "Store" }
                )
            }
        }




        val showCalendar = remember { mutableStateOf(false) }
        val dateOfPurchaseState = state.dateOfPurchase
        val dateOfPurchase = MyInput<String>(value = dateOfPurchaseState.value?.toStringSlash() ?: "", errors = dateOfPurchaseState.errors, displayErrors = dateOfPurchaseState.displayErrors)
        NINUinputFieldNoText(
            value = dateOfPurchase,
            placeholderText = "Date of purchase",
            onClick = { showCalendar.value = true },
            suffix = {
                Icon(painter = painterResource(id = R.drawable.icon_calendar), contentDescription = null,
                    tint = colorScheme.white,
                    modifier = Modifier
                        .size(20.dp)
                )
            }
        )
        CalendarDatePicker(onDateChanged = {action(PurchaseInfoAction.OnDateOfPurchaseUpdate(it))}, showDialog = showCalendar)


        val proofOfPurchaseState = state.proofOfPurchase
        val proofOfPurchase = MyInput<String>(
            value = if (proofOfPurchaseState.value != null) "Image selected" else "", 
            errors = proofOfPurchaseState.errors, 
            displayErrors = proofOfPurchaseState.displayErrors
        )

        val photoSelectOptionBottomSheetDisplay = remember { mutableStateOf(false) }
        NINUinputFieldNoText(
            value = proofOfPurchase,
            placeholderText = "Proof of purchase",
            onClick = {
                photoSelectOptionBottomSheetDisplay.value = true
            },
            suffix = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(painter = painterResource(id = R.drawable.icon_image_plus), contentDescription = null,
                        tint = colorScheme.white,
                        modifier = Modifier.size(20.dp)
                    )
                    proofOfPurchaseState.value?.let {
                        Icon(painter = painterResource(id = R.drawable.bracket_check), contentDescription = null,
                            tint = colorScheme.successMedium,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        )

        TakeChosePhoto(
            photoSelectOptionBottomSheetDisplay = photoSelectOptionBottomSheetDisplay,
            onUpdate = {action(PurchaseInfoAction.OnProofOfPurchaseUpdate(it))}

        )


    }
}


@Preview
@Composable
private fun Screen2Preview() {
    NINUTheme {
        PurchaseInfoScreen(
            state = PurchaseInfoState(deviceConnected = true),
            action = {},
            navBack = {}
        )

    }
}
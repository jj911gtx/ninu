package feature.pairing.screens.purchaseInfo

import android.content.Intent
import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.startActivityForResult
import core.presentation.theme.custom.colorScheme
import io.pc7.ninu.R
import io.pc7.ninu.data.mapper.toStringSlash
import io.pc7.ninu.domain.model.input.MyInput
import io.pc7.ninu.presentation.components.main.buttons.DefaultButtonText
import io.pc7.ninu.presentation.components.main.card.CardBracket
import io.pc7.ninu.presentation.components.main.card.XCard
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




@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PurchaseInfoScreen(
    state: PurchaseInfoState,
    action: (PurchaseInfoAction) -> Unit,
    navBack: () -> Unit,
) {

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
        )
        CalendarDatePicker(onDateChanged = {action(PurchaseInfoAction.OnDateOfPurchaseUpdate(it))}, showDialog = showCalendar)


        val proofOfPurchaseState = state.proofOfPurchase
        val proofOfPurchase = MyInput<String>(value = proofOfPurchaseState.value ?: "", errors = proofOfPurchaseState.errors, displayErrors = proofOfPurchaseState.displayErrors)
        NINUinputFieldNoText(
            value = proofOfPurchase,
            placeholderText = "Proof of purchase",
            onClick = {
//                val SELECT_PICTURE = 1
//                // ...
//                val pickIntent = Intent()
//                pickIntent.setType("image/*")
//                pickIntent.setAction(Intent.ACTION_GET_CONTENT)
//
//                val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//
//                val pickTitle = "Select or take a new Picture" // Or get from strings.xml
//                val chooserIntent = Intent.createChooser(pickIntent, pickTitle)
//                chooserIntent.putExtra(
//                    Intent.EXTRA_INITIAL_INTENTS,
//                    arrayOf(takePhotoIntent)
//                )
//                startActivityForResult(chooserIntent, SELECT_PICTURE)
            },
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
package feature.pairing.screens.purchaseInfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import core.presentation.theme.custom.colorScheme
import io.pc7.ninu.R
import io.pc7.ninu.data.mapper.toStringSlash
import io.pc7.ninu.domain.model.input.MyInput
import io.pc7.ninu.presentation.components.GrayBracketWithText
import io.pc7.ninu.presentation.components.main.buttons.DefaultButtonText
import io.pc7.ninu.presentation.components.main.card.CardBracket
import io.pc7.ninu.presentation.components.main.card.XCard
import io.pc7.ninu.presentation.components.main.input.datePicker.CalendarDatePicker
import io.pc7.ninu.presentation.components.main.input.text.NINUinputFieldNoText
import io.pc7.ninu.presentation.pairing.purchaseInfo.PurchaseInfoAction
import io.pc7.ninu.presentation.pairing.purchaseInfo.PurchaseInfoState
import io.pc7.ninu.presentation.pairing.purchaseInfo.PurchaseInfoViewModel
import io.pc7.ninu.presentation.pairing.screens.PairingDefaultScreen
import io.pc7.ninu.presentation.theme.NINUTheme


@Composable
fun PurchaseInfoScreen(
    viewModel: PurchaseInfoViewModel,
    navBack: () -> Unit
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
        baseBracketContent = {
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
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .aspectRatio(1f)
                            .fillMaxWidth()
                            .background(colorScheme.custom3D3D3D)
                            .padding(horizontal = 30.dp, vertical = 30.dp)
                        ,
//                        horizontalAlignment = Alignment.CenterHorizontally,
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
                false -> {
                    GrayBracketWithText(
                        content = {
                            Column {
                                Image(painter = painterResource(id = R.drawable.device), contentDescription = "Device",
                                    modifier = Modifier.size(200.dp))
                            }
                        },
                        text = "Press and hold power button on your NINU device."
                    )

                }
            }
        } ,

        bracketText = "Press and hold power button on your NINU device.",
        onClickHelp = { /*TODO*/ },
        buttonOnCLick = { /*TODO*/ },
        buttonText = "Register",
        isButtonEnabled = true,
        onBracketCLick = {},


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
            ModalBottomSheet(
                onDismissRequest = { displayWhereBoughtOptions = false },
                containerColor = Color.Transparent
            ) {
                var selectedItem by remember { mutableStateOf<String?>(null) }
                @Composable
                fun Item(
                    text: String,
                    selected: Boolean,
                ) {
                    CardBracket(
                        onClick = { selectedItem = text },
                        selected = selected,
                        selectedContainerColor = colorScheme.primaryMedium,
                        unselectedContainerColor = colorScheme.primaryDarkest,
                        cornerShape = 15.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = text,
                            style = MaterialTheme.typography.headlineSmall,
                            color = colorScheme.white,
                            modifier = Modifier
                                .padding(15.dp)
                        )
                    }
                }
                XCard(
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Item(
                            text = "Online",
                            selected =  selectedItem == "Online",
                        )
                        Item(
                            text = "Gift",
                            selected =  selectedItem == "Gift",
                        )
                        Item(
                            text = "Store",
                            selected =  selectedItem == "Store",
                        )

                        DefaultButtonText(
                            onClick = {
                                selectedItem?.let { action(PurchaseInfoAction.OnWhereBoughtUpdate(it)) }
                                displayWhereBoughtOptions = false
                            },
                            text = "Confirm",
                            modifier = Modifier
                                .padding(top = 15.dp)
                        )
                    }

                }

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
            onClick = {  },
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
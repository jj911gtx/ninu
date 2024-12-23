package io.pc7.ninu.presentation.pairing.screens.scan

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import core.presentation.theme.custom.colorScheme
import io.pc7.ninu.R
import io.pc7.ninu.presentation.components.main.input.text.NINUTextField
import io.pc7.ninu.presentation.components.util.ObserveAsEvents
import io.pc7.ninu.presentation.components.util.rememberKeyboardVisibility
import io.pc7.ninu.presentation.pairing.scan.ScanAction
import io.pc7.ninu.presentation.pairing.scan.ScanEvent
import io.pc7.ninu.presentation.pairing.scan.ScanState
import io.pc7.ninu.presentation.pairing.scan.ScanViewModel
import io.pc7.ninu.presentation.pairing.screens.PairingDefaultScreen
import io.pc7.ninu.presentation.theme.NINUTheme
import io.pc7.ninu.presentation.util.permission.ManageBluetoothPermissionDisplay
import kotlinx.coroutines.launch


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
    var showPermissionDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    ManageBluetoothPermissionDisplay(
        onAllPermissionsGranted = {
            showPermissionDialog = false
            action(ScanAction.OnProceed)
        },
        onDismiss = {
            showPermissionDialog = false
        }
    )


    PairingDefaultScreen(
        backText = "Device info",
        navBack = navBack,
        bracketContentWithTxt = {
            var openBarcodeReader by remember { mutableStateOf(false) }
            val sizeAnimation = animateDpAsState(
                targetValue = if (openBarcodeReader) 350.dp else 0.dp,
                animationSpec = tween(durationMillis = 400)
            )
//            AnimatedVisibility(
//                visible = openBarcodeReader,
//                enter = fadeIn() + slideInVertically(initialOffsetY = { -it }),
//                exit = fadeOut() + slideOutVertically(targetOffsetY = { -it })
//            ) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .height(sizeAnimation.value),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
//                    BarcodeCameraScreen(onDetected = { println("onDetected") })
                    Spacer(modifier = Modifier.weight(1f))
//                    ZPIZButtonContainerColorEmpty(
//                        onClick = { onAction(BarcodeInputAction.OnBarcodeReaderToggle(false)) }
//                    ) {
//                        Text(text = "close".localized)
//                    }
                }
//            }
//            Icon(painter = painterResource(id = R.drawable.icon_scan), contentDescription = "Scan",
//                tint = colorScheme.white
//            )
        },
        bracketText = "Scan the serial number that is usually located at the bottom of your NINU device.",
        onClickHelp = { /*TODO*/ },
        buttonOnCLick = {
            showPermissionDialog = true
        },
        buttonText =  "Proceed",
        isButtonEnabled = state.serialNumber.value.isNotEmpty(),
        onBracketCLick = {

        }
    ){
        if(!rememberKeyboardVisibility().value){
            Text(
                text = "or",
                color = colorScheme.primaryLight,
                style = MaterialTheme.typography.bodyMedium

            )
        }



        NINUTextField(
            value = state.serialNumber ,
            onUpdate = {action(ScanAction.OnSerialNumberUpdate(it))},
            placeholderText = "Enter serial number",
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


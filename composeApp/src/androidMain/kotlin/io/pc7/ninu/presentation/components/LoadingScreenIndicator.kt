package io.pc7.ninu.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider


@Composable
fun LoadingDialog(
    isLoading: Boolean = true
) {
    if (isLoading) {

        Dialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            ),
            onDismissRequest = {},
        ){
            (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0.3f)
            Box(
                modifier = Modifier
//                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)) // Set the background to transparent
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()

            }

        }
    }


}
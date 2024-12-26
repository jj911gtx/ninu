package io.pc7.ninu.presentation.components.dialog


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.pc7.ninu.presentation.theme.NINUTheme


@Composable
fun NINUDialog(
    onDismiss: () -> Unit,
    content: @Composable () -> Unit,
){

    Dialog(
        onDismissRequest = onDismiss
    ) {
        content()
    }
    
}


@Preview
@Composable
private fun NINUDialogPreview(){
    NINUTheme{
        NINUDialog(
            onDismiss = {},
            content = {

            }
        )
    
    }
}






package io.pc7.ninu.presentation.components.other

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import io.pc7.ninu.presentation.theme.custom.colorScheme

@Composable
fun GrayBracketWithText(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    text: String,
    onClick: (() -> Unit)? = null,

) {
    GrayBracket(
        onClick = onClick
    ){
        content()
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = colorScheme.white
        )
    }
}


@Composable
fun ColumnScope.TextForGrayButton(
    content: @Composable () -> Unit,
    text: String,

) {
    content()
    Text(
        text = text,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium,
        color = colorScheme.white
    )

}







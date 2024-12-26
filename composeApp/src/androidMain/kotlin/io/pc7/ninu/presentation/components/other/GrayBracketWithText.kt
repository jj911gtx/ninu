package io.pc7.ninu.presentation.components.other

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import core.presentation.theme.custom.colorScheme

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







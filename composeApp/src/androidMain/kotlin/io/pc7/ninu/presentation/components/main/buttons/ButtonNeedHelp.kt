package io.pc7.ninu.presentation.components.main.buttons

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import core.presentation.theme.custom.colorScheme


@Composable
fun ButtonNeedHelp(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {

    Text(
        text = "Need help?",
        style = MaterialTheme.typography.titleSmall,
        color = colorScheme.primaryLight,
        modifier = modifier
            .clickable(onClick = onClick)
    )

}
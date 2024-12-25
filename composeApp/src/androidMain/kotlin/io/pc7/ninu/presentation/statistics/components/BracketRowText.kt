package io.pc7.ninu.presentation.statistics.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import core.presentation.theme.custom.colorScheme

@Composable
fun BracketRowText(
    text: String,
    rightText: String
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(5.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = colorScheme.white,
            modifier = Modifier
                .padding(start = 10.dp)
                .weight(1f)
        )

        Text(
            text = rightText,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
            color = colorScheme.white,
            modifier = Modifier.padding(end = 5.dp)
        )
    }

}
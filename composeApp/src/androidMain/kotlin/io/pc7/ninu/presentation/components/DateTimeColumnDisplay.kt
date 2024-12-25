package io.pc7.ninu.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import core.presentation.theme.custom.colorScheme
import io.pc7.ninu.data.mapper.toStringSlash
import io.pc7.ninu.data.mapper.toTimeString
import kotlinx.datetime.LocalDateTime


@Composable
fun LocalDateTime.ColumnDisplay(
    modifier: Modifier = Modifier,

    dateColor: Color = colorScheme.white,
    timeColor: Color = colorScheme.primaryMedium
) {


    Column(
        horizontalAlignment = AbsoluteAlignment.Right
    ) {
        Text(text = date.toStringSlash(),
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Normal),
            color = dateColor,
            modifier = Modifier.padding(bottom = 1.dp)
        )
        Text(
            text = time.toTimeString(),
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Normal),
            color = timeColor
        )
    }

}
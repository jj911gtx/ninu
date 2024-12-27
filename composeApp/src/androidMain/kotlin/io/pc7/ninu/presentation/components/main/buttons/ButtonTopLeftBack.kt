package io.pc7.ninu.presentation.components.main.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.R


@Composable
fun ButtonTopLeftBack(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    contentColor: Color = colorScheme.white
) {

    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(bottom = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Icon(painter = painterResource(id = R.drawable.arrow_back_line), contentDescription = "Back",
            tint = contentColor
        )
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = contentColor
        )
    }

}
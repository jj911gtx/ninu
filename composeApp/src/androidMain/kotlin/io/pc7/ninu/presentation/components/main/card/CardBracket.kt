package io.pc7.ninu.presentation.components.main.card

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.presentation.theme.custom.colorScheme


@Composable
fun CardBracket(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    selectedContainerColor: Color? = null,
    unselectedContainerColor: Color? = null,
    cornerShape: Dp = 5.dp,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = if(selected) selectedContainerColor ?: colorScheme.primaryDark
                else unselectedContainerColor ?: colorScheme.primary,
            contentColor = colorScheme.white,
        ),
        shape = RoundedCornerShape(cornerShape),
        onClick = onClick,
        modifier = modifier,
    ) {
        content()
    }


}
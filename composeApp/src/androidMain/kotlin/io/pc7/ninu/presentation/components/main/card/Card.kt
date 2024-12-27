package io.pc7.ninu.presentation.components.main.card

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.pc7.ninu.presentation.theme.custom.colorScheme


@Composable
fun MyCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,

    padding: PaddingValues = PaddingValues(20.dp),
    content: @Composable () -> Unit,
){

    Card(
        modifier = modifier,
        onClick = onClick ?: {},
        interactionSource = remember {
            MutableInteractionSource()
        },
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.primary
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier.padding(padding)
        ) {
            content()
        }

    }
}
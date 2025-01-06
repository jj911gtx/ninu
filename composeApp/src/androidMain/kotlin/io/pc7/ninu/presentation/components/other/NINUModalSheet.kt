package io.pc7.ninu.presentation.components.other

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.presentation.components.main.buttons.DefaultButtonText
import io.pc7.ninu.presentation.components.main.card.CardBracket
import io.pc7.ninu.presentation.components.main.card.XCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NINUModalSheet(
    onDismiss: () -> Unit,
    onConfirm: (() -> Unit)?,
    content: @Composable ColumnScope.() -> Unit,
) {

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.Transparent
    ) {
//        var selectedItem by remember { mutableStateOf<String?>(null) }

        XCard(
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                content()

                onConfirm?.let {
                    DefaultButtonText(
                        onClick = onConfirm,
                        text = "Confirm",
                        modifier = Modifier
                            .padding(top = 15.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun NINUModalBottomSheetItem(
    text: String,
    selected: Boolean = false,
    onClick: () -> Unit,
) {
    CardBracket(
        onClick = onClick,
        selected = selected,
        selectedContainerColor = colorScheme.primaryMedium,
        unselectedContainerColor = colorScheme.primaryDarkest,
        cornerShape = 15.dp,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall,
            color = colorScheme.white,
            modifier = Modifier
                .padding(15.dp)
        )
    }
}
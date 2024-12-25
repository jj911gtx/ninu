package io.pc7.ninu.presentation.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import core.presentation.theme.custom.colorScheme
import io.pc7.ninu.presentation.components.main.ScrollableColumn
import io.pc7.ninu.presentation.components.main.buttons.DefaultButtonText
import io.pc7.ninu.presentation.components.main.card.XCard
import io.pc7.ninu.presentation.theme.NINUTheme


@Composable
fun PerfumeDescriptionScreen(
    title: String,
    navBack: () -> Unit,
) {

    ScrollableColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        XCard(
            onXClick = navBack
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 32.sp),
                    color = colorScheme.white
                )
            }
        }
        DefaultButtonText(
            onClick = navBack,
            text = "Go back"
        )
    }

}

@Composable
fun ParagraphTitleText(
    text: String,
) {
    Text(text = text,
        style = MaterialTheme.typography.titleLarge,
        color = colorScheme.white,
    )
}
@Composable
fun ParagraphText(
    text: String,
) {
    Text(text = text,
        style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp),
        color = colorScheme.white,
    )
}




@Preview
@Composable
private fun PerfumeDescriptionScreenPreview() {
    NINUTheme{
        PerfumeDescriptionScreen(
            title = "Spiritus",
            navBack = {}
        )
    }
}
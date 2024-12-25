package io.pc7.ninu.presentation.statistics.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import core.presentation.theme.custom.colorScheme
import io.pc7.ninu.domain.model.perfume.PerfumeUseData
import io.pc7.ninu.presentation.components.ColumnDisplay
import io.pc7.ninu.presentation.components.main.card.CardBracket
import io.pc7.ninu.presentation.theme.NINUTheme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime


@Composable
fun PerfumeUseData.PerfumeBracket(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    onSelect: () -> Unit,
) {
    CardBracket(
        onClick = onSelect,
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(10.dp)
        ) {
            Icon(painter = painterResource(id = io.pc7.ninu.R.drawable.icon_apple), contentDescription = null,
                tint = colorScheme.white,
                modifier = Modifier
                    .size(20.dp)
            )

            Text(text = name,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .weight(1f)
            )

            time.ColumnDisplay()

        }
    }
}


@Preview
@Composable
private fun PerfumeBracketPreview() {
    NINUTheme {
        PerfumeUseData(
            name = "Lemon", time = LocalDateTime(date = LocalDate(1999, 5,5), time = LocalTime(12, 11,35))
        ).PerfumeBracket(isSelected = false) {
            
        }
    }

}
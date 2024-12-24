package io.pc7.ninu.presentation.perfumeSelection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import core.presentation.theme.custom.colorScheme
import io.pc7.ninu.domain.model.perfumeSelection.PerfumeSelectionDisplay
import io.pc7.ninu.presentation.theme.NINUTheme


@Composable
fun PerfumeSelectionDisplay.Display(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,

) {


    Column(
        modifier = modifier
            .width(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(
                    brush = Brush.linearGradient(
                        colors = if (selected) listOf(
                            colorScheme.secondaryLight,
                            colorScheme.secondaryDark
                        ) else listOf(colorScheme.custom3D3D3D, colorScheme.primary)
                    )
                )
                .border(
                    1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            colorScheme.primaryDark,
                            colorScheme.primaryDarkest
                        )
                    ),
                    shape = CircleShape
                )
                .clickable(onClick = onClick)

        ){

            Icon(imageVector = Icons.Default.AccountBox, contentDescription = null,
                tint = colorScheme.white,
                modifier = Modifier
                    .padding(30.dp)
                    .aspectRatio(1f)
                    .fillMaxSize()

            )

        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge,
            color = colorScheme.white,
            textAlign = TextAlign.Center
        )

    }


}


@Preview
@Composable
private fun WhereToSelectOptionPreviewUnselected() {
    NINUTheme {
        PerfumeSelectionDisplay(
            name = "Drink with friends", id =1, iconLink = "",
            fragrances = listOf(
                PerfumeSelectionDisplay.Fragrance(
                    enough = true,
                    sku = 1,
                    percentage = 20
                )
            )
        ).Display(
            selected = false,
            onClick = {

            }
        )
    }
}
@Preview
@Composable
private fun WhereToSelectOptionPreviewSelected() {
    NINUTheme {
        PerfumeSelectionDisplay(
            name = "Drink with friends", id =1, iconLink = "",
            fragrances = listOf(
                PerfumeSelectionDisplay.Fragrance(
                    enough = true,
                    sku = 1,
                    percentage = 20
                )
            )
        ).Display(
            selected = false,
            onClick = {

            }
        )
    }
}




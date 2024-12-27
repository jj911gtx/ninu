package io.pc7.ninu.presentation.perfumeSelection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.R.drawable as d
import io.pc7.ninu.domain.model.perfumeSelection.PerfumeSelectionDisplay
import io.pc7.ninu.presentation.theme.NINUTheme


private val icons = listOf(
    d.nomad_life,
    d.glass,
    d.fly_to_the_moon,
    d.present,
    d.icon_car,
    d.ice_cream,
    d.stonks,
    d.sponka,
    d.fly_plane,
    d.peoples,
    d.camera,
    d.books,
    d.icon_favourite,
    d.hand_shake,
    d.heart,
    d.gala,
    d.spade,
    d.icon_party_popper,

    d.edit,
    d.server,
    d.smily,
    d.jing_jang,
    d.free_delete,
    d.heart_excited
)

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
            contentAlignment = Alignment.Center,
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
                .aspectRatio(1f)
                .size(150.dp)

                .clickable(onClick = onClick)



        ){

            Icon(painter = painterResource(id = icons[id-1]), contentDescription = null,
                tint = colorScheme.white,
                modifier = Modifier
                    .size(50.dp)


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




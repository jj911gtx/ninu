package io.pc7.ninu.presentation.components.other

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import io.pc7.ninu.presentation.theme.custom.colorScheme


@Composable
fun CircleItemSelect(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    iconId: Int,
    name: String,
) {


    Column(
        modifier = modifier
            .width(150.dp),
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
                .aspectRatio(1f)
                .size(200.dp)

                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center


        ){

            Icon(painter = painterResource(id = iconId), contentDescription = null,
                tint = colorScheme.white,
                modifier = Modifier
                    .size(45.dp)



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
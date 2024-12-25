package io.pc7.ninu.presentation.perfumeDetailsGeneral

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.presentation.theme.custom.colorScheme
import io.pc7.ninu.R
import io.pc7.ninu.presentation.components.main.ScrollableColumn
import io.pc7.ninu.presentation.components.main.buttons.DefaultButton
import io.pc7.ninu.presentation.components.main.card.XCard
import io.pc7.ninu.presentation.theme.NINUTheme


/**
 * @param icon -> id
 *
 *
 * */
@Composable
fun PerfumeDetailsGeneralScreen(
    navBack: () -> Unit,
    icon: Int,
//    number: Int,

    favouriteState: Boolean,
    onFavouriteChage: () -> Unit,
    onEditMix: () -> Unit,
//    onDelete: () -> Unit,

    buttonEnabled: Boolean,
    onButtonClick: () -> Unit,
    buttonText: String,

    content: @Composable ColumnScope.() -> Unit,
) {

    ScrollableColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        XCard(
            modifier = Modifier,
            onClick = {},
            onXClick = navBack
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        tint = colorScheme.white,
                        modifier = Modifier
                            .size(20.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
//                    Text(
//                        text = number.toString(),
//                        color = colorScheme.white,
//                        style = MaterialTheme.typography.headlineLarge
//                    )
                }
                content()
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {

            UnderCardRowFavourite(
                selected = favouriteState,
                onClick = onFavouriteChage

            )

            UnderCardRow(
                icon = R.drawable.icon_settings_2,
                text = "Edit mix",
                onClick = onEditMix
            )

//            UnderCardRow(
//                icon = R.drawable.icon_delete,
//                text = "Delete",
//                onClick = onDelete
//            )
        }

        Spacer(modifier = Modifier.weight(1f))

        DefaultButton(
            onClick = onButtonClick,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            enabled = buttonEnabled
        ) {
            Text(
                text = buttonText,
                style = MaterialTheme.typography.displayLarge
            )
        }

    }

}

@Composable
private fun UnderCardRow(
    modifier: Modifier = Modifier,
    icon: Int,
    text: String,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
    ) {
        Icon(
            painter = painterResource(id = icon), contentDescription = null,
            tint = colorScheme.secondaryDark2,
            modifier = Modifier
                .size(18.dp)
        )
        Text(text = text,
            color = colorScheme.secondaryDark2,
            style = MaterialTheme.typography.labelMedium.copy(fontSize = (MaterialTheme.typography.labelMedium.fontSize.value + 4f).sp)
        )
    }
    HorizontalDivider(color = colorScheme.primaryDarkest)
}


@Composable
private fun UnderCardRowFavourite(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
    ) {
        Icon(
            painter = painterResource(id = if(selected) R.drawable.icon_favourites_filled else R.drawable.icon_favourite), contentDescription = null,
            tint = colorScheme.secondaryDark2,
            modifier = Modifier
                .size(18.dp)
        )
        Text(text = "Add to favourites",
            color = colorScheme.secondaryDark2,
            style = MaterialTheme.typography.labelMedium.copy(fontSize = (MaterialTheme.typography.labelMedium.fontSize.value + 4f).sp)
        )
    }
    HorizontalDivider(color = colorScheme.custom3D3D3D)
}






@Composable
private fun Paragraph(
    modifier: Modifier = Modifier,
    title: String,
    text: String
) {

    Column(modifier = modifier) {
        Text(
            text = title,
            color = colorScheme.primaryLightest,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = text,
            color = colorScheme.white,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = (MaterialTheme.typography.bodyLarge.fontSize.value + 4f).sp)
        )
    }
}

@Composable
private fun IconDisplay(
    iconId: Int,
    onClick: () -> Unit,
    isSelected: Boolean
) {
    Icon(painter = painterResource(id = iconId), contentDescription = null,
        tint = if(isSelected) colorScheme.secondaryDark2 else colorScheme.white,
        modifier = Modifier
            .size(20.dp)
            .clickable(onClick = onClick)
    )

}



@Preview
@Composable
private fun PerfumeDetailsScreenPreview() {
    NINUTheme {
        PerfumeDetailsGeneralScreen(
            navBack = {},
            icon = R.drawable.icon_apple,
            favouriteState = false,
            onFavouriteChage = {},
            onEditMix = {},
            buttonEnabled = true,
            onButtonClick = {},
            buttonText = "Save",
            content = {}

        )

    }

}
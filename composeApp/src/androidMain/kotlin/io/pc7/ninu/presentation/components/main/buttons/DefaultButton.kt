package io.pc7.ninu.presentation.components.main.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import core.presentation.theme.custom.colorScheme
import io.pc7.ninu.presentation.theme.NINUTheme

enum class ButtonDisplayState{

}


@Composable
fun DefaultButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    displayDisabled: Boolean = false,
    content: @Composable () -> Unit,

) {

    Button(
        modifier = modifier
            .then(
                if (!displayDisabled && enabled) {
                    Modifier.clickable(onClick = onClick)
                } else Modifier
            )
            .wrapContentSize(),
        onClick = onClick,
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContentColor = colorScheme.secondaryLight2.copy(alpha = 0.7f),
            contentColor = colorScheme.white
        ),
        shape = RoundedCornerShape(20),
        enabled = enabled

    ) {

        Box(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = if (!displayDisabled && enabled) {
                            listOf(colorScheme.secondaryLight, colorScheme.secondaryDark)
                        } else {
                            listOf(
                                colorScheme.white.copy(alpha = 0.10f),
                                colorScheme.secondaryLight2.copy(alpha = 0.3f),
                            )

                        }

                    ),
                    shape = RoundedCornerShape(20)
                )
                .then(
                    if (displayDisabled || !enabled) {
                        Modifier.border(
                            width = 1.dp,
                            shape = RoundedCornerShape(20),
                            brush = Brush.verticalGradient(
                                listOf(
                                    colorScheme.secondaryLight3.copy(alpha = 0.100f),
                                    colorScheme.secondaryLight1.copy(alpha = 0.26f),
                                )
                            )
                        )
                    } else {
                        Modifier
                    }
                )

                .clip(RoundedCornerShape(20))
                .fillMaxWidth(0.8f)
            ,
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .padding(vertical = 15.dp, horizontal = 30.dp)
            ){
                content()
            }

        }
    }
}


@Composable
fun DefaultButtonText(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    isEnabled: Boolean = true,
    displayDisabled: Boolean = false,
) {

    DefaultButton(
        modifier = modifier,
        onClick = onClick,
        enabled = isEnabled,
        displayDisabled = displayDisabled
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.displayLarge,
            color = if(!displayDisabled && isEnabled) colorScheme.white else colorScheme.secondaryLight2.copy(alpha = 0.7f)
        )
    }

}


@Preview(widthDp = 300)
@Composable
private fun ButtonPreview() {
    NINUTheme {
        Box(
            modifier = Modifier.background(colorScheme.black)
        ) {
            DefaultButton(
                enabled = true,
                onClick = {},
                displayDisabled = true
            ) {
                Text(text = "hahahha")
            }
        }

    }
}
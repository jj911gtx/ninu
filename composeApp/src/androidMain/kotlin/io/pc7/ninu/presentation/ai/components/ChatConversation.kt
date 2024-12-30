package io.pc7.ninu.presentation.ai.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.pc7.ninu.R
import io.pc7.ninu.domain.model.ai.ChatConversation
import io.pc7.ninu.presentation.theme.NINUTheme
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.presentation.theme.custom.getPrimaryGradient


@Composable
fun ChatConversation.Display(
    columnScope: ColumnScope
) {
    when(this){
        is ChatConversation.AiText -> this.Display(columnScope)
        is ChatConversation.MyText -> this.Display(columnScope)
    }
    
}

@Composable
private fun ColumnScope.DefaultText(
    backgroundColor: Brush,
    fontSize: TextUnit,
    prefix: @Composable () -> Unit = {},
    alignment: Alignment.Horizontal,
    textAlign: TextAlign,
    text: String,
) {


    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .background(brush = backgroundColor)
            .align(alignment)
            .widthIn(max = with(LocalDensity.current) { (0.7f * LocalConfiguration.current.screenWidthDp).dp })
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(10.dp)
        ) {
            prefix()
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = fontSize),
                color = colorScheme.white,
                textAlign = textAlign
            )
        }


    }
    
}


@Composable
private fun ChatConversation.AiText.Display(
    columnScope: ColumnScope
) {

    columnScope.DefaultText(
        backgroundColor = Brush.horizontalGradient(colors = listOf(colorScheme.primary, colorScheme.primary)),
        fontSize = 16.sp,
        prefix = {
            Image(painter = painterResource(id = R.drawable.icon_ai), contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
            )
        },
        alignment = Alignment.Start,
        textAlign = TextAlign.Start,
        text = text

    )



}

@Composable
private fun ChatConversation.MyText.Display(
    columnScope: ColumnScope
) {

    columnScope.DefaultText(
        backgroundColor = getPrimaryGradient(),
        fontSize = 16.sp,
        alignment = Alignment.End,
        textAlign = TextAlign.End,
        text = text

    )


}

@Preview
@Composable
private fun DisplayMyTextPreview() {
    NINUTheme {
        Column {
            ChatConversation.MyText("What scent should I wear for a romantic dinner?").Display(
                this
            )
        }
    }
}

@Preview
@Composable
private fun DisplayAiTextPreview() {
    NINUTheme {
        Column {
            ChatConversation.AiText("What scent should I wear for a romantic dinner?").Display(
                this
            )
        }
    }
}
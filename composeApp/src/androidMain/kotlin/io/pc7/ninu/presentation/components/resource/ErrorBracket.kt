package io.pc7.ninu.presentation.components.resource

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.presentation.theme.NINUTheme


@Composable
fun ErrorBracket(
    modifier: Modifier = Modifier,
    text: String,
) {
    
    
    Box(modifier = modifier
//        .fillMaxWidth()
//        .border(
//            1.5.dp,
//            Color.Red,
//            shape = RoundedCornerShape(15.dp)
//        )
//        .padding(10.dp)
    ){
        Text(text = text,
            color = colorScheme.errorDark
        )

    }
    
}

@Preview
@Composable
private fun ErrorBracketPreview() {
    NINUTheme {
        ErrorBracket(
            text = "Hello it error"
        )
    }

}
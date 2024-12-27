package io.pc7.ninu.presentation.components.main.input.text

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import io.pc7.ninu.presentation.theme.custom.colorScheme
import io.pc7.ninu.domain.model.input.MyInput

@Composable
fun NINUinputFieldNoText(
    modifier: Modifier = Modifier,
    value: MyInput<String>,
    placeholderText: String,
    prefix: (@Composable () -> Unit)? = null,
    suffix: (@Composable () -> Unit)? = null,
    onClick: () -> Unit,
) {

    TextField(
        value = value.value,
        onValueChange = {},
        placeholder = {
            Text(
                text = placeholderText,
                color = value.getColor(),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        prefix = {
            prefix?.let {
                prefix()
                Spacer(modifier = Modifier.width(25.dp))
            }


        },
        suffix = suffix,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorScheme.black,
            unfocusedContainerColor = colorScheme.black,
            focusedTextColor = colorScheme.white,
            unfocusedTextColor = colorScheme.white,
            disabledTextColor = colorScheme.white,
            disabledContainerColor = colorScheme.black
        ),
        enabled = false,
        textStyle = MaterialTheme.typography.bodyMedium,
        modifier = modifier
            .clickable(onClick = onClick)
            .clip(RoundedCornerShape(10.dp))
            .border(
                width = 2.dp,
                color = value.getColor(),
                shape = RoundedCornerShape(10.dp)
            )
            .fillMaxWidth()


    )

}
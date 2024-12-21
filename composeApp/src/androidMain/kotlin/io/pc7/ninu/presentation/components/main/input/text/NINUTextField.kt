package io.pc7.ninu.presentation.components.main.input.text

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import core.presentation.theme.custom.colorScheme
import io.pc7.ninu.domain.model.input.MyInput
import io.pc7.ninu.presentation.components.resource.ErrorBracket


@Composable
fun  NINUTextField(
    modifier: Modifier = Modifier,
    value: MyInput<String>,
    onUpdate: (String) -> Unit,
    placeholderText: String,
    prefix: (@Composable () -> Unit)? = null,
    suffix: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    textHide: Boolean = false,
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Unspecified,
    keyboardActions: KeyboardActions = KeyboardActions(),
    imeAction: ImeAction = ImeAction.Unspecified,
    onUnfocus: () -> Unit = {},
) {


    var wasFocusedBefore by remember { mutableStateOf<Boolean?>(false) }
    Column {
        TextField(
            value = value.value,
            onValueChange = {
                onUpdate(it)
            },
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
            visualTransformation = if (!textHide) VisualTransformation.None else BiggerCircleVisualTransformation(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorScheme.black,
                unfocusedContainerColor = colorScheme.black,
                focusedTextColor = colorScheme.white,
                unfocusedTextColor = colorScheme.white,
                disabledTextColor = colorScheme.primaryLight,
                disabledContainerColor = colorScheme.black,
//            focusedIndicatorColor = colorScheme.white
            ),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            keyboardActions = keyboardActions,
            enabled = enabled,
            singleLine = singleLine,
            textStyle = MaterialTheme.typography.bodyMedium,
            modifier = modifier
                .onFocusChanged { focusState ->
                    if (focusState.hasFocus && wasFocusedBefore == false) {
                        wasFocusedBefore = true
                    } else if (wasFocusedBefore == true && !focusState.hasFocus) {
                        wasFocusedBefore = null
                        onUnfocus()
                    }
                }
                .clickable {
                    println("CLICKED")
                }
                .clip(RoundedCornerShape(10.dp))
                .border(
                    width = 2.dp,
                    color = value.getColor(),
                    shape = RoundedCornerShape(10.dp)
                )
                .fillMaxWidth()

        )

        if(value.displayErrors){
            value.errors.DisplayErrors()
        }

    }

    
}
@Composable
private fun List<String>.DisplayErrors(){

    this.forEach {
        ErrorBracket(text = it)
    }

}

fun MyInput<String>.getColor(): Color{
    return if(this.errors.isNotEmpty() && this.displayErrors){
        colorScheme.errorDark
    }else{
        if(value.isNotEmpty()) colorScheme.white else colorScheme.primaryLight
    }
}


class BiggerCircleVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val transformedText = AnnotatedString.Builder().apply {
            repeat(text.length) {
                append("\u2B24")
            }
        }.toAnnotatedString()

        return TransformedText(
            transformedText,
            OffsetMapping.Identity
        )
    }
}
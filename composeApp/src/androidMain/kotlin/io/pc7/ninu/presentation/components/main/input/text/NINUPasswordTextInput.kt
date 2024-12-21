package io.pc7.ninu.presentation.components.main.input.text

import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import io.pc7.ninu.R
import io.pc7.ninu.domain.model.input.MyInput

@Composable
fun NINUPasswordTextInput(
    modifier: Modifier = Modifier,
    password: MyInput<String>,
    onUpdate: (String) -> Unit,
    placeholder: String,
    onUnfocus: () -> Unit,
    keyboardActions: KeyboardActions,
    imeAction: ImeAction,
) {

    var passwordVisible by remember { mutableStateOf(false) }
    NINUTextField(
        value = password,
        onUpdate = onUpdate,
        placeholderText = placeholder,
        textHide = !passwordVisible,
        prefix = {
            Icon(
                painter = painterResource(id = R.drawable.icon_password),
                contentDescription = null,
            )
        },
        keyboardType = KeyboardType.Password,
        keyboardActions = keyboardActions,
        imeAction = imeAction,
        suffix = {
            if (passwordVisible) {
                Icon(
                    painterResource(id = R.drawable.icon_visible),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable{passwordVisible = false}
                )
            } else {
                Icon(
                    painterResource(id = R.drawable.icon_hide),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable{passwordVisible = true}
                )
            }
        },
        onUnfocus = onUnfocus,
        modifier = modifier
    )

}
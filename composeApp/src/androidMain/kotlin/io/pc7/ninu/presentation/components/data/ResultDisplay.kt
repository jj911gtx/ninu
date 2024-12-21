package io.pc7.ninu.presentation.components.data

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.pc7.ninu.domain.model.ResultMy
import io.pc7.ninu.data.network.error.Error

@Composable
fun <D, E : Error> ResultMy<D, E>.ResultMyDisplay(
    modifier: Modifier = Modifier,
    successContent: @Composable (data: D) -> Unit,
) {
    when (this) {
        is ResultMy.Success -> successContent(data)
        is ResultMy.Error -> {
            error
        }
    }
}
